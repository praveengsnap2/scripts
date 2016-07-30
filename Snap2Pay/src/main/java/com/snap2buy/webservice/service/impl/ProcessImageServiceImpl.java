package com.snap2buy.webservice.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snap2buy.webservice.dao.*;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
import com.snap2buy.webservice.service.ProcessImageService;
import com.snap2buy.webservice.util.ConverterUtil;
import com.snap2buy.webservice.util.ShellUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_PROCESS_IMAGE_SERVICE)
@Scope("prototype")
public class ProcessImageServiceImpl implements ProcessImageService {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    @Qualifier(BeanMapper.BEAN_IMAGE_STORE_DAO)
    private ProcessImageDao processImageDao;

    @Autowired
    @Qualifier(BeanMapper.BEAN_STORE_MASTER_DAO)
    private StoreMasterDao storeMasterDao;

    @Autowired
    @Qualifier(BeanMapper.BEAN_SHELF_ANALYSIS_DAO)
    private ShelfAnalysisDao shelfAnalysisDao;

    @Autowired
    @Qualifier(BeanMapper.BEAN_PRODUCT_MASTER_DAO)
    private ProductMasterDao productMasterDao;

    @Autowired
    @Qualifier(BeanMapper.BEAN_META_SERVICE_DAO)
    private MetaServiceDao metaServiceDao;
    @Override
    public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts storeImageDetails----------------\n");

        if (inputObject.getStoreId().equalsIgnoreCase("-9")){
            inputObject.setStoreId(storeMasterDao.getStoreId(inputObject.getLongitude(), inputObject.getLatitude()));
        }
        LOGGER.info("--------------storeId=" + inputObject.getStoreId() + "-----------------\n");

        if ((inputObject.getUserId().isEmpty())
                || (inputObject.getUserId().equalsIgnoreCase("") )
                || (inputObject.getUserId().equalsIgnoreCase("-9"))){
            inputObject.setUserId("app-web");
        }

        ImageStore imageStore =  new ImageStore(inputObject.getImageUUID(),
                inputObject.getImageFilePath(),
                inputObject.getCategoryId(),
                inputObject.getLatitude(),
                inputObject.getLongitude(),
                inputObject.getTimeStamp(),
                inputObject.getStoreId(),
                inputObject.getHostId(),
                inputObject.getVisitDate(),
                "new",
                "new",
                inputObject.getOrigWidth(),
                inputObject.getOrigHeight(),
                inputObject.getNewWidth(),
                inputObject.getNewHeight(),
                inputObject.getThumbnailPath(),
                inputObject.getUserId(),
                inputObject.getCustomerCode(),
                inputObject.getCustomerProjectId(),
                inputObject.getTaskId(),
                inputObject.getAgentId(),
                inputObject.getTimeStamp(),
                inputObject.getImageHashScore(),
                inputObject.getImageRotation());



        if (inputObject.getSync().equals("true")) {

            processImageDao.insert(imageStore);
            LOGGER.info("---------------ProcessImageServiceImpl start stored image details sync----------------\n");

            String retailerChainCode = storeMasterDao.getRetailerChainCode(imageStore.getStoreId());
            LOGGER.info("--------------retailerChainCode=" + retailerChainCode + "-----------------\n");

            List<LinkedHashMap<String, String>> project = metaServiceDao.getProjectDetail(imageStore.getCustomerProjectId(), imageStore.getCustomerCode());
            String projectTypeId = project.get(0).get("projectTypeId");
            LOGGER.info("--------------runImageAnalysis::projectTypeId=" + projectTypeId + "-----------------\n");


            List<ImageAnalysis> imageAnalysisList = invokeImageAnalysis(imageStore, retailerChainCode, projectTypeId);
            List<ImageAnalysis> result =null;
            if ( null != imageAnalysisList ) {
            
            	LOGGER.info("--------------imageAnalysisList size =" + imageAnalysisList.size() + "-----------------\n");

            	processImageDao.storeImageAnalysis(imageAnalysisList, imageStore);
            	LOGGER.info("---------------ProcessImageServiceImpl start stored imageAnalysis sync----------------\n");
            	boolean isThumbNailProcessingSuccess = true;
                try {
					ShellUtil.createThumbnail(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl create thumbnail done----------------\n");

					processImageDao.updateOrientationDetails(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl all orientation details update done----------------\n");
				} catch (Exception e) {
					LOGGER.error("---------------ProcessImageServiceImpl - createThumbnail and Update Orientation failed----------------\n");
					LOGGER.error("---------------ProcessImageServiceImpl - Marking imageStatus as puased----------------\n");
					processImageDao.updateImageAnalysisStatus("paused", imageStore.getImageUUID());
					isThumbNailProcessingSuccess = false;
				}
                
                if (isThumbNailProcessingSuccess) {
                	 processImageDao.updateImageAnalysisStatus("done", imageStore.getImageUUID());
                 	LOGGER.info("---------------ProcessImageServiceImpl start updated status to done sync----------------\n");
                }

            	//waste call just to get same format for all api, otherwise have to invoke new call to do the join
            	result = processImageDao.getImageAnalysis( imageStore.getImageUUID());

            	//new call to generate aggs ignore the result generated by api
            	processImageDao.generateAggs(imageStore.getCustomerCode(),imageStore.getCustomerProjectId(),imageStore.getStoreId());
                LOGGER.info("---------------ProcessImageServiceImpl generateAggs done ----------------\n");

            } else {
            	boolean isThumbNailProcessingSuccess = true;
                try {
					ShellUtil.createThumbnail(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl create thumbnail done----------------\n");

					processImageDao.updateOrientationDetails(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl all orientation details update done----------------\n");
				} catch (Exception e) {
					LOGGER.error("---------------ProcessImageServiceImpl - createThumbnail and Update Orientation failed----------------\n");
					LOGGER.error("---------------ProcessImageServiceImpl - Marking imageStatus as puased----------------\n");
					processImageDao.updateImageAnalysisStatus("paused", imageStore.getImageUUID());
					isThumbNailProcessingSuccess = false;
				}
                if (isThumbNailProcessingSuccess) {
                    processImageDao.updateImageAnalysisStatus("cron1", imageStore.getImageUUID()); //first retry, via cron.
                }

                //waste call just to get same format for all api, otherwise have to invoke new call to do the join
            	result = processImageDao.getImageAnalysis( imageStore.getImageUUID());

            }

            LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails   sync----------------\n");
            return ConverterUtil.convertImageAnalysisObjectToMap(result);
        } else {

            imageStore.setImageStatus("cron");
            processImageDao.insert(imageStore);
            LOGGER.info("---------------ProcessImageServiceImpl start stored image details not sync----------------\n");

            LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails not sync----------------\n");
            List<java.util.LinkedHashMap<String, String>> result = new ArrayList<java.util.LinkedHashMap<String, String>>();
            return result;
        }
    }

    @Override
    public LinkedHashMap<String, String> getJob(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getJob----------------\n");


        LinkedHashMap<String, String> unProcessedJob = new LinkedHashMap<String, String>();
        String shelfStatus = "new";
        Integer newJobCount = processImageDao.getJobCount(shelfStatus);
        if (newJobCount > 1) {

            ImageStore imageStore = processImageDao.getImageByStatus(shelfStatus);
            shelfStatus = "processing";

            LOGGER.info("got this job imageStore" + imageStore.toString());

            processImageDao.updateStatusAndHost(inputObject.getHostId(), shelfStatus, imageStore.getImageUUID());
            String storeId = storeMasterDao.getStoreId(imageStore.getLongitude(), imageStore.getLatitude());
            processImageDao.updateStoreId(storeId, imageStore.getImageUUID());
            String retailerChainCode = storeMasterDao.getRetailerChainCode(storeId);

            Integer remainingJob = newJobCount - 1;
            unProcessedJob.put("imageUUID", imageStore.getImageUUID());
            unProcessedJob.put("categoryId", imageStore.getCategoryId());
            unProcessedJob.put("imageFilePath", imageStore.getImageFilePath());
            unProcessedJob.put("latitude", imageStore.getLatitude());
            unProcessedJob.put("longitude", imageStore.getLongitude());
            unProcessedJob.put("storeId", storeId);
            unProcessedJob.put("timeStamp", imageStore.getTimeStamp());
            unProcessedJob.put("userId", imageStore.getUserId());
            unProcessedJob.put("dateId", imageStore.getDateId());
            unProcessedJob.put("retailerChainCode", retailerChainCode);
            unProcessedJob.put("remainingJob", remainingJob.toString());
        } else {
            unProcessedJob.put("remainingJob", newJobCount.toString());
        }
        LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails----------------\n");

        return unProcessedJob;
    }


    public List<ImageAnalysis> invokeImageAnalysis(ImageStore imageStore, String retailer, String projectTypeId) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts invokeImageAnalysis----------------\n");
        LOGGER.info("---------------ProcessImageServiceImpl imageFilePath=" + imageStore.getImageFilePath() + ", category=" + imageStore.getCategoryId() + ", uuid=" + imageStore.getImageUUID() + ", retailer=" + retailer + ", store=" + imageStore.getStoreId() + "userId= "+imageStore.getUserId()+"projectTypeId"+projectTypeId+"----------------\n");
        //initializing to null. If it remains null at the end of exeuction, it is considered as a failure.
        //If it is empty, it is considered as a successful analysis which resulted in no SKUs.
        //If it has one ore more elements, it is considered as a successful analysis which resulted in identification of one ore more SKUs.
        List<ImageAnalysis> imageAnalysisList = null; 
        ShellUtil shellUtil = new ShellUtil();
        String result = shellUtil.executeCommand(imageStore.getImageFilePath(), imageStore.getCategoryId(), imageStore.getImageUUID(), retailer, imageStore.getStoreId(), imageStore.getUserId(), projectTypeId);
        if (result.contains("{")) {
            LOGGER.info("---------------ProcessImageServiceImpl  result=" + result + "----------------");
            result = result.replaceAll("\n", "").replaceAll("\n", "");
//            String[] trimStart = result.split("Exited with error code 1");
//            String[] trimLast = trimStart[1].split("Stderr");
//            String jsonString = trimLast[0];
            String jsonString = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
            LOGGER.info("---------------jsonString=" + jsonString + "---------------");
            imageAnalysisList = new ArrayList<ImageAnalysis>();
            JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
            JsonArray skusArray = obj.get("skus").getAsJsonArray();
            for (JsonElement skus : skusArray) {
                ImageAnalysis imageAnalysis = new ImageAnalysis();
                JsonObject upcEntry = skus.getAsJsonObject();
                java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
                imageAnalysis.setUpc(upcEntry.get("UPC").getAsString().trim());
                imageAnalysis.setLeftTopX(upcEntry.get("LEFT_TOP_X").getAsString().trim());
                imageAnalysis.setLeftTopY(upcEntry.get("LEFT_TOP_Y").getAsString().trim());
                imageAnalysis.setWidth(upcEntry.get("Width").getAsString().trim());
                imageAnalysis.setHeight(upcEntry.get("Height").getAsString().trim());
                imageAnalysis.setUpcConfidence(upcEntry.get("UPC_Confidence").getAsString().trim());
                imageAnalysis.setAlternateUpc(upcEntry.get("Alt_UPC").getAsString().trim());
                imageAnalysis.setAlternateUpcConfidence(upcEntry.get("Alt_UPC_Confidence").getAsString().trim());
                imageAnalysis.setPromotion(upcEntry.get("Promotion").getAsString().trim());
                imageAnalysis.setPrice(upcEntry.get("Price").getAsString().trim());
                imageAnalysis.setPriceLabel(upcEntry.get("Price_Label").getAsString().trim());

                imageAnalysisList.add(imageAnalysis);
            }
            String imageRotation = obj.get("imageRotation").getAsString();
            String imageHashScore = obj.get("imageHashScore").getAsJsonArray().get(0).getAsString();
            imageStore.setImageRotation(imageRotation);
            imageStore.setImageHashScore(imageHashScore);
            LOGGER.info("---------------ProcessImageServiceImpl Ends invokeImageAnalysis----------------\n");
            return imageAnalysisList;
        } else {
            return imageAnalysisList;
        }
    }

    @Override
    public List<LinkedHashMap<String, String>> runImageAnalysis(String imageUUID) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts runImageAnalysis----------------\n");

        ImageStore imageStore = processImageDao.findByImageUUId(imageUUID);
        if (imageStore == null) {
            List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            map.put("message", "no image found for this imageUUId");
            result.add(map);
            return result;
        } else if (imageStore.getImageStatus().equalsIgnoreCase("done")) {
            return getImageAnalysis(imageStore.getImageUUID());

        } else {
            LOGGER.info("--------------runImageAnalysis::imageStore=" + imageStore + "-----------------\n");

            String retailerChainCode = storeMasterDao.getRetailerChainCode(imageStore.getStoreId());

            LOGGER.info("--------------runImageAnalysis::retailerChainCode=" + retailerChainCode + "-----------------\n");

            List<LinkedHashMap<String, String>> project = metaServiceDao.getProjectDetail(imageStore.getCustomerProjectId(), imageStore.getCustomerCode());
            String projectTypeId = project.get(0).get("projectTypeId");
            LOGGER.info("--------------runImageAnalysis::projectTypeId=" + projectTypeId + "-----------------\n");
            List<ImageAnalysis> imageAnalysisList = invokeImageAnalysis(imageStore, retailerChainCode,projectTypeId);

            LOGGER.info("--------------runImageAnalysis::imageAnalysisList=" + imageAnalysisList + "-----------------\n");
            
            if ( null != imageAnalysisList ) {

            	LOGGER.info("--------------runImageAnalysis::storeImageAnalysis started-----------------\n");

            	processImageDao.storeImageAnalysis(imageAnalysisList, imageStore);
            	LOGGER.info("--------------runImageAnalysis::storeImageAnalysis done-----------------\n");
            	boolean isThumbNailProcessingSuccess = true;
                try {
					ShellUtil.createThumbnail(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl create thumbnail done----------------\n");

					processImageDao.updateOrientationDetails(imageStore);
					LOGGER.info("---------------ProcessImageServiceImpl all orientation details update done----------------\n");
				} catch (Exception e) {
					LOGGER.error("---------------ProcessImageServiceImpl - createThumbNail and Update Orientation failed----------------\n");
					LOGGER.error("---------------ProcessImageServiceImpl - Marking imageStatus as paused----------------\n");
					processImageDao.updateImageAnalysisStatus("paused", imageStore.getImageUUID());
					isThumbNailProcessingSuccess = false;
				}

                if ( isThumbNailProcessingSuccess ) {
                	String imageStatus = "done";
                	processImageDao.updateImageAnalysisStatus(imageStatus, imageUUID);
                	LOGGER.info("--------------runImageAnalysis::updateStatus=" + imageStatus + "-----------------\n");
                }

            } else {    
            	String imageStatus = "error"; //if analysis failed after retries, set the status to error to stop retry.
            	if ( imageStore.getImageStatus().equalsIgnoreCase("cron") ) { //logic to stop infinite retries, only 2 retries after first attempt failed.
            		imageStatus = "cron1";            		
            	} else if ( imageStore.getImageStatus().equalsIgnoreCase("cron1")) {
            		imageStatus = "cron2";            		
            	}

            	processImageDao.updateImageAnalysisStatus(imageStatus, imageUUID);
            	LOGGER.info("--------------runImageAnalysis::updateStatus=" + imageStatus + "-----------------\n");
            	imageAnalysisList = new ArrayList<ImageAnalysis>(); //keep the list empty for response.
            	
            }
            
            LOGGER.info("---------------ProcessImageServiceImpl Ends runImageAnalysis ----------------\n");
            
            return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);
        }
    }

    @Override
    public List<LinkedHashMap<String, String>> processNextImage() {

        LOGGER.info("---------------ProcessImageServiceImpl Starts processNextImage----------------\n");
        ImageStore imageStore = processImageDao.getNextImageDetails();
        List<LinkedHashMap<String, String>> imageAnalysisList=new ArrayList<LinkedHashMap<String, String>>();
        if (imageStore != null) {
            LOGGER.info("---------------ProcessImageServiceImpl Starts processNextImage imageUUID = " + imageStore.getImageUUID() + "----------------\n");
            imageAnalysisList = getImageAnalysis(imageStore.getImageUUID());
            //call to generate aggs ignore the resuts returned by the api
            processImageDao.generateAggs(imageStore.getCustomerCode(),imageStore.getCustomerProjectId(),imageStore.getStoreId());
        }else{
            LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
            temp.put("message", "no more job left to process");
            imageAnalysisList.add(temp);
        }

        return imageAnalysisList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getImageAnalysis(String imageUUID) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getImageAnalysis----------------\n");
        String status = processImageDao.getImageAnalysisStatus(imageUUID);
        if (status.equalsIgnoreCase("done")) {
            List<ImageAnalysis> imageAnalysisList = processImageDao.getImageAnalysis(imageUUID);
            LOGGER.info("---------------ProcessImageServiceImpl Ends getImageAnalysis ----------------\n");
            return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);
        } else if (status.equalsIgnoreCase("error") && status.equalsIgnoreCase("paused")) { // if status in error or paused, all retries are completed. No more processing for this imageUUID.
            LOGGER.error("--------------ProcessImageServiceImpl -- imageUUID " + imageUUID + " -- has exhausted allowed retries.--------\n");
            //dummy call for output format
            List<ImageAnalysis> imageAnalysisList = processImageDao.getImageAnalysis(imageUUID);
            LOGGER.info("---------------ProcessImageServiceImpl Ends getImageAnalysis ----------------\n");
            return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);
        } else {
        	List<LinkedHashMap<String, String>> imageAnalysisList = runImageAnalysis(imageUUID);
            LOGGER.info("---------------ProcessImageServiceImpl Ends getImageAnalysis ----------------\n");
            return imageAnalysisList;
        }
    }


    @Override
    public List<LinkedHashMap<String, String>> getStoreOptions() {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getStoreOptions----------------\n");

        List<LinkedHashMap<String, String>> storeMasterList = storeMasterDao.getStoreOptions();

        LOGGER.info("---------------ProcessImageServiceImpl Ends getStoreOptions ----------------\n");
        return storeMasterList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getImages(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getImages----------------\n");

        List<LinkedHashMap<String, String>> imageStoreList = processImageDao.getImages(inputObject.getStoreId(), inputObject.getVisitDate());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getImages ----------------\n");
        return imageStoreList;
    }
    
    @Override
    public List<LinkedHashMap<String, String>> getProjectStoreImages(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectStoreImages----------------\n");

        List<LinkedHashMap<String, String>> imageStoreList = processImageDao.getProjectStoreImages(inputObject.getCustomerCode(),
        		inputObject.getCustomerProjectId(),inputObject.getStoreId());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectStoreImages ----------------\n");
        return imageStoreList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getStores(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getStores----------------\n");

        List<LinkedHashMap<String, String>> storeMasterList = storeMasterDao.getStores(inputObject.getRetailerChainCode(), inputObject.getStateCode(), inputObject.getCity());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getStores ----------------\n");
        return storeMasterList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getImageMetaData(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getImageMetaData----------------\n");
        List<ImageStore> imageStoreList = new ArrayList<ImageStore>();
        ImageStore imageStore = processImageDao.findByImageUUId(inputObject.getImageUUID());
        imageStoreList.add(imageStore);
        LOGGER.info("---------------ProcessImageServiceImpl Ends getImageMetaData ----------------\n");
        return ConverterUtil.convertImageStoreObjectToMap(imageStoreList);
    }

    @Override
    public List<LinkedHashMap<String, String>> doDistributionCheck(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts doDistributionCheck----------------\n");
        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        String imageStatus = processImageDao.getImageAnalysisStatus(inputObject.getPrevImageUUID());
        if (imageStatus.equalsIgnoreCase("done")) {
            map = processImageDao.getFacing(inputObject.getImageUUID());
        } else {
            getImageAnalysis(inputObject.getPrevImageUUID());
            map = processImageDao.getFacing(inputObject.getImageUUID());
        }

        Set<String> keySet = map.keySet();
        List<UpcFacingDetail> listDistributionList = productMasterDao.getUpcForList(inputObject.getListId());

        for (UpcFacingDetail unit : listDistributionList) {
            LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
            if (keySet.contains(unit.getUpc())) {
                UpcFacingDetail upcFacingDetail1 = (UpcFacingDetail) map.get(unit.getUpc());
                entry.put("upc", unit.getUpc());
                entry.put("productLongName", unit.getProductLongName());
                entry.put("productShortName", unit.getProductShortName());
                entry.put("brandName", unit.getBrandName());
                entry.put("facing", upcFacingDetail1.getCount());
                entry.put("osa", "1");
            } else {
                entry.put("upc", unit.getUpc());
                entry.put("productLongName", unit.getProductLongName());
                entry.put("productShortName", unit.getProductShortName());
                entry.put("brandName", unit.getBrandName());
                entry.put("facing", "0");
                entry.put("osa", "0");
            }
            result.add(entry);
        }

        LOGGER.info("---------------ProcessImageServiceImpl Ends doDistributionCheck ----------------\n");
        return result;
    }

    @Override
    public List<LinkedHashMap<String, String>> doBeforeAfterCheck(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts doBeforeAfterCheck----------------\n");
        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
        LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
        String prevImageStatus = processImageDao.getImageAnalysisStatus(inputObject.getPrevImageUUID());
        if (prevImageStatus.equalsIgnoreCase("done")) {
            map1 = processImageDao.getFacing(inputObject.getPrevImageUUID());
        } else {
            getImageAnalysis(inputObject.getPrevImageUUID());
            map1 = processImageDao.getFacing(inputObject.getPrevImageUUID());
        }

        String imageStatus = processImageDao.getImageAnalysisStatus(inputObject.getImageUUID());
        if (imageStatus.equalsIgnoreCase("done")) {
            map2 = processImageDao.getFacing(inputObject.getImageUUID());
        } else {
            getImageAnalysis(inputObject.getImageUUID());
            map2 = processImageDao.getFacing(inputObject.getImageUUID());
        }


        Set<String> keySet1 = map1.keySet();
        Set<String> keySet2 = map2.keySet();

        Set<String> union = new HashSet<String>(keySet1);
        union.addAll(keySet2);

        Set<String> intersection = new HashSet<String>(keySet1);
        intersection.retainAll(keySet2);

        for (String unit : union) {
            LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
            if (intersection.contains(unit)) {
                UpcFacingDetail upcFacingDetail1 = (UpcFacingDetail) map1.get(unit);
                LOGGER.info("---------------ProcessImageServiceImpl " + upcFacingDetail1.toString() + "----------------\n");
                UpcFacingDetail upcFacingDetail2 = (UpcFacingDetail) map2.get(unit);
                LOGGER.info("---------------ProcessImageServiceImpl " + upcFacingDetail2.toString() + " ----------------\n");
                entry.put("upc", unit);
                entry.put("productLongName", upcFacingDetail1.getProductLongName());
                entry.put("productShortName", upcFacingDetail1.getProductShortName());
                entry.put("brandName", upcFacingDetail1.getBrandName());
                entry.put("before_facing", upcFacingDetail1.getCount());
                entry.put("before_osa", "1");
                entry.put("after_facing", upcFacingDetail2.getCount());
                entry.put("after_osa", "1");
            } else if (keySet1.contains(unit)) {
                UpcFacingDetail upcFacingDetail1 = (UpcFacingDetail) map1.get(unit);
                LOGGER.info("---------------ProcessImageServiceImpl " + upcFacingDetail1.toString() + "----------------\n");

                entry.put("upc", unit);
                entry.put("productLongName", upcFacingDetail1.getProductLongName());
                entry.put("productShortName", upcFacingDetail1.getProductShortName());
                entry.put("brandName", upcFacingDetail1.getBrandName());
                entry.put("before_facing", upcFacingDetail1.getCount());
                entry.put("before_osa", "1");
                entry.put("after_facing", "0");
                entry.put("after_osa", "0");
            } else if (keySet2.contains(unit)) {
                UpcFacingDetail upcFacingDetail2 = (UpcFacingDetail) map2.get(unit);
                LOGGER.info("---------------ProcessImageServiceImpl " + upcFacingDetail2.toString() + " ----------------\n");
                entry.put("upc", unit);
                entry.put("productLongName", upcFacingDetail2.getProductLongName());
                entry.put("productShortName", upcFacingDetail2.getProductShortName());
                entry.put("brandName", upcFacingDetail2.getBrandName());
                entry.put("before_facing", "0");
                entry.put("before_osa", "0");
                entry.put("after_facing", upcFacingDetail2.getCount());
                entry.put("after_osa", "1");
            }
            result.add(entry);
        }

        LOGGER.info("---------------ProcessImageServiceImpl Ends doBeforeAfterCheck ----------------\n");
        return result;
    }

    @Override
    public List<LinkedHashMap<String, String>> doShareOfShelfAnalysis(InputObject inputObject) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts doShareOfShelfAnalysis::"+inputObject.getImageUUIDCsvString()+"----------------\n");

        for (String imageUUID: inputObject.getImageUUIDCsvString().split(",")){
            String status = processImageDao.getImageAnalysisStatus(imageUUID);
            if (!status.equalsIgnoreCase("done")) {
                runImageAnalysis(imageUUID);
            }
        }

        List<LinkedHashMap<String, String>> imageAnalysisList = processImageDao.doShareOfShelfAnalysis(inputObject.getImageUUIDCsvString());

        LOGGER.info("---------------ProcessImageServiceImpl Ends doShareOfShelfAnalysis ----------------\n");

        return imageAnalysisList;


    }

    @Override
    public File doShareOfShelfAnalysisCsv(InputObject inputObject, String tempFilePath) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts doShareOfShelfAnalysisCsv----------------\n");

        List<LinkedHashMap<String, String>> imageAnalysisList = processImageDao.doShareOfShelfAnalysis(inputObject.getImageUUIDCsvString());


        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(tempFilePath);
            String input="Input:"+"\n";
            String info1="retailer"+","+inputObject.getRetailer()+"\n";
            String info2="state"+","+inputObject.getState()+"\n";
            String info3="city"+","+inputObject.getCity()+"\n";
            String info4="street"+","+inputObject.getStreet()+"\n";
            String line = " "+","+" "+"\n";
            Long totalCount = 0L;

            LinkedHashMap<String, Long> summeryMetric=new LinkedHashMap<String, Long>();
            for (LinkedHashMap<String, String> row : imageAnalysisList) {
                String brand = row.get("brandName");
                Long updateCount = 0L;
                if (summeryMetric.keySet().contains(brand)) {
                    Long brandCount = summeryMetric.get(brand);
                    updateCount = brandCount + Long.valueOf(row.get("facing"));
                    totalCount = totalCount + Long.valueOf(row.get("facing"));
                } else {
                    updateCount = Long.valueOf(row.get("facing"));
                    totalCount = totalCount + Long.valueOf(row.get("facing"));
                }
                summeryMetric.put(brand, updateCount);
            }
            String summary="Summary:"+"\n";
            String info6="BrandName"+","+"Percentage"+"\n";
            StringBuilder info7 = new StringBuilder();
            for(String x:summeryMetric.keySet()){
                Double percentage =(summeryMetric.get(x).doubleValue() / totalCount) * 100;
                info7.append(x+ "," + String.format("%.2f", percentage)+"\n");
            }

            String meta =input+info1+info2+info3+info4+line+summary+info6+info7.toString()+line;
            fileWriter.append(meta);

            String headers="UPC,Facing,Product Short Name,Product Long Name,Brand Name"+"\n";
            fileWriter.append(headers);

            for (LinkedHashMap<String, String> row : imageAnalysisList) {
                StringBuilder shareOfShelfAnalysisRow = new StringBuilder();
                shareOfShelfAnalysisRow.append(row.get("upc") + ",");
                shareOfShelfAnalysisRow.append(row.get("facing") + ",");
                shareOfShelfAnalysisRow.append(row.get("productShortName") + ",");
                shareOfShelfAnalysisRow.append(row.get("productLongName") + ",");
                shareOfShelfAnalysisRow.append(row.get("brandName"));

                fileWriter.append(shareOfShelfAnalysisRow.toString() + "\n");
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("---------------ProcessImageServiceImpl Ends doShareOfShelfAnalysisCsv----------------\n");
        File f = new File(tempFilePath);
        return f;
    }

    @Override
    public void updateLatLong(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts updateLatLong----------------\n");

        processImageDao.updateLatLong(inputObject.getImageUUID(), inputObject.getLatitude(),inputObject.getLongitude());

        LOGGER.info("---------------ProcessImageServiceImpl Ends updateLatLong ----------------\n");
    }

    @Override
    public List<LinkedHashMap<String, String>> generateAggs(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts generateAggs----------------\n");

        List<LinkedHashMap<String, String>> result = processImageDao.generateAggs(inputObject.getCustomerCode(),inputObject.getCustomerProjectId(),inputObject.getStoreId());

        LOGGER.info("---------------ProcessImageServiceImpl Ends generateAggs----------------\n");

        return result;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectStoreResults(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectStoreResults----------------\n");

        List<LinkedHashMap<String, String>>  result = processImageDao.getProjectStoreResults(inputObject.getCustomerCode(),inputObject.getCustomerProjectId(),inputObject.getStoreId());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectStoreResults----------------\n");

        return result;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectTopStores(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectTopStores----------------\n");

        List<LinkedHashMap<String, String>>  result = processImageDao.getProjectTopStores(inputObject.getCustomerCode(), inputObject.getCustomerProjectId(), inputObject.getLimit());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectTopStores----------------\n");

        return result;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectBottomStores(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectBottomStores----------------\n");

        List<LinkedHashMap<String, String>>  result = processImageDao.getProjectBottomStores(inputObject.getCustomerCode(), inputObject.getCustomerProjectId(), inputObject.getLimit());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectBottomStores----------------\n");

        return result;
    }

	@Override
	public List<StoreWithImages> getProjectStoresWithNoUPCs(InputObject inputObject) {
		LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectStoresWithNoUPCs----------------\n");

        List<StoreWithImages>  result = processImageDao.getProjectStoresWithNoUPCs(inputObject.getCustomerCode(), inputObject.getCustomerProjectId());
        
        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectStoresWithNoUPCs----------------\n");
        
        return result;
	}

	@Override
	public List<StoreWithImages> getProjectAllStoreImages(InputObject inputObject) {
		LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectAllStoreImages----------------\n");

        List<StoreWithImages>  result = processImageDao.getProjectAllStoreImages(inputObject.getCustomerCode(), inputObject.getCustomerProjectId());
        
        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectAllStoreImages----------------\n");
        
        return result;
	}

	@Override
	public List<DuplicateImages> getProjectStoresWithDuplicateImages(InputObject inputObject) {
		LOGGER.info("---------------ProcessImageServiceImpl Starts getProjectStoresWithDuplicateImages----------------\n");

        List<DuplicateImages>  result = processImageDao.getProjectStoresWithDuplicateImages(inputObject.getCustomerCode(), inputObject.getCustomerProjectId());
        
        LOGGER.info("---------------ProcessImageServiceImpl Ends getProjectStoresWithDuplicateImages----------------\n");
        
        return result;
	}

    //    public List<java.util.LinkedHashMap<String, String>> readImageAnalysis(String imageUUID) {
//        LOGGER.info("---------------ProcessImageDaoImpl Starts readImageAnalysis----------------\n");
//        LOGGER.info("---------------ProcessImageDaoImpl imageUUID=" + imageUUID );
//        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
//        ShellUtil shellUtil = new ShellUtil();
//        String result = shellUtil.readResult(imageUUID);
//        if (result.contains("{")) {
//            LOGGER.info("---------------ProcessImageDaoImpl  result=" + result + "----------------");
//            result = result.replaceAll("\n", "").replaceAll("\n", "");
//            String jsonString = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
//            LOGGER.info("---------------jsonString=" + jsonString + "---------------");
//
//            JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
//            JsonArray skusArray = obj.get("skus").getAsJsonArray();
//            for (JsonElement skus : skusArray) {
//                JsonObject upcEntry = skus.getAsJsonObject();
//                java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
//                temp.put("UPC", upcEntry.get("upc").getAsString());
//                temp.put("Left Top X", upcEntry.get("x").getAsString());
//                temp.put("Left Top Y", upcEntry.get("y").getAsString());
//                temp.put("Width", upcEntry.get("width").getAsString());
//                temp.put("Height", upcEntry.get("height").getAsString());
//                resultList.add(temp);
//            }
//            LOGGER.info("---------------ProcessImageDaoImpl Ends invokeImageAnalysis----------------\n");
//            return resultList;
//        } else {
//            return resultList;
//        }
//    }
//
//    select
//    final.UPC ,
//    PRODUCT_SHORT_NAME,
//    PRODUCT_LONG_NAME,
//    BRAND_NAME,
//    before_osa,
//    after_osa,
//    before_facing,
//    after_facing
//    from ProductMaster
//    join (
//            (
//            select t1.upc, t1.osa as before_osa, t2.osa as after_osa, t1.count as before_facing, t2.count as after_facing from
//                    (SELECT upc, "1" as osa, count(*) as count FROM ImageAnalysis  WHERE imageUUID = "0094ac77-1526-4fac-829d-690d3ee97568" group by upc) as t1,
//    (SELECT upc, "1" as osa, count(*) as count FROM ImageAnalysis  WHERE imageUUID = "124d1e60-cd40-4185-b211-fedbcc94db95" group by upc) as t2
//    where t1.upc = t2.upc
//    )
//    union all
//            (
//                    select t1.upc, t1.osa as before_osa, "0" as after_osa, t1.count as before_facing, "0" as after_facing from
//                    (SELECT upc, "1" as osa, count(*) as count FROM ImageAnalysis  WHERE imageUUID = "0094ac77-1526-4fac-829d-690d3ee97568" group by upc) as t1
//    where t1.upc not in (SELECT upc FROM ImageAnalysis  WHERE imageUUID = "124d1e60-cd40-4185-b211-fedbcc94db95" group by upc)
//    )
//    union all
//            (
//                    select t2.upc,  "0" as before_osa, t2.osa as after_osa, "0" as before_facing, t2.count  as after_facing from
//                    (SELECT upc, "1" as osa, count(*) as count FROM ImageAnalysis  WHERE imageUUID = "124d1e60-cd40-4185-b211-fedbcc94db95" group by upc) as t2
//    where t2.upc not in (SELECT upc FROM ImageAnalysis  WHERE imageUUID = "0094ac77-1526-4fac-829d-690d3ee97568" group by upc)
//    )
//            ) final on final.upc = ProductMaster.UPC

}
