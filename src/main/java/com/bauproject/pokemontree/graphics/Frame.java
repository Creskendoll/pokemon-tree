package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.bauproject.pokemontree.structures.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bauproject.pokemontree.Data;
import com.bauproject.pokemontree.Input;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Frame extends JFrame
{
    private static final long serialVersionUID = 1L;
    public Frame()  {
        super("Pokemon Tree");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("./icon.png");
        this.setIconImage(icon.getImage());

        this.setLayout(new BorderLayout());
        Button changeTypeButton = new Button("Change Type");
        Button showAllButton = new Button("Show All/Partial");
        changeTypeButton.setBounds(50,50,100,50);
        showAllButton.setBounds(200,50,100,50);

        changeTypeButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
            if (Data.visibleTree.compareTo(TreeEnum.AVL) == 0) {
                Data.visibleTree = TreeEnum.BST;
                if(Data.showPartialTree) {
                    Data.partialTree = new Tree();
                    for (int i = 0; i < Data.leafStep.get(TreeEnum.BST); i++) {
                        // Cast obj to JSON
                        JSONObject imgObject = (JSONObject) Data.treeArray.get(i);
                        // Get image name
                        String img_name = (String) imgObject.get("name");

                        // Average color
                        JSONArray avg_color_JSON = (JSONArray) imgObject.get("average_color");
                        Color avgColor = new Color(avg_color_JSON);

                        // Add the color to tree
                        Data.partialTree.add(avgColor, img_name, Data.sortBy, 1, 0);
                    }
                }
            }else {
                Data.visibleTree = TreeEnum.AVL;
                if(Data.showPartialTree) {
                    Data.partialTree = new AVLTree();
                    for (int i = 0; i < Data.leafStep.get(TreeEnum.AVL); i++) {
                        // Cast obj to JSON
                        JSONObject imgObject = (JSONObject) Data.treeArray.get(i);
                        // Get image name
                        String img_name = (String) imgObject.get("name");

                        // Average color
                        JSONArray avg_color_JSON = (JSONArray) imgObject.get("average_color");
                        Color avgColor = new Color(avg_color_JSON);

                        // Add the color to tree
                        Data.partialTree.add(avgColor, img_name, Data.sortBy, 1, 0);
                    }
                }
            }
            Data.panel.repaint();
            }
        });
        showAllButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.showPartialTree = !Data.showPartialTree;
                Data.panel.repaint();
            }
        });
        
        changeTypeButton.setFocusable(false);
        showAllButton.setFocusable(false);
        
        this.add(changeTypeButton);
        this.add(showAllButton);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Input());
    }
    
    public void show(TreeEnum treeEnum) {
        Data.visibleTree = treeEnum;
        Data.panel = new TreePanel(this);
        
        this.add(new JScrollPane(Data.panel));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
