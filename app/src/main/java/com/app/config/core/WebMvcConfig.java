package com.app.config.core;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"com.app"})
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messagesResources");
		return source;
	}
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		/*
	    StringHttpMessageConverter must appear first in the list so that Spring has a chance to use
	     it for Spring RestController methods that return simple String. Otherwise, it will use
	      MappingJackson2HttpMessageConverter and clutter the response with escaped quotes and such
		 */
		converters.add(stringHttpMessageConverter);
		converters.add(createXmlHttpMessageconverter());
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper(); 
		objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
				WRITE_DATES_AS_TIMESTAMPS, false);
		// Other options such as how to deal with nulls or identing...
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		converter.setObjectMapper(objectMapper);
		converters.add(converter);
	}

	private HttpMessageConverter<?> createXmlHttpMessageconverter(){
		XStreamMarshaller marshaller = new XStreamMarshaller();
		MarshallingHttpMessageConverter marsllingConverter = new MarshallingHttpMessageConverter();

		marsllingConverter.setMarshaller(marshaller);
		marsllingConverter.setUnmarshaller(marshaller);
		return marsllingConverter;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/views/**").addResourceLocations("/", "/WEB-INF/views/").setCachePeriod(31556926);
	}

}
