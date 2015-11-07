/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.snap2pay.webservice.mapper;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * @author keerthanathangaraju
 */
public interface ParamMapper {
  String IMAGE_UUID = "imageUUID";
  String HOST_ID = "hostId";
  String CATEGORY_ID = "categoryId";
  String LATITUDE = "latitude";
  String LONGITUDE = "longitude";
  String USER_ID = "userId";

  String TIMESTAMP = "timestamp";
}
