package com.snap2pay.webservice.dao.impl;

import com.snap2pay.webservice.dao.ProductMasterDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ProductMaster;
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

/**
 * Created by sachin on 1/23/16.
 */
@Component(value = BeanMapper.BEAN_PRODUCT_MASTER_DAO)
@Scope("prototype")
public class ProductMasterDaoImpl implements ProductMasterDao {
  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public ProductMaster getUpcDetails(String upc) {
    LOGGER.info("---------------ProductMasterDaoImpl Starts getUpcDetails----------------\n");
    String sql = "SELECT * FROM ProductMaster WHERE UPC = ?";

    Connection conn = null;
    ProductMaster productMaster = null;
    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, upc);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        productMaster = new ProductMaster(rs.getString("UPC"), rs.getString("MFG_NAME"), rs.getString("BRAND_NAME"), rs.getString("PRODUCT_TYPE"), rs.getString("PRODUCT_SHORT_NAME"), rs.getString("PRODUCT_LONG_NAME"), rs.getString("low_fat"), rs.getString("fat_free"), rs.getString("gluten_free"), rs.getString("why_buy_1"), rs.getString("why_buy_2"), rs.getString("why_buy_3"), rs.getString("why_buy_4"), rs.getString("romance_copy_1"), rs.getString("romance_copy_2"), rs.getString("romance_copy_3"), rs.getString("romance_copy_4"), rs.getString("height"), rs.getString("width"), rs.getString("depth"), rs.getString("product_rating"));
          LOGGER.info("---------------ProductMasterDaoImpl - "+productMaster.toString()+"----------------\n");

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
    LOGGER.info("---------------ProductMasterDaoImpl Starts getThumbnails----------------\n");
    String sql = "SELECT name, image FROM Thumbnails WHERE id = ?";
    String filePath = "filePath not found";
    File image= null;
    Connection conn = null;
    ProductMaster productMaster = null;
    try {
      conn = dataSource.getConnection();
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, upc);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
          String name = rs.getString(1);
          LOGGER.info("---------------ProductMasterDaoImpl - getThumbnails image found "+name+"----------------\n");
          filePath = "/tmp/snap2buy/image/" + name;
        image = new File(filePath);
        FileOutputStream fos = new FileOutputStream(image);

        byte[] buffer = new byte[1];
        InputStream is = rs.getBinaryStream(2);
        while (is.read(buffer) > 0) {
          fos.write(buffer);
        }
        fos.close();
      }
        else{
          String name = "default.jpg";
          LOGGER.info("---------------ProductMasterDaoImpl - getThumbnails "+name+"----------------\n");
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
      "011111040908.png", "011111040946.png", "011111057593.png", "011111058101.png", "011111074705.png", "011111076242.png", "011111076266.png", "011111079731.png", "011111115224.png", "011111122246.png", "011111123311.png", "011111124127.png", "011111124257.png", "011111125186.png", "011111127388.png", "011111143104.png", "011111165106.png", "011111216631.png", "011111262706.png", "011111265936.png", "011111265943.png", "011111265950.png", "011111266018.png", "011111269736.png", "011111610118.png", "011111610156.png", "011111610798.png", "011111611023.png", "011111611207.png", "011111611337.png", "011111611641.png", "011111613287.png", "011111613348.png", "011111613782.png", "011111614246.png", "016000146228.jpg", "016000151482.jpg", "016000168930.jpg", "016000168947.jpg", "016000262829.jpg", "016000264601.jpg", "016000264700.jpg", "016000275058.jpg", "016000275119.jpg", "016000275164.jpg", "016000275294.jpg", "016000275324.jpg", "016000275362.jpg", "016000275393.jpg", "016000275492.jpg", "016000275652.jpg", "016000275720.jpg", "016000277069.jpg", "016000277076.jpg", "016000278554.jpg", "016000284159.jpg", "016000295704.jpg", "016000298606.jpg", "016000411265.jpg", "016000411487.jpg", "016000411494.jpg", "016000412699.jpg", "016000413146.jpg", "016000415614.jpg", "016000417274.jpg", "016000418882.jpg", "016000420151.jpg", "016000431010.jpg", "016000431027.jpg", "016000434165.jpg", "016000435087.jpg", "016000439801.jpg", "016000450646.jpg", "016000456822.jpg", "016000457980.jpg", "016000461260.jpg", "016000482876.jpg", "016000482883.jpg", "022400393643.jpg", "022400393650.jpg", "022400393667.jpg", "022400393674.jpg", "022400393681.jpg", "022400393728.jpg", "022400393735.jpg", "022400393742.jpg", "022400393759.jpg", "022400393766.jpg", "022400393773.jpg", "022400393797.jpg", "022400415871.jpg", "022400415918.jpg", "022400416168.jpg", "022400429793.jpg", "022400429809.jpg", "022400430423.jpg", "022400441290.jpg", "022400441474.jpg", "022400441481.jpg", "022400441559.jpg", "022400442037.jpg", "022400621821.jpg", "022400621906.jpg", "022400623726.jpg", "022400623740.jpg", "022400623887.jpg", "022400623924.jpg", "022400623931.jpg", "022400623979.jpg", "022400624181.jpg", "022400624204.jpg", "022400624235.jpg", "022400624419.jpg", "022400624464.jpg", "022400624471.jpg", "022400624488.jpg", "022400624495.jpg", "022400624792.jpg", "022400624808.jpg", "022400641003.jpg", "022400641126.jpg", "022400641508.jpg", "022400641966.jpg", "022400641973.jpg", "022400642178.jpg", "022400642611.jpg", "022400643168.jpg", "022400643366.jpg", "022400643618.jpg", "022400651606.jpg", "022400651613.jpg", "022400652092.jpg", "022400652108.jpg", "022400652160.jpg", "022400652191.jpg", "022400652207.jpg", "022400652214.jpg", "022400652290.jpg", "030000065075.jpg", "030000065310.jpg", "030000065679.jpg", "030000315286.jpg", "030000400036.jpg", "030000400050.jpg", "037000855200.png", "037000855767.png", "037000856412.png", "037000872771.jpg", "037000872795.jpg", "037000872801.jpg", "071249207253.jpg", "071249207260.jpg", "071249207277.jpg", "071249207284.jpg", "071249207291.jpg", "071249207307.jpg", "071249239988.jpg", "071249239995.jpg", "071249240007.jpg", "071249240014.jpg", "071249240021.jpg", "071249248607.jpg", "071249248614.jpg", "071249265888.jpg", "071249265895.jpg", "071249278574.jpg", "071249278581.jpg", "071249282328.jpg", "071249292372.jpg", "078742094458.jpg", "078742122847.jpg", "078742123745.jpg", "078742229003.jpg", "078742229010.jpg", "078742315577.jpg", "078742371573.jpg", "078742371597.jpg", "078742371610.jpg", "078742371627.jpg", "078742432397.jpg", "078742432403.jpg", "078742432410.jpg", "079400025227.jpg", "079400025234.jpg", "079400128041.jpg", "079400188977.jpg", "079400188991.jpg", "079400193759.jpg", "079400193919.jpg", "079400211378.jpg", "079400211385.jpg", "079400255990.jpg", "079400256003.jpg", "079400257512.jpg", "079400333285.jpg", "079400333308.jpg", "079400333360.jpg", "079400333384.jpg", "079400333391.jpg", "079400333513.jpg", "079400333957.jpg", "079400334381.jpg", "079400338327.jpg", "079400338334.jpg", "079400338341.jpg", "079400391483.jpg", "079400391506.jpg", "079400398413.jpg", "079400425560.jpg", "079400425577.jpg", "079400426420.jpg", "079400426437.jpg", "079400701107.jpg", "079400716101.jpg", "079400717306.jpg", "079400736000.jpg", "079400750402.jpg", "079400767509.jpg", "079400767608.jpg", "079400832801.jpg", "079400832900.jpg", "079400858603.jpg", "079400892003.jpg", "080878000616.jpg", "080878002184.jpg", "080878004065.jpg", "080878008308.jpg", "080878008995.jpg", "080878040315.jpg", "080878040322.jpg", "080878042166.jpg", "080878042197.jpg", "080878042203.jpg", "080878042234.jpg", "080878042265.jpg", "080878042272.jpg", "080878042289.jpg", "080878042302.jpg", "080878042333.jpg", "080878042357.jpg", "080878042364.jpg", "080878042388.jpg", "080878042500.jpg", "080878042524.jpg", "080878042531.jpg", "080878042555.jpg", "080878042562.jpg", "080878042586.jpg", "080878042593.jpg", "080878042630.jpg", "080878042678.jpg", "080878042692.jpg", "080878048281.jpg", "080878054428.jpg", "080878057061.jpg", "080878057078.jpg", "080878171019.jpg", "080878171026.jpg", "080878171255.jpg", "080878171262.jpg", "080878171309.jpg", "080878171316.jpg", "080878171323.jpg", "080878172092.jpg", "080878176960.jpg", "080878176977.jpg", "080878177172.jpg", "080878177257.jpg", "080878177264.jpg", "080878177646.jpg", "080878179558.jpg", "080878179572.jpg", "381519001383.jpg", "381519001437.jpg", "381519019104.jpg", "381519019111.jpg", "381519019258.jpg", "381519019289.jpg", "381519019319.jpg", "381519019340.jpg", "381519019364.jpg", "381519019371.jpg", "381519019388.jpg", "381519019579.jpg", "381519019654.jpg", "381519183584.jpg", "603084215102.jpg", "603084215119.jpg", "603084215126.jpg", "603084215133.jpg", "603084223534.jpg", "603084230624.jpg", "603084230631.jpg", "603084249640.jpg", "603084249657.jpg", "603084249664.jpg", "603084249671.jpg", "0681131052481.png"
    };

    Connection conn = null;
    ProductMaster productMaster = null;
    FileInputStream fis = null;

    for (String name :imageNameList) {
      try {
        File file = new File("/home/naveen/ThumbnailsPraveen/"+name);
        fis = new FileInputStream(file);
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name.replace(".png","").replace(".jpg",""));
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
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext(
      "base-spring-ctx.xml");
    ProductMasterDaoImpl productMasterDaoImpl = (ProductMasterDaoImpl) context.getBean("ProductMasterDaoImpl");
    LOGGER.info("Checking logger");

    productMasterDaoImpl.getThumbnails("381519019340");

  }
}
