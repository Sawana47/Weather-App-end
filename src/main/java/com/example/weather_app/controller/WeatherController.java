package com.example.weather_app.controller;

import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.service.ActivitySuggestionEngine;
import com.example.weather_app.service.WeatherClothingSuggestion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    @Value("${api.key}")
    private String apiKey;

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        if (weatherResponse != null) {
            model.addAttribute("city", weatherResponse.getName());
            model.addAttribute("country", weatherResponse.getSys().getCountry());
            model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription());
            model.addAttribute("temperature", weatherResponse.getMain().getTemp());
            model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
            model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
            String weatherIcon = "wi wi-owm-" + weatherResponse.getWeather().get(0).getId();
            model.addAttribute("weatherIcon", weatherIcon);
            String weatherSuggestion = WeatherClothingSuggestion.
            		getClothingSuggestion(weatherResponse.getMain().getTemp(), weatherResponse.getWeather().get(0).getDescription(),
            				weatherResponse.getWind().getSpeed());
            model.addAttribute("weatherSuggestion", weatherSuggestion);
        } else {
            model.addAttribute("error", "City not found.");
        }

        return "weather";
    }
    
    @GetMapping("/activity-suggestion")
    @ResponseBody
    public String getActivitySuggestion(
            @RequestParam("city") String city,
            @RequestParam("activity") String activityName) {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        if (weatherResponse != null && activityName != null && !activityName.isEmpty()) {
            try {
                activityName = activityName.toUpperCase().replace(" ", "_");
                return ActivitySuggestionEngine.getSuggestion(activityName, weatherResponse);
            } catch (IllegalArgumentException e) {
                return "Activity not recognized.";
            }
        }
        return "Unable to fetch activity suggestion.";
    }

}
