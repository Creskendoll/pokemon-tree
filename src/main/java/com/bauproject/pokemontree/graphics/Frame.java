package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.bauproject.pokemontree.structures.*;

import com.bauproject.pokemontree.Data;
import com.bauproject.pokemontree.Input;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;


public class Frame extends JFrame
{
    private static final long serialVersionUID = 1L;
    public Frame()  {
        super("Pokemon Tree");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon("./icon.png");
        this.setIconImage(icon.getImage());

        this.setLayout(new BorderLayout());
        Button changeTypeButton = new Button("Change Type");
        Button showAllButton = new Button("Show All/Partial");
        Choice treeTypesChoice = new Choice();
        treeTypesChoice.add(TreeEnum.BST.toString());
        treeTypesChoice.add(TreeEnum.AVL.toString());

        changeTypeButton.setBounds(50,50,100,50);
        showAllButton.setBounds(200,50,100,50);
        treeTypesChoice.setBounds(50,50,100,50);

        changeTypeButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Data.visibleTree == TreeEnum.AVL) {
                    Data.visibleTree = TreeEnum.BST;
                }else {
                    Data.visibleTree = TreeEnum.AVL;
                }
                buildTree();
                Data.panel.repaint();
            }
        });
        showAllButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Data.showPartialTree = !Data.showPartialTree;
                Data.panel.repaint();
            }
        });

        treeTypesChoice.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selection = e.getItem().toString();
                if (selection == TreeEnum.AVL.toString())
                    Data.visibleTree = TreeEnum.AVL;
                else if (selection == TreeEnum.BST.toString())
                    Data.visibleTree = TreeEnum.BST;
                buildTree();
                Data.panel.repaint();
            }
        });
        
        changeTypeButton.setFocusable(false);
        treeTypesChoice.setFocusable(false);
        showAllButton.setFocusable(false);
        
        // this.add(changeTypeButton);
        this.add(showAllButton);
        this.add(treeTypesChoice);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Input());
    }

    private void buildTree() {
        if(Data.showPartialTree) {
            if (Data.visibleTree == TreeEnum.BST){
                Data.partialTree = new Tree();
            } else if (Data.visibleTree == TreeEnum.AVL) {
                Data.partialTree = new AVLTree();
            }
            for (int i = 0; i < Data.leafStep.get(Data.visibleTree); i++) {
                Node node = Data.nodeList.get(i);
                Data.partialTree.add(node);
            }
        }
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
