package com.snap2buy.webservice.model;

/**
 * Created by sachin on 5/29/16.
 */
public class Project {
    String id;
    String customerProjectId;
    String customerId;
    String projectTypeId;
    String categoryId;
    String retailerId;
    String storeCount;
    String startDate;
    String createdDate;
    String createdBy;
    String updatedDate;
    String updatedBy;
    String status;

    public Project(String id, String customerProjectId, String customerId, String projectTypeId, String categoryId, String retailerId, String storeCount, String startDate, String createdDate, String createdBy, String updatedDate, String updatedBy, String status) {
        this.id = id;
        this.customerProjectId = customerProjectId;
        this.customerId = customerId;
        this.projectTypeId = projectTypeId;
        this.categoryId = categoryId;
        this.retailerId = retailerId;
        this.storeCount = storeCount;
        this.startDate = startDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(String projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(String storeCount) {
        this.storeCount = storeCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", customerProjectId='" + customerProjectId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", projectTypeId='" + projectTypeId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", retailerId='" + retailerId + '\'' +
                ", storeCount='" + storeCount + '\'' +
                ", startDate='" + startDate + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
