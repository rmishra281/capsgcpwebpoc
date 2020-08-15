package com.gcpweb.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.gcpweb.*")
@SpringBootApplication
public class CapsgcpwebpocApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapsgcpwebpocApplication.class, args);
	}

}
