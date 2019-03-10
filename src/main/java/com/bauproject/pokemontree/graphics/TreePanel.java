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

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TreePanel extends JPanel {
    private static final long serialVersionUID = -7101644392759127265L;
    int treeDepth;
    private float scale = 1;
    private Point mousePt;
    AffineTransform at = new AffineTransform();

    private static final int W = 1360;
    private static final int H = 720;

    int canvasWidth = 4000;
    int canvasHeight = 720;

    public TreePanel(JFrame f) {
        this.treeDepth = Data.visibleTree.maxDepth();
        final JFrame frame = f;

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
                double dx = mouse_location.getX() - frameCenter.getX();
                double dy = mouse_location.getY() - frameCenter.getY();
                
                // System.out.printf("Mouse X:%.2f\nMouse Y:%.2f\n", mouse_location.getX(), mouse_location.getY());
                // System.out.printf("Window X:%.2f\nWindow Y:%.2f\n", windowLocation.getX(), windowLocation.getY());
                AffineTransform _at = new AffineTransform();
                _at.setToScale(scale, scale);
                _at.translate(-dx*1/scale, -dy*1/scale);
                // System.out.printf("Delta X:%.2f\nDelta Y:%.2f\n", dx, dy);
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
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                at.translate(dx*1/scale, dy*1/scale);
                mousePt = e.getPoint();
                repaint();
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (int)((float)W * scale);
        int h = (int)((float)H * scale);
        Dimension size = new Dimension(w, h);
        return size;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int img_width =  (int)(30f * scale);
        int img_height = (int)(30f * scale);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.darkGray);
        g2.fillRect(0, 0, canvasWidth, canvasHeight);
        
        g2.setTransform(at);
        for(Node node : Data.visibleTree.toList()) {
            Point node_position = node.getPosition(canvasWidth, canvasHeight, this.treeDepth);  
            int node_X = (int)node_position.getX();
            int node_Y = (int)node_position.getY();
            // Draw branches
            if (node.getLeft() != null) {
                Point left_position = node.getLeft().getPosition(canvasWidth, canvasHeight, this.treeDepth);
                int left_X = (int)left_position.getX();
                int left_Y = (int)left_position.getY();
                g2.setColor(Color.cyan);
                g2.drawLine(node_X+img_width/2, node_Y+img_height/2, left_X+img_width/2, left_Y+img_height/2);
            }
            if (node.getRight() != null) {
                Point right_position = node.getRight().getPosition(canvasWidth, canvasHeight, this.treeDepth);
                int right_X = (int)right_position.getX();
                int right_Y = (int)right_position.getY();
                g2.setColor(Color.cyan);
                g2.drawLine(node_X+img_width/2, node_Y+img_height/2, right_X+img_width/2, right_Y+img_height/2);
            }
            
            // Positions for consecutive stacking
            // int img_X = ((node_index * img_width) % (int) Math.ceil((double)(getWidth() - img_width)));
            // int img_Y = (((node_index * img_height) / (int) Math.ceil((double)(getWidth() - img_width))) * img_height);
            
            // Draw nodes
            g2.setColor(node.getColor().toJavaColor());
            // g.fillOval(img_X, img_Y, node_width, node_height);
            // g2.fillOval(node_X, node_Y, img_width, img_height);
            g2.drawImage(node.getImage(), node_X, node_Y, img_width, img_height, this);        
            
            // String debugInfo = "Depth: " + String.valueOf(node.getDepth()) + "  Index: " + String.valueOf(node.getIndex()); 
            // g.drawString(debugInfo, tree_node_x, tree_node_y + node_height+20);
        }
        g2.dispose();
    }


}