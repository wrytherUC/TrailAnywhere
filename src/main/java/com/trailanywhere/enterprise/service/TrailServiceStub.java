package com.trailanywhere.enterprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trailanywhere.enterprise.dto.Trail;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * Contains hardcoded data for unit testing.
 */
@Component
public class TrailServiceStub implements ITrailService {
    /**
     * Fetches a trail with a specified name.
     * @param trailName - Name of the trail being searched for
     * @return - trail
     */
    @Override
    public Trail fetchByTrailName(String trailName) {
        Trail trail = new Trail();
        trail.setTrailID(1);
        trail.setName("Forrest Park");
        return trail;
    }

    /**
     * Fetches all trails by specified difficulty
     * @param difficulty - difficulty selected by user
     * @return - trails
     */
    @Override
    public ArrayList<Trail> fetchByDifficulty(String difficulty) {
        ArrayList<Trail> list = new ArrayList<>();
        Trail trailOne = new Trail();
        Trail trailTwo = new Trail();
        trailOne.setDifficulty(difficulty);
        trailTwo.setDifficulty(difficulty);
        list.add(trailOne);
        list.add(trailTwo);
        return list;
    }

    /**
     * Fetches all trails with the same zip code
     * @param zipCode - zip code selected by user
     * @return - list containing all trails
     */
    @Override
    public ArrayList<Trail> fetchByZipCode(String zipCode) {
        ArrayList<Trail> list = new ArrayList<>();
        Trail trailOne = new Trail();
        Trail trailTwo = new Trail();
        trailOne.setZipCode(zipCode);
        trailTwo.setZipCode(zipCode);
        list.add(trailOne);
        list.add(trailTwo);
        return list;
    }

    /**
     * Fetches weather data with user provided zip code
     * @param zipCode - zip code selected by user
     * @return - JSON containing weather data
     */
    @Override
    public JsonNode getCurrentWeatherByZipCode(String zipCode) {
        Trail trailOne = new Trail();
        trailOne.setZipCode(zipCode);

        // Build URL string
        String apiURL = "https://geocoding-api.open-meteo.com/v1/search?name=" + trailOne.getZipCode() + "&count=10&language=en&format=json";

        try {
            // Fetch data from API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(apiURL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Output JSON data
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body());
            trailOne.setLatitude(node.at("/results/0/latitude").asText());
            trailOne.setLongitude(node.at("/results/0/longitude").asText());
        } catch(Exception e) {
            e.printStackTrace();
        }

        return getCurrentWeather(trailOne.getLatitude(), trailOne.getLongitude());
    }

    /**
     * Fetches weather data with user provided coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - JSON containing weather data
     */
    @Override
    public JsonNode getCurrentWeather(String latitude, String longitude) {
        // Build URL string
        String apiURL = "https://api.open-meteo.com/v1/forecast";
        String options = "?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m,relativehumidity_2m,precipitation_probability&daily=sunset&current_weather=true&temperature_unit=fahrenheit&windspeed_unit=mph&precipitation_unit=inch&timezone=America%2FNew_York&forecast_days=1";
        String completeURL = apiURL + options;

        try {
            // Fetch data from API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create(completeURL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Output JSON data
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.body());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
