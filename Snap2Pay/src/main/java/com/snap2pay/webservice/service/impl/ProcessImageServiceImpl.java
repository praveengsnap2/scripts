package com.snap2pay.webservice.service.impl;

import com.snap2pay.webservice.dao.ProcessImageDao;
import com.snap2pay.webservice.dao.ShelfAnalysisDao;
import com.snap2pay.webservice.dao.StoreMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ImageStore;
import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.service.ProcessImageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

  @Autowired
  @Qualifier(BeanMapper.BEAN_SHELF_ANALYSIS_DAO)
  private ShelfAnalysisDao shelfAnalysisDao;


  @Override
  public List<LinkedHashMap<String, String>> storeImageDetails(InputObject inputObject) {

    LOGGER.info("---------------ProcessImageServiceImpl Starts storeImageDetails----------------\n");

    ImageStore imageStore = new ImageStore();
    imageStore.setImageUUID(inputObject.getImageUUID());
    imageStore.setImageFilePath(inputObject.getImageFilePath());
    imageStore.setUserId(inputObject.getUserId());
    imageStore.setCategoryId(inputObject.getCategoryId());
    imageStore.setLatitude(inputObject.getLatitude());
    imageStore.setLongitude(inputObject.getLongitude());
    imageStore.setTimeStamp(inputObject.getTimeStamp());
      String storeId=storeMasterDao.getStoreId(inputObject.getLongitude(), inputObject.getLatitude());

      LOGGER.info("--------------storeId="+storeId+"-----------------\n");

      imageStore.setStoreId(storeId);
      imageStore.setStatus("new");
    processImageDao.insert(imageStore);

    if (inputObject.getSync().equals("true")) {
        String retailerChainCode = storeMasterDao.getRetailerChainCode(storeId);
        LOGGER.info("--------------retailerChainCode=" + retailerChainCode + "-----------------\n");
        String result = processImageDao.invokeImageAnalysis(inputObject.getImageFilePath(), inputObject.getCategoryId(), inputObject.getImageUUID(), retailerChainCode, storeId);
        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails   sync----------------\n");
        return generateData();
    }else
        {LOGGER.info("---------------ProcessImageServiceImpl Ends storeImageDetails   not sync----------------\n");

            return generateData();
        }
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
      String storeId=storeMasterDao.getStoreId(imageStore.getLongitude(),imageStore.getLatitude());
      processImageDao.updateStoreId(storeId,imageStore.getImageUUID());
      String retailerChainCode = storeMasterDao.getRetailerChainCode(storeId);

      Integer remainingJob = newJobCount - 1;
      unProcessedJob.put("imageUUID", imageStore.getImageUUID());
      unProcessedJob.put("categoryId", imageStore.getCategoryId());
      unProcessedJob.put("imageFilePath", imageStore.getImageFilePath());
      unProcessedJob.put("latitude", imageStore.getLatitude());
      unProcessedJob.put("longitude", imageStore.getLongitude());
      unProcessedJob.put("storeId", storeId);
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
    private List<LinkedHashMap<String, String>> generateData() {
        List<java.util.LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();


        String sampleData= "884912017864, 0, 0, 365, 473, Y, 4.9, STORE LOW\n" +
                "884912017864, 356, 0, 411, 482, Y, 4.9, STORE LOW\n" +
                "884912005632, 786, 0, 318, 469, Y, 2.5, STORE AVERAGE\n" +
                "884912005632, 1109, 0, 366, 469, Y, 2.5, STORE AVERAGE\n" +
                "038000576287, 1749, 0, 391, 551, N, 4.5, STORE HIGH\n" +
                "884912112217, 0, 630, 210, 537, N, 1.9, STORE LOW\n" +
                "884912112217, 205, 630, 386, 601, N, 1.9, STORE LOW\n" +
                "038000596827, 596, 654, 356, 528, N, 3.5, STORE AVERAGE\n" +
                "038000318290, 943, 679, 317, 493, N, 2.5, STORE AVERAGE\n" +
                "038000318344, 1270, 630, 391, 601, N, 2.1, STORE AVERAGE\n" +
                "038000318344, 1666, 640, 278, 571, N, 2.1, STORE AVERAGE\n" +
                "038000318344, 1964, 630, 391, 601, N, 2.1, STORE AVERAGE\n" +
                "038000318290, 1109, 1324, 391, 601, Y, 2.5, STORE AVERAGE\n" +
                "038000318290, 1431, 1324, 391, 601, Y, 2.5, STORE AVERAGE\n" +
                "884912003911, 29, 2066, 322, 494, N, 1.9, STORE AVERAGE\n" +
                "884912003911, 376, 2066, 322, 494, N, 1.9, STORE AVERAGE\n" +
                "884912003911, 679, 2066, 322, 494, N, 1.9, STORE AVERAGE\n" +
                "038000576249, 1001, 2018, 391, 601, Y, 2.5, STORE AVERAGE\n" +
                "038000576249, 1373, 2066, 322, 494, Y, 2.5, STORE AVERAGE\n" +
                "038000576348, 1641, 2018, 391, 601, N, 1.6, STORE LOW\n" +
                "038000576348, 2018, 2018, 390, 601, N, 1.6, STORE LOW\n" +
                "884912003911, 43, 2658, 391, 601, Y, 1.9, STORE AVERAGE\n" +
                "884912003911, 420, 2658, 386, 601, Y, 1.9, STORE AVERAGE\n" +
                "038000318467, 781, 2628, 367, 562, N, 2.9, STORE AVERAGE\n" +
                "038000318467, 1153, 2687, 293, 537, N, 2.9, STORE AVERAGE\n" +
                "038000318467, 1431, 2604, 391, 601, N, 2.9, STORE AVERAGE\n" +
                "038000786471, 1817, 2746, 298, 483, N, 2.9, STORE HIGH";

        String headerString ="UPC, Left Top X, Left Top Y, Width, Height, Promotion, Price, Price_Flag";
        String[] line = sampleData.split("\n");

        for (String s :line){
            String[] column = s.split(", ");
            String[] headers = headerString.split(", ");
            java.util.LinkedHashMap<String, String> x = new java.util.LinkedHashMap<String, String>();
            for (int i=0; i <headers.length ;i++){
                LOGGER.info("---------------headers[i],column[i]----------------\n"+headers[i]+":"+column[i]);
                x.put(headers[i], column[i]);
            }
            resultList.add(x);
        }

        return resultList;
    }

}
