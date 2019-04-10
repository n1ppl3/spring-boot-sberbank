package ru.pipan.boot2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Primary
@Component
public class OpenWeatherMapReactiveClient implements OpenWeatherMapClient {


	private final String appId;
	private final WebClient webClient;

	public OpenWeatherMapReactiveClient(WebClient.Builder webClientBuilder) {
		this.appId = APP_ID;
		this.webClient = webClientBuilder.baseUrl(BASE_URL)
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.build(); // TODO default timeouts
	}


	@Override
	public Double getCityTemperature(String cityName) {
		try {
			Mono<WeatherResponse> wr = getWeatherByCityName(cityName, DEFAULT_COUNTRY_CODE);
			return wr.block().getMain().getTemp();
		} catch (WebClientResponseException.NotFound hcce) {
			logger.warn("exception looking for '{}' city: {}", cityName, hcce.getLocalizedMessage());
			return null;
		}
	}


	public Mono<WeatherResponse> getWeatherByCityName(String cityName, String countryCode) {
		return webClient.get().uri("/weather?q={cityName},{countryCode}&APPID={appId}&units=metric", cityName, countryCode, appId)
			.retrieve()
			.bodyToMono(WeatherResponse.class);
	}

}
