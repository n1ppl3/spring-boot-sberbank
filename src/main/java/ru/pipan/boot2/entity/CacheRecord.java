package ru.pipan.boot2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Data
@Entity
@NoArgsConstructor
public class CacheRecord {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String cityName;
	private Double temperature;
	private ZonedDateTime creationTime;

	public CacheRecord(String cityName, Double temperature) {
		this.cityName = cityName;
		this.temperature = temperature;
		this.creationTime = ZonedDateTime.now();
	}

}
