package com.fmt;

import java.util.List;
import java.util.ArrayList;

/**
 * This class models a Person
 * 
 * Name: Peter Morales
 * Date: 2023-02-23
 *
 */
public class Person {

	private final String personCode;
	private final String firstName;
	private final String lastName;
	private final Address address;
	private List<String> emails = new ArrayList<>();
	
	public Person(String personCode, String lastName, String firstName, Address address, List<String> emails) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}
	
	@Override
	public String toString() {
		return this.lastName + ", " + this.firstName + " ("+this.personCode + " : " + this.emails + ")\n" 
				+ "     " + this.address;
	}

	public String getPersonCode() {
		return this.personCode;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public Address getAddress() {
		return this.address;
	}

	public List<String> getEmails() {
		return this.emails;
	}
	
	public void addEmail(String emails) {
		this.emails.add(emails);
	}
	
}
