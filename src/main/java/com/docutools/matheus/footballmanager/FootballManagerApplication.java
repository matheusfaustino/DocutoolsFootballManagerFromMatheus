package com.docutools.matheus.footballmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FootballManagerProperties.class)
public class FootballManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballManagerApplication.class, args);
	}

}
