package ru.pipan.boot2.client;

import lombok.Data;

@Data
class WeatherResponse {

	private Main main;

	@Data
	static class Main {
		private Double temp;
	}
}
