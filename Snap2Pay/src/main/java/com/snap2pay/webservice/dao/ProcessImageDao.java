package com.snap2pay.webservice.dao;

import com.snap2pay.webservice.model.ImageStore;

import java.util.LinkedHashMap;

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
}
