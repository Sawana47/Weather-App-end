package com.example.weather_app.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherClothingSuggestion {

    public static String getClothingSuggestion(double temperatureC, String description, double windSpeed) {
        StringBuilder suggestion = new StringBuilder();

        if (temperatureC < 0) {
            suggestion.append("Wear a heavy winter coat, gloves, hat, scarf, and thermal layers.");
        } else if (temperatureC >= 0 && temperatureC < 10) {
            suggestion.append("Wear a warm jacket, sweater, long pants, and closed shoes.");
        } else if (temperatureC >= 10 && temperatureC < 20) {
            suggestion.append("Wear a light jacket or sweater, long sleeves, and jeans.");
        } else if (temperatureC >= 20 && temperatureC < 30) {
            suggestion.append("Wear a T-shirt, shorts or light pants, and sunglasses.");
        } else {
            suggestion.append("Wear light, breathable clothing, hats, and sunscreen.");
        }

        if (description.toLowerCase().contains("rain")) {
            suggestion.append(" Also, carry a waterproof jacket, waterproof shoes, and an umbrella.");
        }

        if (description.toLowerCase().contains("smoke")) {
            suggestion.append(" Wear a mask and prefer breathable fabrics.");
        }

        if (windSpeed > 10 && temperatureC < 20) {
            suggestion.append(" Additionally, wear a windbreaker or windproof jacket.");
        }

        return suggestion.toString();
    }

 
}

