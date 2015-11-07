/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.rest.controller;

import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.model.InputObject;
import com.snap2pay.webservice.model.ShelfAnalysisInput;
import com.snap2pay.webservice.rest.action.RestS2PAction;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.snap2pay.webservice.mapper.ParamMapper;
import com.snap2pay.webservice.util.Snap2PayOutput;

import java.io.File;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Request;
import javax.xml.bind.JAXBElement;
import javax.ws.rs.core.Response;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author keerthanathangaraju
 */
@Path("/S2P")
@Component(value = BeanMapper.BEAN_REST_CONTROLLER_S2P)
@Scope("request")
public class RestS2PController {

  private static Logger LOGGER = Logger.getLogger("s2p");

  @Autowired
  @Qualifier(BeanMapper.BEAN_REST_ACTION_S2P)
  private RestS2PAction restS2PAction;

  @Value(value = "{appProp.filePath}")
  private String filePath;

  @Autowired
  ServletContext servletContext;

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Path("/saveImage")
  public Snap2PayOutput saveImage(
    @QueryParam(ParamMapper.CATEGORY_ID) @DefaultValue("-9") String categoryId,
    @QueryParam(ParamMapper.LATITUDE) @DefaultValue("-9") String latitude,
    @QueryParam(ParamMapper.LONGITUDE) @DefaultValue("-9") String longitude,
    @QueryParam(ParamMapper.TIMESTAMP) @DefaultValue("-9") String timeStamp,
    @QueryParam(ParamMapper.USER_ID) @DefaultValue("N/A") String userId,
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts saveImage----------------\n");
    try {
      UUID uniqueKey = UUID.randomUUID();
      InputObject inputObject = new InputObject();
      inputObject.setImageUUID(uniqueKey.toString());
      inputObject.setCategoryId(categoryId);
      inputObject.setLatitude(latitude);
      inputObject.setLongitude(longitude);
      inputObject.setTimeStamp(timeStamp);
      inputObject.setUserId(userId);


      //Create a factory for disk-based file items
      DiskFileItemFactory factory = new DiskFileItemFactory();

      // Configure a repository (to ensure a secure temp location is used)
      File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
      factory.setRepository(repository);

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      String result = "";
      // Parse the request
      List<FileItem> items = upload.parseRequest(request);
      // Process the uploaded items
      Iterator<FileItem> iter = items.iterator();
      while (iter.hasNext()) {
        FileItem item = iter.next();
        String name = item.getFieldName();
        String value = item.getString();
        if (item.isFormField()) {
          LOGGER.info("Form field " + name + " with value "
            + value + " detected.");
        } else {
          File uploadedFile = new File("/usr/share/s2pImages/" + userId + "/" + uniqueKey.toString());
          //File uploadedFile = new File("/Users/sachin/s2pImages/" + userId + "/" + item.getName());

          if (!uploadedFile.exists()) {
            uploadedFile.getParentFile().mkdirs();
          }
          item.write(uploadedFile);
          inputObject.setImageFilePath(uploadedFile.getAbsolutePath());

          result = ("File field " + name + " with file name "
            + item.getName() + " detected.");
          LOGGER.info(result);
        }
      }
      LOGGER.info("---------------Controller Ends saveImage----------------\n");

      return restS2PAction.saveImage(inputObject);
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();

      inputList.put("userId", userId);
      inputList.put("poutstatus", "-5");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends----------------\n");
      return rio;
    }
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/getJob")
  public Snap2PayOutput getJob(
    @QueryParam(ParamMapper.HOST_ID) @DefaultValue("-9") String hostId,
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts getJob----------------\n");
    try {
      InputObject inputObject = new InputObject();
      inputObject.setHostId(hostId);

      LOGGER.info("---------------Controller Ends getJob----------------\n");
      return restS2PAction.getJob(inputObject);

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();
      inputList.put("hostId", hostId);
      inputList.put("poutstatus", "-5");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends getJob----------------\n");
      return rio;
    }
  }

  @GET
  @Produces({"image/jpeg", "image/png"})
  @Path("/getImage")
  public Response getImage(
    @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
    @QueryParam(ParamMapper.USER_ID) @DefaultValue("-9") String userId,
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts getImage----------------\n");
    try {

      File f = new File("/usr/share/s2pImages/" + userId + "/" + imageUUID);
      Response.ResponseBuilder r = Response.ok((Object) f);
      r.header("Content-Disposition", "attachment; filename=" + imageUUID);
      LOGGER.info("---------------Controller Ends getImage----------------\n");
      return r.build();

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();
      inputList.put("imageUUID", imageUUID);
      inputList.put("userID", userId);
      inputList.put("poutstatus", "-5");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends getImage----------------\n");
      return Response.serverError().build();
    }
  }

  @POST
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/storeShelfAnalysis")
  public Snap2PayOutput storeShelfAnalysis(
    //@QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
    JAXBElement<ShelfAnalysisInput> p,
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts storeShelfAnalysis----------------\n");
    try {

      ShelfAnalysisInput shelfAnalysisInput = p.getValue();
      System.out.println(shelfAnalysisInput.toString());
      // shelfAnalysisInput.setImageUUID(imageUUID);
      LOGGER.info("---------------Controller Ends getShelfAnalysis----------------\n");

      return restS2PAction.storeShelfAnalysis(shelfAnalysisInput);

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();

      inputList.put("poutstatus", "-5");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends getShelfAnalysis----------------\n");
      return rio;
    }
  }


  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/getShelfAnalysis")
  public Snap2PayOutput getShelfAnalysis(
    @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts getShelfAnalysis----------------\n");
    try {
      Snap2PayOutput rio;
      InputObject inputObject = new InputObject();

      inputObject.setImageUUID(imageUUID);

      LOGGER.info("---------------Controller Ends getShelfAnalysis----------------\n");
      return restS2PAction.getShelfAnalysis(inputObject);

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();

      inputList.put("poutstatus", "-5");
      inputList.put("imageUUID", imageUUID);

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends getShelfAnalysis----------------\n");
      return rio;
    }
  }


  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/checkS2P")
  public Snap2PayOutput checkS2P(
    @Context HttpServletRequest request,
    @Context HttpServletResponse response
  ) {
    LOGGER.info("---------------Controller Starts checkS2P----------------\n");
    LOGGER.info("Inside S2P/checkS2P");
    try {
      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();

      inputList.put("success", "Success");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends checkS2P----------------\n");
      return rio;
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
      LOGGER.error("exception", e);

      Snap2PayOutput rio;
      HashMap<String, String> inputList = new HashMap<String, String>();

      inputList.put("poutstatus", "-5");

      rio = new Snap2PayOutput(null, inputList);
      LOGGER.info("---------------Controller Ends checkS2P----------------\n");
      return rio;
    }
  }
}
