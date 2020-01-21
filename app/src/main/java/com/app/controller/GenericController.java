package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class GenericController {

	/** Logger available to subclasses. */
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	protected ResourceLoader resourceLoader;
	
	public String getPropValue(String key) throws IOException {
		Properties props = new Properties();
		try {
			Resource resource = resourceLoader.getResource("classpath:application.properties");
			props.load(resource.getInputStream());
			return props.getProperty(key);
		}catch(IOException ie) {
			throw ie;
		}
	}
}
