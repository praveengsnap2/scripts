package com.snap2buy.webservice.model;

/**
 * Created by sachin on 10/31/15.
 */
public class StoreMaster {
    String storeId;
    String retailerStoreId;
    String retailerChainCode;
    String retailer;
    String street;
    String city;
    String stateCode;
    String state;
    String zip;
    String latitude;
    String longitude;
    String comments;

    public StoreMaster() {
        super();
    }

    public StoreMaster(String storeId, String retailerStoreId, String retailerChainCode, String retailer, String street, String city, String stateCode, String state, String zip, String latitude, String longitude, String comments) {
        this.storeId = storeId;
        this.retailerStoreId = retailerStoreId;
        this.retailerChainCode = retailerChainCode;
        this.retailer = retailer;
        this.street = street;
        this.city = city;
        this.stateCode = stateCode;
        this.state = state;
        this.zip = zip;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comments = comments;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRetailerStoreId() {
        return retailerStoreId;
    }

    public void setRetailerStoreId(String retailerStoreId) {
        this.retailerStoreId = retailerStoreId;
    }

    public String getRetailerChainCode() {
        return retailerChainCode;
    }

    public void setRetailerChainCode(String retailerChainCode) {
        this.retailerChainCode = retailerChainCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StoreMaster{" +
                "storeId='" + storeId + '\'' +
                ", retailerStoreId='" + retailerStoreId + '\'' +
                ", retailerChainCode='" + retailerChainCode + '\'' +
                ", retailer='" + retailer + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
