package com.snap2buy.webservice.service.impl;

import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.dao.ShelfAnalysisDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.ShelfAnalysis;
import com.snap2buy.webservice.model.ShelfAnalysisInput;
import com.snap2buy.webservice.model.Skus;
import com.snap2buy.webservice.service.ShelfAnalysisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_SHELF_ANALYSIS_SERVICE)
@Scope("prototype")
public class ShelfAnalysisServiceImpl implements ShelfAnalysisService {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    @Qualifier(BeanMapper.BEAN_SHELF_ANALYSIS_DAO)
    private ShelfAnalysisDao shelfAnalysisDao;

    @Autowired
    @Qualifier(BeanMapper.BEAN_IMAGE_STORE_DAO)
    private ProcessImageDao processImageDao;

    @Override
    public void storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput) {
        LOGGER.info("---------------ShelfAnalysisServiceImpl Starts storeShelfAnalysis----------------\n");

        ShelfAnalysis shelfAnalysis = new ShelfAnalysis();

        List<Skus> skus = shelfAnalysisInput.getSkus();

        for (Skus s : skus) {
            shelfAnalysis.setImageUUID(shelfAnalysisInput.getImageUUID());
            shelfAnalysis.setUpc(s.getUpc());
            shelfAnalysis.setExpected_facings(s.getExpected_facings());
            shelfAnalysis.setOn_shelf_availability(s.getOn_shelf_availability());
            shelfAnalysis.setDetected_facings(s.getDetected_facings());
            shelfAnalysis.setPromotion_label_present(s.getPromotion_label_present());
            shelfAnalysis.setPrice(s.getPrice());
            shelfAnalysis.setPromo_price(s.getPromo_price());
            shelfAnalysis.setStoreId(shelfAnalysisInput.getStoreID());
            shelfAnalysis.setCategoryId(shelfAnalysisInput.getCategoryID());
            shelfAnalysisDao.storeShelfAnalysis(shelfAnalysis);
        }
        String status = "done";
        processImageDao.updateStatus(status, shelfAnalysisInput.getImageUUID());
        LOGGER.info("---------------ShelfAnalysisServiceImpl Ends storeShelfAnalysis----------------\n");

    }

    @Override
    public LinkedHashMap<String, String> getShelfAnalysis(String imageUUID) {
        LOGGER.info("---------------ShelfAnalysisServiceImpl Starts getShelfAnalysis----------------\n");

        ShelfAnalysis shelfAnalysis = shelfAnalysisDao.getShelfAnalysis(imageUUID);
        LinkedHashMap<String, String> shelfAnalysisResult = new LinkedHashMap<String, String>();
        shelfAnalysisResult.put("imageUUID", shelfAnalysis.getImageUUID());
        shelfAnalysisResult.put("upc", shelfAnalysis.getUpc());
        shelfAnalysisResult.put("pog", shelfAnalysis.getExpected_facings());
        shelfAnalysisResult.put("osa", shelfAnalysis.getOn_shelf_availability());
        shelfAnalysisResult.put("facing", shelfAnalysis.getDetected_facings());
        shelfAnalysisResult.put("priceLabel", shelfAnalysis.getPrice());
        shelfAnalysisResult.put("storeId", shelfAnalysis.getStoreId());
        shelfAnalysisResult.put("categoryId", shelfAnalysis.getCategoryId());
        LOGGER.info("---------------ShelfAnalysisServiceImpl Ends getShelfAnalysis----------------\n");

        return shelfAnalysisResult;
    }

}
