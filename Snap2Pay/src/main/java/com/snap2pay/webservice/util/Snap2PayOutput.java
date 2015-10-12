package com.snap2pay.webservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

import org.apache.log4j.Level;


@XmlType(propOrder={"metaDetail","row"})
@XmlRootElement(name="S2PResponse")
public class Snap2PayOutput{

	private MyHashMapType metaDetail = new MyHashMapType();
	private static Logger LOGGER = Logger.getLogger("s2p");

	@XmlElement(name="MetaInfo")
	public MyHashMapType getMetaDetail() {
		return metaDetail;
	}

	@XmlElementWrapper(name="ResultSet")
	private List<MyHashMapType> row = new ArrayList<MyHashMapType>();
	
	public void setMetaDetail( HashMap <String,String> inputMap) {
		
		String responseStatus ="SUCCESS";
		String responseMessage ="Request processed successfully";

		//if(columnList.equals("ERRORCODE,ERRORMSG"))
		Object obj = inputMap.get("responseCode");
		int returnStatus=-1;
		if(obj!=null)
		{
			returnStatus = new Integer(obj.toString());
		}	
		if (returnStatus == -2){
			responseStatus ="FAILURE";
			responseMessage ="This name is already in use. Please use a different name.";
		} else if (returnStatus == -3){
			responseStatus ="FAILURE";
			responseMessage ="This name is already in use. Please use a different name.";
		} else if(returnStatus<0)
		{
			responseStatus ="FAILURE";
			responseMessage ="An Internal Error Occured.";
		}

		inputMap.put("ResponseStatus",responseStatus);
		if(!inputMap.containsKey("ResponseMessage"))
		inputMap.put("ResponseMessage",responseMessage);
		metaDetail.setMapProperty(inputMap);
	}

	public List<MyHashMapType> getRow() {
		return row;
	}

	public void setRow(List<java.util.LinkedHashMap<String, String>> resultSet) {

		if(resultSet != null && !resultSet.isEmpty()){
			for (LinkedHashMap<String, String> myMap:resultSet)
			{
				MyHashMapType m = new MyHashMapType();
				m.setLinkedMapProperty(myMap);
				row.add(m);
			}
		} else
		{
			LinkedHashMap<String, String> myMap =new LinkedHashMap<String, String>();
			myMap.put("Message", "No Data Returned");
			MyHashMapType m = new MyHashMapType();
			m.setLinkedMapProperty(myMap);
			row.add(m);
		}

	}
	public Snap2PayOutput(List<java.util.LinkedHashMap<String, String>> resultSet, HashMap inputList) {
		LOGGER.error("Inside Snap2PayOutput");
		
		this.setRow(resultSet);
		this.setMetaDetail(inputList);
	}
	
	

	public Snap2PayOutput() {
		super();
		LOGGER.log(Level.DEBUG, "I reached default no-args constructor of Class Snap2PayOutput.");
	}
        
        
        



}

