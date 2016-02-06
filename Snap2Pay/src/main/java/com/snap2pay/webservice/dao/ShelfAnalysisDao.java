package com.snap2pay.webservice.dao;

import com.snap2pay.webservice.model.ShelfAnalysis;

/**
 * Created by sachin on 10/31/15.
 */
public interface ShelfAnalysisDao {
  public void storeShelfAnalysis(ShelfAnalysis shelfAnalysis);

  public ShelfAnalysis getShelfAnalysis(String imageUUID);

}
