package com.fmt;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

/**
 * Processes a comma-separated value (CSV) file of InvoiceItem data
 * 
 * Name: Peter Morales
 * Date: 2023-03-05
 *
 */
public class InvoiceItemsParser {
	
	public static void parseDataFile(String filename, Map<String, Invoice> invoice, Map<String, Item> item) {
		File f = new File(filename);
		try(Scanner s = new Scanner(f)) {
			int numLines = Integer.parseInt(s.nextLine().trim());
			for(int i = 1; i <= numLines; i++) {
				String line = s.nextLine();
				Product a = null;
				Service b = null;
				Lease c = null;
				Purchase d = null;
				String tokens[] = line.split(",");
				Invoice invoiceCode = invoice.get(tokens[0]);
				Item itemCode = item.get(tokens[1]);
						
				if(itemCode instanceof Product) {	
					int productQuantity = 0;
					productQuantity = Integer.parseInt(tokens[2]);
					Product p = (Product) itemCode;
					a = new Product(p.getItemCode(), p.getName(), p.getUnit(), p.getUnitPrice() , productQuantity);
					invoiceCode.addItem(a);
					
				} else if(itemCode instanceof Service) {
					double serviceHours = 0.0;
					serviceHours = Double.parseDouble(tokens[2]);
					Service ser = (Service) itemCode;
					b = new Service(ser.getItemCode(), ser.getName(), ser.getHourlyRate(), serviceHours);
					invoiceCode.addItem(b);
					
				} else if(itemCode instanceof Equipment) {
					String purchaseType = tokens[2];
					if(purchaseType.equals("P")) {
						double purchasePrice = 0.0;
						purchasePrice = Double.parseDouble(tokens[3]);
						Equipment pur = (Equipment) itemCode;
						d = new Purchase(pur.getItemCode(), pur.getName(), pur.getModel(), purchasePrice);
						invoiceCode.addItem(d);
						
					} else if(purchaseType.equals("L")) {
						double leasePrice = 0.0;
						leasePrice = Double.parseDouble(tokens[3]);
						String sDate = tokens[4];
						LocalDate startDate = LocalDate.parse(sDate);
						String eDate = tokens[5];
						LocalDate endDate = LocalDate.parse(eDate);
						Equipment l = (Equipment) itemCode;
						c = new Lease(l.getItemCode(), l.getName(), l.getModel(), leasePrice, startDate, endDate);
						invoiceCode.addItem(c);
					}
				}
				
			}
			s.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
}
