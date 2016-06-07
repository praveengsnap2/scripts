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
    public List<LinkedHashMap<String, String>> listProjectUpc(String projectId);
    public List<LinkedHashMap<String, String>> listRetailer();

    public List<LinkedHashMap<String, String>> getCategoryDetail(String id);
    public List<LinkedHashMap<String, String>> getCustomerDetail(String customerCode);
    public List<LinkedHashMap<String, String>> getProjectDetail(String customerProjectId);
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(String id);
    public List<LinkedHashMap<String, String>> getSkuTypeDetail(String id);
    public List<LinkedHashMap<String, String>> getProjectUpcDetail(String customerProjectId);
    public List<LinkedHashMap<String, String>> getRetailerDetail(String retailerCode);

    public void createCustomer(Customer customerInput);
    public void createCategory(Category categoryInput);
    public void createRetailer(Retailer retailerInput);
    public void createProjectType(ProjectType projectTypeInput);
    public void createSkuType(SkuType skuTypeInput);
    public void createProject(Project projectInput);
    public void addUpcListToProjectId(List<ProjectUpc> projectUpcList);
    public void addUpcToProjectId(ProjectUpc projectUpcList);
}
