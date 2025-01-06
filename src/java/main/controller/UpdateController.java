package main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UpdateController {

    public UpdateController() {
    }

    public static JSONObject getJsonFromUrl(String urlString) {
        try {
            // Create a URL object
            URL url = new URL(urlString);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8"); 
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                StringBuilder response2 = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response2.append(line);
                }
                reader.close();
                //simple json
                JSONParser parser = new JSONParser();
                org.json.simple.JSONObject ob = (org.json.simple.JSONObject) parser.parse(response2.toString());
                return ob;
            } else {
                System.out.println("GET request failed with Response Code: " + responseCode);
                
            }

            // Disconnect
            connection.disconnect();
            return null;
        } catch (IOException | ParseException e) {
           System.out.println("UpdateController.getJsonFromUrl: " + e.getMessage());
           return null;
        }
    }
    
    
}