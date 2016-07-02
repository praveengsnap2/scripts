package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.model.ProductMaster;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 1/23/16.
 */
public interface ProductMasterService {
    public LinkedHashMap<String, String> getUpcDetails(InputObject inputObject);

    public File getUpcImage(InputObject inputObject);

    void storeThumbnails(String imageFolderPath);

    public List<LinkedHashMap<String, String>> getDistributionLists();

	public void createUpc(ProductMaster upcInput);
}

