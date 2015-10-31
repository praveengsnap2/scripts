package com.snap2pay.webservice.model;

/**
 * Created by sachin on 10/31/15.
 */
public class ShelfAnalysis {

    String imageUUID;
    String upc;
    String pog;
    String osa;
    String facings;
    String priceLabel;
    String storeId;
    String categoryId;

    public ShelfAnalysis() {
        super();
    }

    public ShelfAnalysis(String imageUUID, String upc, String pog, String osa, String facings, String priceLabel, String storeId, String categoryId) {
        this.imageUUID = imageUUID;
        this.upc = upc;
        this.pog = pog;
        this.osa = osa;
        this.facings = facings;
        this.priceLabel = priceLabel;
        this.storeId = storeId;
        this.categoryId = categoryId;
    }

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getPog() {
        return pog;
    }

    public void setPog(String pog) {
        this.pog = pog;
    }

    public String getOsa() {
        return osa;
    }

    public void setOsa(String osa) {
        this.osa = osa;
    }

    public String getFacings() {
        return facings;
    }

    public void setFacings(String facings) {
        this.facings = facings;
    }

    public String getPriceLabel() {
        return priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
