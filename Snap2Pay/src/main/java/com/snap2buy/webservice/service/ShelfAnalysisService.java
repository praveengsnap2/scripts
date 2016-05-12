package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.ShelfAnalysisInput;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ShelfAnalysisService {
    public void storeShelfAnalysis(ShelfAnalysisInput shelfAnalysisInput);

    public LinkedHashMap<String, String> getShelfAnalysis(String imageUUID);
    public List<LinkedHashMap<String, String>> getSelfAnalysisCsv();

}
