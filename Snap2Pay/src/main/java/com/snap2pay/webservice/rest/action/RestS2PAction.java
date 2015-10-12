/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.rest.action;

import com.mongodb.MongoClient;
//import com.mongodb.client.MongoDatabase;
import com.snap2pay.webservice.config.SpringMongoConfig;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ShelfVisit;
//import com.snap2pay.webservice.service.ShelfVisitDAO;
import com.snap2pay.webservice.util.S2PParameters;
import com.snap2pay.webservice.util.Snap2PayOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author keerthanathangaraju
 */
@Component( value=BeanMapper.BEAN_REST_ACTION_S2P )
@Scope("prototype")
public class RestS2PAction {
    private static Logger LOGGER = Logger.getLogger("s2p");
    
//    @Autowired
//    @Qualifier( BeanMapper.BEAN_SHELF_VIST_DAO )
//    private ShelfVisitDAO shelfVisitDAO;

//   @Autowired
//    private MongoTemplate mongoTemplate;
    public Snap2PayOutput saveImage(ShelfVisit shelfVisit){
        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String,String>>();
	List<java.util.LinkedHashMap<String, String>> resultListToPass = new ArrayList<LinkedHashMap<String,String>>();
        
//         MongoClient mongoClient = new MongoClient("localhost", 27017);
//         MongoDatabase db = mongoClient.getDatabase("Snap2Pay");
//         db.getCollection("ShelfVisits").;
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//				"base-spring-ctx.xml");
//        MongoTemplate mongoOps = (MongoTemplate)context.getBean("mongoTemplate");
//        LOGGER.debug("calling insert");
//        mongoTemplate.insert(shelfVisit);


//        ApplicationContext ctx = 
//             new AnnotationConfigApplicationContext(SpringMongoConfig.class);
//        
//	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
//// save
//	mongoOperation.save(shelfVisit);

	// now user object got the created id.
	LOGGER.debug("1. user : " + shelfVisit.getUserId());
                LOGGER.debug("resultlist done");
//                shelfVisitDAO.addVisit(shelfVisit);
		HashMap<String,String> reportInput = new HashMap<String,String>();
		LinkedHashMap<String,String> result = new LinkedHashMap<String,String>();
                result.put("Message", "File saved at location:"+shelfVisit.getImageUrl());
                result.put("FileId", "1");
                resultListToPass.add(result);
		
		reportInput.put("responseCode","200");
		LOGGER.debug("response code done");

		reportInput.put("categoryId",shelfVisit.getCategoryId());
		reportInput.put("latitude",shelfVisit.getLatitude());
		reportInput.put("longitude",shelfVisit.getLongitude());
		reportInput.put("userId",shelfVisit.getUserId());
		LOGGER.debug("column list done");
		
		Snap2PayOutput reportIO = new Snap2PayOutput(resultListToPass, reportInput);
		return reportIO;
    }
    
    private ShelfVisit setShelfVisit(S2PParameters s2PParameters){
        ShelfVisit shelfVisit = new ShelfVisit();
        shelfVisit.setCategoryId(s2PParameters.getCategoryId());
        shelfVisit.setUserId(s2PParameters.getUserId());
        shelfVisit.setLatitude(s2PParameters.getLatitude());
        shelfVisit.setLongitude(s2PParameters.getLongitude());
        shelfVisit.setCategoryId(s2PParameters.getCategoryId());
        return shelfVisit;
    }
    public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"base-spring-ctx.xml");
                RestS2PAction restS2PAction = (RestS2PAction)context.getBean("RestS2PAction");
                LOGGER.info("Checking logger");
//		restS2PAction.printHello();
	}
}
