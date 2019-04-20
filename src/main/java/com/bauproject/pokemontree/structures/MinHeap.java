package com.bauproject.pokemontree.structures;

import java.util.ArrayList;
import java.util.Arrays;

public class MinHeap extends Tree{
	private Node[] Heap; 
	private int size; 
	private int maxsize = 801; 

	private static final int FRONT = 1; 

	public MinHeap() 
	{ 
		this.size = 0;
		Heap = new Node[this.maxsize + 1];
		Heap[0] = new Node(new Color(0,0,0), "0001.png", 1, 0);
	}

	// Function to return the position of 
	// the parent for the node currently 
	// at pos 
	private int parent(int pos) 
	{ 
		return pos / 2; 
	} 

	// Function to return the position of the 
	// left child for the node currently at pos 
	private int leftChild(int pos) 
	{ 
		return (2 * pos); 
	} 

	// Function to return the position of 
	// the right child for the node currently 
	// at pos 
	private int rightChild(int pos) 
	{ 
		return (2 * pos) + 1; 
	} 

	// Function that returns true if the passed 
	// node is a leaf node 
	private boolean isLeaf(int pos) 
	{ 
		if (pos >= (size / 2) && pos <= size) { 
			return true; 
		} 
		return false; 
	} 

	// Function to swap two nodes of the heap 
	private void swap(int fpos, int spos) 
	{ 
		Node tmp;
		tmp = Heap[fpos];
		Heap[fpos].index = Heap[spos].index;
		Heap[fpos].depth = Heap[spos].depth;
		Heap[fpos] = Heap[spos];
		Heap[spos].index = tmp.index;
		Heap[spos].depth = tmp.depth;
		Heap[spos] = tmp;
	}

	// Function to heapify the node at pos 
	private void minHeapify(int pos) 
	{ 

		// If the node is a non-leaf node and greater 
		// than any of its child 
		if (!isLeaf(pos)) {
			ColorEnum compareResult0 = Heap[pos].color.compare(Heap[leftChild(pos)].color);
			ColorEnum compareResult1 = Heap[pos].color.compare(Heap[rightChild(pos)].color);
			
			if (compareResult0 == ColorEnum.HIGHER 
				|| compareResult1 == ColorEnum.HIGHER ) {
				
				ColorEnum compareResult2 = Heap[leftChild(pos)].color.compare(Heap[rightChild(pos)].color); 

				// Swap with the left child and heapify 
				// the left child 
				if (compareResult2 == ColorEnum.LOWER) { 
					swap(pos, leftChild(pos)); 
					minHeapify(leftChild(pos)); 
				} 

				// Swap with the right child and heapify 
				// the right child 
				else { 
					swap(pos, rightChild(pos)); 
					minHeapify(rightChild(pos)); 
				} 
			} 
		} 
	} 

	// Function to insert a node into the heap 
	public void insert(Node element) 
	{
		Node parentNode = Heap[size];
		element.depth = parentNode.depth + 1;
		element.index = parentNode.left == null ? (parentNode.index * 2) - 1 
												: (parentNode.index * 2);
		Heap[++size] = element;
		int current = size; 

		ColorEnum compareResult = Heap[current].color.compare(Heap[parent(current)].color); 
		while (compareResult == ColorEnum.LOWER) { 
			swap(current, parent(current));
			current = parent(current);
			compareResult = Heap[current].color.compare(Heap[parent(current)].color);
		} 
	} 

	// Function to print the contents of the heap 
	public void print() 
	{ 
		for (int i = 1; i <= size / 2; i++) { 
			System.out.print(" PARENT : " + Heap[i] 
					+ " LEFT CHILD : " + Heap[2 * i] 
				+ " RIGHT CHILD :" + Heap[2 * i + 1]); 
			System.out.println(); 
		} 
	} 

	@Override
    public ArrayList<Node> toList() {
		Node[] headlessArr = new Node[Heap.length];
		System.arraycopy(Heap, 1, headlessArr, 0, Heap.length-1);
        return new ArrayList<Node>(Arrays.asList(headlessArr));
	}
	@Override
    public void add(Node node) {
        insert(node);
    }

	// Function to build the min heap using 
	// the minHeapify 
	public void minHeap() 
	{ 
		for (int pos = (size / 2); pos >= 1; pos--) { 
			minHeapify(pos); 
		} 
	} 

	// Function to remove and return the minimum 
	// element from the heap 
	public Node remove() 
	{ 
		Node popped = Heap[FRONT]; 
		Heap[FRONT] = Heap[size--]; 
		minHeapify(FRONT); 
		return popped; 
	}
} 
