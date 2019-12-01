package com.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

public class MessageConverter {

	public List<HttpMessageConverter<?>> getMessageConverters(){
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		
		XStreamMarshaller marshaller = new XStreamMarshaller();
		MarshallingHttpMessageConverter marshlingConverter = new MarshallingHttpMessageConverter(marshaller);
		messageConverters.add(marshlingConverter);
		
		return messageConverters;
		
	}
}
