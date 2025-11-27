package com.example.weather_app.service;

import org.springframework.stereotype.Service;

import com.example.weather_app.model.WeatherResponse;

@Service
public class ActivitySuggestionEngine {

    public static String getSuggestion(String activity, WeatherResponse weatherResponse) {

        double temp = weatherResponse.getMain().getTemp();
        double humidity = weatherResponse.getMain().getHumidity();
        double wind = weatherResponse.getWind().getSpeed();
        double rainProb = calculateRainProbability(temp,humidity, wind);

        activity = activity.toLowerCase();

        switch (activity) {

            case "cycling":
                return cyclingAdvice(temp, wind, humidity, rainProb);

            case "running":
                return runningAdvice(temp, humidity, rainProb);

            case "walking":
                return walkingAdvice(temp, wind, rainProb);

            case "office":
            case "commute":
                return officeAdvice(temp, humidity, rainProb);

            case "trekking":
            case "hiking":
                return trekkingAdvice(temp, wind, rainProb);

            case "gym":
                return gymAdvice(temp, humidity);

            case "outdoor sports":
            case "cricket":
            case "football":
                return sportsAdvice(temp, humidity);

            case "driving":
                return drivingAdvice(rainProb, wind);

            case "travelling":
                return travelAdvice(temp, rainProb);

            case "kids":
                return kidsAdvice(temp, wind, rainProb);

            case "elderly":
                return elderlyAdvice(temp, humidity);

            default:
                return "No activity selected — please choose one.";
        }
    }

    private static String cyclingAdvice(double temp, double wind, double humidity, double rain) {

        if (rain > 50) return "Rain likely — avoid cycling, roads may be slippery.";
        if (wind > 30) return "⚠ Strong winds — cycling can be dangerous.";
        
        StringBuilder s = new StringBuilder("Cycling Tips:\n");

        if (temp < 10) s.append("- Wear thermal base layer + gloves.\n");
        if (temp > 32) s.append("- Stay hydrated, avoid afternoon ride.\n");
        if (humidity > 75) s.append("- Humidity high — sweating will increase.\n");

        s.append("- Wind speed: " + wind + " m/s — adjust speed.\n");
        s.append("- Check brakes and tyre pressure before riding.");

        return s.toString();
    }

    private static String runningAdvice(double temp, double humidity, double rain) {

        if (rain > 60) return "Heavy rain expected — avoid running.";
        
        StringBuilder s = new StringBuilder("Running Tips:\n");

        if (temp < 8) s.append("- Wear warm layer, protect ears.\n");
        if (temp > 28) s.append("- Run early morning or late evening.\n");
        if (humidity > 80) s.append("- High humidity — expect more fatigue.\n");

        s.append("- Prefer breathable clothing.\n");

        return s.toString();
    }

    private static String walkingAdvice(double temp, double wind, double rain) {

        if (rain > 40) return "Carry umbrella — rain possible.";
        if (wind > 25) return "Strong winds — avoid long walks.";

        StringBuilder s = new StringBuilder("Walking Tips:\n");

        if (temp < 10) s.append("- Wear jacket.\n");
        if (temp > 30) s.append("- Carry water.\n");

        return s.toString();
    }

    private static String officeAdvice(double temp, double humidity, double rain) {

        StringBuilder s = new StringBuilder("Office Commute Tips:\n");

        if (rain > 50) s.append("- Carry umbrella or raincoat.\n");
        if (temp < 10) s.append("- Wear warm clothes.\n");
        if (humidity > 70) s.append("- Prefer cotton/light clothing.\n");

        return s.toString();
    }

    private static String trekkingAdvice(double temp, double wind, double rain) {

        if (rain > 40) return "Avoid trekking — trails get slippery.";
        if (wind > 20) return "High wind — risky at high altitude.";

        StringBuilder s = new StringBuilder("Trekking Tips:\n");

        if (temp < 5) s.append("- Very cold — wear thermals.\n");
        if (temp > 28) s.append("- Avoid midday trek.\n");

        s.append("- Carry hydration + energy bars.");

        return s.toString();
    }

    private static String gymAdvice(double temp, double humidity) {

        StringBuilder s = new StringBuilder("Gym Tips:\n");

        if (humidity > 75) s.append("- Sweat may feel sticky — carry towel.\n");
        if (temp > 30) s.append("- Hydrate well.\n");

        s.append("- Perfect for indoor workout.");

        return s.toString();
    }

    private static String sportsAdvice(double temp, double humidity) {

        StringBuilder s = new StringBuilder("Outdoor Sports Tips:\n");

        if (temp > 34) s.append("- Too hot, play in evening.\n");
        if (humidity > 65) s.append("- Humidity increases fatigue.\n");
        return s.toString();
    }

    private static String drivingAdvice(double rain, double wind) {

        if (rain > 60) return "⚠ Heavy rain expected — driving visibility may reduce.";
        if (wind > 30) return "⚠ Strong winds — drive carefully.";

        return "Driving conditions normal.";
    }

    private static String travelAdvice(double temp, double rain) {

        if (rain > 50) return "Rain may cause delays — keep buffer time.";

        StringBuilder s = new StringBuilder("Travel Tips:\n");

        if (temp < 10) s.append("- Pack warm layers.\n");
        if (temp > 30) s.append("- Stay hydrated.\n");

        return s.toString();
    }

    private static String kidsAdvice(double temp, double wind, double rain) {

        if (rain > 50) return "Keep kids indoors — rain expected.";
        if (wind > 25) return "Windy — avoid open parks.";

        StringBuilder s = new StringBuilder("Kids Safety:\n");

        if (temp < 12) s.append("- Warm clothing recommended.\n");
        if (temp > 32) s.append("- Avoid playing outdoors in afternoon.\n");

        return s.toString();
    }

    private static String elderlyAdvice(double temp, double humidity) {

        StringBuilder s = new StringBuilder("Elderly Safety:\n");

        if (temp < 15) s.append("- Keep warm, avoid early morning walks.\n");
        if (temp > 32) s.append("- Avoid heat — stay indoors.\n");
        if (humidity > 70) s.append("- Humidity may cause breathlessness.\n");

        return s.toString();
    }
    
    public static double calculateRainProbability(double temp, double humidity, double wind) {
        double probability = humidity * 0.7;
        if (temp < 20) {
            probability += (20 - temp) * 1.5; // 
        }
        probability -= wind * 0.5; // 

        if (probability < 0) probability = 0;
        if (probability > 100) probability = 100;

        return probability;
    }
}
