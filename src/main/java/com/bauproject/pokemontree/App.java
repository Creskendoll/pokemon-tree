package com.bauproject.pokemontree;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class App 
{
    public static void main( String[] args )
    {
        // JSON file path
        String filePath = "./data.json";
        JSONParser parser = new JSONParser();

        try {
            // Read JSON as array
            JSONArray arr = (JSONArray) parser.parse(new FileReader(filePath));
            // For each image in JSON array
            for (Object obj : arr) {
                // Cast obj to JSON  
                JSONObject imgObject = (JSONObject) obj;
                // Get image name
                String img_name = (String) imgObject.get("name");
                
                // Average color
                JSONArray avg_color_JSON = (JSONArray) imgObject.get("average_color");
                Color avgColor = new Color(avg_color_JSON);
                
                // Dominant color
                JSONArray dom_color_JSON = (JSONArray) imgObject.get("dominant_color");
                Color dom_color = new Color(dom_color_JSON);
                
                System.out.println(img_name);
                System.out.println(avgColor);
                System.out.println(dom_color);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File can not be found: %s\n", filePath);
            e.printStackTrace(System.out);
        } catch (IOException e) {
            System.out.printf("Error while trying to read file: %s\n", filePath);
            e.printStackTrace(System.out);
        } catch (ParseException e) {
            System.out.printf("Error while parsing file: %s\n", filePath);
            e.printStackTrace(System.out);
        }
    }
}
