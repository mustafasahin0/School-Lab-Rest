package org.example.client;


import feign.Param;
import org.example.dto.Weather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url ="http://api.weatherstack.com", name = "WEATHER-CLIENT")
public interface WeatherClient {

    @GetMapping("/current")
    Weather getWeather(@Param("access_key") String accessKey, @Param("query") String cityName);
}
