package com.snap2buy.webservice.dao;

import com.snap2buy.webservice.model.DistributionList;
import com.snap2buy.webservice.model.ProductMaster;

import java.io.File;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProductMasterDao {

    public ProductMaster getUpcDetails(String upc);

    public void storeThumbnails();

    public File getThumbnails(String upc);

    public List<DistributionList> getDistributionLists();

    public List<String> getUpcForList(String listId);

}
