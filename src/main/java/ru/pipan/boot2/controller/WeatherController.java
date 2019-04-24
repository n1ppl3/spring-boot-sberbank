package ru.pipan.boot2.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pipan.boot2.service.WeatherService;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Slf4j
@RestController
public class WeatherController {

	private static final ExecutorService wcExecutor = newFixedThreadPool(10, new CustomizableThreadFactory("wc-pool-"));


	private final WeatherService weatherService;

	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
		logger.warn("CONSTRUCTOR:    this.weatherService = {}; this.weatherService.getClass() = {}", this.weatherService, this.weatherService.getClass());
	}


	@PostConstruct
	public void myPostConstruct() {
		logger.warn("POST CONSTRUCT: this.weatherService = {}; this.weatherService.getClass() = {}", this.weatherService, this.weatherService.getClass());
	}


	@GetMapping("/temperature/{cityName}")
	public CompletableFuture<?> getCityTemperature(@PathVariable("cityName") String cityName) {
		return CompletableFuture.supplyAsync(() -> {
			Double temperature = weatherService.getCityTemperature(cityName);
			logger.info("cityName/temperature = {}/{}", cityName, temperature);
			return new TemperatureResponse(temperature);
		}, wcExecutor);
	}


	@Data
	@AllArgsConstructor
	public static class TemperatureResponse {
		private Double temperature;
	}

}
