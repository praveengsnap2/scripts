package com.snap2buy.webservice.service.impl;

import com.snap2buy.webservice.dao.MetaServiceDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
import com.snap2buy.webservice.service.MetaService;
import com.snap2buy.webservice.util.ConverterUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_META_SERVICE)
@Scope("prototype")
public class MetaServiceImpl implements MetaService {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    @Qualifier(BeanMapper.BEAN_META_SERVICE_DAO)
    private MetaServiceDao metaServiceDao;

    @Override
    public List<LinkedHashMap<String, String>> listCategory() {
        LOGGER.info("---------------MetaServiceImpl Starts listCategory----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listCategory();

        LOGGER.info("---------------MetaServiceImpl Ends listCategory ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> listCustomer() {
        LOGGER.info("---------------MetaServiceImpl Starts listCustomer----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listCustomer();

        LOGGER.info("---------------MetaServiceImpl Ends listCustomer ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> listProject(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts listProject----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listProject(inputObject.getCustomerCode());

        LOGGER.info("---------------MetaServiceImpl Ends listProject ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> listProjectType() {
        LOGGER.info("---------------MetaServiceImpl Starts listProjectType----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listProjectType();

        LOGGER.info("---------------MetaServiceImpl Ends listProjectType ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> listSkuType() {
        LOGGER.info("---------------MetaServiceImpl Starts listSkuType----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listSkuType();

        LOGGER.info("---------------MetaServiceImpl Ends listSkuType ----------------\n");
        return resultList;
    }
    @Override
    public List<LinkedHashMap<String, String>> listProjectUpc(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts listProjectUpc----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listProjectUpc(inputObject.getCustomerProjectId(), inputObject.getCustomerCode());

        LOGGER.info("---------------MetaServiceImpl Ends listProjectUpc ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> listRetailer() {
        LOGGER.info("---------------MetaServiceImpl Starts listRetailer----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listRetailer();

        LOGGER.info("---------------MetaServiceImpl Ends listRetailer ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getRetailerDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getRetailerDetail retailerCode = " + inputObject.getRetailerCode() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getRetailerDetail(inputObject.getRetailerCode());

        LOGGER.info("---------------MetaServiceImpl Ends getRetailerDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>>  getProjectUpcDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectUpcDetail customerProjectId = " + inputObject.getCustomerProjectId() + "customerCode"+inputObject.getCustomerCode()+"----------------\n");

        List<ProjectUpc> resultList = metaServiceDao.getProjectUpcDetail(inputObject.getCustomerProjectId(), inputObject.getCustomerCode());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectUpcDetail ----------------\n");
        return ConverterUtil.convertProjectUpcObjectToMap(resultList);
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectTypeDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getProjectTypeDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectTypeDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getSkuTypeDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getSkuTypeDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getSkuTypeDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getSkuTypeDetail ----------------\n");
        return resultList;

    }

    @Override
    public List<Project> getProjectDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectDetail customerProjectId = " + inputObject.getCustomerProjectId() + "customerCode="+inputObject.getCustomerCode()+"----------------\n");
        
        List<ProjectUpc> productUpcList = metaServiceDao.getProjectUpcDetail(inputObject.getCustomerProjectId(), inputObject.getCustomerCode());
        LOGGER.info("---------------MetaServiceImpl getProjectUpcDetail got size ="+productUpcList.size()+"----------------\n");

        List<ProjectQuestion> projectQuestionsList = metaServiceDao.getProjectQuestionsDetail(inputObject.getCustomerCode(), inputObject.getCustomerProjectId());
        LOGGER.info("---------------MetaServiceImpl getProjectQuestionsDetail got size ="+projectQuestionsList.size()+"----------------\n");
        
        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getProjectDetail(inputObject.getCustomerProjectId(), inputObject.getCustomerCode());
        
        Project project = new Project(resultList.get(0));
        project.setProjectUpcList(productUpcList);
        project.setProjectQuestionsList(projectQuestionsList);
        List<Project> projectList = new ArrayList<Project>();
        projectList.add(project);
        
        LOGGER.info("---------------MetaServiceImpl Ends getProjectDetail ----------------\n");
        return projectList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getCustomerDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getCustomerDetail customerCode = " + inputObject.getCustomerCode() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getCustomerDetail(inputObject.getCustomerCode());

        LOGGER.info("---------------MetaServiceImpl Ends getCustomerDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getCategoryDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getCategoryDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getCategoryDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getCategoryDetail ----------------\n");
        return resultList;
    }

    @Override
    public void createCustomer(Customer customerInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createCustomer customer code = " + customerInput.getCustomerCode()+ "----------------\n");

        metaServiceDao.createCustomer(customerInput);


        LOGGER.info("---------------MetaServiceImpl Ends createCustomer id generate = "+customerInput.getId()+"----------------\n");
    }

    @Override
    public void createCategory(Category categoryInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createCategory category name = " + categoryInput.getName()+ "----------------\n");

        metaServiceDao.createCategory(categoryInput);

        LOGGER.info("---------------MetaServiceImpl Ends createCategory id generate = "+categoryInput.getId()+"----------------\n");

    }

    @Override
    public void createRetailer(Retailer retailerInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createRetailer retailer code = " + retailerInput.getName()+ "----------------\n");

        metaServiceDao.createRetailer(retailerInput);

        LOGGER.info("---------------MetaServiceImpl Ends createRetailer id generate = "+retailerInput.getId()+"----------------\n");

    }

    @Override
    public void createProjectType(ProjectType projectTypeInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createProjectType projectType code = " + projectTypeInput.getName()+ "----------------\n");

        metaServiceDao.createProjectType(projectTypeInput);

        LOGGER.info("---------------MetaServiceImpl Ends createProjectType id generate = "+projectTypeInput.getId()+"----------------\n");

    }

    @Override
    public void createSkuType(SkuType skuTypeInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createSkuType skuType code = " + skuTypeInput.getName()+ "----------------\n");

        metaServiceDao.createSkuType(skuTypeInput);

        LOGGER.info("---------------MetaServiceImpl Ends createSkuType id generate = "+skuTypeInput.getId()+"----------------\n");

    }

    @Override
    public void createProject(Project projectInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createProject Project code = " + projectInput.getProjectName()+ "----------------\n");
        metaServiceDao.createProject(projectInput);
        LOGGER.info("---------------MetaServiceImpl project created with id ="+projectInput.getId()+"now adding upc list -------------");
        
        metaServiceDao.addUpcListToProjectId(projectInput.getProjectUpcList(), projectInput.getCustomerProjectId(), projectInput.getCustomerCode());
        LOGGER.info("---------------MetaServiceImpl upc list addded -------------");
        
        metaServiceDao.addQuestionsListToProjectId(projectInput.getProjectQuestionsList(), projectInput.getCustomerCode(),  projectInput.getCustomerProjectId());
        LOGGER.info("---------------MetaServiceImpl upc list addded -------------");
        
        LOGGER.info("---------------MetaServiceImpl Ends createProject id generate = "+projectInput.getId()+"----------------\n");
    }

    @Override
    public void addUpcToProjectId(ProjectUpc projectUpc) {
        LOGGER.info("---------------MetaServiceImpl Starts addUpcToProjectId ProjectId = " + projectUpc.getCustomerProjectId()+"upc = "+projectUpc.getUpc()+"----------------\n");

        metaServiceDao.addUpcToProjectId(projectUpc);

        LOGGER.info("---------------MetaServiceImpl Ends addUpcToProjectId ----------------\n");

    }

    @Override
    public List<LinkedHashMap<String, String>> listStores() {
        LOGGER.info("---------------MetaServiceImpl Starts listStores ----------------\n");

        List<LinkedHashMap<String, String>> resultList =  metaServiceDao.listStores();

        LOGGER.info("---------------MetaServiceImpl Ends listStores ----------------\n");

        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getStoreDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getStoreDetail storeId = " + inputObject.getStoreId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getStoreDetail(inputObject.getStoreId());

        LOGGER.info("---------------MetaServiceImpl Ends getStoreDetail ----------------\n");
        return resultList;
    }
    
    @Override
    public void createStore(StoreMaster storeMaster) {
        LOGGER.info("---------------MetaServiceImpl Starts createStore id = " + storeMaster.getStoreId()+ "----------------\n");

        metaServiceDao.createStore(storeMaster);

        LOGGER.info("---------------MetaServiceImpl Ends createStore ----------------\n");

    }
    @Override
    public void updateStore(StoreMaster storeMaster) {
        LOGGER.info("---------------MetaServiceImpl Starts updateStore id = " + storeMaster.getStoreId()+ "----------------\n");

        metaServiceDao.updateStore(storeMaster);

        LOGGER.info("---------------MetaServiceImpl Ends updateStore ----------------\n");

    }
    @Override
    public void updateProject(Project projectInput) {
        LOGGER.info("---------------MetaServiceImpl Starts updateProject Project code = " + projectInput.getProjectName()+ "----------------\n");
        metaServiceDao.updateProject(projectInput);
        LOGGER.info("---------------MetaServiceImpl project updated with id ="+projectInput.getId()+"now adding upc list -------------");
        
        metaServiceDao.updateUpcListToProjectId(projectInput.getProjectUpcList(), projectInput.getCustomerProjectId(), projectInput.getCustomerCode());
        LOGGER.info("---------------MetaServiceImpl upc list updated -------------");
        
        metaServiceDao.updateQuestionsListToProjectId(projectInput.getProjectQuestionsList(), projectInput.getCustomerCode(), projectInput.getCustomerProjectId());
        LOGGER.info("---------------MetaServiceImpl questions list updated -------------");
        
        LOGGER.info("---------------MetaServiceImpl Ends updateProject id generate = "+projectInput.getId()+"----------------\n");
    }

    public List<LinkedHashMap<String, String>> getProjectSummary(InputObject inputObject){
        LOGGER.info("---------------MetaServiceImpl Starts getProjectSummary inputObject="+inputObject+"----------------\n");
       // List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        List<LinkedHashMap<String, String>> resultList =metaServiceDao.getProjectSummary(inputObject.getCustomerProjectId(),  inputObject.getCustomerCode());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectSummary ----------------\n");
        return resultList;
    }
}
