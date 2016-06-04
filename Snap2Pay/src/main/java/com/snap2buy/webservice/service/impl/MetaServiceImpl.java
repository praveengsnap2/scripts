package com.snap2buy.webservice.service.impl;

import com.snap2buy.webservice.dao.*;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
import com.snap2buy.webservice.service.MetaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    public List<LinkedHashMap<String, String>> listProject() {
        LOGGER.info("---------------MetaServiceImpl Starts listProject----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listProject();

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
    public List<LinkedHashMap<String, String>> listProjectUpc() {
        LOGGER.info("---------------MetaServiceImpl Starts listProjectUpc----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.listProjectUpc();

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
        LOGGER.info("---------------MetaServiceImpl Starts getRetailerDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getRetailerDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getRetailerDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectUpcDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectUpcDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getProjectUpcDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectUpcDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectTypeDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getProjectTypeDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectTypeDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getProjectDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getProjectDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getProjectDetail(inputObject.getId());

        LOGGER.info("---------------MetaServiceImpl Ends getProjectDetail ----------------\n");
        return resultList;
    }

    @Override
    public List<LinkedHashMap<String, String>> getCustomerDetail(InputObject inputObject) {
        LOGGER.info("---------------MetaServiceImpl Starts getCustomerDetail id = " + inputObject.getId() + "----------------\n");

        List<LinkedHashMap<String, String>> resultList = metaServiceDao.getCustomerDetail(inputObject.getId());

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
        LOGGER.info("---------------MetaServiceImpl Starts createCustomer customer code = " + customerInput.getCode()+ "----------------\n");

        Integer id= metaServiceDao.createCustomer(customerInput);

        LOGGER.info("---------------MetaServiceImpl Ends createCustomer id generate = "+id+"----------------\n");
    }

    @Override
    public void createCategory(Category categoryInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createCategory category name = " + categoryInput.getName()+ "----------------\n");

        Integer id= metaServiceDao.createCategory(categoryInput);

        LOGGER.info("---------------MetaServiceImpl Ends createCategory id generate = "+id+"----------------\n");

    }

    @Override
    public void createRetailer(Retailer retailerInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createRetailer retailer code = " + retailerInput.getName()+ "----------------\n");

        Integer id= metaServiceDao.createRetailer(retailerInput);

        LOGGER.info("---------------MetaServiceImpl Ends createRetailer id generate = "+id+"----------------\n");

    }

    @Override
    public void createProjectType(ProjectType projectTypeInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createProjectType projectType code = " + projectTypeInput.getName()+ "----------------\n");

        Integer id= metaServiceDao.createProjectType(projectTypeInput);

        LOGGER.info("---------------MetaServiceImpl Ends createProjectType id generate = "+id+"----------------\n");

    }

    @Override
    public void createProject(Project projectInput) {
        LOGGER.info("---------------MetaServiceImpl Starts createProject Project code = " + projectInput.getProjectName()+ "----------------\n");
        Integer id= metaServiceDao.createProject(projectInput);
        LOGGER.info("---------------MetaServiceImpl project created with id ="+id+"now adding upc list -------------");
        metaServiceDao.addUpcListToProjectId(projectInput.getProjectUpcList());
        LOGGER.info("---------------MetaServiceImpl upc list addded -------------");
        LOGGER.info("---------------MetaServiceImpl Ends createProject id generate = "+id+"----------------\n");
    }

    @Override
    public void addUpcToProjectId(ProjectUpc projectUpc) {
        LOGGER.info("---------------MetaServiceImpl Starts addUpcToProjectId ProjectId = " + projectUpc.getCustomerProjectId()+"upc = "+projectUpc.getUpc()+"----------------\n");

        metaServiceDao.addUpcToProjectId(projectUpc);

        LOGGER.info("---------------MetaServiceImpl Ends addUpcToProjectId ----------------\n");

    }
}