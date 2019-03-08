package com.reportit.reportitbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ReportitBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportitBackendApplication.class, args);
	}

}
