package com.snap2pay.webservice.model;

/**
 * Created by sachin on 10/17/15.
 */
public class ImageStore {

    String imageUUID;
    String userId;
    String ImageFilePath;
    String categoryId;
    String latitude;
    String longitude;
    String timeStamp;
    String storeId;
    String status;
    String hostId;


    public ImageStore() {
        super();
    }

    public ImageStore(String storeId, String imageUUID, String userId, String imageFilePath, String categoryId, String latitude, String longitude, String timeStamp,String status) {
        this.storeId = storeId;
        this.imageUUID = imageUUID;
        this.userId = userId;
        this.ImageFilePath = imageFilePath;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.status=status;
    }

    public ImageStore(String imageUUID, String userId, String imageFilePath, String categoryId, String latitude, String longitude, String timeStamp, String status) {
        this.imageUUID = imageUUID;
        this.userId = userId;
        this.ImageFilePath = imageFilePath;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.status=status;
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
                ", userId='" + userId + '\'' +
                ", ImageFilePath='" + ImageFilePath + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}

