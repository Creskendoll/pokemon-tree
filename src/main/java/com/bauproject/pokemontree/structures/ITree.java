package com.bauproject.pokemontree.structures;

import java.util.ArrayList;

public interface ITree {
    public void add(Color color, String imgName, int index, int depth);
    public void add(Node node);

    public int maxDepth();
    
    public ArrayList<Node> toList();
    
    public ArrayList<Node> toList(TreeEnum type);
}