package com.bauproject.pokemontree;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bauproject.pokemontree.structures.Color;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Input
 */
public class Input implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            int currentStep = Data.leafStep.get(Data.visibleTree);
            // Cast obj to JSON
            JSONObject imgObject = (JSONObject) Data.treeArray.get(currentStep);
            // Get image name
            String img_name = (String) imgObject.get("name");
            
            // // Average color
            JSONArray avg_color_JSON = (JSONArray) imgObject.get("average_color");
            Color avgColor = new Color(avg_color_JSON);
            
            // Add the color to tree
            Data.partialTree.add(avgColor, img_name, Data.sortBy, 1, 0);
         
            Data.leafStep.put(Data.visibleTree, currentStep+1);
            Data.panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    
}