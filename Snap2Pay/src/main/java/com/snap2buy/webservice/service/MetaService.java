package com.snap2buy.webservice.service;

import com.snap2buy.webservice.model.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface MetaService {

    public List<LinkedHashMap<String, String>> listCategory();
    public List<LinkedHashMap<String, String>> listCustomer();
    public List<LinkedHashMap<String, String>> listProject(InputObject inputObject);
    public List<LinkedHashMap<String, String>> listProjectType();
    public List<LinkedHashMap<String, String>> listSkuType();
    public List<LinkedHashMap<String, String>> listProjectUpc(InputObject inputObject);
    public List<LinkedHashMap<String, String>> listRetailer();

    public List<LinkedHashMap<String, String>> getCategoryDetail(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getCustomerDetail(InputObject inputObject);
    public List<Project> getProjectDetail(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getSkuTypeDetail(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getProjectUpcDetail(InputObject inputObject);
    public List<LinkedHashMap<String, String>> getRetailerDetail(InputObject inputObject);

    public void createCustomer(Customer customerInput);
    public void createCategory(Category categoryInput);
    public void createRetailer(Retailer retailerInput);
    public void createProjectType(ProjectType projectTypeInput);
    public void createSkuType(SkuType skuTypeInput);
    public void createProject(Project projectInput);
    public void addUpcToProjectId(ProjectUpc projectUpc);

    public void createStore(StoreMaster storeMaster);
    public void updateStore(StoreMaster storeMaster);
    public void updateProject(Project projectInput);
    public List<LinkedHashMap<String, String>> listStores();
    public List<LinkedHashMap<String, String>> getStoreDetail(InputObject inputObject);

    public List<LinkedHashMap<String, String>> getProjectSummary(InputObject inputObject);

}
