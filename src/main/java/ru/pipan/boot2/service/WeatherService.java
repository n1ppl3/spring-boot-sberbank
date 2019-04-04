package ru.pipan.boot2.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pipan.boot2.client.OpenWeatherMapClient;
import ru.pipan.boot2.entity.CacheRecord;
import ru.pipan.boot2.repository.CacheRecordRepository;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

	private final OpenWeatherMapClient openWeatherMapClient;
	private final CacheRecordRepository cacheRecordRepository;

	@Transactional
	public Double getCityTemperature(@NonNull String cityName) {
		cityName = cityName.toLowerCase();
		CacheRecord recordInDb = cacheRecordRepository.findByCityName(cityName);
		if (recordInDb != null) {
			logger.info("Found '{}' in DB!", cityName);
			return recordInDb.getTemperature();
		}
		Double result = openWeatherMapClient.getCityTemperature(cityName);
		cacheRecordRepository.save(new CacheRecord(cityName, result));
		return result;
	}

}
