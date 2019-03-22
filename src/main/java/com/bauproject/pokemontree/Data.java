package com.bauproject.pokemontree;

import com.bauproject.pokemontree.structures.*;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bauproject.pokemontree.graphics.*;

public class Data {
    public static TreeEnum visibleTree = TreeEnum.BST;
    public static Map<TreeEnum, Tree> trees = new HashMap<TreeEnum, Tree>();
    public static Map<TreeEnum, Integer> leafStep = new HashMap<TreeEnum, Integer>();
    public static boolean showPartialTree = false;
    public static Tree partialTree = new Tree();
    public static TreePanel panel = null;
    public static ColorEnum sortBy = ColorEnum.BRIGHTNESS;
    public static JSONArray treeArray = new JSONArray();
    public static ArrayList<Node> nodeList = new ArrayList<Node>();
}