package com.myapp.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication //(exclude = { SecurityAutoConfiguration.class,  ManagementWebSecurityAutoConfiguration.class })
@ComponentScan
public class SpringBootProductsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProductsDemoApplication.class, args);
	}

	 @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
