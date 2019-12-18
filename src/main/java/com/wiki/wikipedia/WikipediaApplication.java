package com.wiki.wikipedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WikipediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikipediaApplication.class, args);
	}

}
