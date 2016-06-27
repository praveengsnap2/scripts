/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.snap2buy.webservice.model;
//

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author sachin
 */
@Document
public class InputObject {

    @Id
    private String visitId;
    private String retailerChainCode;
    private String listId;
    private String state;
    private String stateCode;
    private String city;
    private String upc;
    private String hostId;
    private String visitDate;
    private String shopId;
    private String categoryId;
    private String userId;
    private String timeStamp;
    private String imageFilePath;
    private String imageUUID;
    private String prevImageUUID;
    private String latitude;
    private String longitude;
    private String sync;
    private Integer responseCode;
    private String responseMessage;
    private String startTime;
    private String endTime;
    private String endDate;
    private String startDate;
    private String frequency;
    //private String dateId;
    private String storeId;
    private String brandId;
    private String marketId;
    private String chainId;
    private String queryId;
    private String limit;
    private String prevStartTime;
    private String prevEndTime;
    private String origWidth;
    private String origHeight;
    private String newWidth;
    private String newHeight;
    private String thumbnailPath;
    private String imageUUIDCsvString;
    private String street;
    private String retailer;
    private String customerCode;
    private String retailerCode;
    private String customerProjectId;
    private String taskId;
    private String agentId;
    private String id;

    public String getRetailerCode() {
        return retailerCode;
    }

    public void setRetailerCode(String retailerCode) {
        this.retailerCode = retailerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getImageUUIDCsvString() {
        return imageUUIDCsvString;
    }

    public void setImageUUIDCsvString(String imageUUIDCsvString) {
        this.imageUUIDCsvString = imageUUIDCsvString;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
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

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPrevImageUUID() {
        return prevImageUUID;
    }

    public void setPrevImageUUID(String prevImageUUID) {
        this.prevImageUUID = prevImageUUID;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getRetailerChainCode() {
        return retailerChainCode;
    }

    public void setRetailerChainCode(String retailerChainCode) {
        this.retailerChainCode = retailerChainCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private Map<Object, Object> morefiltersMap;

    public Map<Object, Object> getMorefiltersMap() {
        return morefiltersMap;
    }

    public void setMorefiltersMap(Map<Object, Object> morefiltersMap) {
        this.morefiltersMap = morefiltersMap;
    }

    public String getPrevStartTime() {
        return prevStartTime;
    }

    public void setPrevStartTime(String prevStartTime) {
        this.prevStartTime = prevStartTime;
    }

    public String getPrevEndTime() {
        return prevEndTime;
    }

    public void setPrevEndTime(String prevEndTime) {
        this.prevEndTime = prevEndTime;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

//    public String getDateId() {
//        return dateId;
//    }
//
//    public void setDateId(String dateId) {
//        this.dateId = dateId;
//    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

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
                ", retailerChainCode='" + retailerChainCode + '\'' +
                ", listId='" + listId + '\'' +
                ", state='" + state + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", city='" + city + '\'' +
                ", upc='" + upc + '\'' +
                ", hostId='" + hostId + '\'' +
                ", visitDate='" + visitDate + '\'' +
                ", shopId='" + shopId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", userId='" + userId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", imageFilePath='" + imageFilePath + '\'' +
                ", imageUUID='" + imageUUID + '\'' +
                ", prevImageUUID='" + prevImageUUID + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sync='" + sync + '\'' +
                ", responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", frequency='" + frequency + '\'' +
                ", storeId='" + storeId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", marketId='" + marketId + '\'' +
                ", chainId='" + chainId + '\'' +
                ", queryId='" + queryId + '\'' +
                ", limit='" + limit + '\'' +
                ", prevStartTime='" + prevStartTime + '\'' +
                ", prevEndTime='" + prevEndTime + '\'' +
                ", origWidth='" + origWidth + '\'' +
                ", origHeight='" + origHeight + '\'' +
                ", newWidth='" + newWidth + '\'' +
                ", newHeight='" + newHeight + '\'' +
                ", thumbnailPath='" + thumbnailPath + '\'' +
                ", imageUUIDCsvString='" + imageUUIDCsvString + '\'' +
                ", street='" + street + '\'' +
                ", retailer='" + retailer + '\'' +
                ", customerCode='" + customerCode + '\'' +
                ", retailerCode='" + retailerCode + '\'' +
                ", customerProjectId='" + customerProjectId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", id='" + id + '\'' +
                ", morefiltersMap=" + morefiltersMap +
                '}';
    }


}
