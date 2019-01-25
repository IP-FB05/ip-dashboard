package de.fhaachen.ipdashboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import de.fhaachen.ipdashboard.storage.StorageProperties;
import de.fhaachen.ipdashboard.storage.StorageService;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class IpdashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpdashboardApplication.class, args);
	}

	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            //storageService.deleteAll();
            storageService.loadAll();
            storageService.init();
        };
    }

}

