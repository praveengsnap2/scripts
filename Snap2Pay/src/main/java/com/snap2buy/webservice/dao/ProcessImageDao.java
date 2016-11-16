package com.snap2buy.webservice.dao;

import com.snap2buy.webservice.model.DuplicateImages;
import com.snap2buy.webservice.model.ImageAnalysis;
import com.snap2buy.webservice.model.ImageStore;
import com.snap2buy.webservice.model.StoreWithImages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sachin on 10/17/15.
 */
public interface ProcessImageDao {

    public ImageStore findByImageUUId(String imageUUId);
    public ImageStore getImageByStatus(String shelfStatus);
    public Integer getJobCount(String shelfStatus);
    public Integer getCronJobCount();
    public String getImageAnalysisStatus(String imageUUID);
    public ImageStore getNextImageDetails();
    public void insert(ImageStore imageStore);
    public void storeImageAnalysis(List<ImageAnalysis> ImageAnalysisList,ImageStore imageStore);
    public void updateStatusAndHost(String hostId, String status, String imageUUID);
    public void updateImageAnalysisStatus(String status, String imageUUID);
    public void updateImageAnalysisHostId(String hostId, String imageUUID);
    public void updateOrientationDetails(ImageStore imageStore);
    public void updateShelfAnalysisStatus(String status, String imageUUID);
    public void updateStoreId(String storeId, String imageUUID);
    public List<LinkedHashMap<String,String>> getImages(String storeId, String dateId);
    public List<LinkedHashMap<String,String>> getProjectStoreImages(String customerCode, String customerProjectId, String storeId);   
    public List<ImageAnalysis> getImageAnalysis(String imageUUID);
    public LinkedHashMap<String,Object> getFacing(String imageUUID);
    public List<LinkedHashMap<String,String>> doShareOfShelfAnalysis(String getImageUUIDCsvString);
//    public List<LinkedHashMap<String,String>> doShareOfShelfAnalysisCsv(String getImageUUIDCsvString);
    public void updateLatLong(String imageUUID,String latitude,String longitude);

    public  List<LinkedHashMap<String, String>>  generateAggs(String customerCode, String customerProjectId, String storeId);
    public List<LinkedHashMap<String,String>>  getProjectStoreResults(String customerCode, String customerProjectId, String storeId);
    public List<LinkedHashMap<String,String>>  getProjectTopStores(String customerCode, String customerProjectId, String limit);
    public List<LinkedHashMap<String,String>>  getProjectBottomStores(String customerCode, String customerProjectId, String limit);
	public List<StoreWithImages> getProjectStoresWithNoUPCs(String customerCode, String customerProjectId);
	public List<StoreWithImages> getProjectAllStoreImages(String customerCode,String customerProjectId);
	public List<DuplicateImages> getProjectStoresWithDuplicateImages(String customerCode, String customerProjectId);
	public List<LinkedHashMap<String, String>> generateStoreResults(String customerCode, String customerProjectId, String storeId);
	public List<LinkedHashMap<String, String>> getProjectAllStoreResults(String customerCode, String customerProjectId);
	public void reprocessProjectByStore(String customerCode, String customerProjectId,List<String> storeIdsToReprocess);
	public List<String> getProjectStoreIds(String customerCode, String customerProjectId, boolean onlyDone);
	public List<LinkedHashMap<String, String>> getProjectAllStoreResultsDetail(String customerCode, String customerProjectId);
	public void updateProjectResultByStore(String customerCode,	String customerProjectId, List<String> storeIds, String resultCode);
	public void updateProjectResultStatusByStore(String customerCode,String customerProjectId, List<String> storeIds, String status);
	public List<String> getProjectStoreIds(String customerCode,	String customerProjectId);
	public List<LinkedHashMap<String, String>> insertOrUpdateStoreResult(String customerCode, String customerProjectId, String storeId,
			String countDistinctUpc, String sumFacing, String sumUpcConfidence,	String resultCode, String status, String imageUrl);
	public void saveRepResponses(String customerCode, String customerProjectId,	String storeId, Map<String, String> repResponses);
	Map<String, String> getRepResponsesByStore(String customerCode,	String customerProjectId, String storeId);
	public List<String> getProjectStoreIdsForRecompute(String customerCode,	String customerProjectId);

}
