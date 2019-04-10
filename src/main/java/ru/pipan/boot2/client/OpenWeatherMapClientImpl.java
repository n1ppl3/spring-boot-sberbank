package ru.pipan.boot2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Component
public class OpenWeatherMapClientImpl implements OpenWeatherMapClient {


	private final String appId;
	private final RestTemplate restTemplate;

	public OpenWeatherMapClientImpl(RestTemplateBuilder restTemplateBuilder) {
		this.appId = APP_ID;
		this.restTemplate = restTemplateBuilder.rootUri(BASE_URL)
			.setReadTimeout(Duration.ofSeconds(30))
			.setConnectTimeout(Duration.ofSeconds(30))
			.build();
	}


	@Override
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
