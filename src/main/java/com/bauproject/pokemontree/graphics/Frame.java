package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bauproject.pokemontree.structures.*;

import com.bauproject.pokemontree.Data;
import com.bauproject.pokemontree.Input;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;


public class Frame extends JFrame
{
    private static final long serialVersionUID = 1L;
    public Frame()  {
        super("Pokemon Tree");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("./icon.png");
        this.setIconImage(icon.getImage());

        this.setLayout(new BorderLayout());
        // Tree types
        Choice treeTypesChoice = new Choice();
        treeTypesChoice.add(TreeEnum.BST.toString());
        treeTypesChoice.add(TreeEnum.AVL.toString());
        treeTypesChoice.add(TreeEnum.MIN_HEAP.toString());
        // Sort by options
        Label sortLabel = new Label("Sort By:");
        Choice sortByChoice = new Choice();
        sortByChoice.add(ColorEnum.BRIGHTNESS.toString());
        sortByChoice.add(ColorEnum.HUE.toString());
        sortByChoice.add(ColorEnum.SATURATION.toString());
        sortByChoice.add(ColorEnum.RED.toString());
        sortByChoice.add(ColorEnum.GREEN.toString());
        sortByChoice.add(ColorEnum.BLUE.toString());
        // Partial Tree
        Label partialLabel = new Label("Show Partial?");
        Checkbox showPartialCheck = new Checkbox();
        // Size slider
        // https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html
        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL,
        20, 170, 30);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setPaintTicks(true);
        Hashtable<Object, Object> labelTable = new Hashtable<Object, Object>();
        labelTable.put( new Integer( 20 ), new JLabel("Small") );
        labelTable.put( new Integer( 170 ), new JLabel("Big") );
        sizeSlider.setLabelTable( labelTable );
        sizeSlider.setPaintLabels(true);
        
        treeTypesChoice.setBounds(50,50,100,50);
        sortLabel.setBounds(200, 30, 100, 20);
        sortByChoice.setBounds(200,50,100,50);
        partialLabel.setBounds(350, 30, 100, 20);
        showPartialCheck.setBounds(350,50,100,25);
        sizeSlider.setBounds(500, 30, 300, 70);

        // Switch between partian and full view
        showPartialCheck.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                Data.showPartialTree = e.getStateChange() == ItemEvent.SELECTED;
                buildTree();
            }
        });


        // Select tree type 
        treeTypesChoice.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selection = e.getItem().toString();
                if (selection == TreeEnum.AVL.toString())
                    Data.visibleTree = TreeEnum.AVL;
                else if (selection == TreeEnum.BST.toString())
                    Data.visibleTree = TreeEnum.BST;
                else if (selection == TreeEnum.MIN_HEAP.toString())
                    Data.visibleTree = TreeEnum.MIN_HEAP;
                buildTree();
            }
        });

        // Sort the nodes by
        sortByChoice.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selection = e.getItem().toString();

                if(ColorEnum.BRIGHTNESS.toString() == selection)
                    Data.sortBy = ColorEnum.BRIGHTNESS;
                else if(ColorEnum.HUE.toString() == selection)
                    Data.sortBy = ColorEnum.HUE;
                else if(ColorEnum.SATURATION.toString() == selection)
                    Data.sortBy = ColorEnum.SATURATION;
                else if(ColorEnum.RED.toString() == selection)
                    Data.sortBy = ColorEnum.RED;
                else if(ColorEnum.GREEN.toString() == selection)
                    Data.sortBy = ColorEnum.GREEN;
                else if(ColorEnum.BLUE.toString() == selection)
                    Data.sortBy = ColorEnum.BLUE;
                
                buildTree();
            }
        });
        
        sizeSlider.addChangeListener(new ChangeListener(){
        
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                Data.nodeSize = (int)source.getValue();
                Data.panel.repaint();
            }
        });

        // So that we can scroll
        treeTypesChoice.setFocusable(false);
        showPartialCheck.setFocusable(false);
        sortByChoice.setFocusable(false);
        sizeSlider.setFocusable(false);

        // Add components to view 
        this.add(sizeSlider);
        this.add(sortByChoice);
        this.add(showPartialCheck);
        this.add(treeTypesChoice);
        this.add(sortLabel);
        this.add(partialLabel);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Input());
    }

    // Constructs a tree from ground up
    private void buildTree() {
        if(Data.showPartialTree) {
            if (Data.visibleTree == TreeEnum.BST){
                Data.partialTree = new Tree();
            } else if (Data.visibleTree == TreeEnum.AVL) {
                Data.partialTree = new AVLTree();
            } else if (Data.visibleTree == TreeEnum.MIN_HEAP) {
                Data.partialTree = new MinHeap();
            }
            for (int i = 0; i < Data.leafStep.get(Data.visibleTree); i++) {
                Node node = Data.nodeList.get(i);
                Data.partialTree.add(node);
            }
        } else {
            switch (Data.visibleTree) {
                case BST:
                    Data.trees.put(Data.visibleTree, new Tree());
                    break;
                case AVL:
                    Data.trees.put(Data.visibleTree, new AVLTree());    
                    break;
                case MIN_HEAP:
                    Data.trees.put(Data.visibleTree, new MinHeap());        
                    break;
                default:            
                    Data.trees.put(Data.visibleTree, new Tree());
                    break;
            }
            for (Node node : Data.nodeList) {
                Data.trees.get(Data.visibleTree).add(node);
            }
        }
        Data.panel.repaint();
    }
    
    public void show(TreeEnum treeEnum) {
        Data.visibleTree = treeEnum;
        Data.panel = new TreePanel(this);
        
        this.add(new JScrollPane(Data.panel));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
