package com.fmt;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Processes a comma-separated value (CSV) file of Invoice data
 * 
 * Name: Peter Morales
 * Date: 2023-03-05
 *
 */
public class InvoiceParser {
	
	public static Map<String, Invoice> parseDataFile(String filename, Map<String, Store> store, Map<String, Person> person) {
		List<Invoice> invoice = new ArrayList<Invoice>();
		File f = new File(filename);
		try(Scanner s = new Scanner(f)) {
			int numLines = Integer.parseInt(s.nextLine().trim());
			for(int i = 1; i <= numLines; i++) {
				String line = s.nextLine();
				Invoice d = null;
				String tokens[] = line.split(",");
				String invoiceCode = tokens[0];
				Store storeCode = store.get(tokens[1]);
				Person customerCode = person.get(tokens[2]);
				Person salesPersonCode = person.get(tokens[3]);
				String day = tokens[4];
				LocalDate date = LocalDate.parse(day);
				d = new Invoice(invoiceCode, storeCode, customerCode, salesPersonCode, date);
				invoice.add(d);
				storeCode.addInvoice(d);
			}
			s.close();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		Map<String, Invoice> invoiceMap = new HashMap<>();
		for(Invoice i : invoice) {
			invoiceMap.put(i.getInvoiceCode(), i);
		}
		return invoiceMap;
	}
	
}
