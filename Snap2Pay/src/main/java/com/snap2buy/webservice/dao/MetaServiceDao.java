package com.snap2buy.webservice.dao;

import com.snap2buy.webservice.model.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
public interface MetaServiceDao {

    public List<LinkedHashMap<String, String>> listCategory();
    public List<LinkedHashMap<String, String>> listCustomer();
    public List<LinkedHashMap<String, String>> listProject(String customerCode);
    public List<LinkedHashMap<String, String>> listProjectType();
    public List<LinkedHashMap<String, String>> listSkuType();
    public List<LinkedHashMap<String, String>> listProjectUpc(String customerProjectId, String customerCode);
    public List<LinkedHashMap<String, String>> listRetailer();

    public List<LinkedHashMap<String, String>> getCategoryDetail(String id);
    public List<LinkedHashMap<String, String>> getCustomerDetail(String customerCode);
    public List<LinkedHashMap<String, String>> getProjectDetail(String customerProjectId, String customerCode);
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(String id);
    public List<LinkedHashMap<String, String>> getSkuTypeDetail(String id);
    public List<ProjectUpc> getProjectUpcDetail(String customerProjectId, String customerCode);
    public List<LinkedHashMap<String, String>> getRetailerDetail(String retailerCode);

    public void createCustomer(Customer customerInput);
    public void createCategory(Category categoryInput);
    public void createRetailer(Retailer retailerInput);
    public void createProjectType(ProjectType projectTypeInput);
    public void createSkuType(SkuType skuTypeInput);
    public void createProject(Project projectInput);
    public void updateProject(Project projectInput);
    public void addUpcListToProjectId(List<ProjectUpc> projectUpcList, String customerProjectId, String customerCode);
    public void updateUpcListToProjectId(List<ProjectUpc> projectUpcList, String customerProjectId, String customerCode);
    public void addUpcToProjectId(ProjectUpc projectUpcList);

    public List<LinkedHashMap<String, String>> listStores();
    public void createStore(StoreMaster storeMaster);
    public void updateStore(StoreMaster storeMaster);
    public List<LinkedHashMap<String, String>> getStoreDetail(String storeId);

    public List<LinkedHashMap<String, String>> getProjectSummary(String customerProjectId, String customerCode);
}
