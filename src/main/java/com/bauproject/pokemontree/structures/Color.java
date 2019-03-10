package com.bauproject.pokemontree.structures;

import java.util.ArrayList;

import org.json.simple.JSONArray;

public class Color {
    float red;
    float green;
    float blue;
    float alpha;

    public Color(float R, float G, float B) {
        this.red = R;
        this.green = G;
        this.blue = B;    
    }
    public Color(float R, float G, float B, float A) {
        this.red = R;
        this.green = G;
        this.blue = B;    
        this.alpha = A;
    }
    // public Color(ArrayList<Float> colors) {
    //     this.red = colors.get(0);
    //     this.green = colors.get(1);
    //     this.blue = colors.get(2);
    //     if (colors.get(3) != null) {
    //         this.alpha = colors.get(3);
    //     } 
    // }
    public Color(JSONArray colors) {
        ArrayList<Float> color_arr = new ArrayList<Float>();     
                
        // Read values from json
        if (colors != null) {
            for (int i=0;i<colors.size();i++){ 
                color_arr.add(Float.parseFloat(colors.get(i).toString()));
            } 
        } 

        // set object values
        this.red = color_arr.get(0);
        this.green = color_arr.get(1);
        this.blue = color_arr.get(2);
        if (colors.size() == 4) {
            this.alpha = color_arr.get(3);
        }
    }

    public float[] getRGB() {
        return new float[]{red, green, blue};
    }

    public float[] getHSV() {
        float[] hsv = new float[3];
        java.awt.Color.RGBtoHSB((int)this.red,(int)this.green,(int)this.blue,hsv);
        return hsv;
    }
    
    public ColorEnum compareHue(Color c) {
        if(c.getHSV()[0] > this.getHSV()[0]) {
            return ColorEnum.LOWER;
        } else if (c.getHSV()[0] < this.getHSV()[0]) {
            return ColorEnum.HIGHER;
        } else {
            return ColorEnum.EQUAL;
        }                
    }
    public ColorEnum compareSaturation(Color c) {
        if(c.getHSV()[1] > this.getHSV()[1]) {
            return ColorEnum.LOWER;
        } else if (c.getHSV()[1] < this.getHSV()[1]) {
            return ColorEnum.HIGHER;
        } else {
            return ColorEnum.EQUAL;
        }
    }
    public ColorEnum compareBrightness(Color c) {
        if(c.getHSV()[2] > this.getHSV()[2]) {
            return ColorEnum.LOWER;
        } else if (c.getHSV()[2] < this.getHSV()[2]) {
            return ColorEnum.HIGHER;
        } else {
            return ColorEnum.EQUAL;
        }
    }
    public ColorEnum[] compareRGB(Color c) {
        ColorEnum[] result = new ColorEnum[3];
        
        if(c.red > this.red)
            result[0] = ColorEnum.LOWER;
        else
            result[0] = ColorEnum.HIGHER;
        
        if(c.green > this.green)
            result[1] = ColorEnum.LOWER;
        else
            result[1] = ColorEnum.HIGHER;
        
        if(c.blue > this.blue)
            result[2] = ColorEnum.LOWER;
        else
            result[2] = ColorEnum.HIGHER;

        return result;
    }

    public String toString() {
        return String.format("{ RED: %f, GREEN: %f, BLUE: %f }", this.red, this.green, this.blue);
    }

    public java.awt.Color toJavaColor() {
        return new java.awt.Color(this.red/255f, this.green/255f, this.blue/255f);
    }
}
