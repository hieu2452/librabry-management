package com.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableAsync
@EnableMethodSecurity
public class BookApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


	}
}
