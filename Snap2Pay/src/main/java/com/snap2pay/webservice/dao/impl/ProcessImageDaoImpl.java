package com.snap2pay.webservice.dao.impl;

import com.snap2pay.webservice.dao.ProcessImageDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ImageStore;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_IMAGE_STORE_DAO)
@Scope("prototype")
public class ProcessImageDaoImpl implements ProcessImageDao {

  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void insert(ImageStore imageStore) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts insert----------------\n");
    String sql = "INSERT INTO ImageStore (ImageUUID,ImageFilePath,UserId,CategoryId,Latitude,Longitude,TimeStamp,StoreId,Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conn = null;

    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, imageStore.getImageUUID());
      ps.setString(2, imageStore.getImageFilePath());
      ps.setString(3, imageStore.getUserId());
      ps.setString(4, imageStore.getCategoryId());
      ps.setString(5, imageStore.getLatitude());
      ps.setString(6, imageStore.getLongitude());
      ps.setString(7, imageStore.getTimeStamp());
      ps.setString(8, imageStore.getStoreId());
      ps.setString(9, imageStore.getStatus());
      ps.executeUpdate();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Ends insert----------------\n");

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
  public ImageStore findByImageUUId(String imageUUId) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts findByImageUUId----------------\n");
    String sql = "SELECT * FROM ImageStore WHERE imageUUID = ?";

    Connection conn = null;
    ImageStore imageStore = null;
    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, imageUUId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        imageStore = new ImageStore(rs.getString("ImageUUID"), rs.getString("ImageFilePath"), rs.getString("UserId"), rs.getString("CategoryId"), rs.getString("Latitude"), rs.getString("Longitude"), rs.getString("TimeStamp"), rs.getString("StoreId"), rs.getString("Status"));
      }
      rs.close();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Starts findByImageUUId----------------\n");

      return imageStore;
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
  public ImageStore getImageByStatus(String status) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts getImageByStatus----------------\n");
    String sql = "SELECT * FROM ImageStore WHERE status = ?";

    Connection conn = null;
    ImageStore imageStore = null;
    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, status);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        imageStore = new ImageStore(rs.getString("ImageUUID"), rs.getString("UserId"), rs.getString("ImageFilePath"), rs.getString("CategoryId"), rs.getString("Latitude"), rs.getString("Longitude"), rs.getString("TimeStamp"), rs.getString("StoreId"), rs.getString("Status"));
      }
      rs.close();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Ends getImageByStatus----------------\n");

      return imageStore;
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
  public Integer getJobCount(String status) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts getJobCount----------------\n");
    String sql = "SELECT count(*) FROM ImageStore WHERE status = ?";

    Connection conn = null;
    ImageStore imageStore = null;
    try {
      int numberOfRows = 0;
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, status);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        numberOfRows = rs.getInt(1);
      }
      rs.close();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Ends getJobCount----------------\n");

      return numberOfRows;
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
  public void updateStatusAndHost(String hostId, String status, String imageUUID) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatusAndHost----------------\n");
    String sql = "UPDATE ImageStore SET status = ? , hostId = ? WHERE imageUUID = ? ";
    Connection conn = null;

    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, status);
      ps.setString(2, hostId);
      ps.setString(3, imageUUID);
      ps.executeUpdate();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Ends updateStatusAndHost----------------\n");

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
  public void updateStatus(String status, String imageUUID) {
    LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatus----------------\n");
    String sql = "UPDATE ImageStore SET status = ? WHERE imageUUID = ? ";
    Connection conn = null;

    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, status);
      ps.setString(2, imageUUID);
      ps.executeUpdate();
      ps.close();
      LOGGER.info("---------------ProcessImageDaoImpl Ends updateStatus----------------\n");


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
