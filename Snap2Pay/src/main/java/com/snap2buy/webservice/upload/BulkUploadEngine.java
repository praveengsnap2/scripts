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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.snap2buy.webservice.model.InputObject;
import com.snap2buy.webservice.service.ProcessImageService;

public class BulkUploadEngine implements Runnable {
    private static Logger LOGGER = Logger.getLogger("s2b");
    
	String customerCode;
	String customerProjectId;
	String sync;
	String filenamePath;
	String categoryId;
	ProcessImageService processImageService;
	
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
		DecimalFormat format = new DecimalFormat("0.#");
		long currTimestamp = System.currentTimeMillis() / 1000L;
		Date date = new Date(currTimestamp);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String currentDateId = dateFormat.format(date);

        //Create the directory for images to download.
        String imageDirectoryForProject = "/usr/share/s2pImages/" + customerCode + "/" + customerProjectId + "/";
		File imageDirectory = new File(imageDirectoryForProject);
		boolean success = imageDirectory.mkdirs();
        if (!success) {
			LOGGER.error("---------------BulkUploadEngine :: Failed to create directory :: " + imageDirectoryForProject + "----------------\n");
			LOGGER.error("---------------BulkUploadEngine :: Exitting----------------\n");
			return;
        }
        
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
			HSSFSheet imagesSheet = workbook.getSheet("Images");
			int numRows = imagesSheet.getPhysicalNumberOfRows();
			LOGGER.info("---------------BulkUploadEngine :: Number of records :: " + numRows + "----------------\n");
            
			for ( int i = 1 ; i < numRows; i++){
				LOGGER.info("---------------BulkUploadEngine :: Processing Record :: " + i + "----------------\n");
				HSSFRow row = imagesSheet.getRow(i);
				if ( row != null ) {
					String storeId = "";
					if ( row.getCell(0) != null ) {
						CellType type = row.getCell(0).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							storeId = format.format(row.getCell(0).getNumericCellValue());
						} else {
							storeId = row.getCell(0).getStringCellValue();
						}
					}
					
					//Check if storeId already in DB, if yes, skip to next row.
					if ( storeIdsInDB.contains(storeId) ) {
						LOGGER.info("---------------BulkUploadEngine :: storeId exists in DB ..Skipping this store:: " + storeId + "----------------\n");
						continue;
					}
					
					String agentId = "";
					if ( row.getCell(1) != null ) {
						CellType type = row.getCell(1).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							agentId = format.format(row.getCell(1).getNumericCellValue());
						} else {
							agentId = row.getCell(1).getStringCellValue();
						}
					}
					String ticketId = "";
					if ( row.getCell(2) != null ) {
						CellType type = row.getCell(2).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							ticketId = format.format(row.getCell(2).getNumericCellValue());
						} else {
							ticketId = row.getCell(2).getStringCellValue();
						}
					}
					String dateId = "";
					if ( row.getCell(3) != null ) {
						CellType type = row.getCell(3).getCellTypeEnum();
						if ( type == CellType.NUMERIC ) {
							dateId = format.format(row.getCell(3).getNumericCellValue());
						} else {
							dateId = row.getCell(3).getStringCellValue();
						}
					}
					String imageLink = "";
					if ( row.getCell(4) != null ) {
						if (row.getCell(4).getHyperlink() != null ) {
							imageLink = row.getCell(4).getHyperlink().getAddress();
						}
					}
					
					LOGGER.info("---------------BulkUploadEngine :: Record Data :: storeId=" + storeId + ", agentId=" + agentId +",taskId="+ticketId+",dateId="+dateId+"----------------\n");
					LOGGER.info("---------------BulkUploadEngine :: Accessing Image Download Link :: "+ imageLink + "----------------\n");
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
							} catch (Exception e) {
								LOGGER.error("---------------BulkUploadEngine :: Error creating URL for Image Download Link :: "+ e.getMessage() + "----------------\n");
								LOGGER.error("---------------BulkUploadEngine :: Proceeding with next image link----------------\n");
								continue;
							}
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
							if ( code == 200 ){
								UUID uniqueKey = UUID.randomUUID();
								
								String imageFilePath = imageDirectoryForProject + uniqueKey.toString().trim() + ".jpg";
								String imageThumbnailPath = imageDirectoryForProject + uniqueKey.toString().trim() + "-thm.jpg";
								try {
									InputStream is = imageURL.openStream();
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
			LOGGER.info("---------------BulkUploadEngine :: Completed Processing file :: " + filenamePath + "----------------\n");
		
		if ( workbook != null ) {
				try {
					workbook.close();
				} catch (IOException e) {
					LOGGER.error("---------------BulkUploadEngine :: Unexpected Exception while closing work book :: " + e.getMessage() + "----------------\n");

				}
			}
		
	}
}
