package org.example.service.impl;


import org.example.client.CountryClient;
import org.example.client.WeatherClient;
import org.example.dto.AddressDTO;
import org.example.dto.Weather;
import org.example.dto.country.Country;
import org.example.entity.Address;
import org.example.exception.NotFoundException;
import org.example.repository.AddressRepository;
import org.example.service.AddressService;
import org.example.util.MapperUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${access_key}")
    private String accessKey;
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherClient weatherClient;
    private final CountryClient countryClient;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, WeatherClient weatherClient, CountryClient countryClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherClient = weatherClient;
        this.countryClient = countryClient;
    }

    @Override
    public List<AddressDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(address -> mapperUtil.convert(address, new AddressDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO findById(Long id) throws Exception {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        AddressDTO addressDTO = mapperUtil.convert(foundAddress, new AddressDTO());
        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));
        addressDTO.setFlag(countryFlag(addressDTO.getCountry()));
        return addressDTO;
    }

    private Integer retrieveTemperatureByCity(String city) {
        Weather weather = weatherClient.getWeather(accessKey, city);

        if (weather == null || weather.getCurrent() == null) {
            return null;
        }

        return weather.getCurrent().getTemperature();
    }

    private String countryFlag(String countryName) {
        List<Country> countryList = countryClient.getCountry(countryName);

        if (countryList.get(0) == null || countryList.get(0).getFlags() == null) {
            return null;
        }

        return countryList.get(0).getFlags().getPng();
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) throws Exception {

        addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) throws Exception {

        Optional<Address> foundAddress = addressRepository.findById(addressDTO.getId());

        if (foundAddress.isPresent()) {
            throw new Exception("Address Already Exists!");
        }

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

}