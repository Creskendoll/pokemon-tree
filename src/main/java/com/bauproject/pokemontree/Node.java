package com.bauproject.pokemontree;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Node {
    Color color;
    String imgName;
    Node left;
    Node right;
    // TODO: Should we keep the files inside nodes?
    BufferedImage image = null;
    Path tile_folder_path = Paths.get("images\\");
    int depth;
    int index;
 
    Node(Color color) {
        this.color = color;
        right = null;
        left = null;
    }
    Node(Color color, String imgName) {
        this.color = color;
        this.imgName = imgName;
        this.right = null;
        this.left = null;
        try {
            String img_file_path = tile_folder_path.toAbsolutePath().toString() + "\\" + imgName;
            File imageFile = new File(img_file_path);
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.printf("Error reading file: %s", tile_folder_path.toAbsolutePath());
            e.printStackTrace();
        }
    }
    Node(Color color, String imgName, int index, int depth) {
        this.color = color;
        this.imgName = imgName;
        this.index = index;
        this.depth = depth;
        this.right = null;
        this.left = null;
        try {
            String img_file_path = tile_folder_path.toAbsolutePath().toString() + "\\" + imgName;
            File imageFile = new File(img_file_path);
            this.image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.printf("Error reading file: %s", tile_folder_path.toAbsolutePath());
            e.printStackTrace();
        }
    }

    public int[] getPosition(int canvasWidth, int canvasHeight, int maxDepth) {
        int x = this.getIndex() * canvasWidth / ( ((int)Math.pow(2, this.getDepth()) + 1) );
        int y = this.getDepth() * canvasHeight / (maxDepth);
        return new int[]{x, y};
    }

    public int getIndex() {
        return this.index;
    }

    public int getDepth() {
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