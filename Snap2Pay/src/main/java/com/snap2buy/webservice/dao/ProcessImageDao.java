package com.snap2buy.webservice.dao;

import com.snap2buy.webservice.model.ImageAnalysis;
import com.snap2buy.webservice.model.ImageStore;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageDao {

    public ImageStore findByImageUUId(String imageUUId);
    public ImageStore getImageByStatus(String status);
    public Integer getJobCount(String status);
    public String getImageAnalysisStatus(String imageUUID);
    public void insert(ImageStore imageStore);
    public void storeImageAnalysis(List<ImageAnalysis> ImageAnalysisList,ImageStore imageStore);
    public void updateStatusAndHost(String hostId, String status, String imageUUID);
    public void updateImageAnalysisStatus(String status, String imageUUID);
    public void updateShelfAnalysisStatus(String status, String imageUUID);
    public void updateStoreId(String storeId, String imageUUID);
    public List<LinkedHashMap<String,String>> getImages(String storeId, String dateId);
    public List<ImageAnalysis> getImageAnalysis(String imageUUID);
    public LinkedHashMap<String,Object> getFacing(String imageUUID);
    public List<LinkedHashMap<String,String>> doShareOfShelfAnalysis(String getImageUUIDCsvString);
    public List<LinkedHashMap<String,String>> doShareOfShelfAnalysisCsv();
    public void updateLatLong(String imageUUID,String latitude,String longitude);
}
