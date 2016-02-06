package com.snap2pay.webservice.service;

import com.snap2pay.webservice.model.InputObject;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by sachin on 1/23/16.
 */
public interface ProductMasterService {
  public LinkedHashMap<String, String> getUpcDetails(InputObject inputObject);
  public File getUpcImage(InputObject inputObject);

  void storeThumbnails();
}
