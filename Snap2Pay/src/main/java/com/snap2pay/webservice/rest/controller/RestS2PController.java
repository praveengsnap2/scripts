/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2pay.webservice.rest.controller;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.snap2pay.webservice.mapper.BeanMapper;
import com.snap2pay.webservice.rest.action.RestS2PAction;
import com.snap2pay.webservice.util.S2PParameters;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.snap2pay.webservice.mapper.ParamMapper;
import com.snap2pay.webservice.model.ShelfVisit;
import com.snap2pay.webservice.util.Snap2PayOutput;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.ws.rs.Consumes;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 *
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

//    @Value(value = "{appProp.filePath}")
//    private String filePath;

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
            @QueryParam(ParamMapper.TIMESTAMP) @DefaultValue("-9") Long timeStamp,
            @QueryParam(ParamMapper.USER_ID) @DefaultValue("N/A") String userId,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts----------------\n");
        LOGGER.info("Inside S2P/saveImage ");
        try {
            
            ShelfVisit shelfVisit = new ShelfVisit();
            shelfVisit.setCategoryId(categoryId);
            shelfVisit.setLatitude(latitude);
            shelfVisit.setLongitude(longitude);
            Date time=new Date(timeStamp*1000);
            shelfVisit.setVisitDate(time);
            shelfVisit.setUserId(userId);
//            shelfVisit.setCategoryId(s2PParameters.getCategoryId());
//            shelfVisit.setUserId(s2PParameters.getUserId());
//            shelfVisit.setLatitude(s2PParameters.getLatitude());
//            shelfVisit.setLongitude(s2PParameters.getLongitude());
//            shelfVisit.setCategoryId(s2PParameters.getCategoryId());
//            S2PParameters shelfVisit = new S2PParameters();
//            shelfVisit.setCategoryId(categoryId);
//            shelfVisit.setLatitude(latitude);
//            shelfVisit.setLongitude(longitude);
//            shelfVisit.setTimeStamp(timeStamp);
//            shelfVisit.setUserId(userId);
            // Create a factory for disk-based file items
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
                    //File uploadedFile = new File("/usr/share/s2pImages/" + userId + "/" + item.getName());
                    File uploadedFile = new File("/Users/keerthanathangaraju/s2pImages/" + userId + "/" + item.getName());

                    if (!uploadedFile.exists()) {
                        uploadedFile.getParentFile().mkdirs();
                    }
                    item.write(uploadedFile);
                    shelfVisit.setImageUrl(uploadedFile.getAbsolutePath());
                    
//                    GridFS gfsPhoto = new GridFS(db, "photo");
//                    GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
//                    gfsFile.save();
                    result = ("File field " + name + " with file name "
                            + item.getName() + " detected.");
                    LOGGER.info(result);
                }
            }
            return restS2PAction.saveImage(shelfVisit);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("userId", userId);
//            inputList.put("poutstatus", "-5");

            rio = new Snap2PayOutput( null, inputList);
            LOGGER.info("---------------Controller Ends----------------\n");
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
        LOGGER.info("---------------Controller Starts----------------\n");
        LOGGER.info("Inside S2P/checkS2P");
        try {
            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("success", "Success");

            rio = new Snap2PayOutput( null, inputList);
            LOGGER.info("---------------Controller Ends----------------\n");
            return rio;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

//            inputList.put("poutstatus", "-5");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends----------------\n");
            return rio;
        }
    }

}
