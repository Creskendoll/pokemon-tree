package com.bauproject.pokemontree.structures;

import java.util.ArrayList;

// https://www.geeksforgeeks.org/avl-tree-set-1-insertion/
// Java program for insertion in AVL Tree 
public class AVLTree extends Tree { 

    Node root; 
    
    public AVLTree() {
        this.root = null;
    }
    
    @Override
    public Node getRoot() {
        return this.root;
    }

	// A utility function to get the height of the tree 
	long height(Node N) { 
		if (N == null) 
			return 0; 

		return N.getDepth();
    }
    
    @Override
    public int maxDepth() {
        return this.maxDepth(this.root);
    }

	// A utility function to get maximum of two integers 
	private long max(long a, long b) { 
		return (a > b) ? a : b;
	}

	private Node recursiveChangeDepthBy(Node node, long val) {
		if (node != null) {
			node.depth += val;
			return node;
		}
		else {
			node.right = recursiveChangeDepthBy(node.right, (long)Math.pow(2, node.depth));
			node.left = recursiveChangeDepthBy(node.left, val);
		}
		return node;
	}

	private Node recursiveChangeIndexBy(Node node, long val) {
		if (node == null)
			return node;
		else {
			node.index += val;
			if (node.right != null)
				node.right = recursiveChangeIndexBy(node.right, (long)Math.pow(2, node.depth));
			if (node.left != null)
				node.left = recursiveChangeIndexBy(node.left, (long)Math.pow(2, node.depth));
		}
		return node;
	}

	// A utility function to right rotate subtree rooted with y 
	// See the diagram given above. 
	private Node rightRotate(Node y) { 
		Node x = y.left; 
		Node T2 = x.right; 
		 
		// Update node index
		y = recursiveChangeIndexBy(y, (long)Math.pow(2,y.depth));
		y.index += 1;
		
		x.right = recursiveChangeIndexBy(y.right, y.depth);
		x.left = recursiveChangeIndexBy(x.left, y.depth);
		
		if (T2 != null)
			T2.index += 1;

		// Perform rotation 
		x.right = y;
		y.left = T2; 

		y.depth = max(height(y.left), height(y.right)) + 1; 
		x.depth = max(height(x.left), height(x.right)) + 1; 

		// Return new root 
		return x; 
	} 

	// A utility function to left rotate subtree rooted with x 
	// See the diagram given above. 
	private Node leftRotate(Node x) { 
		Node y = x.right;
		Node T2 = y.left;

		// Update node index
		if(y != null)
			y.index -= 1;
			
		y.right  = recursiveChangeIndexBy(y.right, -x.depth);
		y.left  = recursiveChangeIndexBy(y.left, -x.depth);
		
		if (T2 != null)
			T2.index -= 2;

		// Perform rotation 
		y.left = x;
		x.right = T2;
		
		// Update heights 
		x.depth = max(height(x.left), height(x.right)) + 1; 
		y.depth = max(height(y.left), height(y.right)) + 1;
		
		// Return new root 
		return y; 
	} 

	// Get Balance factor of node N 
	private long getBalance(Node N) { 
		if (N == null) 
			return 0; 

		return height(N.left) - height(N.right); 
	} 
    
	private Node addRecursive(Node node, Color color, String imgName, ColorEnum type, long index, long depth) { 

		/* 1. Perform the normal BST insertion */
        if (node == null) {
            // Read image files
            return new Node(color, imgName, index, depth);
            // Don't read image files 
            // return new Node(color, index, depth);
        }

        ColorEnum result = null;
        switch (type) {
            case BRIGHTNESS:
                result = color.compareBrightness(node.color);
                break;
            case SATURATION:
                result = color.compareSaturation(node.color);
                break;
            case HUE:
                result = color.compareHue(node.color);
                break;
            default:
                result = ColorEnum.EQUAL;
                break;
        }

		if (result == ColorEnum.LOWER) 
            node.left = addRecursive(node.left, color, imgName, type, (node.getIndex() * 2) - 1, depth);
		else if (result == ColorEnum.HIGHER) 
            node.right = addRecursive(node.right, color, imgName, type, node.getIndex() * 2, depth);
		else // Duplicate keys not allowed 
			return node;

		/* 2. Update height of this ancestor node */
		node.depth = 1 + max(height(node.left), 
							height(node.right)); 

		/* 3. Get the balance factor of this ancestor 
			node to check whether this node became 
			unbalanced */
		long balance = getBalance(node); 

        // TODO: depend on type
		// If this node becomes unbalanced, then there 
		// are 4 cases Left Left Case 
		if (balance > 1 && color.compareBrightness(node.left.color) == ColorEnum.LOWER) 
			return rightRotate(node);

		// Right Right Case 
		if (balance < -1 && color.compareBrightness(node.right.color) == ColorEnum.HIGHER) 
			return leftRotate(node);

		// Left Right Case 
		if (balance > 1 && color.compareBrightness(node.left.color) == ColorEnum.HIGHER) { 
			node.left = leftRotate(node.left);
			return rightRotate(node);
		} 

		// Right Left Case 
		if (balance < -1 && color.compareBrightness(node.right.color) == ColorEnum.LOWER) { 
			node.right = rightRotate(node.right); 
			return leftRotate(node); 
		} 

		/* return the (unchanged) node pointer */
		return node; 
    }

    @Override
    public void add(Color color, String imgName, ColorEnum type, int index, int depth) {
        this.root = addRecursive(this.root, color, imgName, type, index, depth);
	}
	@Override
    public ArrayList<Node> toList() {
        return this.traverseInOrder(this.root);
    }
} 
// This code has been contributed by Mayank Jaiswal 
