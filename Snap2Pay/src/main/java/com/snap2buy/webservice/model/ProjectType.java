package com.snap2buy.webservice.model;

/**
 * Created by sachin on 5/29/16.
 */
public class ProjectType {
    String id;
    String name;
    String createdDate;
    String status;

    public ProjectType(String id, String name, String createdDate, String status) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
