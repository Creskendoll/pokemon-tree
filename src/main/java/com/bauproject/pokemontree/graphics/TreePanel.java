package com.bauproject.pokemontree.graphics;

import com.bauproject.pokemontree.structures.*;
import com.bauproject.pokemontree.Data;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TreePanel extends JPanel {
    private static final long serialVersionUID = -7101644392759127265L;
    int treeDepth;
    private float scale = 1;
    private Point mousePt;
    AffineTransform at = new AffineTransform();

    int canvasWidth = 4000;
    int canvasHeight = 2000;

    public TreePanel(JFrame f) {
        super();
        // initial position
        at.translate(-1336f, 10f);
        final JFrame frame = f;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        setBackground(Color.darkGray);

        this.addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = -0.05f * e.getPreciseWheelRotation();
                scale += delta;
                if(scale == 0)
                    scale = 1;
                
                Point mouse_location = MouseInfo.getPointerInfo().getLocation();
                // Point windowLocation = frame.getLocation();
                // Point frameCenter = new Point((int)frame.getLocation().getX() + getWidth()/2, (int)frame.getLocation().getY() + getHeight()/2);
                Point frameCenter = new Point((int)frame.getLocation().getX() + getWidth()/2, (int)frame.getLocation().getY() + getHeight()/2);
                
                // System.out.printf("Mouse X:%.2f\nMouse Y:%.2f\n", mouse_location.getX(), mouse_location.getY());
                // System.out.printf("Window X:%.2f\nWindow Y:%.2f\n", windowLocation.getX(), windowLocation.getY());
                AffineTransform _at = new AffineTransform();
                _at.setToScale(scale, scale);

                // calculate translation
                double dx = mouse_location.getX() - frameCenter.getX();
                double dy = mouse_location.getY() - frameCenter.getY();
                double transformX = at.getTranslateX() - dx;
                double transformY = at.getTranslateY() - dy;
                // _at.translate((at.getTranslateX()-dx)*scale, (at.getTranslateY()-dy)*scale);
                _at.translate(transformX*scale, transformY*scale);
                
                // set old transform
                at = _at;
                revalidate();
                repaint();
            }
        });
        
        
        this.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e != null && mousePt != null) {
                    int dx = e.getX() - mousePt.x;
                    int dy = e.getY() - mousePt.y;
                    at.translate(dx*1/scale, dy*1/scale);
                    mousePt = e.getPoint();
                    // String log = String.format("X: %f, \n Y: %f", at.getTranslateX(), at.getTranslateY());
                    // System.out.println(log);
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int img_width =  (int)(Data.nodeSize * scale);
        int img_height = (int)(Data.nodeSize * scale);
        
        Graphics2D g2 = (Graphics2D) g.create();
        // g2.setColor(Color.darkGray);
        // g2.fillRect(0, 0, canvasWidth, canvasHeight);
        g2.setTransform(at);

        // Name of the tree
        // g2.setColor(Color.white);
        // g2.drawString(Data.visibleTree.name(), W/2, 20);
        int treeDepth = 0;
        
        List<Node> iterList = null;
        if(Data.showPartialTree) { 
            iterList = Data.partialTree.toList();
            treeDepth = Data.partialTree.maxDepth();
        } else {
            Tree t = Data.trees.get(Data.visibleTree);
            iterList = t.toList();
            treeDepth =  t.maxDepth();
        }

        if(treeDepth > 0) {
            for(Node node : iterList) {
                Point node_position = node.getPosition(canvasWidth, canvasHeight, treeDepth);  
                int node_X = (int)node_position.getX();
                int node_Y = (int)node_position.getY();

                // Draw branches
                if (node.getLeft() != null) {
                    Point left_position = node.getLeft().getPosition(canvasWidth, canvasHeight, treeDepth);
                    int left_X = (int)left_position.getX();
                    int left_Y = (int)left_position.getY();
                    g2.setColor(Color.cyan);
                    g2.drawLine(node_X+img_width/2, node_Y+img_height/2, left_X+img_width/2, left_Y+img_height/2);
                }
                if (node.getRight() != null) {
                    Point right_position = node.getRight().getPosition(canvasWidth, canvasHeight, treeDepth);
                    int right_X = (int)right_position.getX();
                    int right_Y = (int)right_position.getY();
                    g2.setColor(Color.cyan);
                    g2.drawLine(node_X+img_width/2, node_Y+img_height/2, right_X+img_width/2, right_Y+img_height/2);
                }
                
                // Positions for consecutive stacking
                // int img_X = ((node_index * img_width) % (int) Math.ceil((double)(getWidth() - img_width)));
                // int img_Y = (((node_index * img_height) / (int) Math.ceil((double)(getWidth() - img_width))) * img_height);
                
                // Draw nodes
                if (!Data.showNodeColors)
                    g2.drawImage(node.getImage(), node_X, node_Y, img_width, img_height, this);        
                else {
                    g2.setColor(node.getColor().toJavaColor());
                    g2.fillOval(node_X, node_Y, img_width, img_height);
                }
                
                // g2.setColor(Color.white);
                // String indexInfo = "Index: " + String.valueOf(node.getIndex()); 
                // String depthInfo = "Depth: " + String.valueOf(node.getDepth());
                // g2.drawString(indexInfo, node_X, node_Y + img_height+20);
                // g2.drawString(depthInfo, node_X, node_Y + img_height+35);
            }
        }
        g2.dispose();
    }


}