package com.bauproject.pokemontree.graphics;

import com.bauproject.pokemontree.Color;
import com.bauproject.pokemontree.Node;
import com.bauproject.pokemontree.Tree;

import java.awt.Graphics;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * TreePanel
 */
public class TreePanel extends JPanel {
    Tree colorTree;
    ArrayList<Node> inOrderList;
    ArrayList<Node> preOrderList;
    ArrayList<Node> postOrderList;

    public TreePanel(Tree colorTree) {
        this.colorTree = colorTree;
        this.inOrderList = colorTree.traverseInOrder();
        this.preOrderList = colorTree.traversePreOrder();
        this.postOrderList = colorTree.traversePostOrder();
    }

    @Override
    public void paintComponent(Graphics g) {
        Path tile_folder_path = Paths.get("images\\");
        System.out.println(tile_folder_path);
        int node_height = 25;
        int node_width = 25;
        int img_width = 25;
        int img_height = 25;
        int node_index = 0;
        // TODO: make this draw a tree
        for(Node node : colorTree.traverseInOrder()) {
            g.setColor(node.getColor().toJavaColor());
            // g.fillOval((node_index * node_width) % getWidth(), ((node_index*node_height) / getWidth()) *node_height, node_width, node_height);
            int img_X = ((node_index * img_width) % (getWidth() - img_width));
            int img_Y = (((node_index * img_height) / (getWidth() - img_width)) * img_height);
            g.fillOval(img_X, img_Y, node_width, node_height);
            // g.drawImage(node.getImage(), img_X, img_Y, img_X+img_width, img_Y+img_height, null);
            node_index++;
        }
    }
}