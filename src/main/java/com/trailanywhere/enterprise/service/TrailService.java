package com.trailanywhere.enterprise.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trailanywhere.enterprise.dao.ITrailDAO;
import com.trailanywhere.enterprise.dto.Trail;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Contains hardcoded data for unit testing.
 */
@Service
@NoArgsConstructor
public class TrailService implements ITrailService {

    private ITrailDAO trailDAO;

    // Initialize Logger
    private static final Logger logger = Logger.getLogger(TrailService.class.getName());

    /**
     * Constructor for unit testing with Mockito
     * @param trailDAO
     */
    @Autowired
    public TrailService(ITrailDAO trailDAO) {
        this.trailDAO = trailDAO;
    }


    /**
     * Fetches a trail with a specified name.
     * @param trailName - Name of the trail being searched for
     * @return - trail
     */
    @Override
    public Trail fetchByTrailName(String trailName) {
        return trailDAO.fetchByTrail(trailName);
    }

    /**
     * Add a new trail
     * @param trail - new trail
     */
    @Override
    public void addTrail(Trail trail) throws Exception {
        trailDAO.save(trail);
    }

    /**
     * Fetch all trails
     * @return - list of trails
     */
    @Override
    @CachePut(value="trails")
    public List<Trail> fetchAllTrails() {
        return trailDAO.fetchAllTrails();
    }

    /**
     * Fetches all trails by specified difficulty
     * @param difficulty - difficulty selected by user
     * @return - trails
     */
    @Override
    public List<Trail> fetchByDifficulty(String difficulty) {
        return trailDAO.fetchByDifficulty(difficulty);
    }

    /**
     * Fetches all trails with the same zip code
     * @param zipCode - zip code selected by user
     * @return - list containing all trails
     */
    @Override
    public List<Trail> fetchByZipCode(String zipCode) {
        return trailDAO.fetchByZipCode(zipCode);
    }

    /**
     * Fetches weather data with user provided zip code
     * @param zipCode - zip code selected by user
     * @return - JSON containing weather data
     */
    @Override
    @Cacheable("weatherByZipCode")
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
            logger.severe("Exception occurred while fetching weather by zip code: " + e.getMessage());
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
    @Cacheable("weather")
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
            logger.severe("Exception occurred while fetching current weather: " + e.getMessage());
        }
        return null;
    }

    /**
     * Return all trails with the same coordinates
     * @param latitude - provided latitude coordinate
     * @param longitude - provided longitude coordinate
     * @return - trails
     */
    @Override
    public Trail fetchByCoordinates(String latitude, String longitude) {
        return trailDAO.fetchByCoordinates(latitude, longitude);
    }

    /**
     * Saves a trail
     * @param trail - trail to be saved
     * @return - new trail
     * @throws Exception - handle errors
     */
    @Override
    public Trail save(Trail trail) throws Exception{
        return trailDAO.save(trail);
    }

    /**
     * Deletes a trail
     * @param trailID - trail to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int trailID) throws Exception {
        trailDAO.delete(trailID);
    }

    /**
     * Find a trail based on its ID
     * @param trailID - trail ID
     * @return - trail
     */
    @Override
    public Trail findTrailByID(int trailID) {
        return trailDAO.findTrailByID(trailID);
    }

    @Override
    public List<Trail> fetchByTrailType(String trailType) {
        return trailDAO.fetchByTrailType(trailType);
    }

    @Override
    public Set<String> fetchAllDifficultyTypes() {
        Set<String> allTrailDifficulties;
        allTrailDifficulties = new HashSet<>(trailDAO.fetchAllDifficultyTypes());
        return allTrailDifficulties;
    }

    public Set<String> fetchAllTrailTypes() {
        Set<String> allTrailTypes;
        allTrailTypes = new HashSet<>(trailDAO.fetchAllTrailTypes());
        return allTrailTypes;
    }

}
