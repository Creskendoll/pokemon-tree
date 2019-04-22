package com.bauproject.pokemontree.structures;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Node {
    Color color;
    String imgName;
    Node left;
    Node right;
    BufferedImage image = null;
    private final String IMAGES_FOLDER = "/images/";
    long depth;
    long index;

    public Node(Color color, String imgName, long index, long depth) {
        this.color = color;
        this.imgName = imgName;
        this.index = index;
        this.depth = depth;
        this.right = null;
        this.left = null;
        String img_file_path = IMAGES_FOLDER + imgName;
        try {
            this.image = ImageIO.read(Node.class.getResourceAsStream(img_file_path));
        } catch (IOException e) {
            System.out.printf("Error reading file: %s", img_file_path);
            // this.image = null;
            // e.printStackTrace();
        }
    }
    Node(Color color, long index, long depth) {
        this.color = color;
        this.index = index;
        this.depth = depth;
        this.right = null;
        this.left = null;
    }

    public Point getPosition(int canvasWidth, int canvasHeight, int maxDepth) {
        int x = (int)(this.getIndex() * canvasWidth / ( ((int)Math.pow(2, this.depth) + 1) ));
        int y = (int)(this.depth * canvasHeight / maxDepth);
        return new Point(x, y);
    }

    public long getIndex() {
        return this.index;
    }

    public long getDepth() {
        return this.depth;
    }

    public Color getColor() {
        return this.color;
    }

    public String getImgName() {
        return this.imgName;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public Node getLeft() {
        return this.left;
    }
    public Node getRight() {
        return this.right;
    }

    public String toString() {
        return "{ " + imgName + ", " + color.toString() + " }\n";
    }
}