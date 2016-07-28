package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageService {

    public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getImageAnalysis (String imageUUID);
    public List<LinkedHashMap<String, String>> doShareOfShelfAnalysis (InputObject inputObject);
    public List<LinkedHashMap<String, String>> runImageAnalysis (String imageUUID);
    public List<LinkedHashMap<String, String>> processNextImage();
    public LinkedHashMap<String, String> getJob(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getStoreOptions();
    public List<LinkedHashMap<String, String>> getImages(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectStoreImages(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getStores(InputObject inputObject);
    public List<LinkedHashMap<String, String>> doDistributionCheck(InputObject inputObject);
    public List<LinkedHashMap<String, String>> doBeforeAfterCheck(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getImageMetaData(InputObject inputObject);
    public void updateLatLong(InputObject inputObject);
    public File doShareOfShelfAnalysisCsv(InputObject inputObject,String tempFilePath);
    public List<LinkedHashMap<String, String>> generateAggs(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectStoreResults(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectTopStores(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectBottomStores(InputObject inputObject);
	public List<StoreWithImages> getProjectStoresWithNoUPCs(InputObject inputObject);
	public List<StoreWithImages> getProjectAllStoreImages(InputObject inputObject);
	public List<DuplicateImages> getProjectStoresWithDuplicateImages(InputObject inputObject);
}
