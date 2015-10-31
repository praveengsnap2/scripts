/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.rest.action;

//import com.mongodb.client.MongoDatabase;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.InputObject;
//import com.snap2pay.webservice.service.ShelfVisitDAO;
import com.snap2pay.webservice.model.ShelfAnalysisInput;
import com.snap2pay.webservice.service.ProcessImageService;
import com.snap2pay.webservice.service.ShelfAnalysisService;
import com.snap2pay.webservice.util.Snap2PayOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
        import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
        import org.springframework.stereotype.Component;

/**
 * @author keerthanathangaraju
 */
@Component(value = BeanMapper.BEAN_REST_ACTION_S2P)
@Scope("prototype")
public class RestS2PAction {
    private static Logger LOGGER = Logger.getLogger("s2p");

        @Autowired
        @Qualifier( BeanMapper.BEAN_PROCESS_IMAGE_SERVICE)
        private ProcessImageService processImageService;

        @Autowired
        @Qualifier( BeanMapper.BEAN_SHELF_ANALYSIS_SERVICE)
        private ShelfAnalysisService shelfAnalysisService;


    public Snap2PayOutput saveImage(InputObject inputObject) {
        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("user : " + inputObject.getUserId());
        processImageService.storeImageDetails(inputObject);
        LOGGER.info("StoreImageDetails done");

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode","200");
        result.put("responseMessage", "Image Stored Successfully");
        result.put("filePath",inputObject.getImageFilePath());
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("categoryId", inputObject.getCategoryId());
        reportInput.put("latitude", inputObject.getLatitude());
        reportInput.put("longitude", inputObject.getLongitude());
        reportInput.put("userId", inputObject.getUserId());
        reportInput.put("TimeStamp", inputObject.getTimeStamp());
        LOGGER.debug("column list done");

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        return reportIO;
    }

    public Snap2PayOutput getJob(InputObject inputObject) {
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result=processImageService.getJob(inputObject);

        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("hostId", inputObject.getHostId());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        return reportIO;
    }

    public Snap2PayOutput storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput){
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LOGGER.info("imageUUID : " + shelfAnalysisInput.getImageUUID());
        shelfAnalysisService.storeShelfAnalysis(shelfAnalysisInput);
        LOGGER.info("StoreShelfAnalysis done");


        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        result.put("responseCode","200");
        result.put("responseMessage", "ShelfAnalysis Stored Successfully");
        resultListToPass.add(result);


        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", shelfAnalysisInput.getImageUUID());
        reportInput.put("storeId", shelfAnalysisInput.getStoreId());
        reportInput.put("categoryId", shelfAnalysisInput.getCategoryId());


        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        return reportIO;
    }

    public Snap2PayOutput getShelfAnalysis(InputObject inputObject){
        List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String, String>>();

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        LOGGER.info("imageUUID : " + inputObject.getImageUUID());
        result = shelfAnalysisService.getShelfAnalysis(inputObject.getImageUUID());
        LOGGER.info("getShelfAnalysis done");
        resultListToPass.add(result);

        HashMap<String, String> reportInput = new HashMap<String, String>();
        reportInput.put("imageUUID", inputObject.getImageUUID());

        Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
        return reportIO;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "base-spring-ctx.xml");
        RestS2PAction restS2PAction = (RestS2PAction) context.getBean("RestS2PAction");
        LOGGER.info("Checking logger");
        InputObject inputObject = new InputObject();
        inputObject.setCategoryId("test");
        inputObject.setLatitude("0000.0000");
        inputObject.setLongitude("0000.0000");
        inputObject.setTimeStamp("2008-01-01 00:00:01");
        inputObject.setUserId("agsachin");
        restS2PAction.saveImage(inputObject);
    }
}
