package com.snap2pay.webservice.dao.impl;

import com.snap2pay.webservice.dao.StoreMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by sachin on 10/31/15.
 */
@Component(value = BeanMapper.BEAN_STORE_MASTER_DAO)
@Scope("prototype")
public class StoreMasterImpl implements StoreMasterDao {

    private static Logger LOGGER = Logger.getLogger("s2p");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getStoreId(String longitude, String latitude) {
        return null;
    }

    @Override
    public String getRetailerChainCode(String storeId) {
        return null;
    }
}
