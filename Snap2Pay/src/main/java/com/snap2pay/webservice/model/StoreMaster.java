package com.snap2pay.webservice.model;

/**
 * Created by sachin on 10/31/15.
 */
public class StoreMaster {
  String storeId;
  String retailerStoreId;
  String retailerChainCode;
  String street;
  String city;
  String state;
  String zip;
  String latitude;
  String longitude;


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

  @Override
  public String toString() {
    return "StoreMaster{" +
      "storeId='" + storeId + '\'' +
      ", retailerStoreId='" + retailerStoreId + '\'' +
      ", retailerChainCode='" + retailerChainCode + '\'' +
      ", street='" + street + '\'' +
      ", city='" + city + '\'' +
      ", state='" + state + '\'' +
      ", zip='" + zip + '\'' +
      ", latitude='" + latitude + '\'' +
      ", longitude='" + longitude + '\'' +
      '}';
  }
}
