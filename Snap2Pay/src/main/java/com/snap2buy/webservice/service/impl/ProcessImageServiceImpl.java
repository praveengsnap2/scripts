package com.snap2buy.webservice.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.dao.ProductMasterDao;
import com.snap2buy.webservice.dao.ShelfAnalysisDao;
import com.snap2buy.webservice.dao.StoreMasterDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.ImageAnalysis;
import com.snap2buy.webservice.model.ImageStore;
import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.service.ProcessImageService;
import com.snap2buy.webservice.util.ConverterUtil;
import com.snap2buy.webservice.util.ShellUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    @Override
    public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts storeImageDetails----------------\n");

        ImageStore imageStore = new ImageStore();
        imageStore.setImageUUID(inputObject.getImageUUID());
        imageStore.setImageFilePath(inputObject.getImageFilePath());
        imageStore.setUserId(inputObject.getUserId());
        imageStore.setCategoryId(inputObject.getCategoryId());
        imageStore.setLatitude(inputObject.getLatitude());
        imageStore.setLongitude(inputObject.getLongitude());
        imageStore.setTimeStamp(inputObject.getTimeStamp());
        imageStore.setDateId(inputObject.getVisitDate());

        String storeId = storeMasterDao.getStoreId(inputObject.getLongitude(), inputObject.getLatitude());

        LOGGER.info("--------------storeId=" + storeId + "-----------------\n");

        imageStore.setStoreId(storeId);
        imageStore.setShelfStatus("new");
        processImageDao.insert(imageStore);

        if (inputObject.getSync().equals("true")) {
            String retailerChainCode = storeMasterDao.getRetailerChainCode(storeId);
            LOGGER.info("--------------retailerChainCode=" + retailerChainCode + "-----------------\n");
            List<ImageAnalysis> imageAnalysisList =  invokeImageAnalysis(inputObject.getImageFilePath(), inputObject.getCategoryId(), inputObject.getImageUUID(), retailerChainCode, storeId);
            LOGGER.info("--------------imageAnalysisList=" + imageAnalysisList + "-----------------\n");
            processImageDao.storeImageAnalysis(imageAnalysisList,imageStore);
            processImageDao.updateImageAnalysisStatus("done",imageStore.getImageUUID());
            LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails   sync----------------\n");
            return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);
        } else {
            LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails   not sync----------------\n");
            List<java.util.LinkedHashMap<String, String>> result=new ArrayList<java.util.LinkedHashMap<String, String>>();
            return result;
        }
    }

    @Override
    public LinkedHashMap<String, String> getJob(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getJob----------------\n");


        LinkedHashMap<String, String> unProcessedJob = new LinkedHashMap<String, String>();
        String status = "new";
        Integer newJobCount = processImageDao.getJobCount(status);
        if (newJobCount > 1) {

            ImageStore imageStore = processImageDao.getImageByStatus(status);
            status = "processing";

            LOGGER.info("got this job imageStore" + imageStore.toString());

            processImageDao.updateStatusAndHost(inputObject.getHostId(), status, imageStore.getImageUUID());
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


    public List<ImageAnalysis> invokeImageAnalysis(String imageFilePath, String category, String uuid, String retailer, String store) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts invokeImageAnalysis----------------\n");
        LOGGER.info("---------------ProcessImageDaoImpl imageFilePath=" + imageFilePath + ", category=" + category + ", uuid=" + uuid + ", retailer=" + retailer + ", store=" + store + "----------------\n");
        List<ImageAnalysis> imageAnalysisList = new ArrayList<ImageAnalysis>();
        ShellUtil shellUtil = new ShellUtil();
        String result = shellUtil.executeCommand(imageFilePath, category, uuid, retailer, store);
        if (result.contains("{")) {
            LOGGER.info("---------------ProcessImageDaoImpl  result=" + result + "----------------");
            result = result.replaceAll("\n", "").replaceAll("\n", "");
//            String[] trimStart = result.split("Exited with error code 1");
//            String[] trimLast = trimStart[1].split("Stderr");
//            String jsonString = trimLast[0];
            String jsonString = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
            LOGGER.info("---------------jsonString=" + jsonString + "---------------");

            JsonObject obj = new JsonParser().parse(jsonString).getAsJsonObject();
            JsonArray skusArray = obj.get("skus").getAsJsonArray();
            for (JsonElement skus : skusArray) {
                ImageAnalysis imageAnalysis = new ImageAnalysis();
                JsonObject upcEntry = skus.getAsJsonObject();
                java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
                imageAnalysis.setUpc(upcEntry.get("upc").getAsString().trim());
                imageAnalysis.setLeftTopX(upcEntry.get("x").getAsString().trim());
                imageAnalysis.setLeftTopY(upcEntry.get("y").getAsString().trim());
                imageAnalysis.setWidth(upcEntry.get("width").getAsString().trim());
                imageAnalysis.setHeight(upcEntry.get("height").getAsString().trim());
                imageAnalysisList.add(imageAnalysis);
            }
            LOGGER.info("---------------ProcessImageDaoImpl Ends invokeImageAnalysis----------------\n");
            return imageAnalysisList;
        } else {
            return imageAnalysisList;
        }
    }

    @Override
    public List<LinkedHashMap<String, String>> runImageAnalysis(String imageUUID) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts runImageAnalysis----------------\n");

        ImageStore imageStore =processImageDao.findByImageUUId(imageUUID);

        LOGGER.info("--------------runImageAnalysis::imageStore=" + imageStore + "-----------------\n");

        String retailerChainCode = storeMasterDao.getRetailerChainCode(imageStore.getStoreId());

        LOGGER.info("--------------runImageAnalysis::retailerChainCode=" + retailerChainCode + "-----------------\n");

        List<ImageAnalysis> imageAnalysisList = invokeImageAnalysis(imageStore.getImageFilePath(), imageStore.getCategoryId(), imageStore.getImageUUID(), retailerChainCode, imageStore.getStoreId());

        LOGGER.info("--------------runImageAnalysis::imageAnalysisList=" + imageAnalysisList + "-----------------\n");

        String status="done";
        processImageDao.updateImageAnalysisStatus(status,imageUUID);
        LOGGER.info("--------------runImageAnalysis::updateStatus=" + status + "-----------------\n");

        processImageDao.storeImageAnalysis(imageAnalysisList, imageStore);

        LOGGER.info("---------------ProcessImageServiceImpl Ends runImageAnalysis ----------------\n");
        return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);

    }

    @Override
    public List<LinkedHashMap<String, String>> getImageAnalysis(String imageUUID) {

        LOGGER.info("---------------ProcessImageServiceImpl Starts getImageAnalysis----------------\n");
        String status = processImageDao.getImageAnalysisStatus(imageUUID);

        if (status.equalsIgnoreCase("done"))
        {
            List<ImageAnalysis> imageAnalysisList = processImageDao.getImageAnalysis(imageUUID);
            LOGGER.info("---------------ProcessImageServiceImpl Ends getImageAnalysis ----------------\n");
            return ConverterUtil.convertImageAnalysisObjectToMap(imageAnalysisList);
        }else{
            List<LinkedHashMap<String, String>> imageAnalysisList = runImageAnalysis(imageUUID);
            LOGGER.info("---------------ProcessImageServiceImpl Ends getImageAnalysis ----------------\n");
            return imageAnalysisList;
        }

    }

    @Override
    public List<LinkedHashMap<String, String>> getStoreOptions() {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getStoreOptions----------------\n");

        List<LinkedHashMap<String,String>> storeMasterList = storeMasterDao.getStoreOptions();

        LOGGER.info("---------------ProcessImageServiceImpl Ends getStoreOptions ----------------\n");
        return storeMasterList;
    }
    @Override
    public List<LinkedHashMap<String, String>> getImages(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getImages----------------\n");

        List<LinkedHashMap<String,String>> imageStoreList = processImageDao.getImages(inputObject.getStoreId(),inputObject.getDateId());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getImages ----------------\n");
        return imageStoreList;
    }
    @Override
    public List<LinkedHashMap<String, String>> getStores(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts getStores----------------\n");

        List<LinkedHashMap<String, String>>  storeMasterList = storeMasterDao.getStores(inputObject.getRetailerChainCode(), inputObject.getState(),inputObject.getCity());

        LOGGER.info("---------------ProcessImageServiceImpl Ends getStores ----------------\n");
        return storeMasterList;
    }

    @Override
    public List<LinkedHashMap<String, String>> doDistributionCheck(InputObject inputObject) {
        LOGGER.info("---------------ProcessImageServiceImpl Starts doDistributionCheck----------------\n");
        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> map = processImageDao.getFacing(inputObject.getImageUUID());

        Set<String> keySet =map.keySet();
        List<String> listDistributionList= productMasterDao.getDistributionLists(inputObject.getListName());

        List<String> dlUpc = new ArrayList<String>();
        for (String unit: listDistributionList){
            LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
            if(keySet.contains(unit)){
                entry.put("upc",unit);
                entry.put("facing", map.get(unit));
                entry.put("osa","1");
            }else{
                entry.put("upc",unit);
                entry.put("facing", "0");
                entry.put("osa","0");
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
        LinkedHashMap<String, String> map1=new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> map2=new LinkedHashMap<String, String>();
        String prevImageStatus=processImageDao.getImageAnalysisStatus(inputObject.getPrevImageUUID());
        if (prevImageStatus.equalsIgnoreCase("done")) {
            map1 = processImageDao.getFacing(inputObject.getPrevImageUUID());
        }else {
            getImageAnalysis(inputObject.getPrevImageUUID());
            map1 = processImageDao.getFacing(inputObject.getPrevImageUUID());
        }

        String imageStatus=processImageDao.getImageAnalysisStatus(inputObject.getImageUUID());
        if (imageStatus.equalsIgnoreCase("done")) {
            map2 = processImageDao.getFacing(inputObject.getImageUUID());
        }else{
            getImageAnalysis(inputObject.getImageUUID());
            map2= processImageDao.getFacing(inputObject.getImageUUID());
        }


        Set<String> keyset1=map1.keySet();
        Set<String> keyset2=map2.keySet();

        Set<String> union = new HashSet<String>(keyset1);
        union.addAll(keyset2);

        Set<String> intersection = new HashSet<String>(keyset1);
        intersection.retainAll(keyset2);

        for (String unit: union){
            LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
            if(intersection.contains(unit)){
                entry.put("upc",unit);
                entry.put("before_facing", map1.get(unit));
                entry.put("before_osa","1");
                entry.put("after_facing", map2.get(unit));
                entry.put("after_osa","1");
            }
            else if(keyset1.contains(unit)) {
                entry.put("upc",unit);
                entry.put("before_facing", map1.get(unit));
                entry.put("before_osa","1");
                entry.put("after_facing", "0");
                entry.put("after_osa","0");
            }
            else if (keyset2.contains(unit)){
                entry.put("upc",unit);
                entry.put("before_facing", "0");
                entry.put("before_osa","0");
                entry.put("after_facing", map2.get(unit));
                entry.put("after_osa","1");
            }
            result.add(entry);
        }

        LOGGER.info("---------------ProcessImageServiceImpl Ends doBeforeAfterCheck ----------------\n");
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



}