package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.StoreMasterDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.StoreMaster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 10/31/15.
 */
@Component(value = BeanMapper.BEAN_STORE_MASTER_DAO)
@Scope("prototype")
public class StoreMasterImpl implements StoreMasterDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getStoreId(String longitude, String latitude) {
        LOGGER.info("---------------StoreMasterImpl Starts getStoreId::longitude="+longitude+"::latitude="+latitude+"----------------\n");
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
        LOGGER.info("---------------StoreMasterImpl Starts getRetailerChainCode::storeId="+storeId+"----------------\n");
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


    @Override
    public List<LinkedHashMap<String,String>> getStoreOptions() {
        LOGGER.info("---------------StoreMasterImpl Starts getStoreOptions----------------\n");
        String sql = "SELECT RetailerChainCode,Retailer,StateCode,State,City FROM StoreMaster group by RetailerChainCode,Retailer,StateCode,State,City";

        Connection conn = null;
        StoreMaster storeMaster = null;
        List<LinkedHashMap<String,String>> storeMasterList=new ArrayList<LinkedHashMap<String,String>>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String,String> map =new LinkedHashMap<String,String>();
                map.put("retailerChainCode", rs.getString("RetailerChainCode"));
                map.put("retailer", rs.getString("Retailer"));
                map.put("stateCode", rs.getString("StateCode"));
                map.put("state", rs.getString("State"));
                map.put("city", rs.getString("City"));
                storeMasterList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------StoreMasterImpl Ends getStoreOptions" + storeMasterList.size() + "----------------\n");

            return storeMasterList;
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
    public List<LinkedHashMap<String, String>>  getStores(String retailerChainCode, String stateCode, String city) {
        LOGGER.info("---------------StoreMasterImpl Starts getStores::retailerChainCode="+retailerChainCode+"::stateCode="+stateCode+"::city="+city+"----------------\n");
        String sql = "SELECT RetailerStoreID,StoreID,Street,Latitude,Longitude FROM StoreMaster where RetailerChainCode = ? and StateCode = ? and City = ?";

        Connection conn = null;
        StoreMaster storeMaster = null;
        List<LinkedHashMap<String,String>> storeMasterList=new ArrayList<LinkedHashMap<String,String>>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, retailerChainCode);
            ps.setString(2, stateCode);
            ps.setString(3, city);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String,String> map =new LinkedHashMap<String,String>();
                map.put("retailerStoreId", rs.getString("RetailerStoreID"));
                map.put("storeId", rs.getString("StoreID"));
                map.put("street",rs.getString("Street"));
                map.put("latitude",rs.getString("Latitude"));
                map.put("longitude",rs.getString("Longitude"));
                storeMasterList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------StoreMasterImpl Ends getStores----------------\n");

            return storeMasterList;
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
