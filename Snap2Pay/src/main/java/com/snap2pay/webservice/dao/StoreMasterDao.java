package com.snap2pay.webservice.dao;

/**
 * Created by sachin on 10/31/15.
 */
public interface StoreMasterDao {
    public String getStoreId(String longitude,String latitude);
    public String getRetailerChainCode(String storeId);
}
