package com.cts.countrywebservice.controller;

import com.cts.countrywebservice.model.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final List<Country> countries = Stream.of(
            new Country("India", "IN", "New Delhi"),
            new Country("United States", "US", "Washington, D.C."),
            new Country("United Kingdom", "GB", "London"),
            new Country("Australia", "AU", "Canberra"),
            new Country("Canada", "CA", "Ottawa")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Country> getAllCountries() {
        return countries;
    }

    @GetMapping("/{code}")
    public Country getCountryByCode(@PathVariable String code) {
        return countries.stream()
                .filter(country -> country.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }
}
