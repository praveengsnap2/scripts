/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.rest.action;

//import com.mongodb.client.MongoDatabase;

import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.model.ShelfAnalysisInput;
import com.snap2pay.webservice.service.ProcessImageService;
import com.snap2pay.webservice.service.ProductMasterService;
import com.snap2pay.webservice.service.ShelfAnalysisService;
import com.snap2pay.webservice.util.Snap2PayOutput;
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
 * @author keerthanathangaraju
 */
@Component(value = BeanMapper.BEAN_REST_ACTION_S2P)
@Scope("prototype")
public class RestS2PAction {
  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  @Qualifier(BeanMapper.BEAN_PROCESS_IMAGE_SERVICE)
  private ProcessImageService processImageService;

  @Autowired
  @Qualifier(BeanMapper.BEAN_PRODUCT_MASTER_SERVICE)
  private ProductMasterService productMasterService;


  @Autowired
  @Qualifier(BeanMapper.BEAN_SHELF_ANALYSIS_SERVICE)
  private ShelfAnalysisService shelfAnalysisService;


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
    reportInput.put("latitude", inputObject.getLatitude());
    reportInput.put("longitude", inputObject.getLongitude());
    reportInput.put("userId", inputObject.getUserId());
    reportInput.put("TimeStamp", inputObject.getTimeStamp());
    reportInput.put("responseCode", "200");
    reportInput.put("responseMessage", "Image Stored Successfully");
    reportInput.put("filePath", inputObject.getImageFilePath());
    reportInput.put("headers","UPC, Left Top X, Left Top Y, Width, Height, Promotion, Price, Price_Flag");

    LOGGER.debug("column list done");

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
    reportInput.put("imagePth",image.getAbsolutePath());

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

  public File getUpcImage(InputObject inputObject) {
    LOGGER.info("---------------RestAction Starts getUpcImage----------------\n");

    LOGGER.info("upc : " + inputObject.getUpc());

    LOGGER.info("---------------RestAction Ends getUpcImage----------------\n");

    return productMasterService.getUpcImage(inputObject);
  }

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

  public void storeThumbnails() {
    LOGGER.info("---------------RestAction Starts getShelfAnalysis----------------\n");

    productMasterService.storeThumbnails();

    LOGGER.info("getShelfAnalysis done");


    LOGGER.info("---------------RestAction Ends getShelfAnalysis----------------\n");
  }
}
