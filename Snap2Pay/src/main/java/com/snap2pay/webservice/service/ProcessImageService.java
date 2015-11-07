package com.snap2pay.webservice.service;

import com.snap2pay.webservice.model.ImageStore;
import com.snap2pay.webservice.model.InputObject;

import java.util.LinkedHashMap;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageService {

  public void storeImageDetails(InputObject inputObject);

  public LinkedHashMap<String, String> getJob(InputObject inputObject);
}
