package com.fmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Processes a comma-separated value (CSV) file of Person data
 * 
 * Name: Peter Morales
 * Date: 2023-02-23
 *
 */
public class PersonParser {
	
	public static Map<String, Person> parseDataFile(String filename) {
		List<Person> person = new ArrayList<Person>();
		File f = new File(filename);
		try(Scanner s = new Scanner(f)) {
			int numLines = Integer.parseInt(s.nextLine().trim());
			for(int i = 1; i <= numLines; i++) {
				String line = s.nextLine();
				Person a = null;
				String tokens[] = line.split(",");
				String personCode = tokens[0];
				String lastName = tokens[1];
				String firstName = tokens[2];
				String street = tokens[3];
				String city = tokens[4];
				String state = tokens[5];
				String zip = tokens[6];
				String country = tokens[7];
				Address address = new Address(street, city, state, zip, country);
				List<String> emails = new ArrayList<String>();
				if(tokens.length == 8) {
					a = new Person(personCode, lastName, firstName, address, emails);
				} else {
					for(int j = 8; j < tokens.length; j++) {
						emails.add(tokens[j]);
						a = new Person(personCode, lastName, firstName, address, emails);
					}
				}
				person.add(a);
			}
			s.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Map<String, Person> personMap = new HashMap<>();
		for(Person p : person) {
			personMap.put(p.getPersonCode(), p);
		}
		return personMap;
	}
	
}
