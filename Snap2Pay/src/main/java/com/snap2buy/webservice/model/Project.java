package com.snap2buy.webservice.model;

import java.util.List;

/**
 * Created by sachin on 5/29/16.
 */
public class Project {
    String id;
    String projectName;
    String customerProjectId;
    String customerCode;
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
    List<ProjectUpc> projectUpcList;

    public Project(String id, String projectName, String customerProjectId, String customerCode, String projectTypeId, String categoryId, String retailerId, String storeCount, String startDate, String createdDate, String createdBy, String updatedDate, String updatedBy, String status, List<ProjectUpc> projectUpcList) {
        this.id = id;
        this.projectName = projectName;
        this.customerProjectId = customerProjectId;
        this.customerCode = customerCode;
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
        this.projectUpcList = projectUpcList;
    }

    public Project(String id, String projectName, String customerProjectId, String customerCode, String projectTypeId, String categoryId, String retailerId, String storeCount, String startDate, String createdDate, String createdBy, String updatedDate, String updatedBy, String status) {
        this.id = id;
        this.projectName = projectName;
        this.customerProjectId = customerProjectId;
        this.customerCode = customerCode;
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

    public List<ProjectUpc> getProjectUpcList() {
        return projectUpcList;
    }

    public void setProjectUpcList(List<ProjectUpc> projectUpcList) {
        this.projectUpcList = projectUpcList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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
                ", projectName='" + projectName + '\'' +
                ", customerProjectId='" + customerProjectId + '\'' +
                ", customerCode='" + customerCode + '\'' +
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
