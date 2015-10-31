package com.snap2pay.webservice.service.impl;

import com.snap2pay.webservice.dao.ShelfAnalysisDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.*;
import com.snap2pay.webservice.service.ShelfAnalysisService;
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


    @Autowired
    @Qualifier( BeanMapper.BEAN_SHELF_ANALYSIS_DAO )
    private ShelfAnalysisDao shelfAnalysisDao;

    @Override
    public void storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput) {

        ShelfAnalysis shelfAnalysis=new ShelfAnalysis();

        List<Skus> skus=shelfAnalysisInput.getSkus();

        for(Skus s:skus){
            shelfAnalysis.setImageUUID(shelfAnalysisInput.getImageUUID());
            shelfAnalysis.setUpc(s.getUpc());
            shelfAnalysis.setPog(s.getPog());
            shelfAnalysis.setOsa(s.getOsa());
            shelfAnalysis.setFacings(s.getFacings());
            shelfAnalysis.setPriceLabel(s.getPriceLabel());
            shelfAnalysis.setStoreId(shelfAnalysisInput.getStoreId());
            shelfAnalysis.setCategoryId(shelfAnalysisInput.getCategoryId());
            shelfAnalysisDao.storeShelfAnalysis(shelfAnalysis);
        }
    }

    @Override
    public LinkedHashMap<String, String> getShelfAnalysis(String imageUUID) {
        ShelfAnalysis shelfAnalysis = shelfAnalysisDao.getShelfAnalysis(imageUUID);
        LinkedHashMap<String, String> shelfAnalysisResult= new LinkedHashMap<String, String>();
        shelfAnalysisResult.put("imageUUID",shelfAnalysis.getImageUUID());
        shelfAnalysisResult.put("upc",shelfAnalysis.getUpc());
        shelfAnalysisResult.put("pog",shelfAnalysis.getPog());
        shelfAnalysisResult.put("osa",shelfAnalysis.getOsa());
        shelfAnalysisResult.put("facing",shelfAnalysis.getFacings());
        shelfAnalysisResult.put("priceLabel",shelfAnalysis.getPriceLabel());
        shelfAnalysisResult.put("storeId",shelfAnalysis.getStoreId());
        shelfAnalysisResult.put("categoryId",shelfAnalysis.getCategoryId());

    return shelfAnalysisResult;
    }
}
