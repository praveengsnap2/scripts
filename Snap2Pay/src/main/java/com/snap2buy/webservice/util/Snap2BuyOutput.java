package com.snap2buy.webservice.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@XmlType(propOrder = {"metaDetail", "row"})
@XmlRootElement(name = "S2PResponse")
public class Snap2BuyOutput {

    private static Logger LOGGER = Logger.getLogger("s2b");
    private MyHashMapType metaDetail = new MyHashMapType();
    @XmlElementWrapper(name = "ResultSet")
    private List<MyHashMapType> row = new ArrayList<MyHashMapType>();

    public Snap2BuyOutput(List<java.util.LinkedHashMap<String, String>> resultSet, HashMap inputList) {
        LOGGER.info("Inside Snap2BuyOutput");

        this.setRow(resultSet);
        this.setMetaDetail(inputList);
    }

//  @XmlElementWrapper(name = "DataSet")
//  private List<MyHashMapType> dataRow = new ArrayList<MyHashMapType>();
//
//  public List<MyHashMapType> getDataRow() {
//    return dataRow;
//  }
//
//  public void setDataRow(List<MyHashMapType> dataRow) {
//    if (dataRow != null && !dataRow.isEmpty()) {
//      for (LinkedHashMap<String, String> myMap : dataRow) {
//        MyHashMapType m = new MyHashMapType();
//        m.setLinkedMapProperty(myMap);
//        row.add(m);
//      }
//    } else {
//      LinkedHashMap<String, String> myMap = new LinkedHashMap<String, String>();
//      myMap.put("Message", "No Data Returned");
//      MyHashMapType m = new MyHashMapType();
//      m.setLinkedMapProperty(myMap);
//      row.add(m);
//    }
//  }

    public Snap2BuyOutput() {
        super();
        LOGGER.log(Level.DEBUG, "I reached default no-args constructor of Class Snap2BuyOutput.");
    }

    @XmlElement(name = "MetaInfo")
    public MyHashMapType getMetaDetail() {
        return metaDetail;
    }

    public void setMetaDetail(HashMap<String, String> inputMap) {
        metaDetail.setMapProperty(inputMap);
    }

    public List<MyHashMapType> getRow() {
        return row;
    }

//  public Snap2BuyOutput(List<java.util.LinkedHashMap<String, String>> resultSet, HashMap inputList, List<java.util.LinkedHashMap<String, Object>> dataSet) {
//    LOGGER.error("Inside Snap2BuyOutput");
//
//    this.setRow(resultSet);
//    this.se
//    this.setMetaDetail(inputList);
//  }
//

    public void setRow(List<java.util.LinkedHashMap<String, String>> resultSet) {

        if (resultSet != null && !resultSet.isEmpty()) {
            for (LinkedHashMap<String, String> myMap : resultSet) {
                MyHashMapType m = new MyHashMapType();
                m.setLinkedMapProperty(myMap);
                row.add(m);
            }
        } else {
            LinkedHashMap<String, String> myMap = new LinkedHashMap<String, String>();
            myMap.put("Message", "No Data Returned");
            MyHashMapType m = new MyHashMapType();
            m.setLinkedMapProperty(myMap);
            row.add(m);
        }

    }

    @Override
    public String toString() {
        return "Snap2BuyOutput{" +
                "metaDetail=" + metaDetail +
                ", row=" + row +
                '}';
    }
}

