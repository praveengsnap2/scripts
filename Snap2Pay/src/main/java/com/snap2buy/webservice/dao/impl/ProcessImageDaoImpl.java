package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.ImageAnalysis;
import com.snap2buy.webservice.model.ImageStore;
import com.snap2buy.webservice.model.UpcFacingDetail;
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
        String sql = "insert into ImageStoreNew (imageUUID, userId, ImageFilePath, categoryId, latitude, longitude, timeStamp, storeId, hostId, dateId, imageStatus, shelfStatus, origWidth, origHeight, newWidth, newHeight, thumbnailPath, customerCode, customerProjectId, taskId, agentId)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                        rs.getString("agentId"));
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
                        rs.getString("agentId"));
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
        String sql = "UPDATE ImageStoreNew SET imageStatus = ? WHERE imageUUID = ? ";
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
    public String getNextImageUuid() {
        LOGGER.info("---------------ProcessImageDaoImpl Starts getNextImageUuid----------------\n");
        String sql = "SELECT imageUUID FROM ImageStoreNew WHERE imageStatus = \"cron\" order by timeStamp limit 1";
        Connection conn = null;
        String imageUUID="queryFailed";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                imageUUID = rs.getString("imageUUID");
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProcessImageDaoImpl Ends getNextImageUuid imageUUID = "+imageUUID+"----------------\n");

            return imageUUID;
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
                    ps.setString(9, imageAnalysis.getAlternateUpc());
                    ps.setString(10, imageAnalysis.getAlternateUpcConfidence());
                    ps.setString(11, imageAnalysis.getLeftTopX());
                    ps.setString(12, imageAnalysis.getLeftTopY());
                    ps.setString(13, imageAnalysis.getWidth());
                    ps.setString(14, imageAnalysis.getHeight());
                    ps.setString(15, imageAnalysis.getPromotion());
                    ps.setString(16, imageAnalysis.getPrice());
                    ps.setString(17, imageAnalysis.getPriceLabel());
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
    public List<LinkedHashMap<String, String>> generateAggs(String customerCode, String customerProjectId, String storeId, String imageUUID) {
        LOGGER.info("---------------ProcessImageDaoImpl Starts generateAggs::::customerCode="+customerCode+"::customerProjectId="+customerProjectId+"::storeId="+storeId+"imageUUID= "+imageUUID+"----------------\n");
        //String sql = "select imageUUID, customerCode, customerProjectId, storeId, upc, max(facing) as facing, upcConfidence from (select customerCode, customerProjectId, storeId, imageUUID, upc, count(upc) as facing, avg(upcConfidence) as upcConfidence from ImageAnalysisNew where customerCode = ? and customerProjectId = ? and storeId = ? and imageUUID = ? group by customerCode, customerProjectId, storeId, imageUUID, upc ) a group by customerCode, customerProjectId, storeId, upc order by upc";
        String sql ="SELECT d.imageUUID, " +
                "       d.customerCode, " +
                "       d.customerProjectId, " +
                "       d.storeId, " +
                "       d.newUpc AS upc, " +
                "       max(facing) AS facing, " +
                "       upcConfidence " +
                "FROM " +
                "  (SELECT c.imageUUID, " +
                "          c.customerCode,  " +
                "          c.customerProjectId, " +
                "          c.storeId, " +
                "          c.newUpc, " +
                "          count(c.newUpc) AS facing, " +
                "                             avg(c.upcConfidence) AS upcConfidence " +
                "   FROM " +
                "     (SELECT imageUUID, " +
                "             customerCode, " +
                "             customerProjectId, " +
                "             storeId, " +
                "             upcConfidence, " +
                "             CASE WHEN b.upc IS NULL THEN \"999999999999\" ELSE b.upc END AS newUpc " +
                "      FROM ImageAnalysisNew a " +
                "      LEFT OUTER JOIN " +
                "        ( SELECT upc " +
                "         FROM ProjectUpc " +
                "         WHERE customerCode = \""+customerCode+"\" "+
                "           AND customerProjectId = \""+customerProjectId+"\" ) b ON (a.upc=b.upc) " +
                "      WHERE customerCode = \"" +customerCode+"\" "+
                "        AND customerProjectId = \""+customerProjectId +"\" "+
                "        AND storeId = \""+storeId +"\" "+
                "        AND imageUUID = \""+imageUUID +"\" "+
                "   GROUP BY c.customerCode, " +
                "            c.customerProjectId, " +
                "            c.storeId, " +
                "            c.newUpc, " +
                "            c.imageUUID) d " +
                "GROUP BY d.customerCode, " +
                "         d.customerProjectId, " +
                "         d.storeId, " +
                "         d.newUpc ";
        String sql2 = "insert into ProjectStoreData (imageUUID, customerCode, customerProjectId, storeId, upc, facing, upcConfidence) values (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        Connection conn2 = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            conn2 = dataSource.getConnection();
            conn2.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            PreparedStatement ps2 = conn2.prepareStatement(sql2);

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
                result.add(map);
                ps2.setString(1, map.get("imageUUID"));
                ps2.setString(2, map.get("customerCode"));
                ps2.setString(3, map.get("customerProjectId"));
                ps2.setString(4, map.get("storeId"));
                ps2.setString(5, map.get("upc"));
                ps2.setString(6, map.get("facing"));
                ps2.setString(7, map.get("upcConfidence"));
                ps2.addBatch();
            }
            int[] rs2 = ps2.executeBatch();
            rs.close();
            ps.close();
            ps2.close();

            LOGGER.info("---------------ProcessImageDaoImpl Ends generateAggs----------------\n");
            return result;
        } catch (SQLException e) {
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);
            throw new RuntimeException(e);
        } finally {
            if ((conn != null)||(conn2 != null)) {
                try {
                    conn.close();
                    conn2.close();
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
        String sql = "select * from ProjectStoreData where customerCode = ? and customerProjectId = ? and storeId = ? order by facing";
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, storeId);
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
        String sql = "select customerCode, customerProjectId, count(distinct(upc)) as order1, sum(facing) as order2, sum(upcConfidence) as order3 from ProjectStoreData where customerCode = ? and customerProjectId = ? group by customerCode, customerProjectId, storeId order by order1, order2, order3 desc limit ?";
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("countDistinctUpc", rs.getString("order1"));
                map.put("sumfacing", rs.getString("order2"));
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
        String sql = "select customerCode, customerProjectId, count(distinct(upc)) as order1, sum(facing) as order2, sum(upcConfidence) as order3 from ProjectStoreData where customerCode = ? and customerProjectId = ? group by customerCode, customerProjectId, storeId order by order1, order2, order3 asc limit ?";
        Connection conn = null;
        List<LinkedHashMap<String,String>> result=new ArrayList<LinkedHashMap<String,String>>();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customerCode);
            ps.setString(2, customerProjectId);
            ps.setString(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("customerCode", rs.getString("customerCode"));
                map.put("customerProjectId", rs.getString("customerProjectId"));
                map.put("countDistinctUpc", rs.getString("order1"));
                map.put("sumfacing", rs.getString("order2"));
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
}
