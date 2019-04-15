package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.bauproject.pokemontree.structures.*;

import com.bauproject.pokemontree.Data;
import com.bauproject.pokemontree.Input;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
        Button showAllButton = new Button("Show All/Partial");
        // Tree types
        Choice treeTypesChoice = new Choice();
        treeTypesChoice.add(TreeEnum.BST.toString());
        treeTypesChoice.add(TreeEnum.AVL.toString());
        // Sort by options
        Choice sortByChoice = new Choice();
        sortByChoice.add(ColorEnum.BRIGHTNESS.toString());
        sortByChoice.add(ColorEnum.HUE.toString());
        sortByChoice.add(ColorEnum.SATURATION.toString());
        sortByChoice.add(ColorEnum.RED.toString());
        sortByChoice.add(ColorEnum.GREEN.toString());
        sortByChoice.add(ColorEnum.BLUE.toString());

        treeTypesChoice.setBounds(50,50,100,50);
        showAllButton.setBounds(350,50,100,50);
        sortByChoice.setBounds(200,50,100,50);

        // Switch between partian and full view
        showAllButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.showPartialTree = !Data.showPartialTree;
                buildTree();
                Data.panel.repaint();
            }
        });

        // Select tree type 
        treeTypesChoice.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selection = e.getItem().toString();
                if (selection == TreeEnum.AVL.toString())
                    Data.visibleTree = TreeEnum.AVL;
                else if (selection == TreeEnum.BST.toString())
                    Data.visibleTree = TreeEnum.BST;
                buildTree();
                Data.panel.repaint();
            }
        });

        // Sort the nodes by
        sortByChoice.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selection = e.getItem().toString();

                if(ColorEnum.BRIGHTNESS.toString() == selection)
                    Data.sortBy = ColorEnum.BRIGHTNESS;
                else if(ColorEnum.HUE.toString() == selection)
                    Data.sortBy = ColorEnum.HUE;
                else if(ColorEnum.SATURATION.toString() == selection)
                    Data.sortBy = ColorEnum.SATURATION;
                else if(ColorEnum.RED.toString() == selection)
                    Data.sortBy = ColorEnum.RED;
                else if(ColorEnum.GREEN.toString() == selection)
                    Data.sortBy = ColorEnum.GREEN;
                else if(ColorEnum.BLUE.toString() == selection)
                    Data.sortBy = ColorEnum.BLUE;
                
                buildTree();
                Data.panel.repaint();
            }
        });
        
        // So that we can scroll
        treeTypesChoice.setFocusable(false);
        showAllButton.setFocusable(false);
        sortByChoice.setFocusable(false);
        
        // Add components to view 
        this.add(sortByChoice);
        this.add(showAllButton);
        this.add(treeTypesChoice);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Input());
    }

    // Constructs a tree from ground up
    private void buildTree() {
        if(Data.showPartialTree) {
            if (Data.visibleTree == TreeEnum.BST){
                Data.partialTree = new Tree();
            } else if (Data.visibleTree == TreeEnum.AVL) {
                Data.partialTree = new AVLTree();
            }
            for (int i = 0; i < Data.leafStep.get(Data.visibleTree); i++) {
                Node node = Data.nodeList.get(i);
                Data.partialTree.add(node);
            }
        } else {
            Data.trees.put(Data.visibleTree, new Tree());

            for (Node node : Data.nodeList) {
                Data.trees.get(Data.visibleTree).add(node);
            }
        }
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
