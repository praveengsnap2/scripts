package com.snap2buy.webservice.model;

/**
 * Created by sachin on 5/29/16.
 */
public class ProjectUpc {
    String id;
    String upc;
    String expectedFacingCount;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;

    public ProjectUpc(String id, String upc, String ExpectedFacingCount, String imageUrl1, String imageUrl2, String imageUrl3) {
        this.id = id;
        this.upc = upc;
        this.expectedFacingCount = ExpectedFacingCount;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getExpectedFacingCount() {
        return expectedFacingCount;
    }

    public void setExpectedFacingCount(String expectedFacingCount) {
        this.expectedFacingCount = expectedFacingCount;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    @Override
    public String toString() {
        return "ProjectUpc{" +
                "id='" + id + '\'' +
                ", upc='" + upc + '\'' +
                ", expectedFacingCount='" + expectedFacingCount + '\'' +
                ", imageUrl1='" + imageUrl1 + '\'' +
                ", imageUrl2='" + imageUrl2 + '\'' +
                ", imageUrl3='" + imageUrl3 + '\'' +
                '}';
    }
}
