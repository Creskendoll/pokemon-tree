package com.bauproject.pokemontree;

import com.bauproject.pokemontree.structures.*;

import org.json.simple.JSONArray;

import com.bauproject.pokemontree.graphics.*;

public class Data {
    public static Tree bstTree = new Tree();
    public static Tree avlTree = new AVLTree();
    public static Tree visibleTree = null;
    public static TreePanel panel = null;
    public static int leafStep = 0;
    public static JSONArray treeArray = new JSONArray();
}