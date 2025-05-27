package com.TestAPI_AMS.API_AMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiAmsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(ApiAmsApplication.class, args);
		System.out.println("TESTING");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApiAmsApplication.class);
	}
}
