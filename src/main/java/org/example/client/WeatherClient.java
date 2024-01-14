package org.example.client;


import feign.Param;
import org.example.dto.Weather;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url ="http://api.weatherstack.com", name = "WEATHER-CLIENT")
public interface WeatherClient {

    @GetMapping("/current")
    Weather getWeather(@RequestParam("access_key") String accessKey, @RequestParam("query") String cityName);
}
