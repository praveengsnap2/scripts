package com.snap2pay.webservice.util;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.HashMap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MyHashMapType {

    //public List<MyHashMapEntryType> column = new ArrayList<MyHashMapEntryType>();


    private static Logger LOGGER = Logger.getLogger("pbob");
    private java.util.LinkedHashMap<String, String> mapProperty;

    @XmlAnyElement
    public List<JAXBElement<String>> getMapProperty() {
        List<JAXBElement<String>> elements = new ArrayList<JAXBElement<String>>();
        for (Map.Entry<String, String> m : mapProperty.entrySet())
            elements.add(new JAXBElement(new javax.xml.namespace.QName(m.getKey().replace(' ', '_')),
                    String.class, m.getValue()));
        return elements;
    }

    // Returns LinkedHashMap<String, String> , called from ReportInputOutput Class in toString() method
    public LinkedHashMap<String, String> returnMapProperty() {
        return mapProperty;
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


    public MyHashMapType() {
        mapProperty = new LinkedHashMap<String, String>();
    }
}


