package com.app.controller;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class GenericController {

	/** Logger available to subclasses. */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
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
