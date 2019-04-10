package ru.pipan.boot2.client;


public interface OpenWeatherMapClient {

	String DEFAULT_COUNTRY_CODE = "uk";

	// в идеале все настройки вынести в properties или yaml файлы
	String APP_ID = "c4b1ee5c227a93e3deb50c4b19cf203f";
	String BASE_URL = "http://api.openweathermap.org/data/2.5";

	Double getCityTemperature(String cityName);
}
