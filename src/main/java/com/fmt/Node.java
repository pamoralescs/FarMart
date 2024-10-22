package com.fmt;

/**
 * This class models a Node
 * 
 * Name: Peter Morales 
 * Date: 2023-05-04
 * 
 */
public class Node<T> {
	private Node<T> next;
	private T data;

	public Node(T data) {
		this.data = data;
		this.next = null;
	}

	public T getData() {
		return data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

}
