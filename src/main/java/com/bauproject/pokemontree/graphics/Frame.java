package com.bauproject.pokemontree.graphics;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.bauproject.pokemontree.structures.*;
import com.bauproject.pokemontree.Data;
import com.bauproject.pokemontree.Input;

import java.awt.event.ActionListener;
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
        Button changeOrderButton = new Button("Change Type");
        changeOrderButton.setBounds(50,50,100,50);

        changeOrderButton.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
            if (Data.visibleTree instanceof AVLTree) {
                Data.visibleTree = Data.bstTree;
            }else {
                Data.visibleTree = Data.avlTree;
            }
            Data.panel.repaint();
            }
        });
        
        changeOrderButton.setFocusable(false);
        
        this.add(changeOrderButton);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new Input());
    }
    
    public void show(TreeEnum treeEnum) {
        switch (treeEnum) {
            case BST:
                Data.visibleTree = Data.bstTree;
                break;
                
            case AVL:
                Data.visibleTree = Data.avlTree;
                break;
                
            default:
                Data.visibleTree = new Tree();
                break;
        }
        Data.panel = new TreePanel(this);
        
        this.add(new JScrollPane(Data.panel));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
