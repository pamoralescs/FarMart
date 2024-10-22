package com.fmt;

/**
 *  This is a class that models Products.
 *  
 *  Name: Peter Morales
 *  Date: 2023-02-23
 *
 */
public class Product extends Item {
	
	private final Integer itemId;
	private final String unit;
	private final double unitPrice;
	private final int quantity;
	
	public Product(String code, String name, String unit, double unitPrice) {
		super(code,  name);
		this.itemId = null;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantity = 0;
	}

	public Product(String code, String name, String unit, double unitPrice, int quantity) {
		super(code, name);
		this.itemId = null;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	public Product(Integer itemId, String code, String name, String unit, double unitPrice, int quantity) {
		super(code, name);
		this.itemId = itemId;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return getItemCode() + "     (Product) " + getName() + "\n     " + this.quantity + " @ " + String.format("$%5.2f", this.unitPrice) + "/" + this.unit;
	}

	public Integer getItemId() {
		return itemId;
	}

	public String getUnit() {
		return this.unit;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public double getTotal() {
		return getSubTotal() + getTaxes();
	}

	@Override
	public double getTaxes() {
		return Math.round((getSubTotal() * 0.0715) * 100) / 100.0;
	}

	@Override
	public double getSubTotal() {
		return Math.round((getQuantity() * getUnitPrice()) * 100) / 100.0;
	}

	
}
