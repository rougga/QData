package ma.rougga.qdata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import ma.rougga.qdata.controller.report.EmpTableController;
import ma.rougga.qdata.controller.report.GblTableController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

public class UpdateController {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UpdateController.class);
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
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
                logger.error("GET request failed with Response Code: " + responseCode);
            }

            // Disconnect
            connection.disconnect();
            return null;
        } catch (IOException | ParseException e) {
            logger.error("UpdateController.getJsonFromUrl: " + e.getMessage());
            return null;
        }
    }

    public boolean restoreAllAgenceDataById(UUID id_agence) {

        boolean isSuccessful = false;
        isSuccessful = new GblTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new EmpTableController().restoreOldRowsByAgenceId(id_agence);
        // restore other tables

        return isSuccessful;
    }

    public boolean updateAgencesTodayData(UUID id_agence) {
        boolean isDone = false;
        isDone = new GblTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new EmpTableController().updateAgenceFromJson(null, null, id_agence.toString());
        // update other tables
        
        return isDone;
    }
    
    public void updateAllAgencesTodayData(){
        new GblTableController().updateFromJson(null, null);
        
        
        
        
        
        // update other tables
        logger.info("updateAllAgencesTodayData() Finished! .");
    }
    
}
