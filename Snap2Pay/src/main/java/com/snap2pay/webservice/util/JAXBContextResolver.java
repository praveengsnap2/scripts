package com.snap2pay.webservice.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import com.snap2pay.webservice.util.Snap2PayOutput;
//import com.snap2pay.webservice.rest.action.RestS2PAction;



import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.springframework.stereotype.Component;

@Component
@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {
	private JAXBContext context;

	private Class[] types = {Snap2PayOutput.class, MyHashMapType.class};

	@SuppressWarnings({ "unchecked", "deprecation" })
	public JAXBContextResolver() throws Exception {
		Map props = new HashMap<String, Object>();
		props.put(JSONJAXBContext.JSON_ARRAYS, new HashSet<String>(1) {
			{
				add("Row");
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
