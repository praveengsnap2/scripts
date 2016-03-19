package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.InputObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageService {

    public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getImageAnalysis (String imageUUID);
    public List<LinkedHashMap<String, String>> runImageAnalysis (String imageUUID);
    public LinkedHashMap<String, String> getJob(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getStoreOptions();
    public List<LinkedHashMap<String, String>> getImages(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getStores(InputObject inputObject);
    public List<LinkedHashMap<String, String>> doDistributionCheck(InputObject inputObject);
    public List<LinkedHashMap<String, String>> doBeforeAfterCheck(InputObject inputObject);
}