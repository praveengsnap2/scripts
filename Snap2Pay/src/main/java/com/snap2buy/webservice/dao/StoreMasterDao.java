package com.snap2buy.webservice.dao;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/31/15.
 */
public interface StoreMasterDao {
    public String getStoreId(String longitude, String latitude);
    public String getRetailerChainCode(String storeId);
    public List<LinkedHashMap<String,String>> getStoreOptions();
    public List<LinkedHashMap<String, String>>  getStores(String retailerChainCode, String state,String city);
   }
