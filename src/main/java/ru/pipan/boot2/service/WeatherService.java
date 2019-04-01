package ru.pipan.boot2.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.pipan.boot2.client.OpenWeatherMapClient;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

	private final OpenWeatherMapClient openWeatherMapClient;

	public Double getCityTemperature(String cityName) {
		return openWeatherMapClient.getCityTemperature(cityName);
	}

}
