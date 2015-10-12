package com.snap2pay.webservice.util;

public class S2PParameters {

    private String userId;
    private String latitude;
    private String longitude;
    private String categoryId;
    private String timeStamp;
    private String filePath;

    public S2PParameters() {

    }

    public S2PParameters(String userId, String latitude, String longitude, String categoryId, String timeStamp, String filePath) {
        this.setUserId(userId);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setCategoryId(categoryId);
        this.setTimeStamp(timeStamp);
        this.setFilePath(filePath);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
