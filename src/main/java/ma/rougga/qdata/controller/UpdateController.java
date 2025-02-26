package ma.rougga.qdata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import ma.rougga.qdata.controller.report.*;
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
                logger.error("GET request failed with Response Code: {}", responseCode);
            }

            // Disconnect
            connection.disconnect();
            return null;
        } catch (IOException e) {
            logger.error("Agence OFFLINE {}", e.getMessage());
            return null;
        }catch (ParseException e) {
            logger.error("UpdateController.getJsonFromUrl: {}", e.getMessage());
            return null;
        }
    }

    public boolean restoreAllAgenceDataById(UUID id_agence) {

        boolean isSuccessful = false;
        isSuccessful = new GblTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new EmpTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new EmpSerTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new GchTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new GchSerTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new GlaTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new GltTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new ThTTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new ThTTTableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new ThATableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new ThSATableController().restoreOldRowsByAgenceId(id_agence);
        isSuccessful = new CibleController().updateAgenceFromJson(id_agence.toString());
        // restore other tables

        return isSuccessful;
    }

    public boolean updateAgencesTodayData(UUID id_agence) {
        boolean isDone = false;
        isDone = new GblTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new EmpTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new EmpSerTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new GchTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new GchSerTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new GlaTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new GltTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new ThTTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new ThTTTableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new ThATableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new ThSATableController().updateAgenceFromJson(null, null, id_agence.toString());
        isDone = new CibleController().updateAgenceFromJson(id_agence.toString());
        // update other tables

        return isDone;
    }

    public void update() {
        new GblTableController().updateFromJson(null, null);
        new EmpTableController().updateFromJson(null, null);
        new EmpSerTableController().updateFromJson(null, null);
        new GchTableController().updateFromJson(null, null);
        new GchSerTableController().updateFromJson(null, null);
        new GlaTableController().updateFromJson(null, null);
        new GltTableController().updateFromJson(null, null);
        new ThTTableController().updateFromJson(null, null);
        new ThTTTableController().updateFromJson(null, null);
        new ThATableController().updateFromJson(null, null);
        new ThSATableController().updateFromJson(null, null);
        new CibleController().updateFromJson();

        // update other tables
        logger.info("Globale update is Finished! .");
    }

    public void restore() {

        new GblTableController().restoreOldRowsForAllAgences();
        new EmpTableController().restoreOldRowsForAllAgences();
        new EmpSerTableController().restoreOldRowsForAllAgences();
        new GchTableController().restoreOldRowsForAllAgences();
        new GchSerTableController().restoreOldRowsForAllAgences();
        new GlaTableController().restoreOldRowsForAllAgences();
        new GltTableController().restoreOldRowsForAllAgences();
        new ThTTableController().restoreOldRowsForAllAgences();
        new ThTTTableController().restoreOldRowsForAllAgences();
        new ThATableController().restoreOldRowsForAllAgences();
        new ThSATableController().restoreOldRowsForAllAgences();
        new CibleController().updateFromJson();
        // restore other tables
        logger.info("Globale restore is Finished! .");
    }
}
