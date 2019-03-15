package com.bauproject.pokemontree;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bauproject.pokemontree.structures.Color;
import com.bauproject.pokemontree.structures.ColorEnum;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Input
 */
public class Input implements KeyListener {

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_E){
            // Cast obj to JSON
            JSONObject imgObject = (JSONObject) Data.treeArray.get(Data.leafStep);
            // Get image name
            String img_name = (String) imgObject.get("name");
            
            // Average color
            JSONArray avg_color_JSON = (JSONArray) imgObject.get("average_color");
            Color avgColor = new Color(avg_color_JSON);
            
            // Add the color to tree
            // Data.avlTree.add(avgColor, img_name, ColorEnum.BRIGHTNESS, 1, 0);
            // Data.bstTree.add(avgColor, img_name, ColorEnum.BRIGHTNESS, 1, 0);
            Data.visibleTree.add(avgColor, img_name, ColorEnum.BRIGHTNESS, 1, 0);
            
            Data.leafStep += 1;
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