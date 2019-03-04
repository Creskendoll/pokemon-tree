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

    // ======================== Add methods ========================

    // Add without image name
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

    // Add with image name
    private Node addRecursive(Node current, Color color, String imgName, ColorEnum type) {
        if (current == null) {
            // Read image files
            // return new Node(color, imgName);
            // Don't read image files 
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
            current.left = addRecursive(current.left, color, imgName, type);
        } else if (result == ColorEnum.HIGHER) {
            current.right = addRecursive(current.right, color, imgName, type);
        } else {
            // value already exists
            current.left = addRecursive(current.left, color, imgName, type);
            // Results in less nodes
            // return current;
        }
        
        return current;
    }
    public void add(Color color, String imgName, ColorEnum type) {
        this.root = addRecursive(this.root, color, imgName, type);
    }

    // ======================== Traverse methods ========================
    public ArrayList<Node> traverseInOrder() {
        return this.traverseInOrder(this.root);
    }
    private ArrayList<Node> traverseInOrder(Node node) {
        ArrayList<Node> result = new ArrayList<Node>();     

        if (node != null) {
            result.addAll(traverseInOrder(node.left));
            result.add(node);
            result.addAll(traverseInOrder(node.right));
        }

        return result;    
    }

    public ArrayList<Node> traversePreOrder() {
        return this.traversePreOrder(this.root);
    }
    private ArrayList<Node> traversePreOrder(Node node) {
        ArrayList<Node> result = new ArrayList<Node>();     

        if (node != null) {
            result.add(node);
            result.addAll(traversePreOrder(node.left));
            result.addAll(traversePreOrder(node.right));
        }

        return result;
    }

    public ArrayList<Node> traversePostOrder() {
        return this.traversePostOrder(this.root);
    }
    private ArrayList<Node> traversePostOrder(Node node) {
        ArrayList<Node> result = new ArrayList<Node>();     

        if (node != null) {
            result.addAll(traversePostOrder(node.left));
            result.addAll(traversePostOrder(node.right));
            result.add(node);
        }

        return result;
    }

    // ======================== Print methods ========================

    public void printTraverseInOrder() {
        this.printTraverseInOrder(getRoot());
    }
    private void printTraverseInOrder(Node node) {
        if (node != null) {
            printTraverseInOrder(node.left);
            System.out.print(" " + node);
            printTraverseInOrder(node.right);
        }
    }
    public void printTraversePreOrder() {
        this.printTraversePreOrder(getRoot());
    }
    private void printTraversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node);
            printTraversePreOrder(node.left);
            printTraversePreOrder(node.right);
        }
    }
    public void printTraversePostOrder() {
        this.printTraversePostOrder(getRoot());
    }
    private void printTraversePostOrder(Node node) {
        if (node != null) {
            printTraversePostOrder(node.left);
            printTraversePostOrder(node.right);
            System.out.print(" " + node);
        }
    }

    public String toString() {
        this.printTraverseInOrder(root);
        return "";
    }
}