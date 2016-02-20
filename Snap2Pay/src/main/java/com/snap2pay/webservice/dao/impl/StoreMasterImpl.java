package com.snap2pay.webservice.dao.impl;

import com.snap2pay.webservice.dao.StoreMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        LOGGER.info("---------------StoreMasterImpl Starts getStoreId----------------\n");
        String sql = "SELECT * FROM StoreMaster";

        Connection conn = null;
        try {
            String storeId = "NA";
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Double minDistance = 0.0;
            Double currDistance = 0.0;
            if (rs.next()) {
                minDistance = Math.abs(Double.parseDouble(longitude) - Double.parseDouble(rs.getString("Longitude"))) + Math.abs(Double.parseDouble(latitude) - Double.parseDouble(rs.getString("Latitude")));
                if (minDistance <= 0.005) {
                    storeId = rs.getString("StoreId");
                } else {
                    storeId = "0";
                }
            }
            while (rs.next()) {
                currDistance = Math.abs(Double.parseDouble(longitude) - Double.parseDouble(rs.getString("Longitude"))) + Math.abs(Double.parseDouble(latitude) - Double.parseDouble(rs.getString("Latitude")));

                if (currDistance < minDistance) {
                    minDistance = currDistance;
                    if (minDistance <= 0.005) {
                        storeId = rs.getString("StoreId");
                    } else {
                        storeId = "0";
                    }
                }
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------StoreMasterImpl Ends getStoreId----------------\n");

            return storeId;
        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
                    LOGGER.error("exception", e);
                }
            }
        }
    }

    @Override
    public String getRetailerChainCode(String storeId) {
        LOGGER.info("---------------StoreMasterImpl Starts getRetailerChainCode----------------\n");
        String sql = "SELECT * FROM StoreMaster WHERE storeId = ? ";

        Connection conn = null;
        String retailerChainCode = "NA";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, storeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                retailerChainCode = rs.getString("RetailerChainCode");
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------StoreMasterImpl Ends getRetailerChainCode----------------\n");

            return retailerChainCode;
        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
                    LOGGER.error("exception", e);
                }
            }
        }
    }
}
