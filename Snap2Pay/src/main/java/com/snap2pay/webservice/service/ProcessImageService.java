package com.snap2pay.webservice.service;

import com.snap2pay.webservice.model.InputObject;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageService {

  public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject);

  public LinkedHashMap<String, String> getJob(InputObject inputObject);
}
