package ru.pipan.boot2.client;

import lombok.Data;

@Data
public class WeatherResponse {

	private Main main;

	@Data
	public static class Main {
		private Double temp;
	}
}
