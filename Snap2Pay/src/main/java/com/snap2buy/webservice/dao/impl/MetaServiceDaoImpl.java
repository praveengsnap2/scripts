package com.snap2buy.webservice.dao.impl;

import com.mysql.jdbc.Statement;
import com.snap2buy.webservice.dao.MetaServiceDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;
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
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_META_SERVICE_DAO)
@Scope("prototype")
public class MetaServiceDaoImpl implements MetaServiceDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<LinkedHashMap<String, String>> listCategory() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listCategory----------------\n");
        String sql = "SELECT * FROM Category where status = 1";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listCategory----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listCustomer() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listCustomer----------------\n");
        String sql = "SELECT * FROM Customer  where status = 1";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("logo", rs.getString("logo"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listCustomer----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listProject(String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listProject----------------\n");
        String sql = "SELECT * FROM Project where status = 1 and customerCode = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("projectName", rs.getString("projectName"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("projectTypeId", rs.getString("projectTypeId"));
                map.put("categoryId", rs.getString("categoryId"));
                map.put("retailerCode", rs.getString("retailerCode"));
                map.put("storeCount", rs.getString("storeCount"));
                map.put("startDate", rs.getString("startDate"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("createdBy", rs.getString("createdBy"));
                map.put("updatedDate", rs.getString("updatedDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listProject----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listProjectType() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listProjectType----------------\n");
        String sql = "SELECT * FROM ProjectType where status = 1";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listProjectType----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listSkuType() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listSkuType----------------\n");
        String sql = "SELECT * FROM SkuType where status = 1";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listSkuType----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listProjectUpc(String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listProjectUpc----------------\n");
        String sql = "SELECT * FROM ProjectUpc where status = 1 and customerProjectId = ? and customerCode = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerProjectId);
            ps.setString(2, customerCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("upc", rs.getString("upc"));
                map.put("skuTypeId", rs.getString("skuTypeId"));
                map.put("expectedFacingCount", rs.getString("expectedFacingCount"));
                map.put("imageUrl1", rs.getString("imageUrl1"));
                map.put("imageUrl2", rs.getString("imageUrl2"));
                map.put("imageUrl3", rs.getString("imageUrl3"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listProjectUpc----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> listRetailer() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listRetailer----------------\n");
        String sql = "SELECT * FROM Retailer where status = 1";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("retailerCode", rs.getString("retailerCode"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("logo", rs.getString("logo"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listRetailer----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getRetailerDetail(String retailerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getRetailerDetail retailerCode = " + retailerCode + "----------------\n");
        String sql = "SELECT * FROM Retailer where retailerCode = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, retailerCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("retailerCode", rs.getString("retailerCode"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("logo", rs.getString("logo"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getRetailerDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getProjectUpcDetail(String customerProjectId) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getProjectUpcDetail----------------\n");
        String sql = "SELECT * FROM ProjectUpc  where customerProjectId = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerProjectId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("upc", rs.getString("upc"));
                map.put("skuTypeId", rs.getString("skuTypeId"));
                map.put("expectedFacingCount", rs.getString("expectedFacingCount"));
                map.put("imageUrl1", rs.getString("imageUrl1"));
                map.put("imageUrl2", rs.getString("imageUrl2"));
                map.put("imageUrl3", rs.getString("imageUrl3"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getProjectUpcDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getProjectTypeDetail(String id) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getProjectTypeDetail----------------\n");
        String sql = "SELECT * FROM ProjectType where id = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getProjectTypeDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getSkuTypeDetail(String id) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getSkuTypeDetail----------------\n");
        String sql = "SELECT * FROM SkuType where id = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getSkuTypeDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getProjectDetail(String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getProjectDetail----------------\n");
        String sql = "SELECT * FROM Project where customerProjectId = ? and customerCode = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerProjectId);
            ps.setString(2, customerCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("projectName", rs.getString("projectName"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("projectTypeId", rs.getString("projectTypeId"));
                map.put("categoryId", rs.getString("categoryId"));
                map.put("retailerCode", rs.getString("retailerCode"));
                map.put("storeCount", rs.getString("storeCount"));
                map.put("startDate", rs.getString("startDate"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("createdBy", rs.getString("createdBy"));
                map.put("updatedDate", rs.getString("updatedDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getProjectDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getCustomerDetail(String id) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getCustomerDetail----------------\n");
        String sql = "SELECT * FROM Customer where customerCode = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("name", rs.getString("name"));
                map.put("type", rs.getString("type"));
                map.put("logo", rs.getString("logo"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getCustomerDetail----------------\n");

            return resultList;
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
    public List<LinkedHashMap<String, String>> getCategoryDetail(String id) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getCategoryDetail----------------\n");
        String sql = "SELECT * FROM Category where id = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));
                map.put("createdDate", rs.getString("createdDate"));
                map.put("status", rs.getString("status"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getCategoryDetail----------------\n");

            return resultList;
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
    public void createCustomer(Customer customerInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createCustomer::customerInput=" + customerInput + "----------------\n");
        String sql = "INSERT INTO Customer ( customerCode, name, type, logo, createdDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerInput.getCustomerCode());
            ps.setString(2, customerInput.getName());
            ps.setString(3, customerInput.getType());
            ps.setString(4, customerInput.getLogo());
            ps.setString(5, customerInput.getCreatedDate());
            ps.setString(6, customerInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createCustomer----------------\n");

        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void createCategory(Category categoryInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createCategory::categoryInput=" + categoryInput + "----------------\n");
        String sql = "INSERT INTO Category ( name, createdDate, status) VALUES (?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ;
            ps.setString(1, categoryInput.getName());
            ps.setString(2, categoryInput.getCreatedDate());
            ps.setString(3, categoryInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoryInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createCategory----------------\n");


        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void createRetailer(Retailer retailerInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createRetailer::retailerInput=" + retailerInput + "----------------\n");
        String sql = "INSERT INTO Retailer ( retailerCode, name, type, logo, createdDate, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Integer id = -1;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ;
            ps.setString(1, retailerInput.getRetailerCode());
            ps.setString(2, retailerInput.getName());
            ps.setString(3, retailerInput.getType());
            ps.setString(4, retailerInput.getLogo());
            ps.setString(5, retailerInput.getCreatedDate());
            ps.setString(6, retailerInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    retailerInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createRetailer----------------\n");


        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void createProjectType(ProjectType projectTypeInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createProjectType::projectTypeInput=" + projectTypeInput + "----------------\n");
        String sql = "INSERT INTO ProjectType ( name, createdDate, status) VALUES (?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ;
            ps.setString(1, projectTypeInput.getName());
            ps.setString(2, projectTypeInput.getCreatedDate());
            ps.setString(3, projectTypeInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectTypeInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createProjectType----------------\n");


        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void createSkuType(SkuType skuTypeInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createSkuType::skuTypeInput=" + skuTypeInput + "----------------\n");
        String sql = "INSERT INTO SkuType ( name, createdDate, status) VALUES (?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ;
            ps.setString(1, skuTypeInput.getName());
            ps.setString(2, skuTypeInput.getCreatedDate());
            ps.setString(3, skuTypeInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    skuTypeInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createSkuType----------------\n");


        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void createProject(Project projectInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createProject::customerInput=" + projectInput + "----------------\n");
        String sql = "INSERT INTO Project ( projectName, customerProjectId, customerCode, projectTypeId, categoryId, retailerCode, storeCount, startDate, createdDate, createdBy, updatedDate, updatedBy, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ;
            ps.setString(1, projectInput.getProjectName());
            ps.setString(2, projectInput.getCustomerProjectId());
            ps.setString(3, projectInput.getCustomerCode());
            ps.setString(4, projectInput.getProjectTypeId());
            ps.setString(5, projectInput.getCategoryId());
            ps.setString(6, projectInput.getRetailerCode());
            ps.setString(7, projectInput.getStoreCount());
            ps.setString(8, projectInput.getStartDate());
            ps.setString(9, projectInput.getCreatedDate());
            ps.setString(10, projectInput.getCreatedBy());
            ps.setString(11, projectInput.getUpdatedDate());
            ps.setString(12, projectInput.getUpdatedBy());
            ps.setString(13, projectInput.getStatus());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends createProject----------------\n");


        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);


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
    public void addUpcListToProjectId(List<ProjectUpc> projectUpcList) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts addUpcToProjectId::projectUpcList=" + projectUpcList + "----------------\n");
        String sql = "INSERT INTO ProjectUpc ( customerProjectId, customerCode upc, skuTypeId, expectedFacingCount, imageUrl1, imageUrl2, imageUrl3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        if (!projectUpcList.isEmpty()) {
            for (ProjectUpc projectUpc : projectUpcList) {
                try {
                    conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, projectUpc.getCustomerProjectId());
                    ps.setString(2, projectUpc.getCustomerCode());
                    ps.setString(3, projectUpc.getUpc());
                    ps.setString(4, projectUpc.getSkuTypeId());
                    ps.setString(5, projectUpc.getExpectedFacingCount());
                    ps.setString(6, projectUpc.getImageUrl1());
                    ps.setString(7, projectUpc.getImageUrl2());
                    ps.setString(8, projectUpc.getImageUrl3());
                    ps.executeUpdate();
                    ps.close();

                    LOGGER.info("---------------MetaServiceDaoImpl Ends addUpcToProjectId----------------\n");

                } catch (SQLException e) {
                    LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
                    LOGGER.error("exception", e);

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
    }

    @Override
    public void addUpcToProjectId(ProjectUpc projectUpc) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts addUpcToProjectId::projectUpc=" + projectUpc + "----------------\n");
        String sql = "INSERT INTO ProjectUpc ( customerProjectId, customerCode, upc, skuTypeId, expectedFacingCount, imageUrl1, imageUrl2, imageUrl3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, projectUpc.getCustomerProjectId());
            ps.setString(2, projectUpc.getCustomerCode());
            ps.setString(3, projectUpc.getUpc());
            ps.setString(4, projectUpc.getSkuTypeId());
            ps.setString(5, projectUpc.getExpectedFacingCount());
            ps.setString(6, projectUpc.getImageUrl1());
            ps.setString(7, projectUpc.getImageUrl2());
            ps.setString(8, projectUpc.getImageUrl3());
            ps.executeUpdate();
            ps.close();

            LOGGER.info("---------------MetaServiceDaoImpl Ends addUpcToProjectId----------------\n");

        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

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
