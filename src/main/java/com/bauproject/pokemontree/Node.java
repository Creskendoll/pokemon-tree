package com.bauproject.pokemontree;

public class Node {
    Color color;
    Node left;
    Node right;
 
    Node(Color color) {
        this.color = color;
        right = null;
        left = null;
    }
}