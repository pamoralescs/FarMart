package com.fmt;

/**
 *  This is a class for modeling Equipment.
 * 
 *	Name: Peter Morales
 *	Date: 2023-02-23
 * 
 */
public class Equipment extends Item {
	
	private final Integer itemId;
	private final String model;

	public Equipment(String code, String name, String model) {
		super(code, name);
		this.itemId = null;
		this.model = model;
	}
	
	public Equipment(Integer itemId, String code, String name, String model) {
		super(code, name);
		this.itemId = itemId;
		this.model = model;
	}
	
	@Override
	public String toString() {
		return this.model;
	}
	
	public Integer getItemId() {
		return itemId;
	}

	public String getModel() {
		return this.model;
	}

	// Equipment is an "intermediate" class for these methods
	// and will throw exceptions because they should not be used. 
	@Override
	public double getTotal() {
		throw new UnsupportedOperationException("Unsupported Operation");
	}

	@Override
	public double getTaxes() {
		throw new UnsupportedOperationException("Unsupported Operation");
	}

	@Override
	public double getSubTotal() {
		throw new UnsupportedOperationException("Unsupported Operation");
	}


}
