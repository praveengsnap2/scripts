package com.snap2pay.webservice.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by sachin on 10/31/15.
 */

public class ShelfAnalysisInput {


    String imageUUID;
    String storeID;
    String categoryID;
    String date;
    String status;
    List<Skus> skus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name = "ImageUUID")
    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    @XmlElement(name = "StoreID")
    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    @XmlElement(name = "CategoryID")
    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public List<Skus> getSkus() {
        return skus;
    }

    public void setSkus(List<Skus> skus) {
        this.skus = skus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShelfAnalysisInput{" +
                "imageUUID='" + imageUUID + '\'' +
                ", storeID='" + storeID + '\'' +
                ", categoryID='" + categoryID + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", skus=" + skus +
                '}';
    }
}
