package com.snap2buy.webservice.upload;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UploadStatusTracker {
	
	private UploadStatusTracker(){
		//private constructor to force singleton
	}
	
	private static final Map<String,String> UPLOAD_STATUS_MAP = new ConcurrentHashMap<String,String>();
	
	public static void add(String customerCode, String customerProjectId){
		UPLOAD_STATUS_MAP.put(customerCode+"_"+customerProjectId, "");
	}
	
	public static void remove(String customerCode, String customerProjectId){
		UPLOAD_STATUS_MAP.remove(customerCode+"_"+customerProjectId);
	}
	
	public static String get(String customerCode, String customerProjectId){
		if ( UPLOAD_STATUS_MAP.containsKey(customerCode+"_"+customerProjectId) ) {
			return "IN PROGRESS";
		} else {
			return "NOT IN PROGRESS";
		}
	}
}
