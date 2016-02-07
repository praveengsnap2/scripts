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
          String dirPath = "/tmp/snap2buy/image/";
          File dir =new File(dirPath);
          if (!dir.getParentFile().exists()){
            dir.getParentFile().mkdirs();
              dir.getParentFile().setReadable(true);
              dir.getParentFile().setWritable(true);
              dir.getParentFile().setExecutable(true);
          }else if(!dir.exists()){
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
            "016000147720.JPG", "022400393650.jpg", "030000263938.JPG", "038000391255.JPG", "071249239988.jpg", "079400256003.jpg", "080878171026.jpg", "381519031625.JPG", "381519181726.jpg", "603084297634.jpg",
            "016000275034.JPG", "022400393667.jpg", "030000264423.JPG", "038000391347.JPG", "071249239995.jpg", "079400257512.jpg", "080878171057.JPG", "381519031632.JPG", "381519181733.JPG", "603084297726.JPG",
            "016000275058.jpg", "022400393681.jpg", "030000268056.JPG", "038000391385.JPG", "071249240007.jpg", "079400266163.JPG", "080878171255.jpg", "381519031649.JPG", "381519181740.JPG", "603084297740.JPG",
            "016000275119.jpg", "022400393728.jpg", "030000268728.JPG", "038000395000.JPG", "071249240014.jpg", "079400271228.JPG", "080878171262.jpg", "381519031656.JPG", "381519181771.jpg", "603084297849.JPG",
            "016000275133.JPG", "022400393742.jpg", "030000268735.JPG", "038000556470.JPG", "071249240021.jpg", "079400271235.JPG", "080878171309.jpg", "381519031663.jpg", "381519181849.jpg", "603084298464.JPG",
            "016000275164.jpg", "022400393759.jpg", "030000269053.JPG", "038000556500.JPG", "071249240038.JPG", "079400271730.JPG", "080878171316.jpg", "381519031670.jpg", "381519181870.jpg", "603084335763.JPG",
            "016000275171.JPG", "022400393766.jpg", "030000269206.JPG", "038000556593.JPG", "071249240045.JPG", "079400271747.JPG", "080878171323.jpg", "381519032646.JPG", "381519181887.JPG", "603084335787.JPG",
            "016000275188.JPG", "022400393773.jpg", "030000269664.JPG", "038000556623.JPG", "071249240632.JPG", "079400274182.JPG", "080878172092.jpg", "381519032653.JPG", "381519181894.JPG", "603084350643.jpg",
            "016000275225.JPG", "022400393797.jpg", "030000311561.JPG", "038000556708.JPG", "071249240649.JPG", "079400274205.JPG", "080878173211.JPG", "381519035104.JPG", "381519181900.jpg", "603084350742.jpg",
            "016000275270.JPG", "022400415871.jpg", "030000312087.JPG", "038000556746.JPG", "071249246788.JPG", "079400275004.JPG", "080878175796.JPG", "381519053405.JPG", "381519181917.JPG", "603084350827.JPG",
            "016000275294.jpg", "022400416168.jpg", "030000312773.JPG", "038000556777.JPG", "071249246795.JPG", "079400275516.JPG", "080878175888.JPG", "381519055010.jpg", "381519181924.JPG", "603084351015.JPG",
            "016000275324.jpg", "022400429793.jpg", "030000312902.JPG", "038000556906.JPG", "071249246801.JPG", "079400333285.jpg", "080878176113.JPG", "381519055027.jpg", "381519181931.JPG", "603084411801.jpg",
            "016000275348.JPG", "022400429809.jpg", "030000313282.JPG", "038000561931.JPG", "071249246818.JPG", "079400333292.JPG", "080878176120.JPG", "381519055034.jpg", "381519181948.JPG", "603084411818.jpg",
            "016000275362.jpg", "022400623726.jpg", "030000315286.jpg", "038000570742.JPG", "071249246825.JPG", "079400333308.jpg", "080878176861.JPG", "381519055041.jpg", "381519182105.JPG", "603084411986.JPG",
            "016000275393.jpg", "022400623733.JPG", "030000315958.JPG", "038000570773.JPG", "071249246856.JPG", "079400333360.jpg", "080878176960.jpg", "381519055058.jpg", "381519182136.JPG", "603084411993.JPG",
            "016000275492.jpg", "022400624181.jpg", "030000315989.JPG", "038000576003.JPG", "071249246863.JPG", "079400333377.JPG", "080878176977.jpg", "381519055065.jpg", "381519182150.JPG", "603084412747.JPG",
            "016000275546.JPG", "022400624198.JPG", "030000316788.JPG", "038000576027.JPG", "071249248607.jpg", "079400333384.jpg", "080878177172.jpg", "381519055072.jpg", "381519182181.JPG", "603084412754.JPG",
            "016000275652.jpg", "022400624464.jpg", "030000316801.JPG", "038000576089.JPG", "071249248614.jpg", "079400333650.JPG", "080878177189.JPG", "381519055089.jpg", "381519182198.JPG", "603084420674.JPG",
            "016000275676.JPG", "022400624471.jpg", "030000319031.JPG", "038000576225.JPG", "071249248638.JPG", "079400333667.JPG", "080878177257.jpg", "381519056857.JPG", "381519182204.JPG", "603084433919.JPG",
            "016000275720.jpg", "022400624488.jpg", "030000319048.JPG", "038000576249.JPG", "071249248645.JPG", "079400333674.JPG", "080878177264.jpg", "381519056864.JPG", "381519183560.jpg", "603084433926.JPG",
            "016000275799.JPG", "022400624495.jpg", "030000319932.JPG", "038000576287.JPG", "071249248669.JPG", "079400334114.JPG", "080878177295.JPG", "381519056871.JPG", "381519183584.jpg", "603084433933.JPG",
            "016000409453.JPG", "022400639017.JPG", "030000321089.JPG", "038000576300.JPG", "071249248676.JPG", "079400334121.JPG", "080878177301.JPG", "381519056888.JPG", "381519183652.JPG", "603084433940.JPG",
            "016000410770.JPG", "022400639109.JPG", "030000321096.JPG", "038000576348.JPG", "071249256053.JPG", "079400334138.JPG", "080878177318.JPG", "381519056895.JPG", "381519183669.JPG", "603084433957.JPG",
            "016000412484.JPG", "022400639116.JPG", "030000321881.JPG", "038000576362.JPG", "071249256060.JPG", "079400334381.jpg", "080878177325.JPG", "381519056901.JPG", "381519183676.JPG", "603084433964.JPG",
            "016000413696.JPG", "022400639123.JPG", "030000321898.JPG", "038000589263.JPG", "071249265888.jpg", "079400346933.JPG", "080878177455.JPG", "381519056918.JPG", "381519183683.JPG", "603084434305.JPG",
            "016000429734.JPG", "022400639130.JPG", "030000687178.JPG", "038000596445.JPG", "071249265895.jpg", "079400346940.JPG", "080878177479.JPG", "381519058486.JPG", "381519183706.JPG", "603084434398.JPG",
            "016000434714.JPG", "022400641126.jpg", "037000872771.jpg", "038000596612.JPG", "071249265987.JPG", "079400391483.jpg", "080878177615.JPG", "381519058493.jpg", "381519183775.JPG", "603084434404.JPG",
            "016000434721.JPG", "022400641966.jpg", "038000001109.JPG", "038000596650.jpg", "071249265994.JPG", "079400391490.JPG", "080878177646.jpg", "381519058509.jpg", "603084215102.jpg", "884912002372.JPG",
            "016000443389.JPG", "022400641973.jpg", "038000013027.JPG", "038000596674.JPG", "071249278574.jpg", "079400391506.jpg", "080878177677.JPG", "381519058516.jpg", "603084215119.jpg", "884912002457.JPG",
            "016000444737.JPG", "022400642178.jpg", "038000014116.JPG", "038000596698.JPG", "071249278581.jpg", "079400391711.JPG", "080878179558.jpg", "381519058578.jpg", "603084215126.jpg", "884912002464.JPG",
            "016000446182.JPG", "022400642185.JPG", "038000019128.JPG", "038000596797.JPG", "071249278598.JPG", "079400425560.jpg", "080878179572.jpg", "381519058585.JPG", "603084215133.jpg", "884912002754.JPG",
            "016000447042.JPG", "022400643618.jpg", "038000035302.JPG", "038000596827.JPG", "071249278604.JPG", "079400425577.jpg", "381519001383.jpg", "381519180736.jpg", "603084223534.jpg", "884912003584.JPG",
            "016000450677.JPG", "022400651606.jpg", "038000045301.JPG", "038000597039.JPG", "071249278680.JPG", "079400426284.JPG", "381519001437.jpg", "381519180743.jpg", "603084223541.JPG", "884912003843.JPG",
            "016000451322.JPG", "022400651613.jpg", "038000070105.JPG", "038000599217.JPG", "071249278697.JPG", "079400426314.JPG", "381519019159.JPG", "381519180750.jpg", "603084230624.jpg", "884912003911.JPG",
            "016000451377.JPG", "022400651927.JPG", "038000080203.JPG", "038000599231.JPG", "071249278703.JPG", "079400892003.jpg", "381519019166.JPG", "381519180767.jpg", "603084230631.jpg", "884912003928.JPG",
            "016000476080.JPG", "022400652092.jpg", "038000102240.JPG", "038000663307.JPG", "071249278710.JPG", "080878000616.jpg", "381519019173.JPG", "381519181023.JPG", "603084230648.JPG", "884912004307.JPG",
            "016000481541.JPG", "022400652108.jpg", "038000102653.JPG", "038000695018.JPG", "071249282311.JPG", "080878002184.jpg", "381519019210.JPG", "381519181061.JPG", "603084230655.JPG", "884912004567.JPG",
            "016000482685.JPG", "030000012406.JPG", "038000110894.JPG", "038000704321.JPG", "071249282328.jpg", "080878004065.jpg", "381519019227.JPG", "381519181078.JPG", "603084231386.JPG", "884912004574.JPG",
            "016000483637.JPG", "030000013205.JPG", "038000110924.JPG", "038000708350.JPG", "071249283646.JPG", "080878040315.jpg", "381519019241.JPG", "381519181153.JPG", "603084241705.JPG", "884912004710.JPG",
            "016000483651.JPG", "030000014998.JPG", "038000111488.JPG", "038000761966.JPG", "071249283660.JPG", "080878040322.jpg", "381519019258.jpg", "381519181177.JPG", "603084249640.jpg", "884912005618.JPG",
            "016000483668.JPG", "030000015520.JPG", "038000113284.JPG", "038000762727.JPG", "071249283677.JPG", "080878042166.jpg", "381519019272.JPG", "381519181191.JPG", "603084249657.jpg", "884912005625.JPG",
            "016000484986.JPG", "030000015544.JPG", "038000113536.JPG", "038000786099.JPG", "071249283684.JPG", "080878042197.jpg", "381519019289.jpg", "381519181207.JPG", "603084249664.jpg", "884912005632.JPG",
            "016000484993.JPG", "030000018002.JPG", "038000113567.JPG", "038000786129.JPG", "071249292365.JPG", "080878042203.jpg", "381519019302.JPG", "381519181214.JPG", "603084249671.jpg", "884912006202.JPG",
            "016000486423.JPG", "030000018200.JPG", "038000118074.JPG", "038000786471.JPG", "071249292372.jpg", "080878042234.jpg", "381519019319.jpg", "381519181221.JPG", "603084263844.jpg", "884912006233.JPG",
            "016000486430.JPG", "030000037904.JPG", "038000119118.JPG", "038000786693.JPG", "071249292389.JPG", "080878042265.jpg", "381519019333.JPG", "381519181252.jpg", "603084267408.jpg", "884912012784.JPG",
            "016000486447.JPG", "030000040300.JPG", "038000119149.JPG", "038000786983.JPG", "071249292396.JPG", "080878042272.jpg", "381519019340.jpg", "381519181269.jpg", "603084267415.jpg", "884912014245.JPG",
            "016000487697.JPG", "030000040508.JPG", "038000120336.JPG", "038000787041.JPG", "071249304396.JPG", "080878042289.jpg", "381519019357.JPG", "381519181306.JPG", "603084267422.jpg", "884912017864.JPG",
            "016000487727.JPG", "030000044117.JPG", "038000121128.JPG", "038000787119.JPG", "071249304402.JPG", "080878042302.jpg", "381519019364.jpg", "381519181313.JPG", "603084267439.jpg", "884912029829.JPG",
            "016000487895.JPG", "030000044209.JPG", "038000122736.JPG", "038000787164.JPG", "071249304419.JPG", "080878042333.jpg", "381519019371.jpg", "381519181320.JPG", "603084267446.jpg", "884912032171.JPG",
            "016000487918.JPG", "030000045602.JPG", "038000122767.JPG", "038000787195.JPG", "071249304426.JPG", "080878042357.jpg", "381519019388.jpg", "381519181337.jpg", "603084267545.jpg", "884912102102.JPG",
            "016000487925.JPG", "030000047606.JPG", "038000122804.JPG", "038000787225.JPG", "071249304556.JPG", "080878042364.jpg", "381519019456.JPG", "381519181344.JPG", "603084267552.jpg", "884912103703.JPG",
            "016000487932.JPG", "030000057667.JPG", "038000128417.JPG", "038000787348.JPG", "071249304617.JPG", "080878042388.jpg", "381519019463.JPG", "381519181351.JPG", "603084267569.JPG", "884912109101.JPG",
            "016000487949.JPG", "030000061534.JPG", "038000128479.JPG", "038000801976.JPG", "071249304624.JPG", "080878042500.jpg", "381519019470.JPG", "381519181498.JPG", "603084267576.JPG", "884912111814.JPG",
            "016000487956.JPG", "030000061909.JPG", "038000130915.JPG", "038000802584.JPG", "079400066619.JPG", "080878042524.jpg", "381519019487.JPG", "381519181504.jpg", "603084267583.jpg", "884912112217.JPG",
            "016000487963.JPG", "030000063118.JPG", "038000131301.JPG", "038000862304.JPG", "079400066626.JPG", "080878042531.jpg", "381519019579.jpg", "381519181511.JPG", "603084267675.JPG", "884912113139.JPG",
            "016000487970.JPG", "030000063224.JPG", "038000132780.JPG", "038000870101.JPG", "079400066633.JPG", "080878042555.jpg", "381519019654.jpg", "381519181528.JPG", "603084267682.JPG", "884912114600.JPG",
            "016000487987.jpg", "030000063545.JPG", "038000133886.JPG", "038000908408.JPG", "079400066640.JPG", "080878042562.jpg", "381519019722.jpg", "381519181542.JPG", "603084271658.JPG", "884912116505.JPG",
            "022400264318.JPG", "030000064108.JPG", "038000135217.JPG", "038000919718.JPG", "079400083906.JPG", "080878042586.jpg", "381519019906.jpg", "381519181559.jpg", "603084271665.JPG", "884912117625.JPG",
            "022400264455.JPG", "030000064412.JPG", "038000139505.JPG", "038000921926.JPG", "079400083913.JPG", "080878042593.jpg", "381519019913.jpg", "381519181573.JPG", "603084271672.JPG", "884912126016.JPG",
            "022400264486.JPG", "030000064429.JPG", "038000139529.JPG", "038000924224.JPG", "079400083920.JPG", "080878042616.JPG", "381519022159.JPG", "381519181580.JPG", "603084271689.JPG", "884912126115.JPG",
            "022400264493.JPG", "030000065075.jpg", "038000143632.JPG", "038000926686.JPG", "079400083937.JPG", "080878042630.jpg", "381519022166.JPG", "381519181603.JPG", "603084285235.JPG", "884912129512.JPG",
            "022400336725.JPG", "030000065310.jpg", "038000318290.JPG", "038000928147.JPG", "079400193735.JPG", "080878042678.jpg", "381519022173.JPG", "381519181610.jpg", "603084285259.JPG", "884912129710.JPG",
            "022400336732.JPG", "030000065525.JPG", "038000318344.JPG", "071249207253.jpg", "079400193742.JPG", "080878042692.jpg", "381519022180.JPG", "381519181627.JPG", "603084290390.JPG", "884912180056.JPG",
            "022400336749.JPG", "030000065679.jpg", "038000318467.JPG", "071249207260.jpg", "079400193759.jpg", "080878048281.jpg", "381519022197.JPG", "381519181634.JPG", "603084294916.jpg", "884912180599.JPG",
            "022400337395.jpg", "030000067000.JPG", "038000359828.JPG", "071249207277.jpg", "079400193766.JPG", "080878054428.jpg", "381519022203.JPG", "381519181665.JPG", "603084294923.jpg", "884912181701.JPG",
            "022400338064.jpg", "030000067208.JPG", "038000391033.JPG", "071249207284.jpg", "079400193919.jpg", "080878057061.jpg", "381519030734.JPG", "381519181672.JPG", "603084294930.jpg",
            "022400393629.jpg", "030000069509.JPG", "038000391095.JPG", "071249207291.jpg", "079400193933.JPG", "080878057078.jpg", "381519030741.JPG", "381519181689.JPG", "603084294947.JPG",
            "022400393636.jpg", "030000070604.JPG", "038000391132.JPG", "071249207307.jpg", "079400229687.JPG", "080878057276.JPG", "381519030765.jpg", "381519181696.JPG", "603084294954.JPG",
            "022400393643.jpg", "030000075630.JPG", "038000391224.JPG", "071249239971.JPG", "079400255990.jpg", "080878171019.jpg", "381519030772.jpg", "381519181719.JPG", "603084297610.jpg"
    };

    Connection conn = null;
    ProductMaster productMaster = null;
    FileInputStream fis = null;

    for (String name :imageNameList) {
      try {
        File file = new File("/home/naveen/Image_Thumbnails"+name);
        fis = new FileInputStream(file);
        conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name.replace(".png","").replace(".jpg", "").replace(".JPG","").replace(".PNG","").replace(".jpeg","").replace(".JPEG",""));
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
