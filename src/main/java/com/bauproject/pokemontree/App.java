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
                Data.leafStep.put(TreeEnum.BST, 0);
                Data.leafStep.put(TreeEnum.AVL, 0);

                Data.trees.put(TreeEnum.BST, new Tree());
                Data.trees.put(TreeEnum.AVL, new AVLTree());

                try {
                    // Read JSON as array
                    JSONArray arr = (JSONArray) parser.parse(new FileReader(filePath));
                    Data.treeArray = arr;
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
                        Data.trees.get(TreeEnum.AVL).add(avgColor, img_name, Data.sortBy, 1, 0);
                        Data.trees.get(TreeEnum.BST).add(avgColor, img_name, Data.sortBy, 1, 0);
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
                    frame.show(TreeEnum.BST);

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
