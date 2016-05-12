package com.snap2buy.webservice.dao;

import com.snap2buy.webservice.model.ShelfAnalysis;

import java.util.List;

/**
 * Created by sachin on 10/31/15.
 */
public interface ShelfAnalysisDao {
    public void storeShelfAnalysis(ShelfAnalysis shelfAnalysis);

    public ShelfAnalysis getShelfAnalysis(String imageUUID);

    public List<ShelfAnalysis> getSelfAnalysisCsv();
}
