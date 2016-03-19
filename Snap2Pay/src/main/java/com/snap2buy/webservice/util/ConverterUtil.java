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
            result.add(temp);
        }
        return result;
    }

    public static List<ImageAnalysis> convertImageAnalysisMapToObject( List<LinkedHashMap<String, String>> dataMap) {
        List<ImageAnalysis> dataList = new ArrayList<ImageAnalysis>();
        for (LinkedHashMap<String, String> mapEntry : dataMap) {
            ImageAnalysis listEntry = new ImageAnalysis();
            listEntry.setUpc(mapEntry.get("Upc"));
            listEntry.setLeftTopX(mapEntry.get("LeftTopX"));
            listEntry.setLeftTopY(mapEntry.get("LeftTopY"));
            listEntry.setWidth(mapEntry.get("Width"));
            listEntry.setHeight(mapEntry.get("Height"));
            dataList.add(listEntry);
        }
        return dataList;
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