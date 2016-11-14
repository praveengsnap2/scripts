package com.snap2buy.webservice.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.CellType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.snap2buy.webservice.dao.ProcessImageDao;
import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.model.ProjectQuestion;
import com.snap2buy.webservice.service.ProcessImageService;

public class BulkUploadEngine implements Runnable {
    private static Logger LOGGER = Logger.getLogger("s2b");
    
	private String customerCode;
	private String customerProjectId;
	private String sync;
	private String filenamePath;
	private String categoryId;
	private String retailerCode;
	private List<ProjectQuestion> projectQuestions;
	private ProcessImageService processImageService;
	private ProcessImageDao processImageDao;
	
	public ProcessImageDao getProcessImageDao() {
		return processImageDao;
	}
	public void setProcessImageDao(ProcessImageDao processImageDao) {
		this.processImageDao = processImageDao;
	}
	public ProcessImageService getProcessImageService() {
		return processImageService;
	}
	public void setProcessImageService(ProcessImageService processImageService) {
		this.processImageService = processImageService;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerProjectId() {
		return customerProjectId;
	}
	public void setCustomerProjectId(String customerProjectId) {
		this.customerProjectId = customerProjectId;
	}
	public String getSync() {
		return sync;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
	public String getFilenamePath() {
		return filenamePath;
	}
	public void setFilenamePath(String filenamePath) {
		this.filenamePath = filenamePath;
	}
	
	@Override
	public void run() {
		LOGGER.info("---------------BulkUploadEngine :: Start Processing file :: " + filenamePath + "----------------\n");
		//Add this project status as IN PROGRESS in tracker
		UploadStatusTracker.add(customerCode, customerProjectId);
		//Proceed
		DecimalFormat format = new DecimalFormat("0.#");
		long currTimestamp = System.currentTimeMillis() / 1000L;
		Date date = new Date(currTimestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String currentDateId = dateFormat.format(date);

        //Create the directory for images to download.
        String imageDirectoryForProject = "/usr/share/s2pImages/" + customerCode + "/" + customerProjectId + "/";
        
		File imageDirectory = new File(imageDirectoryForProject);
		imageDirectory.mkdirs();
                
        //Get already uploaded stores for this project, to detect rows to be skipped.
        List<String> storeIdsInDB = processImageService.getProjectStoreIds(customerCode,customerProjectId);
        
        boolean continueProcessing = true;
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(filenamePath));
		} catch ( Exception e ){
			LOGGER.error("---------------BulkUploadEngine :: Failed to open the workbook----------------\n");
			continueProcessing = false;
		}
		
		if ( continueProcessing == true ) {
			String imageSheetName = "Images";
			String internalCustomerCode = customerCode;
			String storeIdColumn = "";
			String agentIdColumn = "";
			String taskIdColumn = "";
			String dateIdColumn = "";
			List<String> imageLinkColumns = new ArrayList<String>();
			Map<String,String> repResponseColumnMap = new HashMap<String,String>();
			
			HSSFSheet metaDataSheet = workbook.getSheet("metaDataSheet");
			int numRows = metaDataSheet.getPhysicalNumberOfRows();
			for ( int i=0; i < numRows; i++){
				HSSFRow row = metaDataSheet.getRow(i);
				if ( row != null ) {
					String column = "";
					if ( row.getCell(0) != null ) {
						CellType type = row.getCell(0).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							column = format.format(row.getCell(0).getNumericCellValue());
						} else {
							column = row.getCell(0).getStringCellValue();
						}
					}
					column = column.trim();
					String value = "";
					if ( row.getCell(1) != null ) {
						CellType type = row.getCell(1).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							value = format.format(row.getCell(1).getNumericCellValue());
						} else {
							value = row.getCell(1).getStringCellValue();
						}
					}
					value = value.trim();
					
					if ( column.startsWith("imageSheet") ) {
						imageSheetName = value;
					} else if ( column.startsWith("internalCustomerCode") ) {
						internalCustomerCode = value;
					} else if ( column.startsWith("retailerStoreId")) {
						storeIdColumn = value;
					} else if ( column.startsWith("agentId")) {
						agentIdColumn = value;
					} else if ( column.startsWith("taskId")) {
						taskIdColumn = value;
					} else if ( column.startsWith("dateId")) {
						dateIdColumn = value;
					} else if ( column.startsWith("photo")) {
						imageLinkColumns.add(value);
					} else if ( column.startsWith("response")) {
						String respQuestionId = column.replace("response", "").trim();
						repResponseColumnMap.put(respQuestionId,value);
					}
				}
			}
			
			HSSFSheet imagesSheet = workbook.getSheet(imageSheetName);
			numRows = imagesSheet.getPhysicalNumberOfRows();
			LOGGER.info("---------------BulkUploadEngine :: Number of records :: " + numRows + "----------------\n");
            
			for ( int i = 1 ; i < numRows; i++){
				LOGGER.info("---------------BulkUploadEngine :: Processing Record :: " + i + "----------------\n");
				HSSFRow row = imagesSheet.getRow(i);
				if ( row != null ) {
					String storeId = "";
					CellReference storeCell = new CellReference(storeIdColumn);
					if ( row.getCell(storeCell.getCol()) != null ) {
						CellType type = row.getCell(storeCell.getCol()).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							storeId = format.format(row.getCell(storeCell.getCol()).getNumericCellValue());
						} else {
							storeId = row.getCell(storeCell.getCol()).getStringCellValue();
						}
					}
					String storeIdWithRetailCode = retailerCode+"_"+storeId;
					//Check if storeId already in DB, if yes, skip to next row.
					if ( storeIdsInDB.contains(storeIdWithRetailCode) ) {
						LOGGER.info("---------------BulkUploadEngine :: storeId exists in DB ..Skipping this store:: " + storeIdWithRetailCode + "----------------\n");
						continue;
					}
					
					String agentId = "";
					CellReference agentCell = new CellReference(agentIdColumn);
					if ( row.getCell(agentCell.getCol()) != null ) {
						CellType type = row.getCell(agentCell.getCol()).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							agentId = format.format(row.getCell(agentCell.getCol()).getNumericCellValue());
						} else {
							agentId = row.getCell(agentCell.getCol()).getStringCellValue();
						}
					}
					String ticketId = "";
					CellReference taskCell = new CellReference(taskIdColumn);
					if ( row.getCell(taskCell.getCol()) != null ) {
						CellType type = row.getCell(taskCell.getCol()).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							ticketId = format.format(row.getCell(taskCell.getCol()).getNumericCellValue());
						} else {
							ticketId = row.getCell(taskCell.getCol()).getStringCellValue();
						}
					}
					String dateId = "";
					CellReference dateCell = new CellReference(dateIdColumn);
					if ( row.getCell(dateCell.getCol()) != null ) {
						CellType type = row.getCell(dateCell.getCol()).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							dateId = format.format(row.getCell(dateCell.getCol()).getNumericCellValue());
						} else {
							dateId = row.getCell(dateCell.getCol()).getStringCellValue();
						}
					}
					List<String> imageLinkList = new ArrayList<String>();
					for ( String imageColumn : imageLinkColumns ) {
						CellReference imageCell = new CellReference(imageColumn);
						if ( row.getCell(imageCell.getCol()) != null ) {
							if (row.getCell(imageCell.getCol()).getHyperlink() != null ) {
								imageLinkList.add(row.getCell(imageCell.getCol()).getHyperlink().getAddress());
							}
						}						
					}
					
					LOGGER.info("---------------BulkUploadEngine :: Record Data :: storeId=" + storeIdWithRetailCode + ", agentId=" + agentId +",taskId="+ticketId+",dateId="+dateId+"----------------\n");
					
					//Make an entry in project store results table for this store
					try {
						processImageDao.insertOrUpdateStoreResult(customerCode, customerProjectId, storeIdWithRetailCode, "0", "0", "0", "0", "1", (imageLinkList.isEmpty() ? "" : imageLinkList.get(0)) );
						LOGGER.info("---------------BulkUploadEngine :: Inserted one record in store results table for this store...\n");
					} catch (Throwable t) {
						LOGGER.info("---------------BulkUploadEngine :: Error inserting one record in store results table for this store..." + t + "\n");
					}
					
					LOGGER.info("---------------BulkUploadEngine :: Storing Rep Responses to DB Start :: Responses Expected :: " + projectQuestions.size() + " ----------------\n");
					//Store project rep responses for each store
					Map<String,String> repResponses = new HashMap<String,String>();
					for(String repResponseQuestionId : repResponseColumnMap.keySet()) {
						String responseColumn = repResponseColumnMap.get(repResponseQuestionId);
						if ( responseColumn != null && !responseColumn.isEmpty() ) {
							CellReference cr = new CellReference(responseColumn);
							if (row.getCell(cr.getCol()) != null) {
								String repResponse = "";
								CellType type = row.getCell(cr.getCol()).getCellTypeEnum();
								if (type == CellType.NUMERIC) {
									repResponse = format.format(row.getCell(cr.getCol()).getNumericCellValue());
								} else {
									repResponse = row.getCell(cr.getCol()).getStringCellValue();
								}
								repResponses.put(repResponseQuestionId, repResponse);	
							}
						}
					}
					try {
						processImageDao.saveRepResponses(customerCode,customerProjectId, storeIdWithRetailCode, repResponses);
						LOGGER.info("---------------BulkUploadEngine :: Storing Rep Responses to DB End----------------\n");
					} catch (Throwable t) {
						LOGGER.error("---------------BulkUploadEngine :: Storing Rep Responses to DB Failed :: " + t + "----------------\n");
					}
					
					//For CMK, we need to scrape the imageLink for actual images
					//For PRM, imageLink can be used as-is for download
					List<URL> imageURLs = new ArrayList<URL>();
					for ( String imageLink : imageLinkList ) {
						if ( customerCode.equals("CMK") || ( customerCode.equals("DEM") && internalCustomerCode.equals("CMK") ) ) {
							Document document = null;
							try {
								document = Jsoup.connect(imageLink).get();
							} catch (Exception e) {
								LOGGER.error("---------------BulkUploadEngine :: Error Accessing image store link :: "+ e.getMessage() + "----------------\n");
								LOGGER.error("---------------BulkUploadEngine :: Proceeding with next row----------------\n");
								continue;
							}
							Elements elements = document.select("img[src^=https://gigwalk]");
							LOGGER.info("---------------BulkUploadEngine :: Number of images to download :: "+ elements.size() + "----------------\n");
							for ( int j=0;j<elements.size();j++){
								URL imageURL = null;
								try {
									imageURL = new URL(elements.get(j).attr("src"));
									imageURLs.add(imageURL);
								} catch (Exception e) {
									LOGGER.error("---------------BulkUploadEngine :: Error creating URL for Image Download Link :: "+ e.getMessage() + "----------------\n");
									LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image link----------------\n");
									continue;
								}
							}
							LOGGER.info("---------------BulkUploadEngine :: Accessing Image Download Link :: "+ imageLink + "----------------\n");
						} else {
							URL imageURL = null;
							try {
								imageURL = new URL(imageLink);
								imageURLs.add(imageURL);
							} catch (Exception e) {
								LOGGER.error("---------------BulkUploadEngine :: Error creating URL for Image Download Link :: "+ e.getMessage() + "----------------\n");
								LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image link----------------\n");
								continue;
							}
						}
					}
					//now iterate over all imageURLs, download the image and saveImage in tables.
						for ( URL imageURL : imageURLs) {
							
							LOGGER.info("---------------BulkUploadEngine :: Downloading from :: "+ imageURL.toString() + "----------------\n");
							
							int code = 200;
							try {
								HttpURLConnection huc =  ( HttpURLConnection )  imageURL.openConnection (); 
								huc.setRequestMethod ("GET"); 
								huc.connect() ; 
								code = huc.getResponseCode();
							} catch (Exception e) {
								LOGGER.error("---------------BulkUploadEngine :: Error checking URL for Image Download Link :: "+ e.getMessage() + "----------------\n");
								LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image link----------------\n");
								continue;
							} 
							if ( code == 200 || code == 303 ){
								URL downloadURL = imageURL;
								if ( code == 303 ) {
									String downloadLink = imageURL.toExternalForm();
									downloadLink = downloadLink.replace("http:", "https:");
									try {
										downloadURL = new URL(downloadLink);
									} catch (Exception e) {
										LOGGER.error("---------------BulkUploadEngine :: Error creating URL for Image Download Link :: "+ e.getMessage() + "----------------\n");
										LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image----------------\n");
										continue;
									}
								}
								UUID uniqueKey = UUID.randomUUID();
								
								String imageFilePath = imageDirectoryForProject + uniqueKey.toString().trim() + ".jpg";
								String imageThumbnailPath = imageDirectoryForProject + uniqueKey.toString().trim() + "-thm.jpg";
								try {
									InputStream is = downloadURL.openStream();
									OutputStream os = new FileOutputStream(imageFilePath);
									byte[] b = new byte[2048];
									int length;
									while ((length = is.read(b)) != -1) {
										os.write(b, 0, length);
									}
									is.close();
									os.close();
								} catch (Exception e) {
									LOGGER.error("---------------BulkUploadEngine :: Error downoading from Image Download Link :: "+ e.getMessage() + "----------------\n");
									LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image link----------------\n");
									continue;
								} 
								File file = new File(imageFilePath);
								if (!file.exists()) {
									file.getParentFile().mkdirs();
									file.getParentFile().setReadable(true);
									file.getParentFile().setWritable(true);
									file.getParentFile().setExecutable(true);
			                    }
								if ( file.length() > 0L ) {
									LOGGER.info("---------------BulkUploadEngine :: Download Successful :: Storing to DB----------------\n");
									InputObject inputObject = new InputObject();
							        if (!dateId.isEmpty()){
						                inputObject.setVisitDate(dateId);
						            } else {
						                inputObject.setVisitDate(currentDateId);
						            } 
							            inputObject.setStoreId(storeId);
							            inputObject.setHostId("1");
							            inputObject.setImageUUID(uniqueKey.toString().trim());
							            inputObject.setCategoryId(categoryId.trim());
							            inputObject.setLatitude("");
							            inputObject.setLongitude("");
							            inputObject.setTimeStamp(""+currTimestamp);
							            inputObject.setUserId("web");
							            inputObject.setSync(sync);
							            inputObject.setAgentId(agentId);
							            inputObject.setCustomerCode(customerCode);
							            inputObject.setCustomerProjectId(customerProjectId);
							            inputObject.setTaskId(ticketId);
							            inputObject.setImageHashScore("0");
							            inputObject.setImageRotation("0");
							            inputObject.setOrigWidth("0");
					                    inputObject.setOrigHeight("0");
					                    inputObject.setNewWidth("0");
					                    inputObject.setNewHeight("0");
					                    inputObject.setImageFilePath(imageFilePath);
					                    inputObject.setThumbnailPath(imageThumbnailPath);
										LOGGER.info("---------------BulkUploadEngine :: Storing Image details to DB Start----------------\n");
					                    processImageService.storeImageDetails(inputObject);
										LOGGER.info("---------------BulkUploadEngine :: Storing Image details to DB End----------------\n");
								} else {
									LOGGER.error("---------------BulkUploadEngine :: Download Failed :: 0 byte file :: "+ imageFilePath + "----------------\n");
								}
							} else {
								LOGGER.error("---------------BulkUploadEngine :: Download Failed :: Unexpected HTTP response code"+ code + "----------------\n");
							}
						}
					}	
				}
			}
		
		if ( workbook != null ) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOGGER.error("---------------BulkUploadEngine :: Unexpected Exception while closing work book :: " + e.getMessage() + "----------------\n");

				}
			}
		
		//Remove this project from upload tracker
		UploadStatusTracker.remove(customerCode, customerProjectId);
		//Done
		LOGGER.info("---------------BulkUploadEngine :: Completed Processing file :: " + filenamePath + "----------------\n");

	}
	public List<ProjectQuestion> getProjectQuestions() {
		return projectQuestions;
	}
	public void setProjectQuestions(List<ProjectQuestion> projectQuestions) {
		this.projectQuestions = projectQuestions;
	}
	public String getRetailerCode() {
		return retailerCode;
	}
	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}
	
}
