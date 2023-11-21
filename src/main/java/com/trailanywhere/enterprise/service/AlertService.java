package com.trailanywhere.enterprise.service;

import com.trailanywhere.enterprise.dao.IAlertDAO;
import com.trailanywhere.enterprise.dto.Alert;
import com.trailanywhere.enterprise.dto.Trail;
import com.trailanywhere.enterprise.dto.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.net.URL;
import java.util.Map;

/**
 * Contain mock data for IAlertService
 */
@Service
@NoArgsConstructor
public class AlertService implements IAlertService {
    ArrayList<Alert> allAlerts = new ArrayList<>();

    @Autowired
    private IAlertDAO alertDAO;

    @Autowired
    TrailService trailService;
    @Autowired
    UserService userService;

    static Map<String, String[]> words = new HashMap<>();

    static int largestWordLength = 0;

    /**
     * Constructor using DAO
     * @param alertDAO - DAO
     */
    public AlertService(IAlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    /**
     * Fetch all alerts
     * @return - list of alerts
     */
    @Override
    public List<Alert> fetchAllAlerts() {
        return alertDAO.fetchAllAlerts();
    }

    /**
     * Add an alert in AlertTests class
     * @param alert - new alert
     */
    @Override
    public void addAlert(Alert alert) {
        allAlerts.add(alert);
    }

    /**
     * Create a new alert
     * @param trailID - trail ID, passed from the controller, to associate the alert with
     * @param userID - user ID, passed from the controller, to associate the alert with
     * @param alertText - Passed from the controller, describes what the alert is
     * @return - alert object
     * @throws Exception - handle errors
     */
    @Override
    public Alert save(int trailID, int userID, String alertText) throws Exception {

        Alert alert = new Alert();

        User user = userService.findUserByID(userID);
        alert.setUser(user);

        Trail trail = trailService.findTrailByID(trailID);
        alert.setTrail(trail);

        loadConfigs();
        String filteredAlertText = filterText(alertText, user.getName());
        alert.setAlertText(filteredAlertText);

        return alertDAO.save(alert);
    }

    /**
     * Delete an alert
     * @param alertID - alert to be deleted
     * @throws Exception - handle errors
     */
    @Override
    public void delete(int alertID) throws Exception {
        alertDAO.delete(alertID);
    }

    /**
     * Find alert based on its ID
     * @param alertID - alert ID
     * @return - alert
     */
    @Override
    public Alert findAlertByID(int alertID) {
        return alertDAO.findAlertByID(alertID);
    }

    /**
     * Find alerts for a trail
     * @param trailID - trail ID
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForTrail(int trailID) {
        return alertDAO.findAlertsForTrail(trailID);
    }

    /**
     * Find alerts made by a user
     * @param userID - user
     * @return - list of alerts
     */
    @Override
    public List<Alert> findAlertsForUser(int userID) {
        return alertDAO.findAlertsForUser(userID);
    }

    public static void loadConfigs() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv").openConnection().getInputStream()));
            String line = "";
            int counter = 0;
            while((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    if(content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] ignore_in_combination_with_words = new String[]{};
                    if(content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    if(word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Iterates over a String input and checks whether a cuss word was found in a list, then checks if the word should be ignored (e.g. bass contains the word *ss).
     * @param input
     * @return
     */

    public static ArrayList<String> badWordsFound(String input) {
        if(input == null) {
            return new ArrayList<>();
        }

        // don't forget to remove leetspeak, probably want to move this to its own function and use regex if you want to use this

        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");


        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // iterate over each letter in the word
        for(int start = 0; start < input.length(); start++) {
            // from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
                if(words.containsKey(wordToCheck)) {
                    // for example, if you want to say the word bass, that should be possible.
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for(int s = 0; s < ignoreCheck.length; s++ ) {
                        if(input.contains(ignoreCheck[s])) {
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }


        for(String s: badWords) {
            System.out.println(s + " qualified as a bad word in a username");
        }
        return badWords;

    }

    public static String filterText(String input, String username) {
        ArrayList<String> badWords = badWordsFound(input);
        if(badWords.size() > 0) {
            return "This alert was blocked because a bad word was found.";
        }
        return input;
    }

}
