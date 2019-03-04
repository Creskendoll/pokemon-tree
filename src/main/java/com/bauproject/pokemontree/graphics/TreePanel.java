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
        int node_height = 25;
        int node_width = 25;
        int img_width = 30;
        int img_height = 30;
        final int canvasWidth = 1920;
        final int canvasHeight = 1080;
        int node_index = 0;
        // TODO: make this draw a tree
        for(Node node : this.inOrderList) {
            // g.fillOval((node_index * node_width) % getWidth(), ((node_index*node_height) / getWidth()) *node_height, node_width, node_height);
            // int tree_node_x = node.getIndex() * canvasWidth / ( ((int)Math.pow(2, node.getDepth()) + 1) );
            // int tree_node_y = node.getDepth() * canvasHeight / this.colorTree.maxDepth();
            int[] node_position = node.getPosition(canvasWidth, canvasHeight, this.colorTree.maxDepth());  
            int tree_node_x = node_position[0]; 
            int tree_node_y = node_position[1];
            
            if (node.getLeft() != null) {
                int[] left_position = node.getLeft().getPosition(canvasWidth, canvasHeight, this.colorTree.maxDepth());
                g.setColor(java.awt.Color.BLACK);
                g.drawLine(tree_node_x, tree_node_y, left_position[0], left_position[1]);
            }
            if (node.getRight() != null) {
                int[] right_position = node.getRight().getPosition(canvasWidth, canvasHeight, this.colorTree.maxDepth());
                g.setColor(java.awt.Color.BLACK);
                g.drawLine(tree_node_x, tree_node_y, right_position[0], right_position[1]);
            }
            // int img_X = ((node_index * img_width) % (int) Math.ceil((double)(getWidth() - img_width)));
            // int img_Y = (((node_index * img_height) / (int) Math.ceil((double)(getWidth() - img_width))) * img_height);
            
            g.setColor(node.getColor().toJavaColor());
            // g.fillOval(img_X, img_Y, node_width, node_height);
            // g.drawImage(node.getImage(), img_X, img_Y, img_width, img_height, null);
            g.fillOval(tree_node_x, tree_node_y, node_width, node_height);
            // String debugInfo = "Depth: " + String.valueOf(node.getDepth()) + "  Index: " + String.valueOf(node.getIndex()); 
            // g.drawString(debugInfo, tree_node_x, tree_node_y + node_height+20);
            
            // g.drawImage(node.getImage(), tree_node_x, tree_node_y, img_width, img_height, null);
            node_index++;
        }
    }
}