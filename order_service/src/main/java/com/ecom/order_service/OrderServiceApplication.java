package com.ecom.order_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class OrderServiceApplication {

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void debugConfig() {
		System.out.println("=== CONFIGURATION DEBUG ===");
		System.out.println("Test property: " + env.getProperty("test-property"));
		System.out.println("Active profiles: " + String.join(", ", env.getActiveProfiles()));
		System.out.println("DB URL: " + env.getProperty("spring.datasource.url"));
		System.out.println("Server port: " + env.getProperty("server.port"));
	}
}