package com.bauproject.pokemontree;

public class Tree {
    Node root;

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
            return current;
        }
     
        return current;
    }

    public void add(Color color, ColorEnum type) {
        root = addRecursive(root, color, type);
    }
}