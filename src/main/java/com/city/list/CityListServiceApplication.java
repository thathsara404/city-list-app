package com.city.list;

import com.city.list.util.ResourceLoaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CityListServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityListServiceApplication.class, args);
	}

}
