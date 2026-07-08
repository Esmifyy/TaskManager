package com.esma.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @Configuration
// @EnableAutoConfiguration
// @ComponentScan
// Alles von den oben ist in SpringBootApplication drinnen (zusammengefasst)
public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

}
