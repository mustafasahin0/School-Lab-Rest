package org.example.client;


import org.example.dto.country.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "https://restcountries.com/v3.1", name = "COUNTRY-FLAG")
public interface CountryClient {

    @GetMapping("/name/{country}")
    List<Country> getCountry(@RequestParam("country") String country);
}
