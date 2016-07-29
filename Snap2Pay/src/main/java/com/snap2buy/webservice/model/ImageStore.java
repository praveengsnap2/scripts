package com.snap2buy.webservice.model;

/**
 * Created by sachin on 5/29/16.
 */
public class ImageStore {
    String imageUUID;
    String imageFilePath;
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
    String userId;

    String customerCode;
    String customerProjectId;
    String taskId;
    String agentId;

    String lastUpdatedTimestamp;
    String imageHashScore;
    String imageRotation;

    public ImageStore() {
        super();
    }

//    public ImageStore(String imageUUID, String imageFilePath, String categoryId, String latitude, String longitude, String timeStamp, String storeId, String hostId, String dateId, String imageStatus, String shelfStatus, String origWidth, String origHeight, String newWidth, String newHeight, String thumbnailPath, String userId, String customerCode, String customerProjectId, String taskId, String agentId) {
//        this.imageUUID = imageUUID;
//        this.imageFilePath = imageFilePath;
//        this.categoryId = categoryId;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.timeStamp = timeStamp;
//        this.storeId = storeId;
//        this.hostId = hostId;
//        this.dateId = dateId;
//        this.imageStatus = imageStatus;
//        this.shelfStatus = shelfStatus;
//        this.origWidth = origWidth;
//        this.origHeight = origHeight;
//        this.newWidth = newWidth;
//        this.newHeight = newHeight;
//        this.thumbnailPath = thumbnailPath;
//        this.userId = userId;
//        this.customerCode = customerCode;
//        this.customerProjectId = customerProjectId;
//        this.taskId = taskId;
//        this.agentId = agentId;
//    }

    public ImageStore(String imageUUID, String imageFilePath, String categoryId, String latitude, String longitude, String timeStamp, String storeId, String hostId, String dateId, String imageStatus, String shelfStatus, String origWidth, String origHeight, String newWidth, String newHeight, String thumbnailPath, String userId, String customerCode, String customerProjectId, String taskId, String agentId, String lastUpdatedTimestamp, String imageHashScore, String imageRotation) {
        this.imageUUID = imageUUID;
        this.imageFilePath = imageFilePath;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
        this.storeId = storeId;
        this.hostId = hostId;
        this.dateId = dateId;
        this.imageStatus = imageStatus;
        this.shelfStatus = shelfStatus;
        this.origWidth = origWidth;
        this.origHeight = origHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.thumbnailPath = thumbnailPath;
        this.userId = userId;
        this.customerCode = customerCode;
        this.customerProjectId = customerProjectId;
        this.taskId = taskId;
        this.agentId = agentId;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
        this.imageHashScore = imageHashScore;
        this.imageRotation = imageRotation;
    }

    public String getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public String getImageHashScore() {
        return imageHashScore;
    }

    public void setImageHashScore(String imageHashScore) {
        this.imageHashScore = imageHashScore;
    }

    public String getImageRotation() {
        return imageRotation;
    }

    public void setImageRotation(String imageRotation) {
        this.imageRotation = imageRotation;
    }

    public String getImageUUID() {
        return imageUUID;
    }

    public void setImageUUID(String imageUUID) {
        this.imageUUID = imageUUID;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "ImageStore{" +
                "imageUUID='" + imageUUID + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
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
                ", userId='" + userId + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", customerProjectId='" + customerProjectId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", lastUpdatedTimestamp='" + lastUpdatedTimestamp + '\'' +
                ", imageHashScore='" + imageHashScore + '\'' +
                ", imageRotation='" + imageRotation + '\'' +
                '}';
    }
}
