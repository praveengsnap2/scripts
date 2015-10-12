///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.snap2pay.webservice.service.impl;
//
////import com.snap2pay.webservice.service.impl.*;
//import com.snap2pay.webservice.service.*;
//import com.snap2pay.webservice.mapper.BeanMapper;
//import com.snap2pay.webservice.model.ShelfVisit;
////import com.snap2pay.webservice.service.ShelfVisitDAO;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.stereotype.Service;
//
///**
// *
// * @author keerthanathangaraju
// */
//@Repository( value=BeanMapper.BEAN_SHELF_VIST_DAO)
//public class ShelfVisitDAOImpl implements ShelfVisitDAO{
//    
//    @Autowired
//    private MongoTemplate mongoTemplate;
//    
//    public static final String COLLECTION_NAME = "shelfvisit";
//    
//    public void addVisit(ShelfVisit shelfVisit) {
//        if (!mongoTemplate.collectionExists(ShelfVisit.class)) {
//            mongoTemplate.createCollection(ShelfVisit.class);
//        }
//        shelfVisit.setVisitId(UUID.randomUUID().toString());
//        mongoTemplate.insert(shelfVisit, COLLECTION_NAME);
//    }
//
//    public List<ShelfVisit> listShelfVisit() {
//        return mongoTemplate.findAll(ShelfVisit.class, COLLECTION_NAME);
//    }
//
//    public void deleteVisit(ShelfVisit shelfVisit) {
//        mongoTemplate.remove(ShelfVisit.class, COLLECTION_NAME);
//    }
//
//    public void updateVisit(ShelfVisit shelfVisit) {
//        mongoTemplate.insert(ShelfVisit.class, COLLECTION_NAME);
//    }
//    
//}
