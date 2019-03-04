package com.bauproject.pokemontree;

public class Node {
    Color color;
    String imgName;
    Node left;
    Node right;
 
    Node(Color color) {
        this.color = color;
        right = null;
        left = null;
    }
    Node(Color color, String imgName) {
        this.color = color;
        this.imgName = imgName;
        right = null;
        left = null;
    }

    public String toString() {
        return "{ " + imgName + ", " + color.toString() + " }\n";
    }
}