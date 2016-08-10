/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2buy.webservice.mapper;

/**
 * @author sachin
 */
public interface BeanMapper {
    String BEAN_REST_ACTION_S2P = "RestS2PAction";
    String BEAN_REST_CONTROLLER_S2P = "RestS2PController";


    String BEAN_PROCESS_IMAGE_SERVICE = "ProcessImageServiceImpl";
    String BEAN_SHELF_ANALYSIS_SERVICE = "ShelfAnalysisServiceImpl";
    String BEAN_PRODUCT_MASTER_SERVICE = "ProductMasterServiceImpl";
    String BEAN_QUERY_GENERATION_SERVICE = "QueryGenerationServiceImpl";
    String BEAN_META_SERVICE = "MetaServiceImpl";
    String BEAN_AUTH_SERVICE = "AuthenticationServiceImpl";
    String BEAN_SALT_SOURCE_SERVICE = "SaltSourceImpl";


    String BEAN_IMAGE_STORE_DAO = "ProcessImageDaoImpl";
    String BEAN_SHELF_ANALYSIS_DAO = "ShelfAnalysisDaoImpl";
    String BEAN_STORE_MASTER_DAO = "StoreMasterDaoImpl";
    String BEAN_PRODUCT_MASTER_DAO = "ProductMasterDaoImpl";
    String BEAN_META_SERVICE_DAO = "MetaServiceDaoImpl";
    String BEAN_AUTH_SERVICE_DAO = "AuthenticationServiceDaoImpl";

}
