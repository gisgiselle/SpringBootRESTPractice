package com.rest.webservices.restexample;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/hello" )
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("HelloWorld Bean");
	}
	
	@GetMapping(path = "/hello/path-variable/{name}" )
	public HelloWorldBean helloWorldBeanVar(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	@GetMapping(path = "/hello/international" )
	public String helloInternationalized(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		
		
		return messageSource
				.getMessage("good.morning.message", null, "Hello default", LocaleContextHolder.getLocale());
	}
	

}
