package com.fmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Processes a comma-separated value (CSV) file of Store data
 * 
 * Name: Peter Morales
 * Date: 2023-02-23
 *
 */
public class StoreParser {
	
	public static Map<String, Store> parseDataFile(String filename, Map<String, Person> person) {
		List<Store> store = new ArrayList<Store>();
		File f = new File(filename);
		try(Scanner s = new Scanner(f)) {
			int numLines = Integer.parseInt(s.nextLine().trim());
			for(int i = 1; i <= numLines; i++) {
				String line = s.nextLine();
				Store b = null;
				String tokens[] = line.split(",");
				String storeCode = tokens[0];
				Person people = person.get(tokens[1]);
				String street = tokens[2];
				String city = tokens[3];
				String state = tokens[4];
				String zip = tokens[5];
				String country = tokens[6];
				Address address = new Address(street, city, state, zip, country);
				b = new Store(storeCode, people, address);
				store.add(b);
			}
			s.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Map<String, Store> storeMap = new HashMap<>();
		for(Store s : store) {
			storeMap.put(s.getStoreCode(), s);
		}
		return storeMap;
	}
	
}
