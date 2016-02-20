package com.snap2pay.webservice.model;

/**
 * Created by sachin on 10/17/15.
 */
public class ImageStore {

    String imageUUID;
    String ImageFilePath;
    String userId;
    String categoryId;
    String latitude;
    String longitude;
    String timeStamp;
    String storeId;
    String status;
    String hostId;
    String dateId;


    public ImageStore() {
        super();
    }

    public ImageStore(String imageUUID, String imageFilePath, String userId, String categoryId, String latitude, String longitude, String timeStamp, String storeId, String status, String dateId) {
        this.imageUUID = imageUUID;
        ImageFilePath = imageFilePath;
        this.userId = userId;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.storeId = storeId;
        this.status = status;
        this.dateId = dateId;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getImageFilePath() {
        return ImageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        ImageFilePath = imageFilePath;
    }

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ImageStore{" +
                "imageUUID='" + imageUUID + '\'' +
                ", ImageFilePath='" + ImageFilePath + '\'' +
                ", userId='" + userId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", storeId='" + storeId + '\'' +
                ", status='" + status + '\'' +
                ", hostId='" + hostId + '\'' +
                ", dateId='" + dateId + '\'' +
                '}';
    }
}

