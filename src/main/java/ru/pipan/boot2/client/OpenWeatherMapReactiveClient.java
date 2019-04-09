package ru.pipan.boot2.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OpenWeatherMapReactiveClient {

	private static final String DEFAULT_COUNTRY_CODE = "uk";


	private final WebClient webClient;
	private final String appId = "c4b1ee5c227a93e3deb50c4b19cf203f";
// в идеале все настройки вынести в properties или yaml файлы
	public OpenWeatherMapReactiveClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
			.baseUrl("http://api.openweathermap.org/data/2.5")
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.build(); // TODO default timeouts
	}


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
