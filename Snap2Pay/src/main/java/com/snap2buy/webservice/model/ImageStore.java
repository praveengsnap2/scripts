package com.snap2buy.webservice.model;

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
    String hostId;
    String dateId;
    String imageStatus;
    String shelfStatus;

     String origWidth;
     String origHeight;
     String newWidth;
     String newHeight;
     String thumbnailPath;

    public ImageStore(String imageUUID, String imageFilePath, String userId, String categoryId, String latitude, String longitude, String timeStamp, String storeId, String dateId, String imageStatus, String shelfStatus, String origWidth, String origHeight, String newWidth, String newHeight, String thumbnailPath) {
        this.imageUUID = imageUUID;
        ImageFilePath = imageFilePath;
        this.userId = userId;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.storeId = storeId;
        this.dateId = dateId;
        this.imageStatus = imageStatus;
        this.shelfStatus = shelfStatus;
        this.origWidth = origWidth;
        this.origHeight = origHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.thumbnailPath = thumbnailPath;
    }

    public String getOrigWidth() {
        return origWidth;
    }

    public void setOrigWidth(String origWidth) {
        this.origWidth = origWidth;
    }

    public String getOrigHeight() {
        return origHeight;
    }

    public void setOrigHeight(String origHeight) {
        this.origHeight = origHeight;
    }

    public String getNewWidth() {
        return newWidth;
    }

    public void setNewWidth(String newWidth) {
        this.newWidth = newWidth;
    }

    public String getNewHeight() {
        return newHeight;
    }

    public void setNewHeight(String newHeight) {
        this.newHeight = newHeight;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }

    public String getShelfStatus() {
        return shelfStatus;
    }

    public void setShelfStatus(String shelfStatus) {
        this.shelfStatus = shelfStatus;
    }

    public ImageStore() {
        super();
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
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
                ", hostId='" + hostId + '\'' +
                ", dateId='" + dateId + '\'' +
                ", imageStatus='" + imageStatus + '\'' +
                ", shelfStatus='" + shelfStatus + '\'' +
                ", origWidth='" + origWidth + '\'' +
                ", origHeight='" + origHeight + '\'' +
                ", newWidth='" + newWidth + '\'' +
                ", newHeight='" + newHeight + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                '}';
    }
}

