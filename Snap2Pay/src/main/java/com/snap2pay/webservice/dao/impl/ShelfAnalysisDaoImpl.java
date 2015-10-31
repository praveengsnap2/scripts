package com.snap2pay.webservice.dao.impl;

import com.snap2pay.webservice.dao.ShelfAnalysisDao;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.ShelfAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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

    private static Logger LOGGER = Logger.getLogger("s2p");

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void storeShelfAnalysis(ShelfAnalysis shelfAnalysis) {
        String sql = "INSERT INTO ShelfAnalysis (imageUUID,upc,pog,osa,facings,priceLabel,storeId,categoryId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, shelfAnalysis.getImageUUID());
            ps.setString(2, shelfAnalysis.getUpc());
            ps.setString(3, shelfAnalysis.getPog());
            ps.setString(4, shelfAnalysis.getOsa());
            ps.setString(5, shelfAnalysis.getFacings());
            ps.setString(6, shelfAnalysis.getPriceLabel());
            ps.setString(7, shelfAnalysis.getStoreId());
            ps.setString(8, shelfAnalysis.getCategoryId());
            ps.executeUpdate();
            ps.close();

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
        String sql = "SELECT * FROM ImageStore WHERE imageUUID = ?";

        Connection conn = null;
        ShelfAnalysis shelfAnalysis = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, imageUUID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                shelfAnalysis = new ShelfAnalysis(rs.getString("imageUUID"),rs.getString("upc"),rs.getString("pog"),rs.getString("osa"),rs.getString("facings"),rs.getString("priceLabel"),rs.getString("storeId"),rs.getString("categoryId"));
            }
            rs.close();
            ps.close();
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
