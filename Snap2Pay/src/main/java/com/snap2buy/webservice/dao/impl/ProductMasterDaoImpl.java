package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.ProductMasterDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.DistributionList;
import com.snap2buy.webservice.model.ProductMaster;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachin on 1/23/16.
 */
@Component(value = BeanMapper.BEAN_PRODUCT_MASTER_DAO)
@Scope("prototype")
public class ProductMasterDaoImpl implements ProductMasterDao {
    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "base-spring-ctx.xml");
        ProductMasterDaoImpl productMasterDaoImpl = (ProductMasterDaoImpl) context.getBean("ProductMasterDaoImpl");
        LOGGER.info("Checking logger");

        productMasterDaoImpl.getThumbnails("381519019340");

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProductMaster getUpcDetails(String upc) {
        LOGGER.info("---------------ProductMasterDaoImpl Starts getUpcDetails::upc="+upc+"----------------\n");
        String sql = "SELECT * FROM ProductMaster WHERE UPC = ?";

        Connection conn = null;
        ProductMaster productMaster = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, upc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                productMaster = new ProductMaster(rs.getString("UPC"),
                        rs.getString("MFG_NAME"),
                        rs.getString("BRAND_NAME"),
                        rs.getString("PRODUCT_TYPE"),
                        rs.getString("PRODUCT_SHORT_NAME"),
                        rs.getString("PRODUCT_LONG_NAME"),
                        rs.getString("attribute_1"),
                        rs.getString("attribute_2"),
                        rs.getString("attribute_3"),
                        rs.getString("attribute_4"),
                        rs.getString("attribute_5"),
                        rs.getString("why_buy_1"),
                        rs.getString("why_buy_2"),
                        rs.getString("why_buy_3"),
                        rs.getString("why_buy_4"),
                        rs.getString("romance_copy_1"),
                        rs.getString("romance_copy_2"),
                        rs.getString("romance_copy_3"),
                        rs.getString("romance_copy_4"),
                        rs.getString("height"),
                        rs.getString("width"),
                        rs.getString("depth"),
                        rs.getString("product_rating")
                );
                LOGGER.info("---------------ProductMasterDaoImpl - " + productMaster.toString() + "----------------\n");

            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProductMasterDaoImpl Ends getUpcDetails----------------\n");

            return productMaster;
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
    public File getThumbnails(String upc) {
        LOGGER.info("---------------ProductMasterDaoImpl Starts getThumbnails::upc="+upc+"----------------\n");
        String sql = "SELECT name, image FROM Thumbnails WHERE id = ?";
        String filePath = "filePath not found";
        File image = null;
        Connection conn = null;
        ProductMaster productMaster = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, upc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString(1);
                LOGGER.info("---------------ProductMasterDaoImpl - getThumbnails image found " + name + "----------------\n");
                String dirPath = "/tmp/snap2buy/image/";
                File dir = new File(dirPath);
                if (!dir.getParentFile().exists()) {
                    dir.getParentFile().mkdirs();
                    dir.getParentFile().setReadable(true);
                    dir.getParentFile().setWritable(true);
                    dir.getParentFile().setExecutable(true);
                } else if (!dir.exists()) {
                    dir.setReadable(true);
                    dir.setWritable(true);
                    dir.setExecutable(true);
                }
                filePath = "/tmp/snap2buy/image/" + name;
                image = new File(filePath);
                FileOutputStream fos = new FileOutputStream(image);

                byte[] buffer = new byte[1];
                InputStream is = rs.getBinaryStream(2);
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
                fos.close();
            } else {
                String name = "default.jpg";
                LOGGER.info("---------------ProductMasterDaoImpl - getThumbnails " + name + "----------------\n");
                filePath = "/tmp/snap2buy/image/" + name;
                image = new File(filePath);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProductMasterDaoImpl Ends getThumbnails----------------\n");

            return image;

        } catch (Exception e) {
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
    public void storeThumbnails() {
        LOGGER.info("---------------ProductMasterDaoImpl Starts storeThumbnails----------------\n");

        String sql = "insert into Thumbnails(id, name, image) values (?, ?, ?)";
        String imageNameList[] = {
                "016000147720.JPG", "022400393650.jpg", "030000263938.JPG", "038000391255.JPG", "071249239988.jpg", "079400256003.jpg", "080878171026.jpg", "381519031625.JPG", "381519181726.jpg", "603084297634.jpg",
                 };

        Connection conn = null;
        ProductMaster productMaster = null;
        FileInputStream fis = null;

        for (String name : imageNameList) {
            try {
                File file = new File("/home/naveen/Image_Thumbnails/" + name);
                fis = new FileInputStream(file);
                conn = dataSource.getConnection();
                conn.setAutoCommit(false);

                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, name.replace(".png", "").replace(".jpg", "").replace(".JPG", "").replace(".PNG", "").replace(".jpeg", "").replace(".JPEG", ""));
                ps.setString(2, name);
                ps.setBinaryStream(3, fis, (int) file.length());
                ps.executeUpdate();
                conn.commit();

                ps.close();
                fis.close();

                LOGGER.info("---------------ProductMasterDaoImpl Ends storeThumbnails----------------\n");

            } catch (SQLException e) {
                LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
                LOGGER.error("exception", e);
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                        fis.close();
                    } catch (SQLException e) {
                        LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
                        LOGGER.error("exception", e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public List<DistributionList> getDistributionLists() {
        LOGGER.info("---------------ProductMasterDaoImpl Starts getDistributionLists----------------\n");
        String sql = "SELECT * FROM DistributionList ";

        Connection conn = null;
        List<DistributionList> listDistributionList=new ArrayList<DistributionList>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DistributionList distributionList=new DistributionList(rs.getString("ListId"),rs.getString("ListName"),rs.getString("Owner"),rs.getString("CreatedTime"),rs.getString("LastModifiedTime"));
                listDistributionList.add(distributionList);
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProductMasterDaoImpl Ends getDistributionLists" + listDistributionList.size() + "----------------\n");

            return listDistributionList;
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
    public List<String> getUpcForList(String listId) {
        LOGGER.info("---------------ProductMasterDaoImpl Starts getUpcForList----------------\n");
        String sql = "SELECT upc FROM DistributionListMapping where listId = ?";

        Connection conn = null;
        List<String> listUpc=new ArrayList<String>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,listId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listUpc.add(rs.getString("upc"));
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ProductMasterDaoImpl Ends listUpc" + listUpc.size() + "----------------\n");

            return listUpc;
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
