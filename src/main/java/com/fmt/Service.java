package com.fmt;

/**
 *  This is a class that models a Service
 *  
 *  Name: Peter Morales
 *  Date: 2023-02-23
 *
 */
public class Service extends Item {
	
	private final double hourlyRate;
	private final double hours;

	public Service(String code, String name, double hourlyRate) {
		super(code, name);
		this.hourlyRate = hourlyRate;
		this.hours = 0;
	}

	public Service(String code, String name, double hourlyRate, double hours) {
		super(code, name);
		this.hourlyRate = hourlyRate;
		this.hours = hours;
	}
	
	public Service(Integer itemId, String code, String name, double hourlyRate, double hours) {
		super(code, name);
		this.hourlyRate = hourlyRate;
		this.hours = hours;
	}

	@Override
	public String toString() {
		return getItemCode() + "     (Service) " + getName() + "\n     " + this.hours + " hours @ " + String.format("$%5.2f", this.hourlyRate) + "/hr";
	}

	public double getHourlyRate() {
		return this.hourlyRate;
	}

	public double getHours() {
		return this.hours;
	}

	@Override
	public double getTotal() {
		return getSubTotal() + getTaxes();
	}

	@Override
	public double getTaxes() {
		return Math.round(getSubTotal() * 0.0345 * 100) / 100.0;
	}

	@Override
	public double getSubTotal() {
		return Math.round(getHourlyRate() * getHours() * 100) / 100.0;
	}


}
