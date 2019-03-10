package com.bauproject.pokemontree;

import com.bauproject.pokemontree.graphics.*;
import com.bauproject.pokemontree.structures.*;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class App {
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

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

                        // Add the color to tree
                        Data.avlTree.add(avgColor, img_name, ColorEnum.BRIGHTNESS, 1, 0);
                        Data.bstTree.add(avgColor, img_name, ColorEnum.BRIGHTNESS, 1, 0);
                    }

                    // UI Config
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace(System.out);
                    } catch (InstantiationException e) {
                        e.printStackTrace(System.out);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace(System.out);
                    } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace(System.out);
                    }

                    Frame frame = new Frame();
                    frame.show(TreeEnum.AVL);

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
        });
    }
}
