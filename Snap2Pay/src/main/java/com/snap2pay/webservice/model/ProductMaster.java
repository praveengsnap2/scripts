package com.snap2pay.webservice.model;

/**
 * Created by sachin on 1/23/16.
 */
public class ProductMaster {
  String upc;
  String mfg_name ;
  String brand_name;
  String product_type;
  String product_short_name;
  String product_long_name;
  String low_fat;
  String fat_free;
  String gluten_free;
  String why_buy_1;
  String why_buy_2;
  String why_buy_3;
  String why_buy_4;
  String romance_copy_1;
  String romance_copy_2;
  String romance_copy_3;
  String romance_copy_4;
  String height;
  String width;
  String depth;


  public ProductMaster(String upc, String mfg_name, String brand_name, String product_type, String product_short_name, String product_long_name, String low_fat, String fat_free, String gluten_free, String why_buy_1, String why_buy_2, String why_buy_3, String why_buy_4, String romance_copy_1, String romance_copy_2, String romance_copy_3, String romance_copy_4, String height, String width, String depth) {
    this.upc = upc;
    this.mfg_name = mfg_name;
    this.brand_name = brand_name;
    this.product_type = product_type;
    this.product_short_name = product_short_name;
    this.product_long_name = product_long_name;
    this.low_fat = low_fat;
    this.fat_free = fat_free;
    this.gluten_free = gluten_free;
    this.why_buy_1 = why_buy_1;
    this.why_buy_2 = why_buy_2;
    this.why_buy_3 = why_buy_3;
    this.why_buy_4 = why_buy_4;
    this.romance_copy_1 = romance_copy_1;
    this.romance_copy_2 = romance_copy_2;
    this.romance_copy_3 = romance_copy_3;
    this.romance_copy_4 = romance_copy_4;
    this.height = height;
    this.width = width;
    this.depth = depth;
  }

  public String getUpc() {
    return upc;
  }

  public void setUpc(String upc) {
    this.upc = upc;
  }

  public String getMfg_name() {
    return mfg_name;
  }

  public void setMfg_name(String mfg_name) {
    this.mfg_name = mfg_name;
  }

  public String getBrand_name() {
    return brand_name;
  }

  public void setBrand_name(String brand_name) {
    this.brand_name = brand_name;
  }

  public String getProduct_type() {
    return product_type;
  }

  public void setProduct_type(String product_type) {
    this.product_type = product_type;
  }

  public String getProduct_short_name() {
    return product_short_name;
  }

  public void setProduct_short_name(String product_short_name) {
    this.product_short_name = product_short_name;
  }

  public String getProduct_long_name() {
    return product_long_name;
  }

  public void setProduct_long_name(String product_long_name) {
    this.product_long_name = product_long_name;
  }

  public String getLow_fat() {
    return low_fat;
  }

  public void setLow_fat(String low_fat) {
    this.low_fat = low_fat;
  }

  public String getFat_free() {
    return fat_free;
  }

  public void setFat_free(String fat_free) {
    this.fat_free = fat_free;
  }

  public String getGluten_free() {
    return gluten_free;
  }

  public void setGluten_free(String gluten_free) {
    this.gluten_free = gluten_free;
  }

  public String getWhy_buy_1() {
    return why_buy_1;
  }

  public void setWhy_buy_1(String why_buy_1) {
    this.why_buy_1 = why_buy_1;
  }

  public String getWhy_buy_2() {
    return why_buy_2;
  }

  public void setWhy_buy_2(String why_buy_2) {
    this.why_buy_2 = why_buy_2;
  }

  public String getWhy_buy_3() {
    return why_buy_3;
  }

  public void setWhy_buy_3(String why_buy_3) {
    this.why_buy_3 = why_buy_3;
  }

  public String getWhy_buy_4() {
    return why_buy_4;
  }

  public void setWhy_buy_4(String why_buy_4) {
    this.why_buy_4 = why_buy_4;
  }

  public String getRomance_copy_1() {
    return romance_copy_1;
  }

  public void setRomance_copy_1(String romance_copy_1) {
    this.romance_copy_1 = romance_copy_1;
  }

  public String getRomance_copy_2() {
    return romance_copy_2;
  }

  public void setRomance_copy_2(String romance_copy_2) {
    this.romance_copy_2 = romance_copy_2;
  }

  public String getRomance_copy_3() {
    return romance_copy_3;
  }

  public void setRomance_copy_3(String romance_copy_3) {
    this.romance_copy_3 = romance_copy_3;
  }

  public String getRomance_copy_4() {
    return romance_copy_4;
  }

  public void setRomance_copy_4(String romance_copy_4) {
    this.romance_copy_4 = romance_copy_4;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getDepth() {
    return depth;
  }

  public void setDepth(String depth) {
    this.depth = depth;
  }

  @Override
  public String toString() {
    return "ProductMaster{" +
      "upc='" + upc + '\'' +
      ", mfg_name='" + mfg_name + '\'' +
      ", brand_name='" + brand_name + '\'' +
      ", product_type='" + product_type + '\'' +
      ", product_short_name='" + product_short_name + '\'' +
      ", product_long_name='" + product_long_name + '\'' +
      ", low_fat='" + low_fat + '\'' +
      ", fat_free='" + fat_free + '\'' +
      ", gluten_free='" + gluten_free + '\'' +
      ", why_buy_1='" + why_buy_1 + '\'' +
      ", why_buy_2='" + why_buy_2 + '\'' +
      ", why_buy_3='" + why_buy_3 + '\'' +
      ", why_buy_4='" + why_buy_4 + '\'' +
      ", romance_copy_1='" + romance_copy_1 + '\'' +
      ", romance_copy_2='" + romance_copy_2 + '\'' +
      ", romance_copy_3='" + romance_copy_3 + '\'' +
      ", romance_copy_4='" + romance_copy_4 + '\'' +
      ", height='" + height + '\'' +
      ", width='" + width + '\'' +
      ", depth='" + depth + '\'' +
      '}';
  }
}
