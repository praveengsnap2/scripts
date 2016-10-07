/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2buy.webservice.rest.action;

//import com.mongodb.client.MongoDatabase;

import com.google.gson.Gson;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
import com.snap2buy.webservice.service.AuthenticationService;
import com.snap2buy.webservice.service.MetaService;
import com.snap2buy.webservice.service.ProcessImageService;
import com.snap2buy.webservice.service.ProductMasterService;
import com.snap2buy.webservice.service.ShelfAnalysisService;
import com.snap2buy.webservice.util.CustomSnap2BuyOutput;
import com.snap2buy.webservice.util.Snap2BuyOutput;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sachin
 */
@Component(value = BeanMapper.BEAN_REST_ACTION_S2P)
@Scope("prototype")
public class RestS2PAction {
    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    @Qualifier(BeanMapper.BEAN_PROCESS_IMAGE_SERVICE)
    private ProcessImageService processImageService;

    @Autowired
    @Qualifier(BeanMapper.BEAN_PRODUCT_MASTER_SERVICE)
    private ProductMasterService productMasterService;

    @Autowired
    @Qualifier(BeanMapper.BEAN_META_SERVICE)
    private MetaService metaService;
    
    @Autowired
    @Qualifier(BeanMapper.BEAN_AUTH_SERVICE)
    private AuthenticationService authService;

//    @Autowired
//    @Qualifier(BeanMapper.BEAN_QUERY_GENERATION_SERVICE)
//    private QueryGenerationService queryGenerationService;


    @Autowired
    @Qualifier(BeanMapper.BEAN_SHELF_ANALYSIS_SERVICE)
    private ShelfAnalysisService shelfAnalysisService;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "base-spring-ctx.xml");
        RestS2PAction restS2PAction = (RestS2PAction) context.getBean("RestS2PAction");
        LOGGER.info("Checking logger");


        InputObject inputObject = new InputObject();
        inputObject.setCategoryId("test");
        inputObject.setLatitude("45.56531392");
        inputObject.setLongitude("-122.8443362");
        inputObject.setTimeStamp("2008-01-01 00:00:01");
        inputObject.setUserId("agsachin");
        System.out.println(restS2PAction.saveImage(inputObject));
    }

    public Snap2BuyOutput saveImage(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts saveImage----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();
        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();

        // resultList=generateData();


        LOGGER.info("user : " + inputObject.getUserId());
        resultList = processImageService.storeImageDetails(inputObject);
        resultListToPass.addAll(resultList);
        LOGGER.info("StoreImageDetails done");

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("categoryId", inputObject.getCategoryId());
        reportInput.put("imageUUID", inputObject.getImageUUID());
        reportInput.put("imageUUID", inputObject.getImageUUID());
        reportInput.put("latitude", inputObject.getLatitude());
        reportInput.put("longitude", inputObject.getLongitude());
        reportInput.put("userId", inputObject.getUserId());
        reportInput.put("TimeStamp", inputObject.getTimeStamp());
        reportInput.put("agentId", inputObject.getAgentId());
        reportInput.put("taskId", inputObject.getTaskId());
        reportInput.put("customerProjectId", inputObject.getCustomerProjectId());
        reportInput.put("customerCode", inputObject.getCustomerCode());
        reportInput.put("dateId", inputObject.getVisitDate());
        reportInput.put("storeId", inputObject.getStoreId());
        reportInput.put("responseCode", "200");
        reportInput.put("responseMessage", "Image Stored Successfully");
        reportInput.put("imageFilePath", inputObject.getImageFilePath());
        reportInput.put("headers", "UPC, Left Top X, Left Top Y, Width, Height, Promotion, Price, Price_Flag");

        LOGGER.info("column list done");

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends saveImage----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getJob(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getJob----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result = processImageService.getJob(inputObject);

        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("hostId", inputObject.getHostId());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getJob----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getUpcDetails(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getUpcDetails----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result = productMasterService.getUpcDetails(inputObject);
        File image = productMasterService.getUpcImage(inputObject);
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("upc", inputObject.getUpc());
        reportInput.put("imagePth", image.getAbsolutePath());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getUpcDetails----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput) {
        LOGGER.info("---------------RestAction Starts storeShelfAnalysis----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + shelfAnalysisInput.getImageUUID());
        shelfAnalysisService.storeShelfAnalysis(shelfAnalysisInput);
        LOGGER.info("StoreShelfAnalysis done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "ShelfAnalysis Stored Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", shelfAnalysisInput.getImageUUID());
        reportInput.put("storeId", shelfAnalysisInput.getStoreID());
        reportInput.put("categoryId", shelfAnalysisInput.getCategoryID());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends storeShelfAnalysis----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getShelfAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getShelfAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        result = shelfAnalysisService.getShelfAnalysis(inputObject.getImageUUID());
        LOGGER.info("getShelfAnalysis done");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getShelfAnalysis----------------\n");

        return reportIO;
    }

    public Snap2BuyOutput getReport(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getReport----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

        //String query = queryGenerationService.generateQuery(inputObject);

        LOGGER.info("getShelfAnalysis done");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getReport----------------\n");

        return reportIO;
    }

    public File getUpcImage(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getUpcImage----------------\n");

        LOGGER.info("upc : " + inputObject.getUpc());

        LOGGER.info("---------------RestAction Ends getUpcImage----------------\n");

        return productMasterService.getUpcImage(inputObject);
    }

    public void storeThumbnails(String imageFolderPath) {
        LOGGER.info("---------------RestAction Starts storeThumbnails----------------\n");

        productMasterService.storeThumbnails(imageFolderPath);

        LOGGER.info("---------------RestAction Ends storeThumbnails----------------\n");
    }

//    public Snap2BuyOutput runImageAnalysis(InputObject inputObject) {
//        LOGGER.info("---------------RestAction Starts runImageAnalysis----------------\n");
//        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();
//
//        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
//        resultListToPass = processImageService.runImageAnalysis(inputObject.getImageUUID());
//
//        HashMap<String, String> reportInput = new HashMap<String, String>();
//
//        reportInput.put("imageUUID", inputObject.getImageUUID());
//
//        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
//        LOGGER.info("---------------RestAction Ends runImageAnalysis----------------\n");
//
//        return reportIO;
//    }

    public Snap2BuyOutput getImageAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getImageAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();
        String defaultHostId = "52.40.228.141";
        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        resultListToPass = processImageService.getImageAnalysis(inputObject.getImageUUID(),defaultHostId);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getImageAnalysis----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput processNextImage(String hostId) {
        LOGGER.info("---------------RestAction Starts processNextImage----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.processNextImage(hostId);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("hostId",hostId);

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends processNextImage----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getStoreOptions() {
        LOGGER.info("---------------RestAction Starts getStoreOptions----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getStoreOptions();

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("header", "retailerChainCode,retailer,stateCode,state,city");

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getStoreOptions----------------\n");

        return reportIO;
    }

    public Snap2BuyOutput getImages(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getImages----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getImages(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("storeId",inputObject.getStoreId());
        reportInput.put("dateId", inputObject.getVisitDate());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getImages----------------\n");

        return reportIO;
    }
    
    public Snap2BuyOutput getProjectStoreImages(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectStoreImages----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getProjectStoreImages(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("customerCode", inputObject.getCustomerCode());
        reportInput.put("customerProjectId", inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectStoreImages----------------\n");

        return reportIO;
    }
    
    public Snap2BuyOutput getStores(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getStores----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getStores(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("retailerChainCode",inputObject.getRetailerChainCode());
        reportInput.put("stateCode", inputObject.getStateCode());
        reportInput.put("city", inputObject.getCity());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getStores----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getDistributionLists() {
        LOGGER.info("---------------RestAction Starts getDistributionLists----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = productMasterService.getDistributionLists();

        HashMap<String, String> reportInput = new HashMap<String, String>();

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getDistributionLists----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput doDistributionCheck(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doDistributionCheck----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.doDistributionCheck(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        Long numUpcFound= 0L;
        Long numUpcNotFound= 0L;
        Long totalFacings= 0L;

        for (LinkedHashMap<String, String> entry : resultListToPass) {
            if (entry.get("osa").equalsIgnoreCase("1")) {
                numUpcFound++;
                totalFacings += Long.parseLong(entry.get("facing"));
            } else if (entry.get("osa").equalsIgnoreCase("0")) {
                numUpcNotFound++;
                totalFacings += Long.parseLong(entry.get("facing"));
            }
        }

        reportInput.put("listId", inputObject.getListId());
        reportInput.put("imageUUID", inputObject.getImageUUID());
        reportInput.put("numUpcFound", String.valueOf(numUpcFound));
        reportInput.put("numUpcNotFound", String.valueOf(numUpcNotFound));
        reportInput.put("totalFacings", String.valueOf(totalFacings));

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends doDistributionCheck----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput doBeforeAfterCheck(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doBeforeAfterCheck----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.doBeforeAfterCheck(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        Long newUpcAdded=0L;
        Long numUpcRemoved =0L;
        Long numUpcMoreFacings =0L;
        Long numUpcLessFacings=0L;
        Long numUpcUnChangedFacings=0L;

        for (LinkedHashMap<String, String> entry : resultListToPass){
            if ((entry.get("before_osa").equalsIgnoreCase("0"))&&(entry.get("after_osa").equalsIgnoreCase("1"))){
                newUpcAdded++;
            } else if ((entry.get("before_osa").equalsIgnoreCase("1"))&&(entry.get("after_osa").equalsIgnoreCase("0"))){
                numUpcRemoved++;
            }else if (Integer.parseInt(entry.get("after_facing")) > Integer.parseInt(entry.get("before_facing"))){
                numUpcMoreFacings++;
            }else if (Integer.parseInt(entry.get("before_facing")) > Integer.parseInt(entry.get("after_facing"))){
                numUpcLessFacings++;
            }else if (Integer.parseInt(entry.get("before_facing")) == Integer.parseInt(entry.get("after_facing"))){
                numUpcUnChangedFacings++;
            }

        }
        reportInput.put("prevImageUUID", inputObject.getPrevImageUUID());
        reportInput.put("imageUUID", inputObject.getImageUUID());
        reportInput.put("newUpcAdded", String.valueOf(newUpcAdded));
        reportInput.put("numUpcRemoved", String.valueOf(numUpcRemoved));
        reportInput.put("numUpcMoreFacings", String.valueOf(numUpcMoreFacings));
        reportInput.put("numUpcLessFacings", String.valueOf(numUpcLessFacings));
        reportInput.put("numUpcUnChangedFacings", String.valueOf(numUpcUnChangedFacings));

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends doBeforeAfterCheck----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getImageMetaData(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getImageMetaData----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getImageMetaData(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getImageMetaData----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput doShareOfShelfAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doShareOfShelfAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + inputObject.getImageUUIDCsvString());
        resultListToPass = processImageService.doShareOfShelfAnalysis(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUIDCsvString());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends doShareOfShelfAnalysis----------------\n");

        return reportIO;
    }

    public File getShelfAnalysisCsv() {
        LOGGER.info("---------------RestAction Starts getShelfAnalysisCsv----------------\n");

        String tempFilePath = "/tmp/csvDownload" + System.currentTimeMillis();

        File shelfAnalysis = shelfAnalysisService.getShelfAnalysisCsv(tempFilePath);

        LOGGER.info("---------------RestAction Ends getShelfAnalysisCsv----------------\n");

        return shelfAnalysis;
    }
    public File doShareOfShelfAnalysisCsv(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doShareOfShelfAnalysisCsv----------------\n");

        String tempFilePath = "/tmp/csvDownload" + System.currentTimeMillis();

        File shareOfShelfAnalysisCsv = processImageService.doShareOfShelfAnalysisCsv(inputObject,tempFilePath);

        LOGGER.info("---------------RestAction Ends doShareOfShelfAnalysisCsv----------------\n");

        return shareOfShelfAnalysisCsv;
    }

    public Snap2BuyOutput updateLatLong(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts updateLatLong----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + inputObject.getImageUUIDCsvString());
        processImageService.updateLatLong(inputObject);
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "updateLatLong Stored Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUID());
        reportInput.put("latitude", inputObject.getLatitude());
        reportInput.put("longitude", inputObject.getLongitude());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends updateLatLong----------------\n");

        return reportIO;
    }

    public Snap2BuyOutput listCategory() {
        LOGGER.info("---------------RestAction Starts listCategory-----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listCategory();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listCategory----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listCustomer() {
        LOGGER.info("---------------RestAction Starts listCustomer-----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listCustomer();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listCustomer----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listProject(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts listProject:: customer code = "+inputObject.getCustomerCode()+"------------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listProject(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode", inputObject.getCustomerCode());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listProject----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listProjectType() {
        LOGGER.info("---------------RestAction Starts listProjectType-----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listProjectType();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listProjectType----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listSkuType() {
        LOGGER.info("---------------RestAction Starts listSkuType-----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listSkuType();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listSkuType----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listRetailer( ) {
        LOGGER.info("---------------RestAction Starts listRetailer------------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listRetailer();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listRetailer----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput listProjectUpc(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts listProjectUpc------------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listProjectUpc(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerProjectId", inputObject.getCustomerProjectId());
        reportInput.put("customerCode", inputObject.getCustomerCode());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listProjectUpc----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getCategoryDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getCategoryDetail--id : " + inputObject.getId()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getCategoryDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", inputObject.getId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getCategoryDetail----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getCustomerDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getCustomerDetail--customerCode : " + inputObject.getCustomerCode()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getCustomerDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode", inputObject.getCustomerCode());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getCustomerDetail----------------\n");
        return reportIO;
    }

    public String getProjectDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectDetail--customerProjectId : " + inputObject.getCustomerProjectId()+"customerCode"+inputObject.getCustomerCode()+"----------------\n");
        List<Project> resultListToPass = metaService.getProjectDetail(inputObject);

        Map<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        List<Map<String,String>> metaList = new ArrayList<Map<String,String>>();
        metaList.add(reportInput);
        
        CustomSnap2BuyOutput reportIO = null;
        if ( resultListToPass.isEmpty()) {
        	Map<String, String> emptyOutput = new HashMap<String, String>();
        	emptyOutput.put("Message", "No Data Returned");
        	List<Map<String,String>> emptyOutputList = new ArrayList<>();
        	emptyOutputList.add(emptyOutput);
        	reportIO = new CustomSnap2BuyOutput(emptyOutputList, metaList);
        } else {
        	reportIO = new CustomSnap2BuyOutput(resultListToPass, metaList);
        }
        //convert to json here
        Gson gson = new Gson();
        String output = gson.toJson(reportIO);
        
        LOGGER.info("---------------RestAction Ends getProjectDetail----------------\n");
        return output;
    }

    public Snap2BuyOutput getProjectTypeDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectTypeDetail--id : " + inputObject.getId()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getProjectTypeDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", inputObject.getId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectTypeDetail----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput getSkuTypeDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getSkuTypeDetail -- id : " + inputObject.getId()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getSkuTypeDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", inputObject.getId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getSkuTypeDetail----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput getProjectUpcDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectUpcDetail--customerProjectId : " + inputObject.getCustomerProjectId()+"customerCode="+inputObject.getCustomerCode()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getProjectUpcDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerProjectid", inputObject.getCustomerProjectId());
        reportInput.put("customerCode", inputObject.getCustomerCode());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectUpcDetail----------------\n");
        return reportIO;
    }

    public Snap2BuyOutput getRetailerDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getRetailerDetail--retailerCode : " + inputObject.getRetailerCode()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getRetailerDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("retailerCode", inputObject.getRetailerCode());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getRetailerDetail----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createCustomer(Customer customerInput) {
        LOGGER.info("---------------RestAction Starts createCustomer----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("code : " + customerInput.getCustomerCode());
        metaService.createCustomer(customerInput);
        LOGGER.info("createCustomer done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "Customer Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", customerInput.getId());
        reportInput.put("code", customerInput.getCustomerCode());
        reportInput.put("name", customerInput.getName());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createCustomer----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createCategory(Category categoryInput) {
        LOGGER.info("---------------RestAction Starts createCategory----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name : " + categoryInput.getName());
        metaService.createCategory(categoryInput);
        LOGGER.info("createCategory done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "Category Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", categoryInput.getId());
        reportInput.put("name", categoryInput.getName());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createCategory----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createRetailer(Retailer retailerInput) {
        LOGGER.info("---------------RestAction Starts createRetailer----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name : " + retailerInput.getName());
        metaService.createRetailer(retailerInput);
        LOGGER.info("createRetailer done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "Retailer Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", retailerInput.getId());
        reportInput.put("name", retailerInput.getName());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createRetailer----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createProjectType(ProjectType projectTypeInput) {
        LOGGER.info("---------------RestAction Starts createProjectType----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name : " + projectTypeInput.getName());
        metaService.createProjectType(projectTypeInput);
        LOGGER.info("createProjectType done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "ProjectType Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", projectTypeInput.getId());
        reportInput.put("name", projectTypeInput.getName());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createProjectType----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createSkuType(SkuType skuTypeInput) {
        LOGGER.info("---------------RestAction Starts createSkuType----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name : " + skuTypeInput.getName());
        metaService.createSkuType(skuTypeInput);
        LOGGER.info("createProjectType done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "SkuType Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", skuTypeInput.getId());
        reportInput.put("name", skuTypeInput.getName());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createSkuType----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createProject(Project projectInput) {
        LOGGER.info("---------------RestAction Starts createProject----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name: " + projectInput.getProjectName());
        metaService.createProject(projectInput);
        LOGGER.info("createProject done");

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "Project Created Successfully");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", projectInput.getCustomerProjectId());
        reportInput.put("name", projectInput.getProjectName());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createProject----------------\n");
        return reportIO;
    }
    
    public Snap2BuyOutput createUpc(ProductMaster upcInput) {
        LOGGER.info("---------------RestAction Starts createUpc----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("UPC : " + upcInput.getUpc());
        productMasterService.createUpc(upcInput);
        LOGGER.info("createUpc done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "UPC Created Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("upc", upcInput.getUpc());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createUpc----------------\n");
        return reportIO;
    }
    
    public Snap2BuyOutput addUpcToProjectId(ProjectUpc projectUpc) {
        LOGGER.info("---------------RestAction Starts createProject----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("customerProjectId : " + projectUpc.getCustomerProjectId());
        metaService.addUpcToProjectId(projectUpc);
        LOGGER.info("createProject done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "add upc to project Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerProjectId", projectUpc.getCustomerProjectId());
        reportInput.put("facingCount", projectUpc.getExpectedFacingCount());
        reportInput.put("upc", projectUpc.getUpc());
        reportInput.put("skuType", projectUpc.getSkuTypeId());
        reportInput.put("ingUrl1", projectUpc.getImageUrl1());
        reportInput.put("ingUrl2", projectUpc.getImageUrl2());
        reportInput.put("ingUrl3", projectUpc.getImageUrl3());


        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends createProject----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput generateAggs(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts generateAggs----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.generateAggs(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends generateAggs----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getProjectStoreResults(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectStoreResults----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.getProjectStoreResults(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectStoreResults----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getProjectTopStores(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectTopStores----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.getProjectTopStores(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("limit",inputObject.getLimit());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectTopStores----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getProjectBottomStores(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectBottomStores----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.getProjectBottomStores(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("limit",inputObject.getLimit());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectBottomStores----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput listStores() {
        LOGGER.info("---------------RestAction Starts listStores-----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.listStores();

        HashMap<String, String> reportInput = new HashMap<String, String>();
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends listStores----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput createStore(StoreMaster storeMaster) {
        LOGGER.info("---------------RestAction Starts storeMaster----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("id : " + storeMaster.getStoreId());
        metaService.createStore(storeMaster);
        LOGGER.info("createStore done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "store Created Successfully");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id",storeMaster.getStoreId());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends storeMaster----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput updateStore(StoreMaster storeMaster) {
        LOGGER.info("---------------RestAction Starts updateStore----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("id : " + storeMaster.getStoreId());
        metaService.updateStore(storeMaster);
        LOGGER.info("updateStore done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "store updated Successfully");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id",storeMaster.getStoreId());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends updateStore----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput updateProject(Project projectInput) {
        LOGGER.info("---------------RestAction Starts updateProject----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("name: " + projectInput.getProjectName());
        metaService.updateProject(projectInput);
        LOGGER.info("updateProject done");

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode", "200");
        result.put("responseMessage", "Project Updated Successfully");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("id", projectInput.getCustomerProjectId());
        reportInput.put("name", projectInput.getProjectName());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends updateProject----------------\n");
        return reportIO;
    }
    public Snap2BuyOutput getProjectSummary(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectSummary----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getProjectSummary(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectSummary----------------\n");

        return reportIO;
    }
    public Snap2BuyOutput getStoreDetail(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getStoreDetail--storeId : " + inputObject.getStoreId()+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = metaService.getStoreDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("storeId", inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getStoreDetail----------------\n");
        return reportIO;
    }
    
    public String getProjectStoresWithNoUPCs(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getProjectStoresWithNoUPCs----------------\n");
        Map<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        List<Map<String,String>> metaList = new ArrayList<Map<String,String>>();
        metaList.add(reportInput);
               
        List<StoreWithImages> resultListToPass = new ArrayList<StoreWithImages>();
        resultListToPass = processImageService.getProjectStoresWithNoUPCs(inputObject);
        CustomSnap2BuyOutput reportIO = null;
        if ( resultListToPass.isEmpty()) {
        	Map<String, String> emptyOutput = new HashMap<String, String>();
        	emptyOutput.put("Message", "No Data Returned");
        	List<Map<String,String>> emptyOutputList = new ArrayList<>();
        	emptyOutputList.add(emptyOutput);
        	reportIO = new CustomSnap2BuyOutput(emptyOutputList, metaList);
        } else {
        	reportIO = new CustomSnap2BuyOutput(resultListToPass, metaList);
        }
        //convert to json here
        Gson gson = new Gson();
        String output = gson.toJson(reportIO);
        LOGGER.info("---------------RestAction Ends getProjectStoresWithNoUPCs----------------\n");
        return output;
    }

	public String getProjectAllStoreImages(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts getProjectAllStoreImages----------------\n");
		Map<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        List<Map<String,String>> metaList = new ArrayList<Map<String,String>>();
        metaList.add(reportInput);
               
        List<StoreWithImages> resultListToPass = new ArrayList<StoreWithImages>();
        resultListToPass = processImageService.getProjectAllStoreImages(inputObject);
        CustomSnap2BuyOutput reportIO = null;
        if ( resultListToPass.isEmpty()) {
        	Map<String, String> emptyOutput = new HashMap<String, String>();
        	emptyOutput.put("Message", "No Data Returned");
        	List<Map<String,String>> emptyOutputList = new ArrayList<>();
        	emptyOutputList.add(emptyOutput);
        	reportIO = new CustomSnap2BuyOutput(emptyOutputList, metaList);
        } else {
        	reportIO = new CustomSnap2BuyOutput(resultListToPass, metaList);
        }
        //convert to json here
        Gson gson = new Gson();
        String output = gson.toJson(reportIO);
        return output;
	}

	public String getProjectStoresWithDuplicateImages(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts getProjectStoresWithDuplicateImages----------------\n");
		Map<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        List<Map<String,String>> metaList = new ArrayList<Map<String,String>>();
        metaList.add(reportInput);
               
        List<DuplicateImages> resultListToPass = new ArrayList<DuplicateImages>();
        resultListToPass = processImageService.getProjectStoresWithDuplicateImages(inputObject);
        CustomSnap2BuyOutput reportIO = null;
        if ( resultListToPass.isEmpty()) {
        	Map<String, String> emptyOutput = new HashMap<String, String>();
        	emptyOutput.put("Message", "No Data Returned");
        	List<Map<String,String>> emptyOutputList = new ArrayList<>();
        	emptyOutputList.add(emptyOutput);
        	reportIO = new CustomSnap2BuyOutput(emptyOutputList, metaList);
        } else {
        	reportIO = new CustomSnap2BuyOutput(resultListToPass, metaList);
        }
        //convert to json here
        Gson gson = new Gson();
        String output = gson.toJson(reportIO);
        return output;
	}

	public Snap2BuyOutput generateStoreResults(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts generateStoreResults----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.generateStoreResults(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId", inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends generateStoreResults----------------\n");

        return reportIO;
	}

	public Snap2BuyOutput getProjectAllStoreResults(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts getProjectAllStoreResults----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.getProjectAllStoreResults(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectAllStoreResults----------------\n");

        return reportIO;
	}
	
	public Snap2BuyOutput getProjectAllStoreResultsDetail(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts getProjectAllStoreResultsDetail----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = processImageService.getProjectAllStoreResultsDetail(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getProjectAllStoreResultsDetail----------------\n");

        return reportIO;
	}

	public Snap2BuyOutput createUser(User userInput) {
		 LOGGER.info("---------------RestAction Starts createUser----------------\n");

	        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

	        LOGGER.info("Check if userId exists for userId : " + userInput.getUserId());
	        List<LinkedHashMap<String,String>> userList = authService.getUserDetail(userInput.getUserId());
	        boolean isSuccess = false;
	        if (null == userList || userList.isEmpty() ) {
	        	LOGGER.info("No existing record found for userId : " + userInput.getUserId());
	        	authService.createUser(userInput);
		        LOGGER.info("New user created with userId : " + userInput.getUserId());
		        isSuccess = true;
	        } else {
	        	LOGGER.error("User Id " + userInput.getUserId() + " already exists in the system. Rejecting the request.");
	        	isSuccess = false;
	        }

	        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	        if ( isSuccess ) {
	        	result.put("responseCode", "200");
	 	        result.put("responseMessage", "User Created Successfully");
	        } else {
	        	result.put("responseCode", "200");
	 	        result.put("responseMessage", "Existing user found. Request Rejected.");
	        }
	        resultListToPass.add(result);

	        HashMap<String, String> reportInput = new HashMap<String, String>();
	        reportInput.put("userId", userInput.getUserId());
	        reportInput.put("firstName", userInput.getFirstName());
	        reportInput.put("lastName", userInput.getLastName());


	        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
	        LOGGER.info("---------------RestAction Ends createUser----------------\n");
	        return reportIO;
	}
	
	public Snap2BuyOutput getUserDetail(String userId) {
		LOGGER.info("---------------RestAction Starts getUserDetail --userName : " + userId+"----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = authService.getUserDetail(userId);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("userId", userId);
        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getUserDetail -- userId : " + userId +"----------------\n");
        return reportIO;
	}
	
	public Snap2BuyOutput updateUser(User userInput) {
		 LOGGER.info("---------------RestAction Starts updateteUser----------------\n");

	        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

	        LOGGER.info("userId : " + userInput.getUserId());
	        authService.updateUser(userInput);
	        LOGGER.info("udpateUser done");


	        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	        result.put("responseCode", "200");
	        result.put("responseMessage", "User Updated Successfully");
	        resultListToPass.add(result);


	        HashMap<String, String> reportInput = new HashMap<String, String>();
	        reportInput.put("userId", userInput.getUserId());
	        reportInput.put("firstName", userInput.getFirstName());
	        reportInput.put("lastName", userInput.getLastName());


	        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
	        LOGGER.info("---------------RestAction Ends updateUser----------------\n");
	        return reportIO;
	}
	
	public Snap2BuyOutput updateUserPassword(User userInput) {
		 LOGGER.info("---------------RestAction Starts updateUserPassword----------------\n");

	        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

	        LOGGER.info("userId : " + userInput.getUserId());
	        authService.updateUserPassword(userInput);
	        LOGGER.info("updateUserPassword done");


	        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	        result.put("responseCode", "200");
	        result.put("responseMessage", "User password updated Successfully");
	        resultListToPass.add(result);

	        HashMap<String, String> reportInput = new HashMap<String, String>();
	        reportInput.put("userId", userInput.getUserId());

	        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
	        LOGGER.info("---------------RestAction Ends updateUserPassword----------------\n");
	        return reportIO;
	}
	
	public Snap2BuyOutput deleteUser(String userId) {
		 LOGGER.info("---------------RestAction Starts deleteUser----------------\n");

	        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

	        LOGGER.info("userId : " + userId);
	        authService.deleteUser(userId);
	        LOGGER.info("deleteUser done");


	        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
	        result.put("responseCode", "200");
	        result.put("responseMessage", "User Deleted Successfully");
	        resultListToPass.add(result);


	        HashMap<String, String> reportInput = new HashMap<String, String>();
	        reportInput.put("userId", userId);

	        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
	        LOGGER.info("---------------RestAction Ends deleteUser----------------\n");
	        return reportIO;
	}

	public Snap2BuyOutput login(String userId, String password) {
		LOGGER.info("---------------RestAction Starts login for user : " + userId + "----------------\n");

        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        List<LinkedHashMap<String,String>> userList = authService.getUserForAuth(userId);
       
        boolean isAuthenticated = true;
        if (userList == null || userList.isEmpty() ) {
        	isAuthenticated = false;
        } else {
        	if ( password == null || !password.equals(userList.get(0).get("password"))) {
        		isAuthenticated = false;
        	}
        }
        
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        if ( isAuthenticated ) {
        	result.put("status", "success");
            result.put("customerCode", userList.get(0).get("customerCode"));
            result.put("firstName", userList.get(0).get("firstName"));
            result.put("lastName", userList.get(0).get("lastName"));
        } else {
        	result.put("status", "failure");
        }
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("userId", userId);

        Snap2BuyOutput reportIO = new Snap2BuyOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends deleteUser----------------\n");
        return reportIO;
	}

	public Snap2BuyOutput recomputeProjectByStore(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts recomputeProjectByStore----------------\n");
        processImageService.recomputeProjectByStore(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(null, reportInput);
        LOGGER.info("---------------RestAction Ends recomputeProjectByStore----------------\n");

        return reportIO;
	}

	public Snap2BuyOutput reprocessProjectByStore(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts reprocessProjectByStore----------------\n");
        processImageService.reprocessProjectByStore(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(null, reportInput);
        LOGGER.info("---------------RestAction Ends reprocessProjectByStore----------------\n");

        return reportIO;
	}

	public Snap2BuyOutput updateProjectResultByStore(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts updateProjectResultByStore----------------\n");
        processImageService.updateProjectResultByStore(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        reportInput.put("resultCode",inputObject.getResultCode());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(null, reportInput);
        LOGGER.info("---------------RestAction Ends updateProjectResultByStore----------------\n");

        return reportIO;
	}

	public Snap2BuyOutput updateProjectResultStatusByStore(InputObject inputObject) {
		LOGGER.info("---------------RestAction Starts updateProjectResultStatusByStore----------------\n");
        processImageService.updateProjectResultStatusByStore(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("customerCode",inputObject.getCustomerCode());
        reportInput.put("customerProjectId",inputObject.getCustomerProjectId());
        reportInput.put("storeId",inputObject.getStoreId());
        reportInput.put("status",inputObject.getStatus());
        Snap2BuyOutput reportIO = new Snap2BuyOutput(null, reportInput);
        LOGGER.info("---------------RestAction Ends updateProjectResultStatusByStore----------------\n");

        return reportIO;
	}
}
