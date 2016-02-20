/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.snap2pay.webservice.model;
//

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author keerthanathangaraju
 */
@Document
public class InputObject {

    @Id
    private String visitId;
    private String upc;
    private String hostId;
    private String visitDate;
    private String shopId;
    private String categoryId;
    private String userId;
    private String timeStamp;
    private String imageFilePath;
    private String imageUUID;
    private String latitude;
    private String longitude;
    private String sync;
    private Integer responseCode;
    private String responseMessage;

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

//    public GridFS getImage() {
//        return image;
//    }
//
//    public void setImage(GridFS image) {
//        this.image = image;
//    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "InputObject{" +
                "visitId='" + visitId + '\'' +
                ", hostId='" + hostId + '\'' +
                ", visitDate=" + visitDate +
                ", shopId='" + shopId + '\'' +
                ", categoryID='" + categoryId + '\'' +
                ", userId='" + userId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", imageUUID='" + imageUUID + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
