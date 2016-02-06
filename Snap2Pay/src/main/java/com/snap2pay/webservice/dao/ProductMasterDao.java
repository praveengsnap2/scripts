package com.snap2pay.webservice.dao;

import com.snap2pay.webservice.model.ProductMaster;

import java.io.File;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProductMasterDao {

    public ProductMaster getUpcDetails(String upc);
    public void storeThumbnails();
    public File getThumbnails(String upc);
}
