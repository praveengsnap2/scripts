package com.snap2pay.webservice.dao;

import com.snap2pay.webservice.model.ImageStore;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageDao {

  public void insert(ImageStore imageStore);

  public ImageStore findByImageUUId(String imageUUId);

  public ImageStore getImageByStatus(String status);

  public Integer getJobCount(String status);

  public void updateStatusAndHost(String hostId, String status, String imageUUID);
  public void updateStatus(String status, String imageUUID);
  public void updateStoreId(String storeId, String imageUUID);
  public String invokeImageAnalysis(String image, String category, String uuid, String retailer, String store);
}
