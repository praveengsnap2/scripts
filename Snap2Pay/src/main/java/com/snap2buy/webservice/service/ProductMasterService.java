package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.InputObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 1/23/16.
 */
public interface ProductMasterService {
    public LinkedHashMap<String, String> getUpcDetails(InputObject inputObject);

    public File getUpcImage(InputObject inputObject);

    void storeThumbnails();

    public List<LinkedHashMap<String, String>> getDistributionLists(InputObject inputObject);
}
