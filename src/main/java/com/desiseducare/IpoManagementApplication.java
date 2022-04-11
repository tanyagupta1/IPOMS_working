package com.desiseducare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This is the whose main function runs then entire spring boot application.
 * The annotation @SpringBootApplication starts the Spring application as a standalone application, runs the embedded servers and loads the beans.
 */
@SpringBootApplication
public class IpoManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(IpoManagementApplication.class, args);
	}





}
