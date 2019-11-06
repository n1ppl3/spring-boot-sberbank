package ru.pipan.boot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	/**
	 * http://localhost:8181/temperature/london
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
