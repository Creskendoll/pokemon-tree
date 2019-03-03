package com.bauproject.pokemontree;

import java.util.ArrayList;

public class Tree {
    Node root;

    public Tree() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    private Node addRecursive(Node current, Color color, ColorEnum type) {
        if (current == null) {
            return new Node(color);
        }
        
        ColorEnum result = null;
        switch (type) {
            case BRIGHTNESS:
                result = current.color.compareBrightness(color);
                break;
            case SATURATION:
                result = current.color.compareSaturation(color);
                break;
            case HUE:
                result = current.color.compareHue(color);
                break;
            default:
                result = ColorEnum.EQUAL;
                break;
        }

        if (result == ColorEnum.LOWER) {
            current.left = addRecursive(current.left, color, type);
        } else if (result == ColorEnum.HIGHER) {
            current.right = addRecursive(current.right, color, type);
        } else {
            // value already exists
            current.left = addRecursive(current.left, color, type);
            // return current;
        }
     
        return current;
    }

    public void add(Color color, ColorEnum type) {
        root = addRecursive(root, color, type);
    }

    public ArrayList<Color> traverseInOrder(Node node) {
        ArrayList<Color> result = new ArrayList<Color>();     

        if (node != null) {
            result.addAll(traverseInOrder(node.left));
            result.add(node.color);
            result.addAll(traverseInOrder(node.right));
        }

        return result;
    }
    public ArrayList<Color> traversePreOrder(Node node) {
        ArrayList<Color> result = new ArrayList<Color>();     

        if (node != null) {
            result.addAll(traversePreOrder(node.left));
            result.add(node.color);
            result.addAll(traversePreOrder(node.right));
        }

        return result;
    }
    public ArrayList<Color> traversePostOrder(Node node) {
        ArrayList<Color> result = new ArrayList<Color>();     

        if (node != null) {
            result.addAll(traversePostOrder(node.left));
            result.add(node.color);
            result.addAll(traversePostOrder(node.right));
        }

        return result;
    }

    public void printTraverseInOrder(Node node) {
        if (node != null) {
            printTraverseInOrder(node.left);
            System.out.print(" " + node.color);
            printTraverseInOrder(node.right);
        }
    }
    public void printTraversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.color);
            printTraversePreOrder(node.left);
            printTraversePreOrder(node.right);
        }
    }
    public void printTraversePostOrder(Node node) {
        if (node != null) {
            printTraversePostOrder(node.left);
            printTraversePostOrder(node.right);
            System.out.print(" " + node.color);
        }
    }
}