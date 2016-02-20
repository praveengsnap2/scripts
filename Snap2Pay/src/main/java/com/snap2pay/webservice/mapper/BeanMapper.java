/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.mapper;

/**
 * @author keerthanathangaraju
 */
public interface BeanMapper {
    String BEAN_REST_ACTION_S2P = "RestS2PAction";
    String BEAN_REST_CONTROLLER_S2P = "RestS2PController";


    String BEAN_PROCESS_IMAGE_SERVICE = "ProcessImageServiceImpl";
    String BEAN_SHELF_ANALYSIS_SERVICE = "ShelfAnalysisServiceImpl";
    String BEAN_PRODUCT_MASTER_SERVICE = "ProductMasterServiceImpl";


    String BEAN_IMAGE_STORE_DAO = "ProcessImageDaoImpl";
    String BEAN_SHELF_ANALYSIS_DAO = "ShelfAnalysisDaoImpl";
    String BEAN_STORE_MASTER_DAO = "StoreMasterDaoImpl";
    String BEAN_PRODUCT_MASTER_DAO = "ProductMasterDaoImpl";


}
