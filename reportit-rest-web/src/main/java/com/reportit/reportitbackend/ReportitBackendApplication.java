package com.reportit.reportitbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableMongoAuditing
@EnableSwagger2
@EnableAsync
public class ReportitBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportitBackendApplication.class, args);
	}

}
