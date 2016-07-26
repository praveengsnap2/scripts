package com.snap2buy.webservice.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomSnap2PayOutput {

    private List<Map<String,String>> MetaInfo = new ArrayList<Map<String,String>>();
    private ResultSet ResultSet = new ResultSet();
    
    public CustomSnap2PayOutput(List<?> rows, List<Map<String,String>> inputList) {
        this.getResultSet().setRow(rows);
        this.setMetaInfo(inputList);
    }

   	public List<Map<String,String>> getMetaInfo() {
		return MetaInfo;
	}

	public void setMetaInfo(List<Map<String,String>> metaInfo) {
		MetaInfo = metaInfo;
	}

	public ResultSet getResultSet() {
		return ResultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		ResultSet = resultSet;
	}
}

