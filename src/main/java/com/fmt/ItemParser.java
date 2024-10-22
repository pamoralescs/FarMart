package com.fmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Processes a comma-separated value (CSV) file of Item data
 * 
 * Name: Peter Morales
 * Date: 2023-02-23
 *
 */
public class ItemParser {
	
	public static Map<String, Item> parseDataFile(String filename) {
		List<Item> item = new ArrayList<Item>();
		File f = new File(filename);
		try(Scanner s = new Scanner(f)) {
			int numLines = Integer.parseInt(s.nextLine().trim());
			for(int i = 1; i <= numLines; i++) {
				String line = s.nextLine();
				Item c = null;
				String tokens[] = line.split(",");
				String itemCode = tokens[0];
				String type = tokens[1];
				String name = tokens[2];
				if(type.equals("E")) {
					String model = tokens[3];
					c = new Equipment(itemCode, name, model);
				} else if (type.equals("P")) {
					String unit = tokens[3];
					double unitPrice = 0.0;
					unitPrice = Double.parseDouble(tokens[4]);
					c = new Product(itemCode, name, unit, unitPrice);
				} else if (type.equals("S")) {
					double hourlyRate = 0.0;
					hourlyRate = Double.parseDouble(tokens[3]);
					c = new Service(itemCode, name, hourlyRate);
				}
				item.add(c);
			}
			s.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Map<String, Item> itemMap = new HashMap<>();
		for(Item i : item) {
			itemMap.put(i.getItemCode(), i);
		}
		return itemMap;
	}
	
}
