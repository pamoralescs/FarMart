package com.fmt;

/**
 * This class is used to help model Items(Equipment, Product, Service).
 * 
 * Name: Peter Morales
 * Date: 2023-02-23
 *
 */
public abstract class Item {
	
	private final Integer itemId;
	private final String itemCode;
	private final String name;
	
	public Item(String code, String name) {
		this.itemId = null;
		this.itemCode = code;
		this.name = name;
	}
	
	public Item(Integer itemId, String code, String name) {
		this.itemId = itemId;
		this.itemCode = code;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.itemCode + this.name;
	}	
	
	public Integer getItemId() {
		return itemId;
	}

	public String getItemCode() {
		return this.itemCode;
	}

	public String getName() {
		return this.name;
	}
	
	public abstract double getTotal();
	
	public abstract double getTaxes();
	
	public abstract double getSubTotal();
	
	
}
