package com.bauproject.pokemontree.structures;

import java.util.ArrayList;

import com.bauproject.pokemontree.Data;

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
		if (node == null)
			return node;
		else {
			node.depth = val;
			node.right = recursiveChangeDepthBy(node.right, val+1);
			node.left = recursiveChangeDepthBy(node.left, val+1);
		}
		return node;
	}

	private Node recursiveChangeIndexBy(Node node, long val) {
		if (node == null)
			return node;
		else {
			node.index = val;
			node.left = recursiveChangeIndexBy(node.left, (node.index * 2) - 1);
			node.right = recursiveChangeIndexBy(node.right, (node.index * 2));
		}
		return node;
	}

	// A utility function to right rotate subtree rooted with y 
	// See the diagram given above. 
	private Node rightRotate(Node y) { 
		Node x = y.left; 
		Node T2 = x.right; 
		 
		// Perform rotation 
		x.right = y;
		y.left = T2;
		
		// update depth
		y.depth = max(height(y.left), height(y.right)) + 1; 
		x.depth = max(height(x.left), height(x.right)) + 1; 
		
		x = recursiveChangeIndexBy(x, x.index);

		// Return new root 
		return x; 
	} 

	// A utility function to left rotate subtree rooted with x 
	// See the diagram given above. 
	private Node leftRotate(Node x) { 
		Node y = x.right;
		Node T2 = y.left;
		
		// Perform rotation 
		y.left = x;
		x.right = T2;

		// Update heights 
		x.depth = max(height(x.left), height(x.right)) + 1; 
		y.depth = max(height(y.left), height(y.right)) + 1;

		y = recursiveChangeIndexBy(y, y.index);

		// Return new root 
		return y;
	} 

	// Get Balance factor of node N 
	private long getBalance(Node N) { 
		if (N == null) 
			return 0; 

		return height(N.left) - height(N.right); 
	} 
    
	private Node addRecursive(Node node, Color color, String imgName, long index, long depth) { 

		/* 1. Perform the normal BST insertion */
        if (node == null) {
            // Read image files
            return new Node(color, imgName, index, depth);
            // Don't read image files 
            // return new Node(color, index, depth);
        }

        ColorEnum result = null;
        switch (Data.sortBy) {
            case BRIGHTNESS:
                result = color.compareBrightness(node.color);
                break;
            case SATURATION:
                result = color.compareSaturation(node.color);
                break;
            case HUE:
                result = color.compareHue(node.color);
				break;
			case RED:
				result = color.compareRGB(node.color)[0];
				break;
			case GREEN:
				result = color.compareRGB(node.color)[1];
				break;
			case BLUE:
				result = color.compareRGB(node.color)[2];
				break;
            default:
                result = ColorEnum.EQUAL;
                break;
        }

		if (result == ColorEnum.LOWER) {
            node.left = addRecursive(node.left, color, imgName, (node.getIndex() * 2) - 1, depth);
			// node.left = recursiveChangeIndexBy(node.left, node.left.index);
		}
		else if (result == ColorEnum.HIGHER) {
			node.right = addRecursive(node.right, color, imgName, node.getIndex() * 2, depth);
			// node.right = recursiveChangeIndexBy(node.right, node.right.index);
		}
		else {
			// Duplicate keys not allowed
			// return recursiveChangeIndexBy(node, node.index);
			return node;
		} 

		/* 2. Update height of this ancestor node */
		node.depth = 1 + max(height(node.left), 
							height(node.right)); 

		/* 3. Get the balance factor of this ancestor 
			node to check whether this node became 
			unbalanced */
		long balance = getBalance(node); 

		// If this node becomes unbalanced, then there 
		// are 4 cases Left Left Case 
		if (balance > 1 && color.compareBrightness(node.left.color) == ColorEnum.LOWER) {
			Node n = rightRotate(node);
			// return recursiveChangeIndexBy(n, n.index);
			return n;
		} 
			
		// Right Right Case 
		if (balance < -1 && color.compareBrightness(node.right.color) == ColorEnum.HIGHER) {
			Node n = leftRotate(node);
			// return recursiveChangeIndexBy(n, n.index);
			return n;
		} 

		// Left Right Case 
		if (balance > 1 && color.compareBrightness(node.left.color) == ColorEnum.HIGHER) { 
			node.left = leftRotate(node.left);
			Node n = rightRotate(node); 
			// return recursiveChangeIndexBy(n, n.index);
			return n;
		} 

		// Right Left Case 
		if (balance < -1 && color.compareBrightness(node.right.color) == ColorEnum.LOWER) { 
			node.right = rightRotate(node.right); 
			Node n = leftRotate(node); 
			// return recursiveChangeIndexBy(n, n.index); 
			return n;
		}

		/* return the (unchanged) node pointer */
		// return recursiveChangeIndexBy(node, node.index);
		return node; 
    }

	@Override
	public TreeEnum getType(){
		return TreeEnum.AVL;
	}

    @Override
    public void add(Color color, String imgName, int index, int depth) {
        this.root = addRecursive(this.root, color, imgName, index, depth);
	}
    @Override
    public void add(Node node) {
        this.root = addRecursive(this.root, node.color, node.imgName, node.index, node.depth);
	}
	@Override
    public ArrayList<Node> toList() {
        return this.traverseInOrder(this.root);
    }
} 
// This code has been contributed by Mayank Jaiswal 
