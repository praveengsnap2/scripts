package com.snap2buy.webservice.util;

import org.apache.log4j.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import java.util.*;

public class MyHashMapType {

    //public List<MyHashMapEntryType> column = new ArrayList<MyHashMapEntryType>();


    private static Logger LOGGER = Logger.getLogger("pbob");
    private java.util.LinkedHashMap<String, String> mapProperty;

    public MyHashMapType() {
        mapProperty = new LinkedHashMap<String, String>();
    }

    @XmlAnyElement
    public List<JAXBElement<String>> getMapProperty() {
        List<JAXBElement<String>> elements = new ArrayList<JAXBElement<String>>();
        for (Map.Entry<String, String> m : mapProperty.entrySet())
            elements.add(new JAXBElement(new javax.xml.namespace.QName(m.getKey().replace(' ', '_')),
                    String.class, m.getValue()));
        return elements;
    }

    public void setMapProperty(Map<String, String> map) {
        Set<String> c = map.keySet();
        Iterator<String> itr = c.iterator();
        while (itr.hasNext()) {

            String i = new String(itr.next());
            //	LOGGER.log("key="+i);
            Object o = map.get(i);
            if (o != null) {
                String value = o.toString();
                // 	LOGGER.log("value="+value);
                mapProperty.put(i, value);
            } else {
                mapProperty.put(i, "");
            }

        }


    }

    // Returns LinkedHashMap<String, String> , called from ReportInputOutput Class in toString() method
    public LinkedHashMap<String, String> returnMapProperty() {
        return mapProperty;
    }

    public void setLinkedMapProperty(java.util.LinkedHashMap<String, String> map) {


        LinkedHashMap<String, String> newLinkedHashMap = new LinkedHashMap<String, String>();
        //	LOGGER.trace("Entering setLinkedHashMap ");
        for (Map.Entry<String, String> me : map.entrySet()) {

            //		LOGGER.trace("Key Value Pair : " + me.toString());

            String key = me.getKey().toString().toUpperCase();


            if (me.getValue() != null) {
                Object o = me.getValue();
    /*			if(key.contains("SOURCE")|| key.contains("TYPE") || key.contains("DOMAIN"))
                { 	long startTime =  Calendar.getInstance().getTimeInMillis();
					mapProperty.put(me.getKey().toString(), Encoding.toHexString(o.toString()));
					long endTime =  Calendar.getInstance().getTimeInMillis();
					LOGGER.trace("Encoding Time Taken = " + (endTime-startTime) + " milliseconds");
				}
				else
		*/
                mapProperty.put(me.getKey().toString(), o.toString());
            } else {
                mapProperty.put(me.getKey().toString(), "");
            }
        }


//			LOGGER.trace("Exiting setLinkedHashMap ");
    }

    @Override
    public String toString() {
        return "MyHashMapType{" +
                "mapProperty=" + mapProperty +
                '}';
    }
}


