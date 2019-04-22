package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
        Data.panel = new TreePanel(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1360, 720));
        setBackground(Color.black);

        ImageIcon icon = new ImageIcon("./icon.png");
        this.setIconImage(icon.getImage());

        this.setLayout(new BorderLayout());
        // Tree types
        JComboBox<String> treeTypesChoice = new JComboBox<String>();
        treeTypesChoice.addItem(TreeEnum.BST.toString());
        treeTypesChoice.addItem(TreeEnum.AVL.toString());
        treeTypesChoice.addItem(TreeEnum.MIN_HEAP.toString());
        // Sort by options
        JComboBox<String> sortByChoice = new JComboBox<String>();
        sortByChoice.addItem(ColorEnum.BRIGHTNESS.toString());
        sortByChoice.addItem(ColorEnum.HUE.toString());
        sortByChoice.addItem(ColorEnum.SATURATION.toString());
        sortByChoice.addItem(ColorEnum.RED.toString());
        sortByChoice.addItem(ColorEnum.GREEN.toString());
        sortByChoice.addItem(ColorEnum.BLUE.toString());
        // Partial Tree
        JCheckBox showPartialCheck = new JCheckBox("Show Partial? (Press Space)");
        // Show node colors chech
        JCheckBox showNodeColorsCheck = new JCheckBox("Show node colors?");
        // Size slider
        // https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html
        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL,
        20, 170, 30);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setPaintTicks(true);
        Hashtable<Object, Object> labelTable = new Hashtable<Object, Object>();
        labelTable.put( Integer.valueOf( 20 ), new JLabel("Small") );
        labelTable.put( Integer.valueOf( 170 ), new JLabel("Big") );
        sizeSlider.setLabelTable( labelTable );
        sizeSlider.setPaintLabels(true);
        
        treeTypesChoice.setBorder(BorderFactory.createRaisedBevelBorder());
        showNodeColorsCheck.setBorder(BorderFactory.createRaisedBevelBorder());
        showPartialCheck.setBorder(BorderFactory.createRaisedBevelBorder());
        sortByChoice.setBorder(BorderFactory.createRaisedBevelBorder());
        sizeSlider.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Switch between partial and full view
        showPartialCheck.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                Data.showPartialTree = e.getStateChange() == ItemEvent.SELECTED;
                Data.panel.repaint();
            }
        });

        showNodeColorsCheck.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                Data.showNodeColors = e.getStateChange() == ItemEvent.SELECTED;
                Data.panel.repaint();
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
        showNodeColorsCheck.setFocusable(false);

        // Add components to view 
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1,5));
        controlPanel.setBackground(Color.cyan);
        
        // controlPanel.add(Data.panel);
        controlPanel.add(treeTypesChoice);
        controlPanel.add(sortByChoice);
        controlPanel.add(showNodeColorsCheck);
        controlPanel.add(showPartialCheck);
        controlPanel.add(sizeSlider);
        
        controlPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));;

        add(controlPanel, BorderLayout.NORTH);

        add(Data.panel, BorderLayout.CENTER);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new Input());

        pack();
    }

    // Constructs a tree from ground up
    private void buildTree() {
        if (Data.visibleTree == TreeEnum.BST){
            Data.partialTree = new Tree();
        } else if (Data.visibleTree == TreeEnum.AVL) {
            Data.partialTree = new AVLTree();
        } else if (Data.visibleTree == TreeEnum.MIN_HEAP) {
            Data.partialTree = new MinHeap();
        }
        for (int i = 0; i < Data.leafStep.get(Data.visibleTree); i++) {
            Data.partialTree.add(Data.nodeList.get(i));
        }

        Tree newTree;
        switch (Data.visibleTree) {
            case BST:
                newTree = new Tree();
                break;
            case AVL:
                newTree = new AVLTree();
                break;
            case MIN_HEAP:
                newTree = new MinHeap();
                break;
            default:            
                newTree = new MinHeap();
                break;
        }
        Data.trees.put(Data.visibleTree, newTree);

        for (Node node : Data.nodeList) {
            Data.trees.get(Data.visibleTree).add(node);
        }
        Data.panel.repaint();
    }
    
    public void show(TreeEnum treeEnum) {
        Data.visibleTree = treeEnum;
        this.setVisible(true);
    }
}
