package com.fmt;

/**
 *  This is a class to model a Purchase.
 * 
 *	Name: Peter Morales
 *	Date: 2023-03-08
 * 
 */
public class Purchase extends Equipment {
	
	private final double price;

	public Purchase(String code, String name, String model, double price) {
		super(code, name, model);
		this.price = price;
	}
	
	@Override
	public String toString() {
		return getItemCode() + "     (Purchase) " + getName() + " - " + getModel();
	}

	public double getPrice() {
		return this.price;
	}
	
	@Override
	public double getTotal() {
		return getSubTotal() + getTaxes();
	}

	@Override
	public double getTaxes() {
		return 0.0;
	}

	@Override
	public double getSubTotal() {
		return Math.round(getPrice() * 100) / 100.0;
	}
	
}
