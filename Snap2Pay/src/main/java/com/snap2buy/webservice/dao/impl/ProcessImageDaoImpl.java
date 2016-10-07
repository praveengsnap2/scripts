package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.MetaServiceDao;
import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sachin on 10/17/15.
 */
@Component(value = BeanMapper.BEAN_IMAGE_STORE_DAO)
@Scope("prototype")
public class ProcessImageDaoImpl implements ProcessImageDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    @Qualifier(BeanMapper.BEAN_META_SERVICE_DAO)
    private MetaServiceDao metaServiceDao;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(ImageStore imageStore) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts insert " + imageStore + "----------------\n");
        long currTimestamp = System.currentTimeMillis() / 1000L;

        String sql = "insert into ImageStoreNew (imageUUID, userId, ImageFilePath, categoryId, latitude, longitude, timeStamp, storeId, hostId, dateId, imageStatus, shelfStatus, origWidth, origHeight, newWidth, newHeight, thumbnailPath, customerCode, customerProjectId, taskId, agentId, lastUpdatedTimestamp)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageStore.getImageUUID());
            ps.setString(2, imageStore.getUserId());
            ps.setString(3, imageStore.getImageFilePath());
            ps.setString(4, imageStore.getCategoryId());
            ps.setString(5, imageStore.getLatitude());
            ps.setString(6, imageStore.getLongitude());
            ps.setString(7, imageStore.getTimeStamp());
            ps.setString(8, imageStore.getStoreId());
            ps.setString(9, imageStore.getHostId());
            ps.setString(10, imageStore.getDateId());
            ps.setString(11, imageStore.getImageStatus());
            ps.setString(12, imageStore.getShelfStatus());
            ps.setString(13, imageStore.getOrigWidth());
            ps.setString(14, imageStore.getOrigHeight());
            ps.setString(15, imageStore.getNewWidth());
            ps.setString(16, imageStore.getNewHeight());
            ps.setString(17, imageStore.getThumbnailPath());
            ps.setString(18, imageStore.getCustomerCode());
            ps.setString(19, imageStore.getCustomerProjectId());
            ps.setString(20, imageStore.getTaskId());
            ps.setString(21, imageStore.getAgentId());
            ps.setString(22, String.valueOf(currTimestamp));
            
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
        LOGGER.info("---------------ProcessImageDaoImpl Starts findByImageUUId::imageUUId="+imageUUId+"----------------\n");
        String sql = "SELECT * FROM ImageStoreNew WHERE imageUUID = ?";

        Connection conn = null;
        ImageStore imageStore = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imageStore = new ImageStore(rs.getString("ImageUUID"),
                        rs.getString("ImageFilePath"),
                        rs.getString("CategoryId"),
                        rs.getString("Latitude"),
                        rs.getString("Longitude"),
                        rs.getString("TimeStamp"),
                        rs.getString("StoreId"),
                        rs.getString("HostId"),
                        rs.getString("dateId"),
                        rs.getString("imageStatus"),
                        rs.getString("shelfStatus"),
                        rs.getString("origWidth"),
                        rs.getString("origHeight"),
                        rs.getString("newWidth"),
                        rs.getString("newHeight"),
                        rs.getString("thumbnailPath"),
                        rs.getString("userId"),
                        rs.getString("customerCode"),
                        rs.getString("customerProjectId"),
                        rs.getString("taskId"),
                        rs.getString("agentId"),
                        rs.getString("lastUpdatedTimestamp"),
                        rs.getString("imageHashScore"),
                        rs.getString("imageRotation")
                );
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Starts findByImageUUId result= "+imageStore.getImageUUID()+"----------------\n");

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
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageByStatus::shelfStatus="+shelfStatus+"----------------\n");
        String sql = "SELECT * FROM ImageStoreNew WHERE shelfStatus = ?";

        Connection conn = null;
        ImageStore imageStore = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfStatus);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                imageStore = new ImageStore(rs.getString("ImageUUID"),
                        rs.getString("ImageFilePath"),
                        rs.getString("CategoryId"),
                        rs.getString("Latitude"),
                        rs.getString("Longitude"),
                        rs.getString("TimeStamp"),
                        rs.getString("StoreId"),
                        rs.getString("HostId"),
                        rs.getString("dateId"),
                        rs.getString("imageStatus"),
                        rs.getString("shelfStatus"),
                        rs.getString("origWidth"),
                        rs.getString("origHeight"),
                        rs.getString("newWidth"),
                        rs.getString("newHeight"),
                        rs.getString("thumbnailPath"),
                        rs.getString("userId"),
                        rs.getString("customerCode"),
                        rs.getString("customerProjectId"),
                        rs.getString("taskId"),
                        rs.getString("agentId"),
                        rs.getString("lastUpdatedTimestamp"),
                        rs.getString("imageHashScore"),
                        rs.getString("imageRotation")
                );
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImageByStatus result= "+imageStore.getImageUUID()+"----------------\n");

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
        LOGGER.info("---------------ProcessImageDaoImpl Starts getJobCount::shelfStatus="+shelfStatus+"----------------\n");
        String sql = "SELECT count(*) FROM ImageStoreNew WHERE shelfStatus = ?";

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
            LOGGER.info("---------------ProcessImageDaoImpl Ends getJobCount numberOfRows = "+numberOfRows+"----------------\n");

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
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatusAndHost::hostId="+hostId+"::shelfStatus="+shelfStatus+"::imageUUID="+imageUUID+"----------------\n");
        String sql = "UPDATE ImageStoreNew SET shelfStatus = ? , hostId = ? WHERE imageUUID = ? ";
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
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateShelfAnalysisStatus::shelfStatus="+shelfStatus+"::imageUUID="+imageUUID+"----------------\n");
        String sql = "UPDATE ImageStoreNew SET shelfStatus = ? WHERE imageUUID = ? ";
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
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateImageAnalysisStatus::imageStatus="+imageStatus+"::imageUUID="+imageUUID+"----------------\n");
        long currTimestamp = System.currentTimeMillis() / 1000L;
        String sql = "UPDATE ImageStoreNew SET imageStatus = ?, lastUpdatedTimestamp = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageStatus);
            ps.setString(2, String.valueOf(currTimestamp));
            ps.setString(3, imageUUID);
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
    public void updateImageAnalysisHostId(String hostId, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateImageAnalysisStatus::hostId="+hostId+"::imageUUID="+imageUUID+"----------------\n");
        long currTimestamp = System.currentTimeMillis() / 1000L;
        String sql = "UPDATE ImageStoreNew SET hostId = ?, lastUpdatedTimestamp = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hostId);
            ps.setString(2, String.valueOf(currTimestamp));
            ps.setString(3, imageUUID);
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
    public void updateOrientationDetails(ImageStore imageStore) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateOrientationDetails::imageRotation="+imageStore.getImageRotation()+"::imageHashScore="+imageStore.getImageHashScore()+"::orig-height::"+imageStore.getOrigHeight()+"::orig-width::"+imageStore.getOrigWidth()+"::new-height::"+imageStore.getNewHeight()+"::new-width::"+imageStore.getNewWidth()+"----------------\n");
        long currTimestamp = System.currentTimeMillis() / 1000L;
        String sql = "UPDATE ImageStoreNew SET imageRotation = ?, imageHashScore = ?, origWidth = ?, origHeight = ?, newWidth = ?, newHeight = ?, lastUpdatedTimestamp = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageStore.getImageRotation());
            ps.setString(2, imageStore.getImageHashScore());
            ps.setString(3, imageStore.getOrigWidth());
            ps.setString(4, imageStore.getOrigHeight());
            ps.setString(5, imageStore.getNewWidth());
            ps.setString(6, imageStore.getNewHeight());
            ps.setString(7, String.valueOf(currTimestamp));
            ps.setString(8, imageStore.getImageUUID());

            ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends updateOrientationDetails----------------\n");

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
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateStatus::storeId="+storeId+"::imageUUID="+imageUUID+"----------------\n");
        String sql = "UPDATE ImageStoreNew SET storeId = ? WHERE imageUUID = ? ";
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
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageAnalysis::imageUUID="+imageUUID+"----------------\n");
        String sql = "select ImageAnalysisNew.imageUUID, ImageAnalysisNew.customerCode, ImageAnalysisNew.customerProjectId, ImageAnalysisNew.storeId, ImageAnalysisNew.dateId, ImageAnalysisNew.upc, ImageAnalysisNew.upcConfidence, ImageAnalysisNew.alternateUpc, ImageAnalysisNew.alternateUpcConfidence, ImageAnalysisNew.leftTopX, ImageAnalysisNew.leftTopY, ImageAnalysisNew.width, ImageAnalysisNew.height, ImageAnalysisNew.promotion, ImageAnalysisNew.price, ImageAnalysisNew.priceLabel, ProductMaster.PRODUCT_SHORT_NAME, ProductMaster.PRODUCT_LONG_NAME, ProductMaster.BRAND_NAME from ImageAnalysisNew, ProductMaster where ImageAnalysisNew.upc = ProductMaster.UPC and ImageAnalysisNew.imageUUID = ? ";
        List<ImageAnalysis> ImageAnalysisList=new ArrayList<ImageAnalysis>();
        Connection conn = null;
        ImageAnalysis imageAnalysis = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                imageAnalysis = new ImageAnalysis(rs.getString("imageUUID"), rs.getString("customerCode"), rs.getString("customerProjectId"), rs.getString("storeId"), rs.getString("dateId"), rs.getString("upc"), rs.getString("upcConfidence"), rs.getString("alternateUpc"), rs.getString("alternateUpcConfidence"), rs.getString("leftTopX"), rs.getString("leftTopY"), rs.getString("width"), rs.getString("height"), rs.getString("promotion"), rs.getString("price"), rs.getString("priceLabel"),rs.getString("PRODUCT_SHORT_NAME"), rs.getString("PRODUCT_LONG_NAME"), rs.getString("BRAND_NAME"));
                ImageAnalysisList.add(imageAnalysis);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImageAnalysis numberOfRows = "+ImageAnalysisList.size()+"----------------\n");

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
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImageAnalysisStatus::imageUUID="+imageUUID+"----------------\n");
        String sql = "SELECT imageStatus FROM ImageStoreNew WHERE imageUUID = ?";
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
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImageAnalysisStatus imageStatus = "+imageStatus+"----------------\n");

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

    @Override
    public ImageStore getNextImageDetails() {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getNextImageDetails----------------\n");
        String sql = "SELECT * FROM ImageStoreNew WHERE imageStatus in (\"cron\",\"cron1\",\"cron2\") order by lastUpdatedTimestamp limit 1";
        Connection conn = null;
        ImageStore imageStore = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) { imageStore = new ImageStore(
                    rs.getString("ImageUUID"),
                    rs.getString("ImageFilePath"),
                    rs.getString("CategoryId"),
                    rs.getString("Latitude"),
                    rs.getString("Longitude"),
                    rs.getString("TimeStamp"),
                    rs.getString("StoreId"),
                    rs.getString("HostId"),
                    rs.getString("dateId"),
                    rs.getString("imageStatus"),
                    rs.getString("shelfStatus"),
                    rs.getString("origWidth"),
                    rs.getString("origHeight"),
                    rs.getString("newWidth"),
                    rs.getString("newHeight"),
                    rs.getString("thumbnailPath"),
                    rs.getString("userId"),
                    rs.getString("customerCode"),
                    rs.getString("customerProjectId"),
                    rs.getString("taskId"),
                    rs.getString("agentId"),
                    rs.getString("lastUpdatedTimestamp"),
                    rs.getString("imageHashScore"),
                    rs.getString("imageRotation")
                );
            }
            rs.close();
            ps.close();

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
    //change to insert multiple statement at one go
    @Override
    public void storeImageAnalysis(List<ImageAnalysis> ImageAnalysisList, ImageStore imageStore) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts storeImageAnalysis::ImageAnalysisList size="+ImageAnalysisList.size()+"::imageStore="+imageStore+"----------------\n");
        String sql = "INSERT INTO ImageAnalysisNew (imageUUID, customerCode, customerProjectId, storeId, dateId, upc, upcConfidence, alternateUpc, alternateUpcConfidence, leftTopX, leftTopY, width, height, promotion, price, priceLabel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        String checkSql = "select count(*) as count from ImageAnalysisNew where imageUUID = ?";


        for (ImageAnalysis imageAnalysis : ImageAnalysisList) {
            try {
                conn = dataSource.getConnection();
                Boolean insert=false;
                PreparedStatement checkPs = conn.prepareStatement(checkSql);
                checkPs.setString(1,imageStore.getImageUUID());
                ResultSet rs = checkPs.executeQuery();
                while (rs.next()) {
                   if (rs.getInt("count") == 0 ){
                       insert=true;
                   }
                }
                rs.close();
                checkPs.close();
                if (insert=true) {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, imageStore.getImageUUID());
                    ps.setString(2, imageStore.getCustomerCode());
                    ps.setString(3, imageStore.getCustomerProjectId());
                    ps.setString(4, imageStore.getStoreId());
                    ps.setString(5, imageStore.getDateId());
                    ps.setString(6, imageAnalysis.getUpc());
                    ps.setString(7, imageAnalysis.getUpcConfidence());
                    ps.setString(8, imageAnalysis.getAlternateUpc());
                    ps.setString(9, imageAnalysis.getAlternateUpcConfidence());
                    ps.setString(10, imageAnalysis.getLeftTopX());
                    ps.setString(11, imageAnalysis.getLeftTopY());
                    ps.setString(12, imageAnalysis.getWidth());
                    ps.setString(13, imageAnalysis.getHeight());
                    ps.setString(14, imageAnalysis.getPromotion());
                    ps.setString(15, imageAnalysis.getPrice());
                    ps.setString(16, imageAnalysis.getPriceLabel());
                    ps.executeUpdate();
                    ps.close();
                }
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
        LOGGER.info("---------------ProcessImageDaoImpl Starts getImages::storeId="+storeId+"::dateId="+dateId+"----------------\n");
        String sql = "SELECT imageUUID FROM ImageStoreNew WHERE storeId = ? and dateId = ?";
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
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImages numberOfRows = "+imageStoreList+"----------------\n");

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
    public LinkedHashMap<String,Object> getFacing(String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getFacing::imageUUID="+imageUUID+"----------------\n");
        String sql = "select IA.UPC , PRODUCT_SHORT_NAME, PRODUCT_LONG_NAME, BRAND_NAME, IA.count from ProductMaster join (SELECT upc, count(*) as count FROM ImageAnalysisNew WHERE imageUUID = ? group by upc) IA on ProductMaster.UPC = IA.upc;";
        LinkedHashMap<String,Object> map=new LinkedHashMap<String,Object>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("upc"),new UpcFacingDetail(rs.getString("upc"),rs.getString("count"),rs.getString("PRODUCT_SHORT_NAME"),rs.getString("PRODUCT_LONG_NAME"),rs.getString("BRAND_NAME")));
                }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getFacing numberOfRows" + map.keySet().size()+"----------------\n");

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

    @Override
    public List<LinkedHashMap<String,String>> doShareOfShelfAnalysis(String getImageUUIDCsvString) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getFacing::getImageUUIDCsvString="+getImageUUIDCsvString+"----------------\n");

        String baseSql = "select upc, max(facing) as facing, PRODUCT_SHORT_NAME, PRODUCT_LONG_NAME, BRAND_NAME from  (select ImageAnalysisNew.imageUUID as imageUUID, ImageAnalysisNew.upc, count(*) as facing, ProductMaster.PRODUCT_SHORT_NAME, ProductMaster.PRODUCT_LONG_NAME, ProductMaster.BRAND_NAME from ImageAnalysisNew, ProductMaster where ImageAnalysisNew.upc = ProductMaster.UPC and ImageAnalysisNew.imageUUID IN (";

        StringBuilder builder = new StringBuilder();
        builder.append(baseSql);

        for( String entry: getImageUUIDCsvString.split(",")) {
            builder.append("?,");
        }

        String sql = builder.deleteCharAt(builder.length() -1).toString()+") group by ImageAnalysisNew.upc, ImageAnalysisNew.imageUUID order by ProductMaster.BRAND_NAME) a group by upc";
        LOGGER.info("---------------ProcessImageDaoImpl Starts getFacing::sql="+sql+";----------------\n");

       // LinkedHashMap<String,Object> map=new LinkedHashMap<String,Object>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            int i=1;

            for( String entry: getImageUUIDCsvString.split(",")) {
                ps.setString(i++, entry);
            }

            ResultSet rs = ps.executeQuery();
            String curr = "initialTestString";
            List<LinkedHashMap<String,String>> multipleImageAnalysisList=new ArrayList<LinkedHashMap<String,String>>();

            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("upc", rs.getString("upc"));
                map.put("facing", rs.getString("facing"));
                map.put("productShortName", rs.getString("PRODUCT_SHORT_NAME"));
                map.put("productLongName", rs.getString("PRODUCT_LONG_NAME"));
                map.put("brandName", rs.getString("BRAND_NAME"));
                multipleImageAnalysisList.add(map);
            }
            rs.close();
            ps.close();

            LOGGER.info("---------------ProcessImageDaoImpl Ends getFacing numberOfRows = "+multipleImageAnalysisList.size()+"----------------\n");
            return multipleImageAnalysisList;
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
    public void updateLatLong(String imageUUID, String latitude, String longitude) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts updateLatLong::imageUUID="+imageUUID+"::latitude="+latitude+"::longitude="+longitude+"----------------\n");
        String sql = "UPDATE ImageStoreNew SET latitude = ? , longitude = ? WHERE imageUUID = ? ";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, latitude);
            ps.setString(2, longitude);
            ps.setString(3, imageUUID);
            ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends updateLatLong----------------\n");

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
    public List<LinkedHashMap<String, String>> generateAggs(String customerCode, String customerProjectId, String storeId) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts generateAggs::::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"::storeId="+storeId+"----------------\n");
        //String sql = "select imageUUID, customerCode, customerProjectId, storeId, upc, max(facing) as facing, upcConfidence from (select customerCode, customerProjectId, storeId, imageUUID, upc, count(upc) as facing, avg(upcConfidence) as upcConfidence from ImageAnalysisNew where customerCode = ? and customerProjectId = ? and storeId = ? and imageUUID = ? group by customerCode, customerProjectId, storeId, imageUUID, upc ) a group by customerCode, customerProjectId, storeId, upc order by upc";
        String sql ="SELECT d.imageUUID, " +
                "       c.customerCode, " +
                "       c.customerProjectId, " +
                "       c.storeId, " +
                "       c.facing, " +
                "       c.upc, " +
                "       d.upcConfidence " +
                "FROM " +
                "  (SELECT customerCode, " +
                "          customerProjectId, " +
                "          storeId, " +
                "          CASE " +
                "              WHEN b.upc IS NULL THEN sum(facing) " +
                "              ELSE max(facing) " +
                "          END AS facing, " +
                "                 CASE " +
                "                     WHEN b.upc IS NULL THEN \"999999999999\" " +
                "                     ELSE b.upc " +
                "                 END AS upc, " +
                "                        max(upcConfidence) AS upcConfidence " +
                "   FROM " +
                "     ( SELECT imageUUID, " +
                "              customerCode, " +
                "              customerProjectId, " +
                "              storeId, " +
                "              upc, " +
                "              count(*) AS facing, " +
                "                          avg(upcConfidence) AS upcConfidence " +
                "      FROM ImageAnalysisNew " +
                "      WHERE customerCode = \""+ customerCode +"\" "+
                "        AND customerProjectId = \""+ customerProjectId +"\" "+
                "        AND storeId = \""+ storeId +"\" "+
                "      GROUP BY imageUUID, " +
                "               customerCode, " +
                "               customerProjectId, " +
                "               storeId, " +
                "               upc ) a " +
                "   LEFT JOIN " +
                "     ( SELECT upc " +
                "      FROM ProjectUpc " +
                "      WHERE customerCode = \""+ customerCode +"\" "+
                "        AND customerProjectId = \""+customerProjectId+"\") b ON (a.upc = b.upc) " +
                "   GROUP BY customerCode, " +
                "            customerProjectId, " +
                "            storeId, " +
                "            upc ) c " +
                "LEFT OUTER JOIN " +
                "  (SELECT imageUUID, " +
                "          customerCode, " +
                "          customerProjectId, " +
                "          storeId, " +
                "          upc, " +
                "          count(*) AS facing, " +
                "                      avg(upcConfidence) AS upcConfidence " +
                "   FROM ImageAnalysisNew " +
                "   WHERE customerCode = \""+ customerCode +"\" "+
                "     AND customerProjectId = \""+ customerProjectId +"\" "+
                "     AND storeId = \""+ storeId +"\" "+
                "     AND upc IN " +
                "       (SELECT upc " +
                "        FROM ProjectUpc " +
                "        WHERE customerCode = \""+ customerCode +"\" "+
                "          AND customerProjectId = \""+ customerProjectId +"\") " +
                "   GROUP BY imageUUID, " +
                "            customerCode, " +
                "            customerProjectId, " +
                "            storeId, " +
                "            upc) d ON (c.upc=d.upc) " +
                "AND (c.facing=d.facing) " +
                "AND (c.upcConfidence = d.upcConfidence)";


        String sql2 = "delete from ProjectStoreData where customerCode = ? and customerProjectId = ? and storeId = ?";
        String sql3 = "insert into ProjectStoreData (imageUUID, customerCode, customerProjectId, storeId, upc, facing, upcConfidence) values (?, ?, ?, ?, ?, ?, ?)";
        LOGGER.info("---------------ProcessImageDaoImpl Starts generateSql="+sql+"" +"---------------------");
        LOGGER.info("---------------ProcessImageDaoImpl Starts insertSql= "+sql2+"---------------------");
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();
        List<String> selectedUpcs = new ArrayList<String>();
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            PreparedStatement ps3 = conn.prepareStatement(sql3);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("imageUUID", rs.getString("imageUUID"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("storeId", rs.getString("storeId"));
                map.put("upc", rs.getString("upc"));
                map.put("facing", rs.getString("facing"));
                map.put("upcConfidence", rs.getString("upcConfidence"));
                if ( !selectedUpcs.contains( map.get("upc") ) ) {
                	selectedUpcs.add(map.get("upc"));
                	result.add(map);
	                ps3.setString(1, map.get("imageUUID"));
	                ps3.setString(2, map.get("customerCode"));
	                ps3.setString(3, map.get("customerProjectId"));
	                ps3.setString(4, map.get("storeId"));
	                ps3.setString(5, map.get("upc"));
	                ps3.setString(6, map.get("facing"));
	                ps3.setString(7, map.get("upcConfidence"));
	                ps3.addBatch();
                }
            }
            ps2.setString(1,customerCode);
            ps2.setString(2,customerProjectId);
            ps2.setString(3,storeId);
            Boolean rs2 = ps2.execute();
            int[] rs3 = ps3.executeBatch();
            conn.commit();

            rs.close();
            ps.close();
            ps2.close();
            ps3.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends generateAggs----------------\n");
            return result;
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
    public List<LinkedHashMap<String, String>> getProjectStoreResults(String customerCode, String customerProjectId, String storeId) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoreResults::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"::storeId="+storeId+"----------------\n");

        String sql = "select a.imageUUID,b.customerCode,b.customerProjectId,a.storeId,b.upc,a.facing,a.upcConfidence, b.BRAND_NAME,b.PRODUCT_SHORT_NAME,b.PRODUCT_LONG_NAME from " +
                " (select * from  ProjectStoreData where customerCode= \""+customerCode+"\" and customerProjectId = \""+customerProjectId+"\" and storeId = \""+storeId+"\") a  " +
                " right outer join  " +
                " (select ProjectUpc.upc, ProjectUpc.customerCode, ProjectUpc.customerProjectId,ProductMaster.BRAND_NAME,ProductMaster.PRODUCT_SHORT_NAME,ProductMaster.PRODUCT_LONG_NAME from ProjectUpc,ProductMaster"
                + " where customerCode=\""+customerCode+"\" and customerProjectId = \""+customerProjectId+"\" and ProjectUpc.UPC = ProductMaster.UPC) b  " +
                " on (a.upc = b.upc) " +
                "  and (a.customerCode = b.customerCode)  " +
                "  and (a.customerProjectId = b.customerProjectId) ";

        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("imageUUID", rs.getString("imageUUID"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("storeId", rs.getString("storeId"));
                map.put("upc", rs.getString("upc"));
                map.put("brand_name", rs.getString("BRAND_NAME"));
                map.put("product_short_name", rs.getString("PRODUCT_SHORT_NAME"));
                map.put("product_long_name", rs.getString("PRODUCT_LONG_NAME"));
                map.put("facing", rs.getString("facing"));
                map.put("upcConfidence", rs.getString("upcConfidence"));
                result.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectStoreResults----------------\n");
            return result;
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
    public List<LinkedHashMap<String, String>> getProjectTopStores(String customerCode, String customerProjectId, String limit) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoreResults::customerCode= "+customerCode+"::customerProjectId= "+customerProjectId+"limit = "+limit+"----------------\n");
        String sql = "select storeId, customerCode, customerProjectId, count(distinct(upc)) as order1, sum(facing) as order2, sum(upcConfidence) as order3 from ProjectStoreData where customerCode = ? and customerProjectId = ? and upc !=\"999999999999\" group by customerCode, customerProjectId, storeId order by order1 desc, order2 desc, order3 desc limit ?";
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setInt(3, Integer.valueOf(limit));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("storeId", rs.getString("storeId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("countDistinctUpc", rs.getString("order1"));
                map.put("sumFacing", rs.getString("order2"));
                map.put("sumUpcConfidence", rs.getString("order3"));
                result.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectTopStores----------------\n");
            return result;
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
    public List<LinkedHashMap<String, String>> getProjectBottomStores(String customerCode, String customerProjectId, String limit) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectBottomStores::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"limit ="+limit+"----------------\n");
        String sql = "select storeId, customerCode, customerProjectId, count(distinct(upc)) as order1, sum(facing) as order2, sum(upcConfidence) as order3 from ProjectStoreData where customerCode = ? and customerProjectId = ? and upc !=\"999999999999\" group by customerCode, customerProjectId, storeId order by order1 asc, order2 asc, order3 asc limit ?";
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setInt(3, Integer.valueOf(limit));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("storeId", rs.getString("storeId"));
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("countDistinctUpc", rs.getString("order1"));
                map.put("sumFacing", rs.getString("order2"));
                map.put("sumUpcConfidence", rs.getString("order3"));
                result.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectBottomStores----------------\n");
            return result;
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
	public List<LinkedHashMap<String, String>> getProjectStoreImages(
			String customerCode, String customerProjectId, String storeId) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoreImages::customerCode="+customerCode
				+"::customerProjectId="+customerProjectId+"::storeId="+storeId+"----------------\n");
        String sql = "SELECT imageUUID,dateId,agentId,taskId FROM ImageStoreNew WHERE  "
        		+ "customerCode = ? and customerProjectId = ? and storeId = ? order by dateId desc";
        List<LinkedHashMap<String,String>> imageStoreList=new ArrayList<LinkedHashMap<String,String>>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, storeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String,String> map =new LinkedHashMap<String,String>();
                map.put("imageUUID", rs.getString("imageUUID"));
                map.put("dateId", rs.getString("dateId"));
                map.put("agentId", rs.getString("agentId"));
                map.put("taskId", rs.getString("taskId"));
                
                imageStoreList.add(map);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getImages numberOfRows = "+imageStoreList+"----------------\n");

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
	public List<StoreWithImages> getProjectStoresWithNoUPCs(
			String customerCode, String customerProjectId) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoresWithNoUPCs::customerCode="+customerCode
				+"::customerProjectId="+customerProjectId+"----------------\n");
		
        String storesWithNoUPCSql = "select StoreMaster.storeId,StoreMaster.retailerStoreId,StoreMaster.retailerChainCode,StoreMaster.retailer,StoreMaster.street,StoreMaster.city,StoreMaster.stateCode,StoreMaster.state,StoreMaster.zip from StoreMaster JOIN "
        		+ "(select distinct(ImageStoreNew.storeId) from ImageStoreNew where ImageStoreNew.customerCode= ? and ImageStoreNew.customerProjectId= ? and ImageStoreNew.imageStatus = ? and ImageStoreNew.storeId not in "
        		+ "(select distinct(ProjectStoreData.storeId) from ProjectStoreData where ProjectStoreData.customerCode= ? and ProjectStoreData.customerProjectId= ?)) StoresWithNoUPC"
        		+ " ON StoreMaster.storeId = StoresWithNoUPC.storeId";
        
        String storeImagesWithNoUPCSql = "select imageUUID,dateId,agentId,taskId from ImageStoreNew where"
        		+ " storeId = ? and customerCode = ? and customerProjectId = ? and imageStatus= ?;";
        
        List<StoreWithImages> storesWithNoUPCList=new ArrayList<StoreWithImages>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(storesWithNoUPCSql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, "done");
            ps.setString(4, customerCode);
            ps.setString(5, customerProjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	StoreWithImages store = new StoreWithImages();
            	store.setStoreId(rs.getString("storeId"));
            	store.setRetailerStoreId(rs.getString("retailerStoreId"));
            	store.setRetailerChainCode( rs.getString("retailerChainCode"));
            	store.setRetailer( rs.getString("retailer"));
            	store.setStreet(rs.getString("street"));
            	store.setCity( rs.getString("city"));
            	store.setStateCode( rs.getString("stateCode"));
            	store.setState(rs.getString("state"));
            	store.setZip( rs.getString("zip"));
                //find image count and imageUUIDs for this store
                PreparedStatement imageUUIDPs = conn.prepareStatement(storeImagesWithNoUPCSql);
                imageUUIDPs.setString(1, store.getStoreId());
                imageUUIDPs.setString(2, customerCode);
                imageUUIDPs.setString(3, customerProjectId);
                imageUUIDPs.setString(4, "done");
                ResultSet imageUUIDRs = imageUUIDPs.executeQuery();
                int countOfImagesForStore = 0;
                List<StoreImageInfo> imageUUIDs = new ArrayList<StoreImageInfo>();
            	while (imageUUIDRs.next()){
            		countOfImagesForStore++;
            		StoreImageInfo imageInfo = new StoreImageInfo();
            		imageInfo.setImageUUID(imageUUIDRs.getString("imageUUID"));
            		imageInfo.setDateId(imageUUIDRs.getString("dateId"));
            		imageInfo.setAgentId(imageUUIDRs.getString("agentId"));
            		imageInfo.setTaskId(imageUUIDRs.getString("taskId"));
            		imageUUIDs.add(imageInfo);
                }
                imageUUIDRs.close();
                imageUUIDPs.close();
                store.setImageCount(String.valueOf(countOfImagesForStore));
                store.setImageUUIDs(imageUUIDs);
                storesWithNoUPCList.add(store);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectStoresWithNoUPCs numberOofStores = "+storesWithNoUPCList.size()+"----------------\n");

            return storesWithNoUPCList;
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
	public List<StoreWithImages> getProjectAllStoreImages(String customerCode, String customerProjectId) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectAllStoreImages::customerCode="+customerCode
				+"::customerProjectId="+customerProjectId+"----------------\n");
		
        String storesForCustomerCodeProjectIdSql = "select StoreMaster.storeId,StoreMaster.retailerStoreId,StoreMaster.retailerChainCode,StoreMaster.retailer,StoreMaster.street,StoreMaster.city,StoreMaster.stateCode,StoreMaster.state,StoreMaster.zip from StoreMaster JOIN "
        		+ "(select distinct(ImageStoreNew.storeId) from ImageStoreNew where ImageStoreNew.customerCode= ? and ImageStoreNew.customerProjectId= ?) StoresForCustomerCodeProjectId"
        		+ " ON StoreMaster.storeId = StoresForCustomerCodeProjectId.storeId";
        
        String storeImagesSql = "select imageUUID,dateId,agentId,taskId from ImageStoreNew where"
        		+ " storeId = ? and customerCode = ? and customerProjectId = ?";
        
        List<StoreWithImages> storesWithImages =new ArrayList<StoreWithImages>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(storesForCustomerCodeProjectIdSql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	StoreWithImages store = new StoreWithImages();
            	store.setStoreId(rs.getString("storeId"));
            	store.setRetailerStoreId(rs.getString("retailerStoreId"));
            	store.setRetailerChainCode( rs.getString("retailerChainCode"));
            	store.setRetailer( rs.getString("retailer"));
            	store.setStreet(rs.getString("street"));
            	store.setCity( rs.getString("city"));
            	store.setStateCode( rs.getString("stateCode"));
            	store.setState(rs.getString("state"));
            	store.setZip( rs.getString("zip"));
                //find image count and imageUUIDs for this store
                PreparedStatement imageUUIDPs = conn.prepareStatement(storeImagesSql);
                imageUUIDPs.setString(1, store.getStoreId());
                imageUUIDPs.setString(2, customerCode);
                imageUUIDPs.setString(3, customerProjectId);
                ResultSet imageUUIDRs = imageUUIDPs.executeQuery();
                int countOfImagesForStore = 0;
                List<StoreImageInfo> imageUUIDs = new ArrayList<StoreImageInfo>();
            	while (imageUUIDRs.next()){
            		countOfImagesForStore++;
            		StoreImageInfo imageInfo = new StoreImageInfo();
            		imageInfo.setImageUUID(imageUUIDRs.getString("imageUUID"));
            		imageInfo.setDateId(imageUUIDRs.getString("dateId"));
            		imageInfo.setAgentId(imageUUIDRs.getString("agentId"));
            		imageInfo.setTaskId(imageUUIDRs.getString("taskId"));
            		imageUUIDs.add(imageInfo);
                }
                imageUUIDRs.close();
                imageUUIDPs.close();
                store.setImageCount(String.valueOf(countOfImagesForStore));
                store.setImageUUIDs(imageUUIDs);
                storesWithImages.add(store);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectAllStoreImages numberOofStores = "+storesWithImages.size()+"----------------\n");

            return storesWithImages;
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
	public List<DuplicateImages> getProjectStoresWithDuplicateImages(String customerCode, String customerProjectId) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoresWithDuplicateImages::customerCode="+customerCode
				+"::customerProjectId="+customerProjectId+"----------------\n");
		
        String duplicateImagesSql = "SELECT imageStore.imageUUID, imageStore.storeId, imageStore.agentId, imageStore.taskId, imageStore.dateId, imageStore.imageHashScore, "
        		+ "store.retailerStoreId, store.retailerChainCode, store.retailer, store.street, store.city, store.stateCode, store.state, store.zip FROM ImageStoreNew imageStore, StoreMaster store"
        		+ " WHERE imageHashScore IN (SELECT * FROM (SELECT imageHashScore FROM ImageStoreNew  where customerCode=? and customerProjectId=? and imageHashScore is not null and imageHashScore > '0' GROUP BY imageHashScore HAVING COUNT(imageHashScore) > 1) AS a)"
        		+ " and imageStore.customerCode= ? and imageStore.customerProjectId = ? and imageStore.storeId = store.storeId order by imageHashScore";
        
        List<DuplicateImages> duplicateImagesList =new ArrayList<DuplicateImages>();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(duplicateImagesSql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, customerCode);
            ps.setString(4, customerProjectId);
            ResultSet rs = ps.executeQuery();
            String previousHashScore = "dummyhashscore";
            DuplicateImages dupImages = null;
            int count = 1 ;
            while (rs.next()) {
            	String hashScore = rs.getString("imageHashScore");
            	if ( !hashScore.equalsIgnoreCase(previousHashScore) ) {
            		if ( null !=  dupImages ) {
            			//Logic to exclude duplicate images from same store
            			Map<String,DuplicateImageInfo> uniqueDupMap = new LinkedHashMap<String,DuplicateImageInfo>();
            			for ( DuplicateImageInfo duplicate : dupImages.getStoreIds() ) {
            				uniqueDupMap.put(duplicate.getStoreId(),duplicate);
            			}
            			if ( uniqueDupMap.size() > 1 ) {
            				dupImages.getStoreIds().clear();
            				dupImages.getStoreIds().addAll(uniqueDupMap.values());
            				duplicateImagesList.add(dupImages);	
            			} else {
            				count--; //Discard this group of images as it is from the same store, and reset count to previous value
            			}
            		}
            		dupImages = new DuplicateImages();
            		dupImages.setImageHashScore(hashScore);
            		dupImages.setSlNo(""+count);
            		count++;
            		previousHashScore = hashScore;
            	}
            	DuplicateImageInfo dup = new DuplicateImageInfo();
            	dup.setStoreId(rs.getString("storeId"));
            	dup.setRetailerStoreId(rs.getString("retailerStoreId"));
            	dup.setRetailerChainCode( rs.getString("retailerChainCode"));
            	dup.setRetailer( rs.getString("retailer"));
            	dup.setStreet(rs.getString("street"));
            	dup.setCity( rs.getString("city"));
            	dup.setStateCode( rs.getString("stateCode"));
            	dup.setState( rs.getString("state"));
            	dup.setZip( rs.getString("zip"));
            	dup.setImageUUID(rs.getString("imageUUID"));
            	dup.setDateId(rs.getString("dateId"));
            	dup.setAgentId( rs.getString("agentId"));
            	dup.setTaskId( rs.getString("taskId"));
            	dupImages.getStoreIds().add(dup);
            }
            if (  null != dupImages ) {
            	//Logic to exclude duplicate images from same store
    			Map<String,DuplicateImageInfo> uniqueDupMap = new LinkedHashMap<String,DuplicateImageInfo>();
    			for ( DuplicateImageInfo duplicate : dupImages.getStoreIds() ) {
    				uniqueDupMap.put(duplicate.getStoreId(),duplicate);
    			}
    			if ( uniqueDupMap.size() > 1 ) {
    				dupImages.getStoreIds().clear();
    				dupImages.getStoreIds().addAll(uniqueDupMap.values());
    				duplicateImagesList.add(dupImages);	
    			} else {
    				//do nothing - Discard this group of images as it is from the same store
    			}
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectStoresWithDuplicateImages number of duplicates = "+duplicateImagesList.size()+"----------------\n");

            return duplicateImagesList;
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
	public List<LinkedHashMap<String, String>> generateStoreResults(String customerCode, String customerProjectId, String storeId) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts generateStoreResults::customerCode="+customerCode
				+"::customerProjectId="+customerProjectId+"::storeId="+storeId +"----------------\n");
		// get data from ProjectStoreData table for this customer code, project id and store combination
		List<String> aggUPCs = getDistinctUPCsForProject(customerCode, customerProjectId, storeId);
		LOGGER.info("---------------ProcessImageDaoImpl--generateStoreResults::UPCs in agg table :: " + aggUPCs + " ------------------");
		
		Map<String,String> projectStoreData =getProjectStoreData(customerCode, customerProjectId, storeId);
		String countDistinctUpc = projectStoreData.get("count");
		String sumFacing = projectStoreData.get("facing");
		String sumUpcConfidence = projectStoreData.get("confidence");
		
		//Set value to 0 if null or empty.. Set precision to 10 digits in case of decimals
		countDistinctUpc = (null==countDistinctUpc || "".equals(countDistinctUpc)) ? "0" : countDistinctUpc;
		sumFacing = (null==sumFacing || "".equals(sumFacing)) ? "0" : sumFacing;
		sumUpcConfidence = (null==sumUpcConfidence || "".equals(sumUpcConfidence)) ? "0" : sumUpcConfidence;
    	if ( sumUpcConfidence != "0" && sumUpcConfidence.contains(".") ) {
    		String[] confidenceParts = sumUpcConfidence.split("\\.");
    		if ( confidenceParts[1].length() > 9 ) {
    			sumUpcConfidence = confidenceParts[0] + "." + confidenceParts[1].substring(0,10);
    		}
    	}
		
		//calculate resultCode based on UPC findings
		LOGGER.info("---------------ProcessImageDaoImpl--generateStoreResults:: calculating store results ------------------");
		List<LinkedHashMap<String,String>> projectDetail = metaServiceDao.getProjectDetail(customerProjectId, customerCode);
		String resultCode = calculateStoreResult(aggUPCs, projectDetail.get(0), projectStoreData);
		LOGGER.info("---------------ProcessImageDaoImpl--generateStoreResults::Store Result :: " + resultCode + " ------------------");
		String status = resultCode.equals("3") ? "0":"1";
		LOGGER.info("---------------ProcessImageDaoImpl--generateStoreResults::Store Result Status :: " + status + " ------------------");

		String updateStoreResultSql = "UPDATE ProjectStoreResult SET resultCode = ?, countDistinctUpc = ?, sumFacing = ?, sumUpcConfidence = ?, status = ? WHERE customerCode = ? and customerProjectId = ? and storeId = ?";
		String insertStoreResultSql = "INSERT INTO ProjectStoreResult (customerCode, customerProjectId, storeId, resultCode, countDistinctUpc,sumFacing,sumUpcConfidence, status  ) VALUES (?,?,?,?,?,?,?,?)";
		String doResultExistsSql = "SELECT COUNT(*) AS COUNT FROM ProjectStoreResult WHERE customerCode = ? and customerProjectId = ? and storeId = ?";
		int count = 0;
		
		//update/insert resultCode based on UPC findings.
		Connection conn = null;
	    try {
	        conn = dataSource.getConnection();
	        PreparedStatement doResultExistsPs = conn.prepareStatement(doResultExistsSql);
	        doResultExistsPs.setString(1, customerCode);
	        doResultExistsPs.setString(2, customerProjectId);
	        doResultExistsPs.setString(3, storeId);
	        ResultSet doResultExistsRs = doResultExistsPs.executeQuery();
	        
	        if (doResultExistsRs.next()) {
	        	count = Integer.parseInt(doResultExistsRs.getString("COUNT"));
	        }
	        doResultExistsRs.close();
	        doResultExistsPs.close();
	        
	        if ( count > 0 ) {
	        	PreparedStatement updatePs = conn.prepareStatement(updateStoreResultSql);
	        	updatePs.setString(1, resultCode);
	        	updatePs.setInt(2, Integer.parseInt(countDistinctUpc));
	        	updatePs.setInt(3, Integer.parseInt(sumFacing));
	        	updatePs.setBigDecimal(4, new BigDecimal(sumUpcConfidence));
	        	updatePs.setString(5, status);
	        	updatePs.setString(6, customerCode);
	        	updatePs.setString(7, customerProjectId);
	        	updatePs.setString(8, storeId);
	        	updatePs.executeUpdate();
	        	updatePs.close();
	        	
	        } else {
	        	PreparedStatement insertPs = conn.prepareStatement(insertStoreResultSql);
	        	insertPs.setString(1, customerCode);
	        	insertPs.setString(2, customerProjectId);
	        	insertPs.setString(3, storeId);
	        	insertPs.setString(4, resultCode);
	        	insertPs.setInt(5, Integer.parseInt(countDistinctUpc));
	        	insertPs.setInt(6, Integer.parseInt(sumFacing));
	        	insertPs.setBigDecimal(7, new BigDecimal(sumUpcConfidence));
	        	insertPs.setString(8, status);
	        	insertPs.executeUpdate();
	        	insertPs.close();
	        }
	        
	        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();
	        LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
	        map.put("storeId", storeId);
	        map.put("resultCode", resultCode);
	        map.put("status", status);
	        result.add(map);
	        LOGGER.info("---------------ProcessImageDaoImpl Ends generateStoreResults result = "+map+"----------------\n");
	        return result;
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
	
	private Map<String, String> getProjectStoreData(String customerCode,String customerProjectId, String storeId) {
		Map<String,String> projectStoreData = new LinkedHashMap<String,String>();
		String countFacingConfidenceSql = " select count(distinct(upc)) as count, sum(facing) facing, sum(upcConfidence) confidence from ProjectStoreData "
				+ "where customerCode=? and customerProjectId=? and storeId=? and upc !=\"999999999999\"";
		
		String upcFacingSql = " select upc,facing from ProjectStoreData where customerCode=? and customerProjectId=? and storeId=? and upc !=\"999999999999\"";
		
		Connection conn = null;
	    try {
	        conn = dataSource.getConnection();
	        PreparedStatement ps = conn.prepareStatement(countFacingConfidenceSql);
	        ps.setString(1, customerCode);
	        ps.setString(2, customerProjectId);
	        ps.setString(3, storeId);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	        	projectStoreData.put("count", rs.getString("count"));
	        	projectStoreData.put("facing",rs.getString("facing") );
	        	projectStoreData.put("confidence", rs.getString("confidence") );
	        }
	        rs.close();
	        ps.close();
	        
	        PreparedStatement ps1 = conn.prepareStatement(upcFacingSql);
	        ps1.setString(1, customerCode);
	        ps1.setString(2, customerProjectId);
	        ps1.setString(3, storeId);
	        ResultSet rs1 = ps1.executeQuery();
	        
	        while(rs1.next()) {
	        	projectStoreData.put(rs1.getString("upc"), rs1.getString("facing"));
	        }
	        rs1.close();
	        ps1.close();
	        
	        
	        LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectStoreData ----------------\n");

	        return projectStoreData;
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

	private String calculateStoreResult(List<String> aggUPCs, Map<String,String> projectDetail, Map<String, String> projectStoreData) {
		String successCriteria = projectDetail.get("successCriteria");
		String partialCriteria = projectDetail.get("partialCriteria");
		String failedCriteria = projectDetail.get("failedCriteria");
		//First evaluate success Criteria
		if ( null != successCriteria && !successCriteria.isEmpty() ) {
			LOGGER.info("---------------ProcessImageDaoImpl--calculateStoreResult:: evaluating success Criteria :: " + successCriteria + " ------------------");
			boolean result = evaluate(successCriteria, aggUPCs, projectStoreData);
			if (result){
				return "1";
			}
		}
		//Then evaluate partial Criteria
		if ( null != partialCriteria && !partialCriteria.isEmpty()){
			LOGGER.info("---------------ProcessImageDaoImpl--calculateStoreResult:: evaluating partial Criteria :: " + partialCriteria + " ------------------");
			boolean result = evaluate(partialCriteria, aggUPCs, projectStoreData);
			if (result){
				return "2";
			}
		}
		//Then evaluate failed Criteria
		if ( null != failedCriteria && !failedCriteria.isEmpty()){
			LOGGER.info("---------------ProcessImageDaoImpl--calculateStoreResult:: evaluating failed Criteria :: " + failedCriteria + " ------------------");
			boolean result = evaluate(failedCriteria, aggUPCs, projectStoreData);
			if (result){
				return "3";
			}
		}
		//if control reaches here, failed.
		LOGGER.info("---------------ProcessImageDaoImpl--calculateStoreResult:: all criteria failed.. Marking store result as failed ------------------");
		return "3";
	}
	
	private boolean evaluate(String Criteria, List<String> aggUPCs, Map<String, String> projectStoreData) {
		boolean evaluateResult = false;
		String[] CriteriaParts = Criteria.split(" ");
		StringBuilder criteriaBuilder = new StringBuilder();
		for(String part : CriteriaParts ) {
			if ( part.startsWith("containsAll") ) {
				String upcPart = part.substring(part.indexOf("(") + 1, part.lastIndexOf(")"));
				List<String> upcList = Arrays.asList(upcPart.split(","));
				if ( aggUPCs.containsAll(upcList)){
					criteriaBuilder.append("true");
				} else {
					criteriaBuilder.append("false");
				}
			} else if ( part.startsWith("containsAny")) {
				String upcPart = part.substring(part.indexOf("(") + 1, part.lastIndexOf(")"));
				List<String> upcList = Arrays.asList(upcPart.split(","));
				if ( ifAnyPresentInList(upcList, aggUPCs)){
					criteriaBuilder.append("true");
				} else {
					criteriaBuilder.append("false");
				}
			} else if ( part.startsWith("containsNone")) {
				String upcPart = part.substring(part.indexOf("(") + 1, part.lastIndexOf(")"));
				List<String> upcList = Arrays.asList(upcPart.split(","));
				if ( !ifAnyPresentInList(upcList, aggUPCs)){
					criteriaBuilder.append("true");
				} else {
					criteriaBuilder.append("false");
				}
			} else if ( part.startsWith("sumFacings")) {
				String upcPart = part.substring(part.indexOf("(") + 1, part.lastIndexOf(")"));
				List<String> upcList = Arrays.asList(upcPart.split(","));
				int sumFacings = 0;
				for(String upc : upcList) {
					String facing =  projectStoreData.get(upc);
					if (facing != null && !facing.isEmpty() ) {
						sumFacings = sumFacings + Integer.parseInt(facing);
					}
				}
				criteriaBuilder.append(sumFacings);
			} else if ( part.equals("AND")){
				criteriaBuilder.append("&&");
			} else if ( part.equals("OR")) {
				criteriaBuilder.append("||");
			} else {
				criteriaBuilder.append(part);
			}
		}
		LOGGER.info("---------------ProcessImageDaoImpl--evaluate:: evaluating processed Criteria :: " + criteriaBuilder.toString() + " ------------------");
		try {
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine se = sem.getEngineByName("JavaScript");
            evaluateResult = (Boolean)se.eval(criteriaBuilder.toString());
        } catch (Exception e) {
           evaluateResult = false;
           LOGGER.error("---------------ProcessImageDaoImpl--evaluate:: evaluating processed Criteria failed :: " + e.getMessage() + " ------------------");
        }
		LOGGER.info("---------------ProcessImageDaoImpl--evaluate:: evaluation result :: " + evaluateResult + " ------------------");
		return evaluateResult;
	}

	private boolean ifAnyPresentInList(List<String> skuUPCsInProject,List<String> aggUPCs) {
		boolean isPresent = false;
		for( String upc : skuUPCsInProject ) {
			if ( aggUPCs.contains(upc) ) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	private List<String> getDistinctUPCsForProject(String customerCode, String customerProjectId, String storeId) {
		List<String> upcList = new ArrayList<String>();
		String distinctUPCSql = "select distinct(UPC) as UPC from ProjectStoreData where customerCode=? and customerProjectId=? and storeId=? and upc !=\"999999999999\"";
		
		Connection conn = null;
	    try {
	        conn = dataSource.getConnection();
	        PreparedStatement ps = conn.prepareStatement(distinctUPCSql);
	        ps.setString(1, customerCode);
	        ps.setString(2, customerProjectId);
	        ps.setString(3, storeId);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	        	upcList.add(rs.getString("UPC"));
	        }
	        rs.close();
	        ps.close();
	        LOGGER.info("---------------ProcessImageDaoImpl Ends getDistinctUPCsForProject ----------------\n");

	        return upcList;
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
	public List<LinkedHashMap<String, String>> getProjectAllStoreResults(String customerCode, String customerProjectId) {
		 LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectAllStoreResults::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"----------------\n");
	        String sql = "SELECT result.storeId, store.retailerStoreId, store.retailerChainCode, store.retailer, store.street, store.city, store.stateCode, store.state, store.zip, result.resultCode, resultsMaster.description, result.countDistinctUpc, result.sumFacing, result.sumUpcConfidence, result.status "
	        		+ "FROM ProjectStoreResult result, StoreMaster store, ResultsMaster resultsMaster WHERE result.customerCode = ? and result.customerProjectId = ? and result.storeId = store.storeId and result.resultCode = resultsMaster.resultCode order by result.resultCode asc, result.countDistinctUpc desc, result.sumFacing desc, result.sumUpcConfidence desc ";
	        Connection conn = null;
	        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

	        try {
	            conn = dataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, customerCode);
	            ps.setString(2, customerProjectId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	                map.put("storeId", rs.getString("storeId"));
	                map.put("retailerStoreId", rs.getString("retailerStoreId"));
	                map.put("retailerChainCode", rs.getString("retailerChainCode"));
	                map.put("retailer", rs.getString("retailer"));
	                map.put("street", rs.getString("street"));
	                map.put("city", rs.getString("city"));
	                map.put("stateCode", rs.getString("stateCode"));
	                map.put("state", rs.getString("state"));
	                map.put("zip", rs.getString("zip"));
	                map.put("resultCode", rs.getString("resultCode"));
	                map.put("result", rs.getString("description"));
	                map.put("countDistinctUpc", String.valueOf(rs.getInt("countDistinctUpc")));
	                map.put("sumFacing", String.valueOf(rs.getInt("sumFacing")));
	                map.put("sumUpcConfidence", String.valueOf(rs.getBigDecimal("sumUpcConfidence")));
	                map.put("status", rs.getString("status"));
	                result.add(map);
	            }
	            rs.close();
	            ps.close();
	            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectAllStoreResults----------------\n");
	            return result;
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
	public List<LinkedHashMap<String, String>> getProjectAllStoreResultsDetail(String customerCode, String customerProjectId) {
		 LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectAllStoreResultsDetail::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"----------------\n");
	     List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

	     //Get all UPCs for this project with facing initialized to 0
		 Map<String,String> projectUpcFacingMap = getProjectUpcFacingMap(customerCode,customerProjectId);
		 //Get facing count for each UPC per store for ths project
		 Map<String,Map<String,String>> upcFacingPerStoreMap = getUpcFacingPerStoreMap(customerCode,customerProjectId);
		 
		 String sql = "SELECT result.storeId, store.retailerStoreId, store.retailerChainCode, store.retailer, store.street, store.city, store.stateCode, store.state, store.zip, result.resultCode, resultsMaster.description, result.countDistinctUpc, result.sumFacing, result.sumUpcConfidence, result.status "
	        		+ "FROM ProjectStoreResult result, StoreMaster store, ResultsMaster resultsMaster WHERE result.customerCode = ? and result.customerProjectId = ? and result.storeId = store.storeId and result.resultCode = resultsMaster.resultCode order by result.resultCode asc, result.countDistinctUpc desc, result.sumFacing desc, result.sumUpcConfidence desc ";
	        Connection conn = null;

	        try {
	            conn = dataSource.getConnection();
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setString(1, customerCode);
	            ps.setString(2, customerProjectId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	                String storeId = rs.getString("storeId");
	                map.put("storeId", storeId);
	                map.put("retailerStoreId", rs.getString("retailerStoreId"));
	                map.put("retailerChainCode", rs.getString("retailerChainCode"));
	                map.put("retailer", rs.getString("retailer"));
	                map.put("street", rs.getString("street"));
	                map.put("city", rs.getString("city"));
	                map.put("stateCode", rs.getString("stateCode"));
	                map.put("state", rs.getString("state"));
	                map.put("zip", rs.getString("zip"));
	                map.put("resultCode", rs.getString("resultCode"));
	                map.put("result", rs.getString("description"));
	                map.put("countDistinctUpc", String.valueOf(rs.getInt("countDistinctUpc")));
	                map.put("sumFacing", String.valueOf(rs.getInt("sumFacing")));
	                map.put("sumUpcConfidence", String.valueOf(rs.getBigDecimal("sumUpcConfidence")));
	                map.put("status", rs.getString("status"));
	                map.putAll(projectUpcFacingMap);
	                if ( upcFacingPerStoreMap.containsKey(storeId)){
		                map.putAll(upcFacingPerStoreMap.get(storeId));
	                }
	                result.add(map);
	            }
	            rs.close();
	            ps.close();
	            LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectAllStoreResultsDetail----------------\n");
	            return result;
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

	private Map<String, Map<String, String>> getUpcFacingPerStoreMap(
			String customerCode, String customerProjectId) {
		String upcForProjectSql = "select t1.storeId,t1.upc,t1.facing from ProjectStoreData t1, (select upc from ProjectUpc where customerCode=? and customerProjectId=?) t2 "
				+ "	where t1.customerCode=? and t1.customerProjectId=? and t1.upc != \"999999999999\" and t1.upc = t2.upc";
		Map<String,Map<String,String>> upcFacingPerStoreMap = new LinkedHashMap<String,Map<String,String>>();
		 Connection conn = null;
		 try {
			 conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(upcForProjectSql);
	         ps.setString(1, customerCode);
	         ps.setString(2, customerProjectId);
	         ps.setString(3, customerCode);
	         ps.setString(4, customerProjectId);
	         ResultSet rs = ps.executeQuery();
	         String previousStoreId = "dummyStoreId";
	         String storeId = null;
	         Map<String,String> upcFacingMap = new LinkedHashMap<String,String>();
	         while(rs.next()){
	        	 storeId = rs.getString("storeId");
	        	 if (!storeId.equalsIgnoreCase(previousStoreId)){
	        		 if ( previousStoreId.equalsIgnoreCase("dummyStoreId")) {
	        			 previousStoreId = storeId;
	        		 }
	        		 upcFacingPerStoreMap.put(previousStoreId, upcFacingMap);
	        		 upcFacingMap = new LinkedHashMap<String,String>();
	        		 previousStoreId = storeId;
	        	 }
	    		 upcFacingMap.put(rs.getString("upc"), rs.getString("facing"));
	         }
	         if (storeId != null ) {
	        	 upcFacingPerStoreMap.put(storeId, upcFacingMap);
	         }
	         rs.close();
	         ps.close();
		 } catch (SQLException e){
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
		 return upcFacingPerStoreMap;
	}

	private Map<String, String> getProjectUpcFacingMap(String customerCode,
			String customerProjectId) {
		 String upcForProjectSql = "select upc from ProjectUpc where customerCode=? and customerProjectId=?";
		 Map<String,String> upcForProjectMap = new LinkedHashMap<String,String>();
		 Connection conn = null;
		 try {
			 conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(upcForProjectSql);
	         ps.setString(1, customerCode);
	         ps.setString(2, customerProjectId);
	         ResultSet rs = ps.executeQuery();
	         while(rs.next()){
	        	 upcForProjectMap.put(rs.getString("upc"), "0");
	         }
	         rs.close();
	         ps.close();
		 } catch (SQLException e){
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
		 return upcForProjectMap;
     }

	@Override
	public void reprocessProjectByStore(String customerCode, String customerProjectId, List<String> storeIdsToReprocess) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts reprocessProjectByStore ----------------\n");

		String deleteImageAnalysisSql = "DELETE FROM ImageAnalysisNew WHERE customercode=? AND customerProjectId=? AND storeId=?";
		String deleteProjectStoreResultsSql = "DELETE FROM ProjectStoreResult WHERE customercode=? AND customerProjectId=? AND storeId=?";
		String deleteProjectStoreDataSql = "DELETE FROM ProjectStoreData WHERE customercode=? AND customerProjectId=? AND storeId=?";
		String updateImageStatusSql = "UPDATE ImageStoreNew SET imageStatus=? WHERE customercode=? AND customerProjectId=? AND storeId=?";
		
		Connection conn = null;
		PreparedStatement deleteImageAnalysisPs = null;
	    PreparedStatement deleteProjectStoreResultsPs = null;
	    PreparedStatement deleteProjectStoreDataPs = null;
	    PreparedStatement updateImageStatusPs = null;
		
	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);
	        
	        deleteImageAnalysisPs = conn.prepareStatement(deleteImageAnalysisSql);
	        deleteProjectStoreResultsPs = conn.prepareStatement(deleteProjectStoreResultsSql);
	        deleteProjectStoreDataPs = conn.prepareStatement(deleteProjectStoreDataSql);
	        updateImageStatusPs = conn.prepareStatement(updateImageStatusSql);
	        
	        for ( String storeId : storeIdsToReprocess ) {
	        	deleteImageAnalysisPs.setString(1, customerCode);
		        deleteImageAnalysisPs.setString(2, customerProjectId);
		        deleteImageAnalysisPs.setString(3, storeId);
		        deleteImageAnalysisPs.addBatch();
		        
		        deleteProjectStoreResultsPs.setString(1, customerCode);
		        deleteProjectStoreResultsPs.setString(2, customerProjectId);
		        deleteProjectStoreResultsPs.setString(3, storeId);
		        deleteProjectStoreResultsPs.addBatch();
		        
		        deleteProjectStoreDataPs.setString(1, customerCode);
		        deleteProjectStoreDataPs.setString(2, customerProjectId);
		        deleteProjectStoreDataPs.setString(3, storeId);
		        deleteProjectStoreDataPs.addBatch();
		        
		        updateImageStatusPs.setString(1, "cron");
		        updateImageStatusPs.setString(2, customerCode);
		        updateImageStatusPs.setString(3, customerProjectId);
		        updateImageStatusPs.setString(4, storeId);
		        updateImageStatusPs.addBatch();
	        }
	        
	        deleteImageAnalysisPs.executeBatch();
	        deleteProjectStoreResultsPs.executeBatch();
	        deleteProjectStoreDataPs.executeBatch();
	        updateImageStatusPs.executeBatch();
	        
	        conn.commit();
	        
	     } catch (SQLException e) {
	         LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	         LOGGER.error("exception", e);
	         throw new RuntimeException(e);
	     } finally {
	         if (deleteImageAnalysisPs != null) {
	           try {   
	        	   deleteImageAnalysisPs.close();
                } catch (SQLException e) {
	                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	                 LOGGER.error("exception", e);
	            }
	         }
	         if (deleteProjectStoreResultsPs != null) {
		           try {   
		        	   deleteProjectStoreResultsPs.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         if (deleteProjectStoreDataPs != null) {
		           try {   
		        	   deleteProjectStoreDataPs.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         if (updateImageStatusPs != null) {
		           try {   
		        	   updateImageStatusPs.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         if (conn != null) {
		           try {   
		            	 conn.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         LOGGER.info("---------------ProcessImageDaoImpl Ends reprocessProjectByStore ----------------\n");
	      }
	}

	@Override
	public List<String> getProjectStoreIds(String customerCode, String customerProjectId, boolean onlyDone) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts getProjectStoreIds ----------------\n");
		List<String> storeIdsForProject = new ArrayList<String>();
		StringBuilder getAllStoresForProjectSqlBuilder = new StringBuilder("SELECT DISTINCT(storeId) FROM ImageStoreNew WHERE customerCode=? AND customerProjectId=?");
		if (onlyDone) {
			getAllStoresForProjectSqlBuilder.append(" AND imageStatus in (\"done\",\"error\")");
		}
		Connection conn = null;
	    try {
	        conn = dataSource.getConnection();
	        PreparedStatement ps = conn.prepareStatement(getAllStoresForProjectSqlBuilder.toString());
	        ps.setString(1, customerCode);
	        ps.setString(2, customerProjectId);
	        ResultSet rs = ps.executeQuery();
	        
	        while (rs.next()) {
	        	storeIdsForProject.add(rs.getString("storeId"));
	        }
	        rs.close();
	        ps.close();
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
	         LOGGER.info("---------------ProcessImageDaoImpl Ends getProjectStoreIds ----------------\n");
	      }
		return storeIdsForProject;
	}

	@Override
	public void updateProjectResultByStore(String customerCode,
			String customerProjectId, List<String> storeIds, String resultCode) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts updateProjectResultByStore ----------------\n");

		String updateResultCodeSql = "UPDATE ProjectStoreResult SET resultCode=? WHERE customercode=? AND customerProjectId=? AND storeId=?";
		
		Connection conn = null;
		PreparedStatement updateResultCodePs = null;
		
	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);
	        
	        updateResultCodePs = conn.prepareStatement(updateResultCodeSql);
	        
	        for ( String storeId : storeIds ) {
	        	updateResultCodePs.setString(1, resultCode);
	        	updateResultCodePs.setString(2, customerCode);
	        	updateResultCodePs.setString(3, customerProjectId);
	        	updateResultCodePs.setString(4, storeId);
	        	updateResultCodePs.addBatch();
	        }
	        
	        updateResultCodePs.executeBatch();
	        
	        conn.commit();
	        
	     } catch (SQLException e) {
	         LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	         LOGGER.error("exception", e);
	         throw new RuntimeException(e);
	     } finally {
	         if (updateResultCodePs != null) {
	           try {   
	        	   updateResultCodePs.close();
                } catch (SQLException e) {
	                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	                 LOGGER.error("exception", e);
	            }
	         }
	         if (conn != null) {
		           try {   
		            	 conn.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         LOGGER.info("---------------ProcessImageDaoImpl Ends updateProjectResultByStore ----------------\n");
	      }
		
	}

	@Override
	public void updateProjectResultStatusByStore(String customerCode,
			String customerProjectId, List<String> storeIds, String status) {
		LOGGER.info("---------------ProcessImageDaoImpl Starts updateProjectResultStatusByStore ----------------\n");

		String updateStatusSql = "UPDATE ProjectStoreResult SET status=? WHERE customercode=? AND customerProjectId=? AND storeId=?";
		
		Connection conn = null;
		PreparedStatement updateStatusPs = null;
		
	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);
	        
	        updateStatusPs = conn.prepareStatement(updateStatusSql);
	        
	        for ( String storeId : storeIds ) {
	        	updateStatusPs.setString(1, status);
	        	updateStatusPs.setString(2, customerCode);
	        	updateStatusPs.setString(3, customerProjectId);
	        	updateStatusPs.setString(4, storeId);
	        	updateStatusPs.addBatch();
	        }
	        
	        updateStatusPs.executeBatch();
	        
	        conn.commit();
	        
	     } catch (SQLException e) {
	         LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	         LOGGER.error("exception", e);
	         throw new RuntimeException(e);
	     } finally {
	         if (updateStatusPs != null) {
	           try {   
	        	   updateStatusPs.close();
                } catch (SQLException e) {
	                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
	                 LOGGER.error("exception", e);
	            }
	         }
	         if (conn != null) {
		           try {   
		            	 conn.close();
	                } catch (SQLException e) {
		                 LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
		                 LOGGER.error("exception", e);
		            }
		         }
	         LOGGER.info("---------------ProcessImageDaoImpl Ends updateProjectResultStatusByStore ----------------\n");
	      }
	}
}
