package com.fmt;

import java.util.Map;

/**
 * This class generates the three types of reports.
 * 
 * Name: Peter Morales 
 * Date: 2023-03-10
 * 
 */
public class ReportMaker {

	Map<Integer, Store> store = DatabaseLoader.getStoreSummaries();
	Map<Integer, Invoice> invoice = DatabaseLoader.getInvoiceSummary(store);

	public void getSummaryReport() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("| Summary Report - By Total                                                                  |\n"));
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("%-10s %-10s %-30s %-12s %-11s %-15s\n", "Invoice #", "Store", "Customer", "Num Items", "Tax", " Total"));
		
		double totalItem = 0.0;
		double totalTax = 0.0;
		int numOfItems = 0;
	    for (Map.Entry<Integer, Invoice> e : invoice.entrySet()) {
	    	String nameCustomer = String.format("%s, %s", e.getValue().getCustomer().getLastName(), e.getValue().getCustomer().getFirstName());
	    	sb.append(String.format("%-10s %-10s %-30s %-10s $ %-10.2f $ %-15.2f\n", e.getValue().getInvoiceCode(), 
	    			e.getValue().getStore().getStoreCode(), nameCustomer, e.getValue().getItems().size(), e.getValue().getItemTax(), e.getValue().getItemTotal()));
    		totalItem += e.getValue().getItemTotal();
    		totalTax += e.getValue().getItemTax();
    		numOfItems += e.getValue().getItems().size();
		}
    	sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
    	sb.append(String.format("                                                     %-5d      $ %-10.2f $ %-15.2f\n", numOfItems, totalTax, totalItem));	
		System.out.println(sb);
	}

	public void getStoreSalesReport() {
		// used to populate the stores sales report data.
		for(Invoice i : invoice.values()) {
			Store s = store.get(i.getStore().getStoreId());
			s.addInvoice(i);
		}
		
	    StringBuilder sb = new StringBuilder();
	    sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
		sb.append(String.format("| Store Sales Summary Report                                                                 |\n"));
		sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
        sb.append(String.format("%-10s %-30s %-12s %-15s\n", "Store", "Manager", "# Sales", "Grand Total"));
        
        double totalOfSales = 0.0;
        int numOfSales = 0;
    	for (Map.Entry<Integer, Store> s : store.entrySet()) {
			String nameSalesPerson = String.format("%s, %s", s.getValue().getManager().getLastName(), s.getValue().getManager().getFirstName());
	    	sb.append(String.format("%-10s %-30s %-10d $ %-15.2f\n", s.getValue().getStoreCode(), nameSalesPerson, s.getValue().getInvoices().size(), s.getValue().getSaleTotal()));
	    	totalOfSales += s.getValue().getSaleTotal();
	    	numOfSales += s.getValue().getInvoices().size();
    	}
    	sb.append(String.format("+--------------------------------------------------------------------------------------------+\n"));
    	sb.append(String.format("                                          %-10s $ %-15.2f\n", numOfSales, totalOfSales));	
    	System.out.println(sb);
	}

	public void getItemsReport() {
    	StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Invoice> e : invoice.entrySet()) {
			sb.append(String.format("%-10s %-10s\n", "Invoice  #", e.getValue().getInvoiceCode()));
			sb.append(String.format("%-10s %-10s\n", "Store    #", e.getValue().getStore().getStoreCode()));
			sb.append(String.format("%-10s %-10s\n\n", "Date:     ", e.getValue().getDate()));
			sb.append(String.format("%-10s\n %-10s\n\n", " Customer:", e.getValue().getCustomer()));
			sb.append(String.format("%-10s\n %-10s\n\n", " Sales Person:", e.getValue().getSalesperson()));
	    	sb.append(String.format("%-60s %10s\n", "Item", "Total"));
	    	sb.append(String.format("%-58s %10s\n", "-=-=-=-=-=-=-=-==-=-", "-=-=-=-==-=-"));
	    	
	    	for(Item i : e.getValue().getItems()) {
	    			sb.append(String.format("%10s \n", i));
		    		sb.append(String.format("%60s %10.2f\n", "$", i.getSubTotal()));
	    	}
	    	
	    	sb.append(String.format("%71s\n", "-=-=-=-==-=-"));
	    	sb.append(String.format("%58s $ %10.2f\n", "Subtotal ", e.getValue().getItemSubTotal()));
	    	sb.append(String.format("%58s $ %10.2f\n", "Tax ", e.getValue().getItemTax()));
	    	sb.append(String.format("%58s $ %10.2f\n", "Grand Total ", e.getValue().getItemTotal())); 
		}
		System.out.println(sb);
		
	}

	public void getSalesByCustomer() {
		LinkedList<Invoice> invoices = new LinkedList<Invoice>(Invoice.lastNameCmp);
				
		for (Integer i : invoice.keySet()) {
			invoices.add(invoice.get(i));
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("| Sales By Customer                                                         |\n"));
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("%-10s %-10s %-20s %-20s %-20s \n", "Sale", "Store", "Customer", "Salesperson", "Total"));
		
		for (Invoice i : invoices) {
	    	String nameCustomer = String.format("%s, %s", i.getCustomer().getLastName(), i.getCustomer().getFirstName());
	    	String nameSalesPerson = String.format("%s, %s", i.getSalesperson().getLastName(), i.getSalesperson().getFirstName());
	    	sb.append(String.format("%-10s %-10s %-20s %-20s $ %10.2f \n", i.getInvoiceCode(), i.getStore().getStoreCode(), nameCustomer, nameSalesPerson, i.getItemTotal()));
		}
		System.out.println(sb);
	}

	public void getSalesByTotal() {
		LinkedList<Invoice> invoices = new LinkedList<Invoice>(Invoice.invoiceValueCmp);
				
		for (Integer i : invoice.keySet()) {
			invoices.add(invoice.get(i));
		}

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("| Sales By Total                                                            |\n"));
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("%-10s %-10s %-20s %-20s %-20s \n", "Sale", "Store", "Customer", "Salesperson", "Total"));
		
		for (Invoice i : invoices) {
	    	String nameCustomer = String.format("%s, %s", i.getCustomer().getLastName(), i.getCustomer().getFirstName());
	    	String nameSalesPerson = String.format("%s, %s", i.getSalesperson().getLastName(), i.getSalesperson().getFirstName());
	    	sb.append(String.format("%-10s %-10s %-20s %-20s $ %10.2f \n", i.getInvoiceCode(), i.getStore().getStoreCode(), nameCustomer, nameSalesPerson, i.getItemTotal()));
		}
		System.out.println(sb);
	}

	public void getSalesByStore() {
		LinkedList<Invoice> invoices = new LinkedList<Invoice>(Invoice.storeCodeCmp);
		
		for (Integer i : invoice.keySet()) {
			invoices.add(invoice.get(i));
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("| Sales By Store                                                            |\n"));
		sb.append(String.format("+---------------------------------------------------------------------------+\n"));
		sb.append(String.format("%-10s %-10s %-20s %-20s %-20s \n", "Sale", "Store", "Customer", "Salesperson", "Total"));
		
	    for (Invoice i : invoices) {
	    	String nameCustomer = String.format("%s, %s", i.getCustomer().getLastName(), i.getCustomer().getFirstName());
	    	String nameSalesPerson = String.format("%s, %s", i.getSalesperson().getLastName(), i.getSalesperson().getFirstName());
	    	sb.append(String.format("%-10s %-10s %-20s %-20s $ %10.2f \n", i.getInvoiceCode(), i.getStore().getStoreCode(), nameCustomer, nameSalesPerson, i.getItemTotal()));
		}
		System.out.println(sb);
	}
}
