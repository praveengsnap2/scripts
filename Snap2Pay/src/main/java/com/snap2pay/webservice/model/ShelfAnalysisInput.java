package com.snap2pay.webservice.model;

import java.util.List;

/**
 * Created by sachin on 10/31/15.
 */
public class ShelfAnalysisInput {

    String imageUUID;
    String storeId;
    String categoryId;
    List<Skus> skus;

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
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

    public List<Skus> getSkus() {
        return skus;
    }

    public void setSkus(List<Skus> skus) {
        this.skus = skus;
    }
}
