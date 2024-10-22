package com.fmt;

import java.util.Comparator;
import java.util.Iterator;

/**
 * This class builds a custom LinkedList which takes in a comparator and adds an Iterable interface.
 * It also contains a few convenience methods that adds to the functionality to the LinkedList.
 * 
 * Name: Peter Morales Date: 2023-05-04
 * 
 */
public class LinkedList<T> implements Iterable<T> {
	private int size;
	private Node<T> head;
	private Comparator<T> cmp;

	// This constructor is designed to take in a comparator which will maintain the ordering in the Linked List.
	public LinkedList(Comparator<T> cmp) {
		this.size = 0;
		this.head = null;
		this.cmp = cmp;
	}
	
	// This method supports the getData() and addByIndex() methods by getting a Node at a particular position.
	private Node<T> getNode(int index) {
		Node<T> current = this.head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}

	// This method supports the add() method and adds a node to head of the Linked List.
	private void addToHead(T d) {
		Node<T> newHead = new Node<T>(d);
		newHead.setNext(this.head);
		this.head = newHead;
		this.size++;
	}

	// This method supports the add() method and adds a node to tail of the Linked List.
	private void addToTail(T d) {
		Node<T> newTail = new Node<T>(d);
		if (this.size == 0) {
			this.head = newTail;
			this.size++;
			return;
		}
		Node<T> tail = this.getNode(this.size - 1);
		tail.setNext(newTail);
		this.size++;
		return;
	}
	
	// This method supports the add() method by allowing you to insert into the linked list at a particular index.
	private void addByIndex(T data, int index) {
		if (index == 0) {
			addToHead(data);
		} else if (index == getSize()) {
			addToTail(data);
		} else {
			Node<T> previous = getNode(index - 1);
			Node<T> newNode = new Node<>(data);
			Node<T> current = previous.getNext();

			newNode.setNext(current);
			previous.setNext(newNode);
			this.size++;
		}
	}

	// This method adds data into the linked list.
	public void add(T data) {
		if (this.head == null) {
			addToHead(data);
			return;
		} else {
			Node<T> temp = this.head;
			for (int i = 0; i < getSize(); i++) {
				if (this.cmp.compare(data, temp.getData()) < 0) {
					addByIndex(data, i);
					return;
				} else {
					temp = temp.getNext();
				}
			}
			addToTail(data);
		}
	};

	// This method is here to add functionality by returning the size of the Linked List.
	public int getSize() {
		return this.size;
	}

	// This method is here to add functionality by clearing the entire Linked list.
	public void clear() {
		this.head = null;
		this.size = 0;
	}

	// This method is here to add functionality by returning the data at a given index.
	public T getDataAtIndex(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Invalid index: " + index);
		} else {
			return this.getNode(index).getData();
		}
	}

	// This method is here to add functionality by removing node/data at a particular index.
	public void removeAtIndex(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Invalid index: " + index);
		} else if (index == 0) {
			this.head = this.head.getNext();
			this.size--;
			return;
		} else {
			Node<T> previous = this.getNode(index - 1);
			Node<T> current = previous.getNext();
			Node<T> next = current.getNext();
			previous.setNext(next);
			this.size--;
			return;
		}
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> iterator = new Iterator<T>() {
			Node<T> current = head;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public T next() {
				T data = current.getData();
				current = current.getNext();
				return data;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Not allowed.");
			}
		};
		return iterator;
	}
}
