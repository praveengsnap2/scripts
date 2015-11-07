package com.snap2pay.webservice.service.impl;

import com.snap2pay.webservice.dao.ProcessImageDao;
import com.snap2pay.webservice.dao.StoreMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ImageStore;
import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.service.ProcessImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_PROCESS_IMAGE_SERVICE)
@Scope("prototype")
public class ProcessImageServiceImpl implements ProcessImageService {

  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  @Qualifier(BeanMapper.BEAN_IMAGE_STORE_DAO)
  private ProcessImageDao processImageDao;

  @Autowired
  @Qualifier(BeanMapper.BEAN_STORE_MASTER_DAO)
  private StoreMasterDao storeMasterDao;

  @Override
  public void storeImageDetails(InputObject inputObject) {

    LOGGER.info("---------------ProcessImageServiceImpl Starts storeImageDetails----------------\n");

    ImageStore imageStore = new ImageStore();
    imageStore.setImageUUID(inputObject.getImageUUID());
    imageStore.setImageFilePath(inputObject.getImageFilePath());
    imageStore.setUserId(inputObject.getUserId());
    imageStore.setCategoryId(inputObject.getCategoryId());
    imageStore.setLatitude(inputObject.getLatitude());
    imageStore.setLongitude(inputObject.getLongitude());
    imageStore.setTimeStamp(inputObject.getTimeStamp());
    imageStore.setStoreId(storeMasterDao.getStoreId(inputObject.getLongitude(), inputObject.getLatitude()));
    imageStore.setStatus("new");
    processImageDao.insert(imageStore);
    LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails----------------\n");


  }

  @Override
  public LinkedHashMap<String, String> getJob(InputObject inputObject) {
    LOGGER.info("---------------ProcessImageServiceImpl Starts getJob----------------\n");


    LinkedHashMap<String, String> unProcessedJob = new LinkedHashMap<String, String>();
    String status = "new";
    Integer newJobCount = processImageDao.getJobCount(status);
    if (newJobCount > 1) {

      ImageStore imageStore = processImageDao.getImageByStatus(status);
      status = "processing";
      LOGGER.info("got this job imageStore" + imageStore.toString());
      processImageDao.updateStatusAndHost(inputObject.getHostId(), status, imageStore.getImageUUID());

      String retailerChainCode = storeMasterDao.getRetailerChainCode(imageStore.getStoreId());

      Integer remainingJob = newJobCount - 1;
      unProcessedJob.put("imageUUID", imageStore.getImageUUID());
      unProcessedJob.put("categoryId", imageStore.getCategoryId());
      unProcessedJob.put("imageFilePath", imageStore.getImageFilePath());
      unProcessedJob.put("latitude", imageStore.getLatitude());
      unProcessedJob.put("longitude", imageStore.getLongitude());
      unProcessedJob.put("storeId", imageStore.getStoreId());
      unProcessedJob.put("timeStamp", imageStore.getTimeStamp());
      unProcessedJob.put("userId", imageStore.getUserId());
      unProcessedJob.put("retailerChainCode", retailerChainCode);


      unProcessedJob.put("remainingJob", remainingJob.toString());
    } else {
      unProcessedJob.put("remainingJob", newJobCount.toString());
    }
    LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails----------------\n");

    return unProcessedJob;
  }
}
