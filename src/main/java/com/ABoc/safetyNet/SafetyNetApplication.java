package com.ABoc.safetyNet;

import model.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.DataService;

@SpringBootApplication
public class SafetyNetApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application démarrée!");
		DataService data = new DataService();
	}
}