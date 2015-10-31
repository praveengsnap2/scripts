package com.snap2pay.webservice.service;

import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.model.ShelfAnalysis;
import com.snap2pay.webservice.model.ShelfAnalysisInput;

import java.util.LinkedHashMap;

/**
 * Created by sachin on 10/17/15.
 */
public interface ShelfAnalysisService {
    public void storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput);
    public LinkedHashMap<String, String> getShelfAnalysis(String imageUUID);
}
