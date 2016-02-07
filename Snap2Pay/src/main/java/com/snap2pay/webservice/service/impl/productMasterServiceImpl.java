package com.snap2pay.webservice.service.impl;

import com.snap2pay.webservice.dao.ProductMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.model.ProductMaster;
import com.snap2pay.webservice.service.ProductMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_PRODUCT_MASTER_SERVICE)
@Scope("prototype")
public class productMasterServiceImpl implements ProductMasterService {

  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  @Qualifier(BeanMapper.BEAN_PRODUCT_MASTER_DAO)
  private ProductMasterDao productMasterDao;

  @Override
  public LinkedHashMap<String, String> getUpcDetails(InputObject inputObject) {
    LOGGER.info("---------------ProductMasterDao Starts getUpcDetails----------------\n");


    LinkedHashMap<String, String> upcDetails = new LinkedHashMap<String, String>();
    ProductMaster productMaster= productMasterDao.getUpcDetails(inputObject.getUpc());

    upcDetails.put("upc",productMaster.getUpc());
    upcDetails.put("mfg_name",productMaster.getMfg_name());
    upcDetails.put("brand_name",productMaster.getBrand_name());
    upcDetails.put("product_type",productMaster.getProduct_type());
    upcDetails.put("product_short_name",productMaster.getProduct_short_name());
    upcDetails.put("product_long_name",productMaster.getProduct_long_name());
    upcDetails.put("low_fat",productMaster.getLow_fat());
    upcDetails.put("fat_free",productMaster.getFat_free());
    upcDetails.put("gluten_free",productMaster.getGluten_free());
    upcDetails.put("why_buy_1",productMaster.getWhy_buy_1());
    upcDetails.put("why_buy_2",productMaster.getWhy_buy_2());
    upcDetails.put("why_buy_3",productMaster.getWhy_buy_3());
    upcDetails.put("why_buy_4",productMaster.getWhy_buy_4());
    upcDetails.put("romance_copy_1",productMaster.getRomance_copy_1());
    upcDetails.put("romance_copy_2",productMaster.getRomance_copy_2());
    upcDetails.put("romance_copy_3",productMaster.getRomance_copy_3());
    upcDetails.put("romance_copy_4",productMaster.getRomance_copy_4());
    upcDetails.put("height",productMaster.getHeight());
    upcDetails.put("width",productMaster.getWidth());
      upcDetails.put("depth",productMaster.getDepth());
      upcDetails.put("product_rating",productMaster.getProduct_rating());

    LOGGER.info("---------------ProductMasterDao Ends getUpcDetails----------------\n");

    return upcDetails;
  }

  @Override
  public File getUpcImage(InputObject inputObject) {
    LOGGER.info("---------------ProductMasterDao Starts getUpcDetails----------------\n");
    LOGGER.info("upc : " + inputObject.getUpc());
    LOGGER.info("---------------ProductMasterDao Ends getUpcDetails----------------\n");
    return productMasterDao.getThumbnails(inputObject.getUpc());
  }

  @Override
  public void storeThumbnails() {
    LOGGER.info("---------------ProductMasterDao Starts storeThumbnails----------------\n");
    productMasterDao.storeThumbnails();
    LOGGER.info("---------------ProductMasterDao Ends storeThumbnails----------------\n");
  }
}
