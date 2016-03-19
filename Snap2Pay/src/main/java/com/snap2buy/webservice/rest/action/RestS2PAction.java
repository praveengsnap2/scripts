/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2buy.webservice.rest.action;

//import com.mongodb.client.MongoDatabase;

import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.model.ShelfAnalysisInput;
import com.snap2buy.webservice.service.ProcessImageService;
import com.snap2buy.webservice.service.ProductMasterService;
import com.snap2buy.webservice.service.ShelfAnalysisService;
import com.snap2buy.webservice.util.Snap2PayOutput;
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

    public Snap2PayOutput saveImage(InputObject inputObject) {
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
        reportInput.put("responseCode", "200");
        reportInput.put("responseMessage", "Image Stored Successfully");
        reportInput.put("imageFilePath", inputObject.getImageFilePath());
        reportInput.put("headers", "UPC, Left Top X, Left Top Y, Width, Height, Promotion, Price, Price_Flag");

        LOGGER.info("column list done");

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends saveImage----------------\n");
        return reportIO;
    }

    public Snap2PayOutput getJob(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getJob----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result = processImageService.getJob(inputObject);

        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("hostId", inputObject.getHostId());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getJob----------------\n");
        return reportIO;
    }

    public Snap2PayOutput getUpcDetails(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getUpcDetails----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result = productMasterService.getUpcDetails(inputObject);
        File image = productMasterService.getUpcImage(inputObject);
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("upc", inputObject.getUpc());
        reportInput.put("imagePth", image.getAbsolutePath());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getUpcDetails----------------\n");
        return reportIO;
    }

    public Snap2PayOutput storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput) {
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


        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends storeShelfAnalysis----------------\n");
        return reportIO;
    }

    public Snap2PayOutput getShelfAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getShelfAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        result = shelfAnalysisService.getShelfAnalysis(inputObject.getImageUUID());
        LOGGER.info("getShelfAnalysis done");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getShelfAnalysis----------------\n");

        return reportIO;
    }

    public Snap2PayOutput getReport(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getReport----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

        //String query = queryGenerationService.generateQuery(inputObject);

        LOGGER.info("getShelfAnalysis done");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getReport----------------\n");

        return reportIO;
    }

    public File getUpcImage(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getUpcImage----------------\n");

        LOGGER.info("upc : " + inputObject.getUpc());

        LOGGER.info("---------------RestAction Ends getUpcImage----------------\n");

        return productMasterService.getUpcImage(inputObject);
    }

    public void storeThumbnails() {
        LOGGER.info("---------------RestAction Starts storeThumbnails----------------\n");

        productMasterService.storeThumbnails();

        LOGGER.info("---------------RestAction Ends storeThumbnails----------------\n");
    }

    public Snap2PayOutput runImageAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts runImageAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        resultListToPass = processImageService.runImageAnalysis(inputObject.getImageUUID());

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends runImageAnalysis----------------\n");

        return reportIO;
    }

    public Snap2PayOutput getImageAnalysis(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getImageAnalysis----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        resultListToPass = processImageService.getImageAnalysis(inputObject.getImageUUID());

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getImageAnalysis----------------\n");

        return reportIO;
    }
    public Snap2PayOutput getStoreOptions() {
        LOGGER.info("---------------RestAction Starts getStoreOptions----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getStoreOptions();

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("header", "retailer,state,city");

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getStoreOptions----------------\n");

        return reportIO;
    }

    public Snap2PayOutput getImages(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getImages----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getImages(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("storeId",inputObject.getStoreId());
        reportInput.put("dateId", inputObject.getDateId());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getImages----------------\n");

        return reportIO;
    }
    public Snap2PayOutput getStores(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts getStores----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.getStores(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("retailerChainCode",inputObject.getRetailerChainCode());
        reportInput.put("state", inputObject.getState());
        reportInput.put("city", inputObject.getCity());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getStores----------------\n");

        return reportIO;
    }
    public Snap2PayOutput getDistributionLists() {
        LOGGER.info("---------------RestAction Starts getDistributionLists----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = productMasterService.getDistributionLists();

        HashMap<String, String> reportInput = new HashMap<String, String>();

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends getDistributionLists----------------\n");

        return reportIO;
    }
    public Snap2PayOutput doDistributionCheck(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doDistributionCheck----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.doDistributionCheck(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("listName", inputObject.getListId());
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends doDistributionCheck----------------\n");

        return reportIO;
    }
    public Snap2PayOutput doBeforeAfterCheck(InputObject inputObject) {
        LOGGER.info("---------------RestAction Starts doBeforeAfterCheck----------------\n");
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        resultListToPass = processImageService.doBeforeAfterCheck(inputObject);

        HashMap<String, String> reportInput = new HashMap<String, String>();

        reportInput.put("prevImageUUID", inputObject.getPrevImageUUID());
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        LOGGER.info("---------------RestAction Ends doBeforeAfterCheck----------------\n");

        return reportIO;
    }
}
