package com.bauproject.pokemontree.structures;

import java.util.ArrayList;

public class Tree implements ITree {
    Node root;

    public Tree() {
        this.root = null;
    }

    public Node getRoot() {
        return this.root;
    }

    // ======================== MISC ========================
    public int maxDepth() {
        return this.maxDepth(this.root);
    }
    protected int maxDepth(Node node) { 
       if (node==null) {
           return 0;
       } else {
           /* compute the depth of each subtree */
           int lDepth = maxDepth(node.left); 
           int rDepth = maxDepth(node.right); 
      
           /* use the larger one */
           if (lDepth > rDepth) return(lDepth+1); 
           else return(rDepth+1); 
       }
    }
      

    // ======================== Add methods ========================

    // Add with image name
    private Node addRecursive(Node current, Color color, String imgName, ColorEnum type, long index, long depth) {
        if (current == null) {
            // Read image files
            return new Node(color, imgName, index, depth);
            // Don't read image files 
            // return new Node(color, index, depth);
        }
        
        ColorEnum result = null;
        switch (type) {
            case BRIGHTNESS:
                result = color.compareBrightness(current.color);
                break;
            case SATURATION:
                result = color.compareSaturation(current.color);
                break;
            case HUE:
                result = color.compareHue(current.color);
                break;
            default:
                result = ColorEnum.EQUAL;
                break;
        }

        if (result == ColorEnum.LOWER) {
            current.left = addRecursive(current.left, color, imgName, type, (current.getIndex() * 2) - 1, depth + 1);
        } else if (result == ColorEnum.HIGHER) {
            current.right = addRecursive(current.right, color, imgName, type, current.getIndex() * 2, depth + 1);
        } else {
            // value already exists
            current.left = addRecursive(current.left, color, imgName, type, (current.getIndex() * 2) - 1, depth + 1);
            // Results in less nodes
            // return current;
        }
        
        return current;
    }

    @Override
    public void add(Color color, String imgName, ColorEnum type, int index, int depth) {
        this.root = addRecursive(this.root, color, imgName, type, index, depth);
    }

    // ======================== Traverse methods ========================

    protected ArrayList<Node> traverseInOrder(Node node) {
        ArrayList<Node> result = new ArrayList<Node>();     

        if (node != null) {
            result.addAll(traverseInOrder(node.left));
            result.add(node);
            result.addAll(traverseInOrder(node.right));
        }

        return result;    
    }

    protected ArrayList<Node> traversePreOrder(Node node) {
        ArrayList<Node> result = new ArrayList<Node>();     

        if (node != null) {
            result.add(node);
            result.addAll(traversePreOrder(node.left));
            result.addAll(traversePreOrder(node.right));
        }

        return result;
    }

    protected ArrayList<Node> traversePostOrder(Node node) {
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

    @Override
    public ArrayList<Node> toList() {
        return this.traverseInOrder(this.root);
    }

    @Override
    public ArrayList<Node> toList(TreeEnum type) {
        ArrayList<Node> result = null; 
        switch (type) {
            case PREORDER:
                result = this.traversePreOrder(this.root);
                break;
            case INORDER:
                result = this.traverseInOrder(this.root);
                break;
            case POSTORDER:
                result = this.traversePostOrder(this.root);
                break;
            default:
                result = new ArrayList<Node>();
                break;
        }
        return result;
    }
}