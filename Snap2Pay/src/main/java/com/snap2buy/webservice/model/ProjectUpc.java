package com.snap2buy.webservice.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sachin on 5/29/16.
 */
@XmlRootElement(name = "ProjectUpc")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectUpc {
    @XmlElement
    String id;
    @XmlElement
    String customerProjectId;
    @XmlElement
    String upc;
    @XmlElement
    String skuType;
    @XmlElement
    String expectedFacingCount;
    @XmlElement
    String imageUrl1;
    @XmlElement
    String imageUrl2;
    @XmlElement
    String imageUrl3;

    public ProjectUpc() {
        super();
    }

    public ProjectUpc(String id, String customerProjectId, String upc, String skuType, String expectedFacingCount, String imageUrl1, String imageUrl2, String imageUrl3) {
        this.id = id;
        this.customerProjectId = customerProjectId;
        this.upc = upc;
        this.skuType = skuType;
        this.expectedFacingCount = expectedFacingCount;
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

    public String getCustomerProjectId() {
        return customerProjectId;
    }

    public void setCustomerProjectId(String customerProjectId) {
        this.customerProjectId = customerProjectId;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
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
                ", customerProjectId='" + customerProjectId + '\'' +
                ", upc='" + upc + '\'' +
                ", skuType='" + skuType + '\'' +
                ", expectedFacingCount='" + expectedFacingCount + '\'' +
                ", imageUrl1='" + imageUrl1 + '\'' +
                ", imageUrl2='" + imageUrl2 + '\'' +
                ", imageUrl3='" + imageUrl3 + '\'' +
                '}';
    }
}
