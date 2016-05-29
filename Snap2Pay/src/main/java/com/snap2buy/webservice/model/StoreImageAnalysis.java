package com.snap2buy.webservice.model;

/**
 * Created by sachin on 5/29/16.
 */
public class StoreImageAnalysis {

    String storeId;
    String retailerCode;
    String retailerStoreId;
    String state;
    String city;
    String projectId;
    String taskId;
    String upc;
    String facingCount;
    String score;
    String brand;
    String visitDate;

    public StoreImageAnalysis(String storeId, String retailerCode, String retailerStoreId, String state, String city, String projectId, String taskId, String upc, String facingCount, String score, String brand, String visitDate) {
        this.storeId = storeId;
        this.retailerCode = retailerCode;
        this.retailerStoreId = retailerStoreId;
        this.state = state;
        this.city = city;
        this.projectId = projectId;
        this.taskId = taskId;
        this.upc = upc;
        this.facingCount = facingCount;
        this.score = score;
        this.brand = brand;
        this.visitDate = visitDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRetailerCode() {
        return retailerCode;
    }

    public void setRetailerCode(String retailerCode) {
        this.retailerCode = retailerCode;
    }

    public String getRetailerStoreId() {
        return retailerStoreId;
    }

    public void setRetailerStoreId(String retailerStoreId) {
        this.retailerStoreId = retailerStoreId;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getFacingCount() {
        return facingCount;
    }

    public void setFacingCount(String facingCount) {
        this.facingCount = facingCount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return "StoreImageAnalysis{" +
                "storeId='" + storeId + '\'' +
                ", retailerCode='" + retailerCode + '\'' +
                ", retailerStoreId='" + retailerStoreId + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", projectId='" + projectId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", upc='" + upc + '\'' +
                ", facingCount='" + facingCount + '\'' +
                ", score='" + score + '\'' +
                ", brand='" + brand + '\'' +
                ", visitDate='" + visitDate + '\'' +
                '}';
    }
}
