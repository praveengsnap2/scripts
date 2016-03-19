package com.snap2buy.webservice.model;

/**
 * Created by sachin on 3/15/16.
 */
public class ImageAnalysis {
    String imageUUID;
    String storeId;
    String dateId;
    String upc;
    String upcConfidence;
    String alternateUpc;
    String alternateUpcConfidence;
    String leftTopX;
    String leftTopY;
    String width;
    String height;


    public ImageAnalysis() {
        super();
    }

    public ImageAnalysis(String imageUUID, String storeId, String dateId, String upc, String upcConfidence, String alternateUpc, String alternateUpcConfidence, String leftTopX, String leftTopY, String width, String height) {
        this.imageUUID = imageUUID;
        this.storeId = storeId;
        this.dateId = dateId;
        this.upc = upc;
        this.upcConfidence = upcConfidence;
        this.alternateUpc = alternateUpc;
        this.alternateUpcConfidence = alternateUpcConfidence;
        this.leftTopX = leftTopX;
        this.leftTopY = leftTopY;
        this.width = width;
        this.height = height;
    }

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

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpcConfidence() {
        return upcConfidence;
    }

    public void setUpcConfidence(String upcConfidence) {
        this.upcConfidence = upcConfidence;
    }

    public String getAlternateUpc() {
        return alternateUpc;
    }

    public void setAlternateUpc(String alternateUpc) {
        this.alternateUpc = alternateUpc;
    }

    public String getAlternateUpcConfidence() {
        return alternateUpcConfidence;
    }

    public void setAlternateUpcConfidence(String alternateUpcConfidence) {
        this.alternateUpcConfidence = alternateUpcConfidence;
    }

    public String getLeftTopX() {
        return leftTopX;
    }

    public void setLeftTopX(String leftTopX) {
        this.leftTopX = leftTopX;
    }

    public String getLeftTopY() {
        return leftTopY;
    }

    public void setLeftTopY(String leftTopY) {
        this.leftTopY = leftTopY;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ImageAnalysis{" +
                "imageUUID='" + imageUUID + '\'' +
                ", storeId='" + storeId + '\'' +
                ", dateId='" + dateId + '\'' +
                ", upc='" + upc + '\'' +
                ", upcConfidence='" + upcConfidence + '\'' +
                ", alternateUpc='" + alternateUpc + '\'' +
                ", alternateUpcConfidence='" + alternateUpcConfidence + '\'' +
                ", leftTopX='" + leftTopX + '\'' +
                ", leftTopY='" + leftTopY + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
