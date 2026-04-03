package com.cityskate.cityskate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class StartupLogger {
    private static final Logger log = LoggerFactory.getLogger(StartupLogger.class);

    @EventListener(ApplicationReadyEvent.class)
    public void logSwaggerUrl() {
        log.info("Swagger UI: http://localhost:8080/CitySkate/swagger-ui/index.html");
    }
}

@SpringBootApplication
public class CityskateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityskateApplication.class, args);
	}

}
