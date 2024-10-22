package com.fmt;

/**
 *  This is a class that models an Address.
 *  
 *  Name: Peter Morales
 *  Date: 2023-02-23
 *
 */
public class Address {
	
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String country;
	
	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	@Override
	public String toString() {
		return this.street + "\n" + "     " + this.city + " " + this.state + " " + this.zip + " " + this.country;
	}

	public String getStreet() {
		return this.street;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getZip() {
		return this.zip;
	}

	public String getCountry() {
		return this.country;
	}
	
}
