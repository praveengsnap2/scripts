/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.snap2buy.webservice.rest.controller;

import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.mapper.BeanMapper;
import com.snap2buy.webservice.mapper.ParamMapper;
import com.snap2buy.webservice.model.ImageStore;
import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.model.ShelfAnalysisInput;
import com.snap2buy.webservice.rest.action.RestS2PAction;
import com.snap2buy.webservice.util.ShellUtil;
import com.snap2buy.webservice.util.Snap2PayOutput;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBElement;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sachin
 */
@Path("/S2B")
@Component(value = BeanMapper.BEAN_REST_CONTROLLER_S2P)
@Scope("request")
public class RestS2PController {

    private static Logger LOGGER = Logger.getLogger("s2b");
    @Autowired
    ServletContext servletContext;
    @Autowired
    @Qualifier(BeanMapper.BEAN_REST_ACTION_S2P)
    private RestS2PAction restS2PAction;
    @Value(value = "{appProp.filePath}")
    private String filePath;

    @Autowired
    @Qualifier(BeanMapper.BEAN_IMAGE_STORE_DAO)
    private ProcessImageDao processImageDao;

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
            @QueryParam(ParamMapper.SYNC) @DefaultValue("false") String sync,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts saveImage----------------\n");
        try {
            UUID uniqueKey = UUID.randomUUID();
            InputObject inputObject = new InputObject();

            if((!timeStamp.isEmpty())||(timeStamp!=null)||(timeStamp!="-9")) {
                LOGGER.info("---------------Controller----" + timeStamp + "---------------");
                Date date = new Date(Long.parseLong(timeStamp));
                DateFormat format = new SimpleDateFormat("yyyyMMdd");
                format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                String formattedDate = format.format(date);
                inputObject.setVisitDate(formattedDate);
            }else
            {
                inputObject.setVisitDate("-9");
            }

            inputObject.setImageUUID(uniqueKey.toString().trim());
            inputObject.setCategoryId(categoryId.trim());
            inputObject.setLatitude(latitude.trim());
            inputObject.setLongitude(longitude.trim());
            inputObject.setTimeStamp(timeStamp.trim());
            inputObject.setUserId(userId.trim());
            inputObject.setSync(sync);


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
                    String filenamePath = "/usr/share/s2pImages/" + inputObject.getVisitDate() + "/" + uniqueKey.toString() + ".jpg";
                    String thumbnailPath  = "/usr/share/s2pImages/" + inputObject.getVisitDate() + "/" + uniqueKey.toString() + "-thm.jpg";
                    File uploadedFile = new File(filenamePath);
                    //File uploadedFile = new File("/Users/sachin/s2pImages/" + userId + "/" + item.getName());

                    if (!uploadedFile.exists()) {
                        uploadedFile.getParentFile().mkdirs();
                        uploadedFile.getParentFile().setReadable(true);
                        uploadedFile.getParentFile().setWritable(true);
                        uploadedFile.getParentFile().setExecutable(true);
                    }
                    item.write(uploadedFile);
                    inputObject.setImageFilePath(uploadedFile.getAbsolutePath());

                    String csv=ShellUtil.createThumbnail(filenamePath,thumbnailPath);
                    String values[]=csv.split(",");

                    inputObject.setOrigWidth(values[0].replace("\r","").replace("\n","").trim());
                    inputObject.setOrigHeight(values[1].replace("\r","").replace("\n","").trim());
                    inputObject.setNewWidth(values[2].replace("\r","").replace("\n","").trim());
                    inputObject.setNewHeight(values[3].replace("\r","").replace("\n","").trim());
                    inputObject.setThumbnailPath(thumbnailPath);
                    result = ("File field " + name + " with file name "
                            + item.getName() + " detected.");
                    LOGGER.info(result);
                }
            }
            LOGGER.info("---------------Controller Starts saveImage with details " + inputObject + "----------------\n");

            return restS2PAction.saveImage(inputObject);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("userId", userId);
            inputList.put("error in Input","-9");

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
        LOGGER.info("---------------Controller Starts getJob::hostId::="+hostId+"----------------\n");
        try {
            InputObject inputObject = new InputObject();
            inputObject.setHostId(hostId);

            return restS2PAction.getJob(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("hostId", hostId);
            inputList.put("error in Input","-9");

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
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getImage::imageUUID::="+imageUUID+"----------------\n");
       ImageStore imageStore = processImageDao.findByImageUUId(imageUUID);
        try {

            File f = new File(imageStore.getImageFilePath());
            Response.ResponseBuilder r = Response.ok((Object) f);
            r.header("Content-Disposition", "attachment; filename=" + imageUUID + ".jpg");
            return r.build();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("imageUUID", imageUUID);
            inputList.put("error in Input","-9");

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

            LOGGER.info("---------------Controller  storeShelfAnalysis::="+shelfAnalysisInput.toString()+"----------------\n");

            return restS2PAction.storeShelfAnalysis(shelfAnalysisInput);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

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
        LOGGER.info("---------------Controller Starts getShelfAnalysis::imageUUID::="+imageUUID+"----------------\n");
        try {
            Snap2PayOutput rio;
            InputObject inputObject = new InputObject();

            inputObject.setImageUUID(imageUUID);

            return restS2PAction.getShelfAnalysis(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("imageUUID", imageUUID);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getShelfAnalysis----------------\n");
            return rio;
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getUpcDetails")
    public Snap2PayOutput getUpcDetails(
            @QueryParam(ParamMapper.UPC) @DefaultValue("-9") String upc,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getUpcDetails::upc::="+upc+"----------------\n");
        try {
            InputObject inputObject = new InputObject();

            inputObject.setUpc(upc);

            return restS2PAction.getUpcDetails(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("upc", upc);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getUpcDetails----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({"image/jpeg", "image/png"})
    @Path("/getUpcImage")
    public Response getUpcImage(
            @QueryParam(ParamMapper.UPC) @DefaultValue("-9") String upc,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getUpcImage::upc"+upc+"----------------\n");
        try {
            InputObject inputObject = new InputObject();

            inputObject.setUpc(upc);
            File f = restS2PAction.getUpcImage(inputObject);
            Response.ResponseBuilder r = Response.ok((Object) f);
            r.header("Content-Disposition", "attachment; filename=" + f.getName());
            return r.build();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("upc", upc);
            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getUpcImage----------------\n");
            return Response.serverError().build();
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
        try {
            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("success", "Success");

            rio = new Snap2PayOutput(null, inputList);
            return rio;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends checkS2P----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/storeThumbnails")
    public Snap2PayOutput storeThumbnails(
            @QueryParam(ParamMapper.IMAGE_FOLDER_PATH) @DefaultValue("-9") String imageFolderPath,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts storeThumbnails----------------\n");
        try {
            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            restS2PAction.storeThumbnails(imageFolderPath);
            inputList.put("success", "Success");

            rio = new Snap2PayOutput(null, inputList);
            return rio;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends checkS2P----------------\n");
            return rio;
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getReport")
    public Snap2PayOutput getReport(
            @QueryParam(ParamMapper.FREQUENCY) @DefaultValue("-9") String frequency,
            @QueryParam(ParamMapper.DATE_ID) @DefaultValue("-9") String dateId,
            @QueryParam(ParamMapper.STORE_ID) @DefaultValue("-9") String storeId,
            @QueryParam(ParamMapper.CATEGORY_ID) @DefaultValue("-9") String categoryId,
            @QueryParam(ParamMapper.BRAND_ID) @DefaultValue("-9") String brandId,
            @QueryParam(ParamMapper.MARKET_ID) @DefaultValue("-9") String marketId,
            @QueryParam(ParamMapper.CHAIN_ID) @DefaultValue("-9") String chainId,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getReport----------------\n");
        try {

            InputObject inputObject = new InputObject();

            inputObject.setBrandId(brandId);
            inputObject.setCategoryId(categoryId);
            inputObject.setChainId(chainId);
            inputObject.setDateId(dateId);
            inputObject.setFrequency(frequency);
            inputObject.setMarketId(marketId);
            inputObject.setBrandId(brandId);
            LOGGER.info("---------------Controller getReport::="+inputObject.toString()+"----------------\n");

            return restS2PAction.getReport(inputObject);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("brandId",brandId);
            inputList.put("categoryId", categoryId);
            inputList.put("chainId", chainId);
            inputList.put("dateId", dateId);
            inputList.put("frequency", frequency);
            inputList.put("marketId", marketId);
            inputList.put("brandId", brandId);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getReport----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/runImageAnalysis")
    public Snap2PayOutput runImageAnalysis(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts runImageAnalysis::imageUUID="+imageUUID+"----------------\n");
        try {
            Snap2PayOutput rio;
            InputObject inputObject = new InputObject();

            inputObject.setImageUUID(imageUUID);

            return restS2PAction.runImageAnalysis(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("imageUUID", imageUUID);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends runImageAnalysis----------------\n");
            return rio;
        }
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getImageAnalysis")
    public Snap2PayOutput getImageAnalysis(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getImageAnalysis::imageUUID="+imageUUID+"----------------\n");
        try {

            InputObject inputObject = new InputObject();

            inputObject.setImageUUID(imageUUID);

            return restS2PAction.getImageAnalysis(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("imageUUID", imageUUID);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getImageAnalysis----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getStoreOptions")
    public Snap2PayOutput getStoreOptions(
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getStoreOptions----------------\n");
        try {
            Snap2PayOutput rio;

            return restS2PAction.getStoreOptions();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getStoreOptions----------------\n");
            return rio;
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getImages")
    public Snap2PayOutput getImages(
            @QueryParam(ParamMapper.STORE_ID) @DefaultValue("-9") String storeId,
            @QueryParam(ParamMapper.DATE_ID) @DefaultValue("-9") String dateId,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getImages::storeId="+storeId+"::dateId="+dateId+"----------------\n");
        try {
            InputObject inputObject = new InputObject();

            inputObject.setStoreId(storeId);
            inputObject.setDateId(dateId);
            return restS2PAction.getImages(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getImages----------------\n");
            return rio;
        }
    }

    @GET
      @Produces({MediaType.APPLICATION_JSON})
      @Path("/getStores")
      public Snap2PayOutput getStores(
            @QueryParam(ParamMapper.RETAILER_CHAIN_CODE) @DefaultValue("-9") String retailerChainCode,
            @QueryParam(ParamMapper.STATE_CODE) @DefaultValue("-9") String stateCode,
            @QueryParam(ParamMapper.CITY) @DefaultValue("-9") String city,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getStores::retailerChainCode="+retailerChainCode+"::stateCode="+stateCode+"::city="+city+"----------------\n");
        try {
            InputObject inputObject = new InputObject();

            inputObject.setRetailerChainCode(retailerChainCode);
            inputObject.setStateCode(stateCode);
            inputObject.setCity(city);
            return restS2PAction.getStores(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("retailerChainCode",retailerChainCode);
            inputList.put("stateCode",stateCode);
            inputList.put("city",city);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getStores----------------\n");
            return rio;
        }
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getDistributionLists")
    public Snap2PayOutput getDistributionLists(
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getDistributionLists----------------\n");
        try {
            return restS2PAction.getDistributionLists();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getDistributionLists----------------\n");
            return rio;
        }
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/doDistributionCheck")
    public Snap2PayOutput doDistributionCheck(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @QueryParam(ParamMapper.LIST_ID) @DefaultValue("-9") String listId,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts doDistributionCheck::doDistributionCheck::listId=" + listId + "::imageUUID=" + imageUUID + "----------------\n");
        try {
            InputObject inputObject = new InputObject();
            inputObject.setListId(listId);
            inputObject.setImageUUID(imageUUID);
            return restS2PAction.doDistributionCheck(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("error in Input","-9");
            inputList.put("listId",listId);
            inputList.put("imageUUID",imageUUID);
            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends doDistributionCheck----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/doBeforeAfterCheck")
    public Snap2PayOutput doBeforeAfterCheck(
            @QueryParam(ParamMapper.PREV_IMAGE_UUID) @DefaultValue("-9") String prevImageUUID,
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts doBeforeAfterCheck::imageUUID-1::="+prevImageUUID+"::imageUUID-2::"+imageUUID+"----------------\n");
        try {
            InputObject inputObject = new InputObject();
            inputObject.setPrevImageUUID(prevImageUUID);
            inputObject.setImageUUID(imageUUID);
            return restS2PAction.doBeforeAfterCheck(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("error in Input","-9");
            inputList.put("prevImageUUID",prevImageUUID);
            inputList.put("imageUUID",imageUUID);
            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends doBeforeAfterCheck----------------\n");
            return rio;
        }
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getImageMetaData")
    public Snap2PayOutput getImageMetaData(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getImageMetaData::imageUUID-1::="+imageUUID+"----------------\n");
        try {
            InputObject inputObject = new InputObject();
            inputObject.setImageUUID(imageUUID);
            return restS2PAction.getImageMetaData(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("error in Input","-9");
            inputList.put("imageUUID",imageUUID);
            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getImageMetaData----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/doShareOfShelfAnalysis")
    public Snap2PayOutput doShareOfShelfAnalysis(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUIDCsvString,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts doShareOfShelfAnalysis::imageUUID="+imageUUIDCsvString+"----------------\n");
        try {

            InputObject inputObject = new InputObject();
            inputObject.setImageUUIDCsvString(imageUUIDCsvString);
            return restS2PAction.doShareOfShelfAnalysis(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("imageUUIDCsvString", imageUUIDCsvString);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends doShareOfShelfAnalysis----------------\n");
            return rio;
        }
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/updateLatLong")
    public Snap2PayOutput updateLatLong(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @QueryParam(ParamMapper.LATITUDE) @DefaultValue("-9") String latitude,
            @QueryParam(ParamMapper.LONGITUDE) @DefaultValue("-9") String longitude,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts updateLatLong::imageUUID="+imageUUID+"::latitude="+latitude+"::longitude="+longitude+"----------------\n");
        try {
            InputObject inputObject = new InputObject();

            inputObject.setImageUUID(imageUUID);
            inputObject.setLatitude(latitude);
            inputObject.setLongitude(longitude);
            return restS2PAction.updateLatLong(inputObject);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();

            inputList.put("error in Input","-9");
            inputList.put("retailerChainCode",imageUUID);
            inputList.put("stateCode",latitude);
            inputList.put("city",longitude);

            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends updateLatLong----------------\n");
            return rio;
        }
    }

    @GET
    @Produces({"image/jpeg", "image/png"})
    @Path("/displayImage")
    public StreamingOutput displayImage(
            @QueryParam(ParamMapper.IMAGE_UUID) @DefaultValue("-9") String imageUUID,
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts displayImage::imageUUID::="+imageUUID+"----------------\n");
        ImageStore imageStore = processImageDao.findByImageUUId(imageUUID);
            StreamingOutput so =   new StreamingOutput() {
                @Override
                public void write(OutputStream os) throws IOException, WebApplicationException {
                    File f = new File(imageStore.getThumbnailPath());
        //            File f = new File("/usr/share/s2pImages/keerthana1/b87de3ed-0902-47ed-b487-06aaa44d11df.jpg");

                    InputStream in = new FileInputStream(f);
                    byte[] buf = new byte[8192];
                    int c=0;
                    while ((c = in.read(buf, 0, buf.length)) > 0) {
                        os.write(buf, 0, c);
                        os.flush();
                    }

                    os.close();
                }
            };

       return so;
        }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/getSelfAnalysisCsv")
    public Snap2PayOutput getSelfAnalysisCsv(
            @Context HttpServletRequest request,
            @Context HttpServletResponse response
    ) {
        LOGGER.info("---------------Controller Starts getSelfAnalysisCsv----------------\n");
        try {
            return restS2PAction.getSelfAnalysisCsv();

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("EXCEPTION [" + e.getMessage() + " , " + e);
            LOGGER.error("exception", e);

            Snap2PayOutput rio;
            HashMap<String, String> inputList = new HashMap<String, String>();
            inputList.put("error in Input","-9");
            rio = new Snap2PayOutput(null, inputList);
            LOGGER.info("---------------Controller Ends getSelfAnalysisCsv----------------\n");
            return rio;
        }
    }
}
