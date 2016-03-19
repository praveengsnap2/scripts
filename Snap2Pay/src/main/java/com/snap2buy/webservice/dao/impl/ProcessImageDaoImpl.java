package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.ImageAnalysis;
import com.snap2buy.webservice.model.ImageStore;
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
@Component(value = BeanMapper.BEAN_IMAGE_STORE_DAO)
@Scope("prototype")
public class ProcessImageDaoImpl implements ProcessImageDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(ImageStore imageStore) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts insert " + imageStore + "----------------\n");
        String sql = "INSERT INTO ImageStore (ImageUUID,ImageFilePath,UserId,CategoryId,Latitude,Longitude,TimeStamp,StoreId,Status,dateId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(9, imageStore.getShelfStatus());
            ps.setString(10, imageStore.getDateId());
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
                imageStore = new ImageStore(rs.getString("ImageUUID"), rs.getString("ImageFilePath"), rs.getString("UserId"), rs.getString("CategoryId"), rs.getString("Latitude"), rs.getString("Longitude"), rs.getString("TimeStamp"), rs.getString("StoreId"), rs.getString("dateId"), rs.getString("imageStatus"), rs.getString("shelfStatus"));
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
    public ImageStore getImageByStatus(String shelfStatus) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageByStatus----------------\n");
        String sql = "SELECT * FROM ImageStore WHERE shelfStatus = ?";

        Connection conn = null;
        ImageStore imageStore = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfStatus);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imageStore = new ImageStore(rs.getString("ImageUUID"), rs.getString("ImageFilePath"), rs.getString("UserId"), rs.getString("CategoryId"), rs.getString("Latitude"), rs.getString("Longitude"), rs.getString("TimeStamp"), rs.getString("StoreId"), rs.getString("dateId"),rs.getString("imageStatus"), rs.getString("shelfStatus"));
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
    public Integer getJobCount(String shelfStatus) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getJobCount----------------\n");
        String sql = "SELECT count(*) FROM ImageStore WHERE shelfStatus = ?";

        Connection conn = null;
        ImageStore imageStore = null;
        try {
            int numberOfRows = 0;
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfStatus);
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
    public void updateStatusAndHost(String hostId, String shelfStatus, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatusAndHost----------------\n");
        String sql = "UPDATE ImageStore SET shelfStatus = ? , hostId = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfStatus);
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
    public void updateShelfAnalysisStatus(String shelfStatus, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateShelfAnalysisStatus----------------\n");
        String sql = "UPDATE ImageStore SET shelfStatus = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfStatus);
            ps.setString(2, imageUUID);
            ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends updateShelfAnalysisStatus----------------\n");


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
    public void updateImageAnalysisStatus(String imageStatus, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateImageAnalysisStatus----------------\n");
        String sql = "UPDATE ImageStore SET imageStatus = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageStatus);
            ps.setString(2, imageUUID);
            ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends updateImageAnalysisStatus----------------\n");


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
    public void updateStoreId(String storeId, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatus----------------\n");
        String sql = "UPDATE ImageStore SET storeId = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, storeId);
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

    @Override
     public List<ImageAnalysis> getImageAnalysis(String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageAnalysis----------------\n");
        String sql = "SELECT * FROM ImageAnalysis WHERE imageUUID = ?";
        List<ImageAnalysis> ImageAnalysisList=new ArrayList<ImageAnalysis>();
        Connection conn = null;
        ImageAnalysis imageAnalysis = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                imageAnalysis = new ImageAnalysis(rs.getString("imageUUID"), rs.getString("storeId"), rs.getString("dateId"), rs.getString("upc"), rs.getString("upcConfidence"), rs.getString("alternateUpc"), rs.getString("alternateUpcConfidence"), rs.getString("leftTopX"), rs.getString("leftTopY"), rs.getString("width"), rs.getString("height"));
                ImageAnalysisList.add(imageAnalysis);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImageAnalysis----------------\n");

            return ImageAnalysisList;
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
    public String getImageAnalysisStatus(String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageAnalysisStatus----------------\n");
        String sql = "SELECT imageStatus FROM ImageStore WHERE imageUUID = ?";
        Connection conn = null;
        String imageStatus="queryFailed";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imageStatus = rs.getString("imageStatus");
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImageAnalysisStatus----------------\n");

            return imageStatus;
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

    //change to insert multiple statement at one go
    @Override
    public void storeImageAnalysis(List<ImageAnalysis> ImageAnalysisList,ImageStore imageStore) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts storeImageAnalysis----------------\n");
        String sql = "INSERT INTO ImageAnalysis (imageUUID, storeId, dateId, upc, upcConfidence, alternateUpc, alternateUpcConfidence, leftTopX, leftTopY, width, height ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        Connection conn = null;

        for (ImageAnalysis imageAnalysis : ImageAnalysisList) {
            try {
                conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, imageStore.getImageUUID());
                ps.setString(2, imageStore.getStoreId());
                ps.setString(3, imageStore.getDateId());
                ps.setString(4, imageAnalysis.getUpc());
                ps.setString(5, imageAnalysis.getUpcConfidence());
                ps.setString(6, imageAnalysis.getAlternateUpc());
                ps.setString(7, imageAnalysis.getAlternateUpcConfidence());
                ps.setString(8, imageAnalysis.getLeftTopX());
                ps.setString(9, imageAnalysis.getLeftTopY());
                ps.setString(10, imageAnalysis.getWidth());
                ps.setString(11, imageAnalysis.getHeight());
                ps.executeUpdate();
                ps.close();
                LOGGER.info("---------------ProcessImageDaoImpl Ends storeImageAnalysis----------------\n");

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
    public List<LinkedHashMap<String,String>> getImages(String storeId, String dateId) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImages----------------\n");
        String sql = "SELECT imageUUID FROM ImageStore WHERE storeId = ? and dateId = ?";
        List<LinkedHashMap<String,String>> imageStoreList=new ArrayList<LinkedHashMap<String,String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, storeId);
            ps.setString(2, dateId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String,String> map =new LinkedHashMap<String,String>();
                map.put("imageUUID", rs.getString("imageUUID"));
                imageStoreList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImages----------------\n");

            return imageStoreList;
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
    public LinkedHashMap<String,String> getFacing(String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getFacing----------------\n");
        String sql = "SELECT upc, count(*) as count FROM ImageAnalysis  WHERE imageUUID = ? group by upc";
        LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("upc"),rs.getString("count"));
                }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getFacing----------------\n");

            return map;
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