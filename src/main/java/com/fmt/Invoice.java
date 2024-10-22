package com.fmt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class models an Invoice
 * 
 * Name: Peter Morales
 * Date: 2023-03-10
 * 
 */
public class Invoice {
	
	private final Integer invoiceId;
	private final String invoiceCode;
	private final Store store;
	private final Person customer;
	private final Person salesperson;
	private final LocalDate date;
	private final List<Item> items = new ArrayList<>();
	
	public Invoice(String invoiceCode, Store storeCode, Person customerCode, Person salespersonCode, LocalDate date) {
		this.invoiceId= null;
		this.invoiceCode = invoiceCode;
		this.store = storeCode;
		this.customer = customerCode;
		this.salesperson = salespersonCode;
		this.date = date;
	}
		
	public Invoice(Integer invoiceId, String invoiceCode, Store storeCode, Person customerCode, Person salespersonCode, LocalDate date) {
		this.invoiceId = invoiceId;
		this.invoiceCode = invoiceCode;
		this.store = storeCode;
		this.customer = customerCode;
		this.salesperson = salespersonCode;
		this.date = date;
	}

	@Override
	public String toString() {
		return this.invoiceCode + this.store + this.customer + this.salesperson + this.date + this.items;
	}
	
	public Integer getInvoiceId() {
		return invoiceId;
	}

	public String getInvoiceCode() {
		return this.invoiceCode;
	}

	public Store getStore() {
		return this.store;
	}

	public Person getCustomer() {
		return this.customer;
	}

	public Person getSalesperson() {
		return this.salesperson;
	}

	public LocalDate getDate() {
		return this.date;
	}
	
	public List<Item> getItems() {
		return this.items;
	}

	public void addItem(Item items) {
		this.items.add(items);
	}

	public double getItemTotal() {
		double total = 0.0;
		for(Item i : items) {
			total += i.getTotal();
		}
		return total;
	}
	
	// loops through invoice items list and grabs/totals sub totals.
	public double getItemSubTotal() {
		double total = 0.0;
		for(Item i : items) {
			total += i.getSubTotal();
		}
		return total;
	}
	
	// loops through invoice items list and grabs/totals taxes.
	public double getItemTax() {
		double total = 0.0;
		for(Item i : items) {
			total += i.getTaxes();
		}
		return total;
	}
	
	// This is a comparator that compares by customer last name > customer first name > invoice code
	public static final Comparator<Invoice> lastNameCmp = new Comparator<Invoice>() {
		
		@Override
		public int compare(Invoice a, Invoice b) {
			if (a.getCustomer().getLastName().compareTo(b.getCustomer().getLastName()) > 0) {
				return 1;
			} else if (a.getCustomer().getLastName().compareTo(b.getCustomer().getLastName()) < 0) {
				return -1;
			} else {
				
				if (a.getCustomer().getFirstName().compareTo(b.getCustomer().getFirstName()) > 0) {
					return 1;
				} else if (a.getCustomer().getFirstName().compareTo(b.getCustomer().getFirstName()) < 0) {
					return -1;
				} else {
					
					return a.getInvoiceCode().compareTo(b.getInvoiceCode());
				}
			}
		}
	};
	
	// This is a comparator that compares by item total.
	public static final Comparator<Invoice> invoiceValueCmp = new Comparator<Invoice>() {

		@Override
		public int compare(Invoice b, Invoice a) {
			if (a.getItemTotal() > b.getItemTotal()) {
				return 1;
			} else if (a.getItemTotal() < b.getItemTotal()) {
				return -1;
			} else {
				return 0;
			}
		}
	};
	
	// This is a comparator that compares by store code > salesperson last name > salesperson first name > invoice code.
	public static final Comparator<Invoice> storeCodeCmp = new Comparator<Invoice>() {

		@Override
		public int compare(Invoice a, Invoice b) {
			if (a.getStore().getStoreCode().compareTo(b.getStore().getStoreCode()) > 0) {
				return 1;
			} else if (a.getStore().getStoreCode().compareTo(b.getStore().getStoreCode()) < 0) {
				return -1;
			} else {
				
				if (a.getSalesperson().getLastName().compareTo(b.getSalesperson().getLastName()) > 0) {
					return 1;
				} else if (a.getSalesperson().getLastName().compareTo(b.getSalesperson().getLastName()) < 0) {
					return -1;
				} else {
					
					if (a.getSalesperson().getFirstName().compareTo(b.getSalesperson().getFirstName()) > 0) {
						return 1;
					} else if (a.getSalesperson().getFirstName().compareTo(b.getSalesperson().getFirstName()) < 0) {
						return -1;
					} else {
						
						return a.getInvoiceCode().compareTo(b.getInvoiceCode());
					}
				}
			} 
		}
	};
	
}
