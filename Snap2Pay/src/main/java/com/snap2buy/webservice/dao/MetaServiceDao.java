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
    public List<LinkedHashMap<String, String>> listProject();
    public List<LinkedHashMap<String, String>> listProjectType();
    public List<LinkedHashMap<String, String>> listProjectUpc();
    public List<LinkedHashMap<String, String>> listRetailer();

    public List<LinkedHashMap<String, String>> getCategoryDetail(String id);
    public List<LinkedHashMap<String, String>> getCustomerDetail(String id);
    public List<LinkedHashMap<String, String>> getProjectDetail(String id);
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(String id);
    public List<LinkedHashMap<String, String>> getProjectUpcDetail(String id);
    public List<LinkedHashMap<String, String>> getRetailerDetail(String id);

    public Integer createCustomer(Customer customerInput);
    public Integer createCategory(Category categoryInput);
    public Integer createRetailer(Retailer retailerInput);
    public Integer createProjectType(ProjectType projectTypeInput);
    public Integer createProject(Project projectInput);
    public void addUpcListToProjectId(List<ProjectUpc> projectUpcList);
    public void addUpcToProjectId(ProjectUpc projectUpcList);
}
