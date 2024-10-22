package com.fmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *  This is a class to model a Lease.
 * 
 *	Name: Peter Morales
 *	Date: 2023-03-08
 * 
 */
public class Lease extends Equipment {
	
	private final double fee;
	private final LocalDate startDate;
	private final LocalDate endDate;
	
	public Lease(String code, String name, String model, double fee, LocalDate startDate, LocalDate endDate) {
		super(code, name, model);
		this.fee = fee;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return getItemCode() + "     (Lease) " + getName() + " - " + getModel() + "\n     " + (int) getDays() + " days " + "(" + this.startDate + " -> " + this.endDate + ") @ $" + String.format("%5.2f",this.fee) + "/ 30 days";
	}

	public double getFee() {
		return this.fee;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}
	
	public double getDays() {
		double days = ChronoUnit.DAYS.between(this.startDate, this.endDate);
		return days + 1.0;
	}

	@Override
	public double getTotal() {
		return getSubTotal() + getTaxes();
	}

	@Override
	public double getTaxes() {
		if(getSubTotal() >= 10000 && getSubTotal() < 100000) {
			return 500.0;
		} else if(getSubTotal() >= 100000) {
			return 1500.0;
		} else {
			return 0.0;
		}
	}

	@Override
	public double getSubTotal() {
		return Math.round(((getFee() * (getDays()/30))) * 100) / 100.0;
	}
	
	
}
