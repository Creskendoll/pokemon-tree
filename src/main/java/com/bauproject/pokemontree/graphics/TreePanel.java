package com.bauproject.pokemontree.graphics;

import com.bauproject.pokemontree.Color;
import com.bauproject.pokemontree.Tree;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * TreePanel
 */
public class TreePanel extends JPanel {
    Tree colorTree; 

    public TreePanel(Tree colorTree) {
        this.colorTree = colorTree;
    }

    @Override
    public void paintComponent(Graphics g) {
        int node_height = 20;
        int node_width = 20;
        int node_index = 0;
        // TODO: make this draw a tree
        for(Color color : colorTree.traverseInOrder(colorTree.getRoot())) {
            g.setColor(color.toJavaColor());
            g.fillOval((node_index * node_width) % getWidth(), ((node_index*node_height) / getWidth()) *node_height, node_width, node_height);
            
            node_index++;
        }
    }
}