package com.snap2pay.webservice.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.lang.Object;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.sun.jersey.json.impl.JSONMarshaller;


public class JAXBJsonize {

    public static String convertToJson(Object c) {
        StringWriter sw = null;
        try {
            JAXBContext context = JSONJAXBContext.newInstance(c.getClass());

            sw = new StringWriter();
            com.sun.jersey.json.impl.JSONMarshaller marshaller = new com.sun.jersey.json.impl.JSONMarshaller(context,
                    JSONConfiguration.DEFAULT);

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.setJsonEnabled(true);
            marshaller.marshal(c, sw);

        } catch (Exception e) {
            return e.getMessage();
        }

        return sw.toString();
    }

    public static Object convertToJavaObject(String jsonString, Class clazz) {

        Object out = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            InputStream is = new ByteArrayInputStream(jsonString.getBytes());

            out = mapper.readValue(is, clazz);

            return out;

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        return out;
    }

    public static Object convertToJavaObjectJAXB(String jsonString, Class clazz) {
        Object out = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            System.out.println("class=" + clazz);
            JSONObject obj = new JSONObject(jsonString.toString());
            Configuration config = new Configuration();
            MappedNamespaceConvention con = new MappedNamespaceConvention(config);
            XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(obj, con);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            out = unmarshaller.unmarshal(xmlStreamReader);
            System.out.println("jsonString=" + jsonString);
            System.out.println("classObject=" + out.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
