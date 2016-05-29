package com.snap2buy.webservice.util;

import com.snap2buy.webservice.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sachin on 3/15/16.
 */
public class ConverterUtil {

    public static List<LinkedHashMap<String, String>> convertImageAnalysisObjectToMap(List<ImageAnalysis> dataList) {

        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
        for (ImageAnalysis listEntry : dataList) {
            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
            temp.put("Upc", listEntry.getUpc());
            temp.put("LeftTopX", listEntry.getLeftTopX());
            temp.put("LeftTopY", listEntry.getLeftTopY());
            temp.put("Width", listEntry.getWidth());
            temp.put("Height", listEntry.getHeight());
            temp.put("Promotion", listEntry.getPromotion());
            temp.put("Price", listEntry.getPrice());
            temp.put("PriceLabel", listEntry.getPriceLabel());
            temp.put("productShortName", listEntry.getProductShortName());
            temp.put("productLongName", listEntry.getProductLongName());
            temp.put("brandName", listEntry.getBrandName());
            result.add(temp);
        }
        return result;
    }

    public static List<LinkedHashMap<String, String>> convertImageStoreObjectToMap(List<ImageStore> dataList) {

        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
        for (ImageStore listEntry : dataList) {
            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
            temp.put("imageUUID", listEntry.getImageUUID());
            temp.put("ImageFilePath", listEntry.getImageFilePath());
            temp.put("userId", listEntry.getUserId());
            temp.put("categoryId", listEntry.getCategoryId());
            temp.put("latitude", listEntry.getLatitude());
            temp.put("longitude", listEntry.getLongitude());
            temp.put("timeStamp", listEntry.getTimeStamp());
            temp.put("storeId", listEntry.getStoreId());
            temp.put("hostId", listEntry.getHostId());
            temp.put("dateId", listEntry.getDateId());
            temp.put("imageStatus", listEntry.getImageStatus());
            temp.put("shelfStatus", listEntry.getShelfStatus());
            temp.put("origWidth", listEntry.getOrigWidth());
            temp.put("origHeight", listEntry.getOrigHeight());
            temp.put("newWidth", listEntry.getNewWidth());
            temp.put("newHeight", listEntry.getNewHeight());
            temp.put("thumbnailPath", listEntry.getThumbnailPath());
            temp.put("customerCode", listEntry.getCustomerCode());
            temp.put("projectId", listEntry.getProjectId());
            temp.put("taskId", listEntry.getTaskId());
            temp.put("agentId", listEntry.getAgentId());
            result.add(temp);
        }
        return result;
    }

//    public static List<LinkedHashMap<String, String>> convertStoreMasterObjectToMap(List<StoreMaster> dataList) {
//
//        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
//        for (StoreMaster listEntry : dataList) {
//            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
//            temp.put("retailerChainCode", listEntry.getRetailerChainCode());
//            temp.put("state", listEntry.getState());
//            temp.put("city", listEntry.getCity());
//            temp.put("storeId",listEntry.getStoreId());
//            temp.put("street",listEntry.getStreet());
//            result.add(temp);
//        }
//        return result;
//    }
//
//    public static List<StoreMaster> convertStoreMasterMapToObject(List<LinkedHashMap<String, String>> dataMap) {
//        List<StoreMaster> dataList = new ArrayList<StoreMaster>();
//        for (LinkedHashMap<String, String> mapEntry : dataMap) {
//            StoreMaster listEntry = new StoreMaster();
//            listEntry.setRetailerChainCode(mapEntry.get("retailerChainCode"));
//            listEntry.setState(mapEntry.get("state"));
//            listEntry.setCity(mapEntry.get("city"));
//            listEntry.setStoreId(mapEntry.get("storeId"));
//            listEntry.setStreet(mapEntry.get("street"));
//            dataList.add(listEntry);
//        }
//        return dataList;
//    }
//    public static List<LinkedHashMap<String, String>> convertImageStoreObjectToMap(List<ImageStore> dataList) {
//
//        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
//        for (ImageStore mapEntry : dataList) {
//            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
//            temp.put("imageUUID", mapEntry.getImageUUID());
//            result.add(temp);
//        }
//        return result;
//    }
//
//    public static List<ImageStore> convertImageStoreMapToObject( List<LinkedHashMap<String, String>> dataMap) {
//        List<ImageStore> dataList = new ArrayList<ImageStore>();
//        for (LinkedHashMap<String, String> mapEntry : dataMap) {
//            ImageStore imageStore= new ImageStore();
//            imageStore.setImageUUID(mapEntry.get("imageUUID"));
//            dataList.add(imageStore);
//        }
//        return dataList;
//    }
//    public static List<LinkedHashMap<String, String>> convertProductMasterObjectToMap(List<ProductMaster> dataList) {
//
//        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
//        for (ProductMaster mapEntry : dataList) {
//            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
//            temp.put("upc", mapEntry.getUpc());
//            result.add(temp);
//        }
//        return result;
//    }
//
//    public static List<ProductMaster> convertProductMasterMapToObject( List<LinkedHashMap<String, String>> dataMap) {
//        List<ProductMaster> dataList = new ArrayList<ProductMaster>();
//        for (LinkedHashMap<String, String> mapEntry : dataMap) {
//            ProductMaster productMaster=  new ProductMaster();
//            productMaster.setUpc(mapEntry.get("upc"));
//            dataList.add(productMaster);
//        }
//        return dataList;
//    }
//    public static List<LinkedHashMap<String, String>> convertDistributionListObjectToMap(List<DistributionList> dataList) {
//
//        List<LinkedHashMap<String, String>> result = new ArrayList<LinkedHashMap<String, String>>();
//        for (DistributionList mapEntry : dataList) {
//            java.util.LinkedHashMap<String, String> temp = new java.util.LinkedHashMap<String, String>();
//            temp.put("upc", mapEntry.getUpc());
//            result.add(temp);
//        }
//        return result;
//    }
//
//    public static List<DistributionList> convertDistributionListMapToObject( List<LinkedHashMap<String, String>> dataMap) {
//        List<DistributionList> dataList = new ArrayList<DistributionList>();
//        for (LinkedHashMap<String, String> mapEntry : dataMap) {
//            DistributionList distributionList=  new DistributionList();
//            distributionList.setUpc(mapEntry.get("upc"));
//            dataList.add(distributionList);
//        }
//        return dataList;
//    }
}
