package ru.pipan.boot2.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.pipan.boot2.service.WeatherService;

@Slf4j
@RestController
@AllArgsConstructor
public class WeatherController {


	private final WeatherService weatherService;


	@GetMapping("/temperature/{cityName}")
	public ResponseEntity<?> getCityTemperature(@PathVariable("cityName") String cityName) {
		Double temperature = weatherService.getCityTemperature(cityName);
		logger.info("cityName/temperature = {}/{}", cityName, temperature);
		return ResponseEntity.ok(new TemperatureResponse(temperature));
	}


	@Data
	@AllArgsConstructor
	public static class TemperatureResponse {
		private Double temperature;
	}

}
