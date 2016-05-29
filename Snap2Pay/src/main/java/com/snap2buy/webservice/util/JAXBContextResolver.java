package com.snap2buy.webservice.util;

import com.sun.jersey.api.json.JSONJAXBContext;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//import com.snap2buy.webservice.rest.action.RestS2PAction;

@Component
@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {
    private JAXBContext context;

    private Class[] types = {Snap2PayOutput.class, MyHashMapType.class};

    public JAXBContextResolver() throws Exception {
        Map props = new HashMap<String, Object>();
        props.put(JSONJAXBContext.JSON_ARRAYS, new HashSet<String>(1) {
            {
                add("Row");
                add("row");
                add("MetaInfo");
            }
        });
        props.put(JSONJAXBContext.JSON_NOTATION,
                JSONJAXBContext.JSONNotation.MAPPED);
        // this.context = new
        // JSONJAXBContext(JSONConfiguration.natural().build(), types);
        this.context = new JSONJAXBContext(types, props);
    }

    public JAXBContext getContext(Class<?> aClass) {
        for (Class type : types) {
            if (type == aClass) {
                return context;
            }
        }
        return null;
        //return context;
    }
}
