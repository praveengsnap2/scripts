package com.snap2buy.webservice.dao.impl;

import com.mysql.jdbc.Statement;
import com.snap2buy.webservice.dao.AuthenticationServiceDao;
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
 * Created by anoop on 07/23/16.
 */
@Component(value = BeanMapper.BEAN_AUTH_SERVICE_DAO)
@Scope("prototype")
public class AuthenticationServiceDaoImpl implements AuthenticationServiceDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createUser(User userInput) {
        LOGGER.info("---------------AuthenticationServiceDaoImpl Starts createUser::userInput=" + userInput + "----------------\n");
        String sql = "INSERT INTO User ( firstName, lastName, userId, password, email, customerCode) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userInput.getFirstName());
            ps.setString(2, userInput.getLastName() );
            ps.setString(3, userInput.getUserId());
            ps.setString(4, userInput.getPassword());
            ps.setString(5, userInput.getEmail() );
            ps.setString(6, userInput.getCustomerCode());
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends createUser----------------\n");

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
	public List<LinkedHashMap<String, String>> getUserDetail(String userId) {
		LOGGER.info("---------------AuthenticationServiceDaoImpl Starts getUserDetail user Id = " + userId + "----------------\n");
        String sql = "SELECT userId, firstName, lastName, email, customerCode FROM User where userId = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("userId", rs.getString("userId"));
                map.put("firstName", rs.getString("firstName"));
                map.put("lastName", rs.getString("lastName"));
                map.put("email", rs.getString("email"));
                map.put("customerCode", rs.getString("customerCode"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends getUserDetail----------------\n");

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
	public List<LinkedHashMap<String, String>> getUserForAuth(String userId) {
		LOGGER.info("---------------AuthenticationServiceDaoImpl Starts getUserForAuth user Id = " + userId + "----------------\n");
        String sql = "SELECT userId, password, customerCode, firstName, lastName FROM User where userId = ?";
        List<LinkedHashMap<String, String>> resultList = new ArrayList<LinkedHashMap<String, String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("userId", rs.getString("userId"));
                map.put("password", rs.getString("password"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("firstName", rs.getString("firstName"));
                map.put("lastName", rs.getString("lastName"));
                resultList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends getUserForAuth----------------\n");

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
	public void updateUser(User userInput) {
		LOGGER.info("---------------AuthenticationServiceDaoImpl Starts updateUser::userInput=" + userInput + "----------------\n");
		
		String sql = "UPDATE User  " +
                "set firstName=\"" + userInput.getFirstName() + "\"  " +
                ", lastName=\"" + userInput.getLastName() + "\"  " +
                ", email=\"" + userInput.getEmail() + "\"  " +
                ", customerCode=\"" + userInput.getCustomerCode() + "\"  " +
                "where userId = \""+userInput.getUserId()+"\" ";

        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends updateUser----------------\n");

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
	public void updateUserPassword(User userInput) {
	LOGGER.info("---------------AuthenticationServiceDaoImpl Starts updateUserPassword::userInput=" + userInput + "----------------\n");
		
		String sql = "UPDATE User  " +
                "set password=\"" + userInput.getPassword() + "\"  " +
                "where userId = \""+userInput.getUserId()+"\" ";

        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Updating user password failed, no rows affected.");
            }
            
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends updateUserPassword----------------\n");

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
	public void deleteUser(String userId) {
		String sql = "DELETE FROM User where userId = \""+userId+"\" ";

        Connection conn = null;
        Integer id = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            id = ps.executeUpdate();

            if (id == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
            
            ps.close();
            LOGGER.info("---------------AuthenticationServiceDaoImpl Ends deleteUser----------------\n");

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
