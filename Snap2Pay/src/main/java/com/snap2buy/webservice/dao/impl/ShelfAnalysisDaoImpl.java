package com.snap2buy.webservice.dao.impl;

import com.snap2buy.webservice.dao.ShelfAnalysisDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.model.ShelfAnalysis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.script.*;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sachin on 10/31/15.
 */
@Component(value = BeanMapper.BEAN_SHELF_ANALYSIS_DAO)
@Scope("prototype")
public class ShelfAnalysisDaoImpl implements ShelfAnalysisDao {

    private static Logger LOGGER = Logger.getLogger("s2b");

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws ScriptException, IOException {

        StringWriter writer = new StringWriter(); //ouput will be stored here

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();

        context.setWriter(writer); //configures output redirection
        ScriptEngine engine = manager.getEngineByName("python");
        engine.eval(new FileReader("/Users/sachin/test/test.py"), context);
        System.out.println(writer.toString());
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void storeShelfAnalysis(ShelfAnalysis shelfAnalysis) {
        LOGGER.info("---------------ShelfAnalysisDaoImpl Starts storeShelfAnalysis----------------\n");
        String sql = "INSERT INTO ShelfAnalysis (imageUUID, product_code, expected_facings, on_shelf_availability, detected_facings, promotion_label_present, price, promo_price, storeId, categoryId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfAnalysis.getImageUUID());
            ps.setString(2, shelfAnalysis.getUpc());
            ps.setString(3, shelfAnalysis.getExpected_facings());
            ps.setString(4, shelfAnalysis.getOn_shelf_availability());
            ps.setString(5, shelfAnalysis.getDetected_facings());
            ps.setString(6, shelfAnalysis.getPromotion_label_present());
            ps.setString(7, shelfAnalysis.getPrice());
            ps.setString(8, shelfAnalysis.getPromo_price());
            ps.setString(9, shelfAnalysis.getStoreId());
            ps.setString(10, shelfAnalysis.getCategoryId());
            ps.executeUpdate();
            ps.close();
            LOGGER.info("---------------ShelfAnalysisDaoImpl Ends storeShelfAnalysis----------------\n");

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
    public ShelfAnalysis getShelfAnalysis(String imageUUID) {
        LOGGER.info("---------------ShelfAnalysisDaoImpl Starts getShelfAnalysis----------------\n");
        String sql = "SELECT * FROM ImageStore WHERE imageUUID = ?";

        Connection conn = null;
        ShelfAnalysis shelfAnalysis = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shelfAnalysis = new ShelfAnalysis(rs.getString("imageUUID"), rs.getString("product_code"), rs.getString("expected_facings"), rs.getString("on_shelf_availability"), rs.getString("detected_facings"), rs.getString("promotion_label_present"), rs.getString("price"), rs.getString("promo_price"), rs.getString("storeId"), rs.getString("categoryId"));
            }
            rs.close();
            ps.close();
            LOGGER.info("---------------ShelfAnalysisDaoImpl Ends getShelfAnalysis----------------\n");

            return shelfAnalysis;
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
