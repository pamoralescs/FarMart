package com.fmt;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *  Converts java files to json files/format.
 * 
 *  Name: Peter Morales
 *  Date: 2023-02-23
 *
 */
public class DataConverter {
	
	public static final String PERSONS_FILE = "data/Persons.csv";
	public static final String STORES_FILE = "data/Stores.csv";
	public static final String ITEMS_FILE = "data/Items.csv";
	
	public static void main(String args[]) {

		Map<String, Person> people = PersonParser.parseDataFile(PERSONS_FILE);
		Map<String, Store> store = StoreParser.parseDataFile(STORES_FILE, people);
		Map<String, Item> item = ItemParser.parseDataFile(ITEMS_FILE);
		
		GsonBuilder gson = new GsonBuilder();
		gson.setPrettyPrinting();
		Gson peopleOutput = gson.create();
		try {
			PrintWriter pw = new PrintWriter("data/Persons.json");
			pw.printf(peopleOutput.toJson(people.values()));
			pw.close();
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
		
		Gson storeOutput = gson.create();
		try {
			PrintWriter pw = new PrintWriter("data/Stores.json");
			pw.printf(storeOutput.toJson(store.values()));
			pw.close();
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
		
		Gson itemOutput = gson.create();
		try {
			PrintWriter pw = new PrintWriter("data/Items.json");
			pw.printf(itemOutput.toJson(item.values()));
			pw.close();
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		}
	}
	
}
