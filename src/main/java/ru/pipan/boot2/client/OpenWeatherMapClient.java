package ru.pipan.boot2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Component
public class OpenWeatherMapClient {

	private static final String DEFAULT_COUNTRY_CODE = "uk";


	private final RestTemplate restTemplate;
	private final String appId = "c4b1ee5c227a93e3deb50c4b19cf203f";
// в идеале все настройки вынести в properties или yaml файлы
	public OpenWeatherMapClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder
			.rootUri("http://api.openweathermap.org/data/2.5")
			.setReadTimeout(Duration.ofSeconds(30))
			.setConnectTimeout(Duration.ofSeconds(30))
			.build();
	}


	public Double getCityTemperature(String cityName) {
		try {
			WeatherResponse wr = getWeatherByCityName(cityName, DEFAULT_COUNTRY_CODE);
			return wr.getMain().getTemp();
		} catch (HttpClientErrorException hcce) {
			logger.warn("exception looking for '{}' city: {}", cityName, hcce.getLocalizedMessage());
			return null;
		}
	}


	public WeatherResponse getWeatherByCityName(String cityName, String countryCode) {
		return restTemplate.getForEntity("/weather?q={cityName},{countryCode}&APPID={appId}&units=metric",
			WeatherResponse.class, cityName, countryCode, appId)
			.getBody();
	}

}
