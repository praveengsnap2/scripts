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
                map.put("description", rs.getString("description"));
                map.put("owner", rs.getString("owner"));
                map.put("endDate", rs.getString("endDate"));
                map.put("successCriteria", rs.getString("successCriteria"));
                map.put("partialCriteria", rs.getString("partialCriteria"));
                map.put("failedCriteria", rs.getString("failedCriteria"));
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
        String sql = "SELECT * FROM ProjectUpc where customerProjectId = ? and customerCode = ?";
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
    public List<ProjectUpc> getProjectUpcDetail(String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getProjectUpcDetail----------------\n");
        String sql = "SELECT * FROM ProjectUpc  where customerProjectId = ? and customerCode = ?";
        List<ProjectUpc> resultList = new ArrayList<ProjectUpc>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerProjectId);
            ps.setString(2, customerCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProjectUpc projectUpc = new ProjectUpc();
                projectUpc.setId(rs.getString("id"));
                projectUpc.setCustomerProjectId(rs.getString("customerProjectId"));
                projectUpc.setCustomerCode(rs.getString("customerCode"));
                projectUpc.setUpc(rs.getString("upc"));
                projectUpc.setSkuTypeId(rs.getString("skuTypeId"));
                projectUpc.setExpectedFacingCount(rs.getString("expectedFacingCount"));
                String url = rs.getString("imageUrl1");
                projectUpc.setImageUrl1( url == null ? "" : url );
                url = rs.getString("imageUrl2");
                projectUpc.setImageUrl2(url == null ? "" : url );
                url = rs.getString("imageUrl3");
                projectUpc.setImageUrl3(url == null ? "" : url );
                resultList.add(projectUpc);
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

            if (rs.next()) {
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
                map.put("description", rs.getString("description"));
                map.put("owner", rs.getString("owner"));
                map.put("endDate", rs.getString("endDate"));
                map.put("successCriteria", rs.getString("successCriteria"));
                map.put("partialCriteria", rs.getString("partialCriteria"));
                map.put("failedCriteria", rs.getString("failedCriteria"));
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
        LOGGER.info("---------------MetaServiceDaoImpl Starts createProject::projectInput=" + projectInput + "----------------\n");
        String sql = "INSERT INTO Project ( projectName, customerProjectId, customerCode, projectTypeId, categoryId, retailerCode, storeCount, startDate, createdDate, createdBy, updatedDate, updatedBy, status, description, owner, endDate, successCriteria, partialCriteria, failedCriteria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
            ps.setString(14, projectInput.getDescription());
            ps.setString(15, projectInput.getOwner());
            ps.setString(16, projectInput.getEndDate());
            ps.setString(17, projectInput.getSuccessCriteria());
            ps.setString(18, projectInput.getPartialCriteria());
            ps.setString(19, projectInput.getFailedCriteria());
            id = ps.executeUpdate();

            if ((id == 0)||(id == null)) {
                throw new SQLException("Creating project failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    projectInput.setId(generatedKeys.getString(1));
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
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
    public void addUpcListToProjectId(List<ProjectUpc> projectUpcList, String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts addUpcToProjectId::projectUpcList=" + projectUpcList + "customerProjectId = " + customerProjectId + "customerCode = " + customerCode + "----------------\n");
        String sql = "INSERT INTO ProjectUpc ( customerProjectId, customerCode, upc, skuTypeId, expectedFacingCount, imageUrl1, imageUrl2, imageUrl3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        if (!projectUpcList.isEmpty()) {
            for (ProjectUpc projectUpc : projectUpcList) {
                try {
                    conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, customerProjectId);
                    ps.setString(2, customerCode);
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
    public void updateUpcListToProjectId(List<ProjectUpc> projectUpcList, String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts updateUpcListToProjectId::projectUpcList=" + projectUpcList + "customerProjectId = " + customerProjectId + "customerCode = " + customerCode + "----------------\n");
        String deleteSql = "delete from ProjectUpc where customerProjectId = \"" + customerProjectId + "\" and customerCode = \"" + customerCode + "\"";
        String sql = "INSERT INTO ProjectUpc ( customerProjectId, customerCode, upc, skuTypeId, expectedFacingCount, imageUrl1, imageUrl2, imageUrl3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;


        if (!projectUpcList.isEmpty()) {
            try {
                conn = dataSource.getConnection();
                PreparedStatement deletePs = conn.prepareStatement(deleteSql);
                deletePs.execute();
                deletePs.close();

                for (ProjectUpc projectUpc : projectUpcList) {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, customerProjectId);
                    ps.setString(2, customerCode);
                    ps.setString(3, projectUpc.getUpc());
                    ps.setString(4, projectUpc.getSkuTypeId());
                    ps.setString(5, projectUpc.getExpectedFacingCount());
                    ps.setString(6, projectUpc.getImageUrl1());
                    ps.setString(7, projectUpc.getImageUrl2());
                    ps.setString(8, projectUpc.getImageUrl3());
                    ps.executeUpdate();
                    ps.close();

                    LOGGER.info("---------------MetaServiceDaoImpl Ends updateUpcListToProjectId----------------\n");

                }
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

    @Override
    public List<LinkedHashMap<String, String>> listStores() {
        LOGGER.info("---------------MetaServiceDaoImpl Starts listStores----------------\n");
        String sql = "SELECT * FROM StoreMaster";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("storeId", rs.getString("StoreID"));
                map.put("retailerStoreId", rs.getString("RetailerStoreID"));
                map.put("retailerChainCode", rs.getString("RetailerChainCode"));
                map.put("retailer", rs.getString("Retailer"));
                map.put("street", rs.getString("Street"));
                map.put("city", rs.getString("City"));
                map.put("stateCode", rs.getString("StateCode"));
                map.put("state", rs.getString("State"));
                map.put("zip", rs.getString("ZIP"));
                map.put("latitude", rs.getString("Latitude"));
                map.put("longitude", rs.getString("Longitude"));
                map.put("comments", rs.getString("comments"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends listStores----------------\n");

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
    public List<LinkedHashMap<String, String>> getStoreDetail(String storeId) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getStoreDetail----------------\n");
        String sql = "SELECT * FROM StoreMaster where StoreID = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, storeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("storeId", rs.getString("StoreID"));
                map.put("retailerStoreId", rs.getString("RetailerStoreID"));
                map.put("retailerChainCode", rs.getString("RetailerChainCode"));
                map.put("retailer", rs.getString("Retailer"));
                map.put("street", rs.getString("Street"));
                map.put("city", rs.getString("City"));
                map.put("stateCode", rs.getString("StateCode"));
                map.put("state", rs.getString("State"));
                map.put("zip", rs.getString("ZIP"));
                map.put("latitude", rs.getString("Latitude"));
                map.put("longitude", rs.getString("Longitude"));
                map.put("comments", rs.getString("comments"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends getStoreDetail----------------\n");

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
    public void createStore(StoreMaster storeMaster) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts createStore::storeMaster=" + storeMaster + "----------------\n");
        String sql = "INSERT INTO StoreMaster ( StoreID, RetailerStoreID, RetailerChainCode, Retailer, Street, City, StateCode, State, ZIP, Latitude, Longitude, comments ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, storeMaster.getStoreId());
            ps.setString(2, storeMaster.getRetailerStoreId());
            ps.setString(3, storeMaster.getRetailerChainCode());
            ps.setString(4, storeMaster.getRetailer());
            ps.setString(5, storeMaster.getStreet());
            ps.setString(6, storeMaster.getCity());
            ps.setString(7, storeMaster.getStateCode());
            ps.setString(8, storeMaster.getState());
            ps.setString(9, storeMaster.getZip());
            ps.setString(10, storeMaster.getLatitude());
            ps.setString(11, storeMaster.getLongitude());
            ps.setString(12, storeMaster.getComments());
            Boolean status = ps.execute();
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
    public void updateStore(StoreMaster storeMaster) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts updateStore::storeMaster=" + storeMaster + "----------------\n");
        String sql = "UPDATE StoreMaster " +
                "set RetailerStoreID=\"" + storeMaster.getRetailerStoreId() + "\"  " +
                ", RetailerChainCode=\"" + storeMaster.getRetailerChainCode() + "\"  " +
                ", Retailer=\"" + storeMaster.getRetailer() + "\"  " +
                ", Street=\"" + storeMaster.getStreet() + "\"  " +
                ", City=\"" + storeMaster.getCity() + "\"  " +
                ", StateCode=\"" + storeMaster.getStateCode() + "\"  " +
                ", State=\"" + storeMaster.getState() + "\"  " +
                ", ZIP=\"" + storeMaster.getZip() + "\"  " +
                ", Latitude=\"" + storeMaster.getLatitude() + "\"  " +
                ", Longitude=\"" + storeMaster.getLongitude() + "\"  " +
                ", comments=\"" + storeMaster.getComments() + "\"  " +
                "where StoreID=\"" + storeMaster.getStoreId() + "\" ";

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            int id = ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends updateStore----------------\n");

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
    public void updateProject(Project projectInput) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts updateProject::projectInput=" + projectInput + "----------------\n");
        String sql = "UPDATE Project  " +
                "set projectName=\"" + projectInput.getProjectName() + "\"  " +
                ", projectTypeId=\"" + projectInput.getProjectTypeId() + "\"  " +
                ", categoryId=\"" + projectInput.getCategoryId() + "\"  " +
                ", retailerCode=\"" + projectInput.getRetailerCode() + "\"  " +
                ", storeCount=\"" + projectInput.getStoreCount() + "\"  " +
                ", startDate=\"" + projectInput.getStartDate() + "\"  " +
                ", createdDate=\"" + projectInput.getCreatedDate() + "\"  " +
                ", createdBy=\"" + projectInput.getCreatedBy() + "\"  " +
                ", updatedDate=\"" + projectInput.getUpdatedDate() + "\"  " +
                ", updatedBy=\"" + projectInput.getUpdatedBy() + "\"  " +
                ", status=\"" + projectInput.getStatus() + "\"  " +
                ", description=\"" + projectInput.getDescription() + "\"  " +
                ", owner=\"" + projectInput.getOwner() + "\"  " +
                ", endDate=\"" + projectInput.getEndDate() + "\"  " +
                ", successCriteria=\"" + projectInput.getSuccessCriteria() + "\"  " +
                ", partialCriteria=\"" + projectInput.getPartialCriteria() + "\"  " +
                ", failedCriteria=\"" + projectInput.getFailedCriteria() + "\"  " +
                "where customerCode = \""+projectInput.getCustomerCode()+"\" and customerProjectId= \""+projectInput.getCustomerProjectId() +"\" ";

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            int id = ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------MetaServiceDaoImpl Ends updateStore----------------\n");

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
    public List<LinkedHashMap<String, String>> getProjectSummary(String customerProjectId, String customerCode) {
        LOGGER.info("---------------MetaServiceDaoImpl Starts getProjectSummary----------------\n");
        String totalStoresSql = "SELECT storeCount FROM Project where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\"";
        String storesWithImagesSql = "select count(distinct(storeId)) as storesWithImages from ImageStoreNew where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\"";
        String storesWithAllProjectUpcsSql =
                "select count(storeId) as storesWithAllProjectUpcs from ( " +
                        "(select storeId, count(distinct(upc))  as upcCount from ProjectStoreData where upc != \"999999999999\" and customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" group by storeId) a  " +
                        "join  " +
                        "( select count(distinct(upc)) as upcCount from ProjectUpc where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" ) b  " +
                        "on (a.upcCount >= b.upcCount) " +
                        ")";
        
        String storesWithProjectUpcsSql = "select count(distinct(storeId)) as storesWithProjectUpcs from ProjectStoreData where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" " ;
        
        String storesToBeProcessedSql = "select count(distinct(storeId)) as storesToBeProcessed from ImageStoreNew  where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" " +
        		"and imageStatus in ( \"cron\", \"cron1\", \"cron2\", \"paused\")";

        String imagesReceivedSql = "select count(distinct(imageUUID)) as imagesReceived from ImageStoreNew where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" " ;
        
        String imagesProcessedSql = "select count(distinct(imageUUID)) as imagesProcessed from ImageStoreNew where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" "
        		+ "and imageStatus in (\"done\", \"error\") " ;
        
        String successfulStoresSql = "SELECT COUNT(*) as successfulStores FROM ProjectStoreResult WHERE customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" AND resultCode = \"1\" AND status = \"1\"";
        
        String partiallySuccessfulStoresSql = "SELECT COUNT(*) as partiallySuccessfulStores FROM ProjectStoreResult WHERE customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" AND resultCode = \"2\" AND status = \"1\"";
        
        String failedStoresSql = "SELECT COUNT(*) as failedStores FROM ProjectStoreResult WHERE customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" AND resultCode = \"3\" AND status = \"1\"";
        
        String imagesInErrorStateSql = "select count(distinct(imageUUID)) as imagesInErrorState from ImageStoreNew where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" "
        		+ "and imageStatus =\"error\"" ;
        
        String imagesInProcessingStateSql = "select count(distinct(imageUUID)) as imagesInProcessingState from ImageStoreNew where customerProjectId =\"" + customerProjectId + "\" and customerCode=\"" + customerCode + "\" "
        		+ "and imageStatus LIKE \"processing%\"" ;
        
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;

        String totalStores = null;
        String storesWithImages = null;
        String storesWithAllProjectUpcs = null;
        String StoresWithNoProjectUpcs = null;
        String storesWithPartialProjectUpcs = null;
        String storesWithProjectUpcsVal = null;
        String storesToBeProcessedVal = null;
        String imagesReceivedVal = null;
        String imagesProcessedVal = null;
        String imagesInErrorStateVal = null;
        String imagesInProcessingStateVal = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement totalStoresPs = conn.prepareStatement(totalStoresSql);
            PreparedStatement storesWithImagesPs = conn.prepareStatement(storesWithImagesSql);
            PreparedStatement storesWithAllProjectUpcsPs = conn.prepareStatement(storesWithAllProjectUpcsSql);
            PreparedStatement storesWithProjectUpcsPs = conn.prepareStatement(storesWithProjectUpcsSql);
            PreparedStatement storesToBeProcessedPs = conn.prepareStatement(storesToBeProcessedSql);
            PreparedStatement imagesProcessedPs = conn.prepareStatement(imagesProcessedSql);
            PreparedStatement imagesReceivedPs = conn.prepareStatement(imagesReceivedSql);
            PreparedStatement successfulStoresPs = conn.prepareStatement(successfulStoresSql);
            PreparedStatement partiallySuccessfulStoresPs = conn.prepareStatement(partiallySuccessfulStoresSql);
            PreparedStatement failedStoresPs = conn.prepareStatement(failedStoresSql);
            PreparedStatement imagesInErrorStatePs = conn.prepareStatement(imagesInErrorStateSql);
            PreparedStatement imagesInProcessingStatePs = conn.prepareStatement(imagesInProcessingStateSql);
            
            ResultSet totalStoresRs = totalStoresPs.executeQuery();
            if (totalStoresRs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                totalStores = totalStoresRs.getString("storeCount");
                map.put("totalStores", totalStores);
                resultList.add(map);
            }
            totalStoresRs.close();
            totalStoresPs.close();

            ResultSet storesWithImagesRs = storesWithImagesPs.executeQuery();
            if (storesWithImagesRs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                storesWithImages = storesWithImagesRs.getString("storesWithImages");
                map.put("storesWithImages", storesWithImages);
                resultList.add(map);
            }
            storesWithImagesRs.close();
            storesWithImagesPs.close();

            ResultSet storesToBeProcessedRs = storesToBeProcessedPs.executeQuery();
            if (storesToBeProcessedRs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                storesToBeProcessedVal = storesToBeProcessedRs.getString("storesToBeProcessed");
                map.put("storesToBeProcessed", storesToBeProcessedVal);
                resultList.add(map);
            }
            storesToBeProcessedRs.close();
            storesToBeProcessedPs.close();
            
            ResultSet storesWithAllProjectUpcsRs = storesWithAllProjectUpcsPs.executeQuery();
            if (storesWithAllProjectUpcsRs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                storesWithAllProjectUpcs = storesWithAllProjectUpcsRs.getString("storesWithAllProjectUpcs");
                map.put("storesWithAllProjectUpcs", storesWithAllProjectUpcs);
                resultList.add(map);
            }
            storesWithAllProjectUpcsRs.close();
            storesWithAllProjectUpcsPs.close();
            
            ResultSet storesWithProjectUpcsRs = storesWithProjectUpcsPs.executeQuery();
            if (storesWithProjectUpcsRs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                storesWithProjectUpcsVal = storesWithProjectUpcsRs.getString("storesWithProjectUpcs");
                StoresWithNoProjectUpcs = String.valueOf(Integer.parseInt(storesWithImages) - Integer.parseInt(storesWithProjectUpcsVal) - Integer.parseInt(storesToBeProcessedVal));
                map.put("StoresWithNoProjectUpcs", StoresWithNoProjectUpcs);
                resultList.add(map);
            }
            storesWithProjectUpcsRs.close();
            storesWithProjectUpcsPs.close();

            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
            storesWithPartialProjectUpcs=String.valueOf(Integer.parseInt(storesWithProjectUpcsVal) - Integer.parseInt(storesWithAllProjectUpcs));
            map.put("storesWithPartialProjectUpcs", storesWithPartialProjectUpcs);
            resultList.add(map);
            
            ResultSet imagesProcessedRs = imagesProcessedPs.executeQuery();
            if (imagesProcessedRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                imagesProcessedVal = imagesProcessedRs.getString("imagesProcessed");
                map1.put("imagesProcessed", imagesProcessedVal);
                resultList.add(map1);
            }
            imagesProcessedRs.close();
            imagesProcessedPs.close();
            
            ResultSet imagesReceivedRs = imagesReceivedPs.executeQuery();
            if (imagesReceivedRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                imagesReceivedVal = imagesReceivedRs.getString("imagesReceived");
                map1.put("imagesReceived", imagesReceivedVal);
                resultList.add(map1);
            }
            imagesReceivedRs.close();
            imagesReceivedPs.close();
            
            ResultSet successfulStoresRs = successfulStoresPs.executeQuery();
            if (successfulStoresRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                String successfulStoresVal = successfulStoresRs.getString("successfulStores");
                map1.put("successfulStores", successfulStoresVal);
                resultList.add(map1);
            }
            successfulStoresRs.close();
            successfulStoresPs.close();
            
            ResultSet partiallySuccessfulStoresRs = partiallySuccessfulStoresPs.executeQuery();
            if (partiallySuccessfulStoresRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                String partiallySuccessfulStoresVal = partiallySuccessfulStoresRs.getString("partiallySuccessfulStores");
                map1.put("partiallySuccessfulStores", partiallySuccessfulStoresVal);
                resultList.add(map1);
            }
            partiallySuccessfulStoresRs.close();
            partiallySuccessfulStoresPs.close();            
            
            ResultSet failedStoresRs = failedStoresPs.executeQuery();
            if (failedStoresRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                String failedStoresVal = failedStoresRs.getString("failedStores");
                map1.put("failedStores", failedStoresVal);
                resultList.add(map1);
            }
            failedStoresRs.close();
            failedStoresPs.close();
            
            ResultSet imagesInErrorStateRs = imagesInErrorStatePs.executeQuery();
            if (imagesInErrorStateRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                imagesInErrorStateVal = imagesInErrorStateRs.getString("imagesInErrorState");
                map1.put("imagesInErrorState", imagesInErrorStateVal);
                resultList.add(map1);
            }
            imagesInErrorStateRs.close();
            imagesInErrorStatePs.close();
            
            ResultSet imagesInProcessingStateRs = imagesInProcessingStatePs.executeQuery();
            if (imagesInProcessingStateRs.next()) {
                LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
                imagesInProcessingStateVal = imagesInProcessingStateRs.getString("imagesInProcessingState");
                map1.put("imagesInProcessingState", imagesInProcessingStateVal);
                resultList.add(map1);
            }
            imagesInProcessingStateRs.close();
            imagesInProcessingStatePs.close();
            
            LOGGER.info("---------------MetaServiceDaoImpl Ends getProjectSummary----------------\n");

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
}
