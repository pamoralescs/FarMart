package com.fmt;

import java.util.ArrayList;
import java.util.List;

/**
 *  This is a class that models a Store
 *  
 *  Name: Peter Morales
 *  Date: 2023-02-23
 *
 */
public class Store {
	
		private final Integer storeId;
		private final String storeCode;
		private final Person manager;
		private final Address address;
		private final List<Invoice> invoices = new ArrayList<>();

		public Store(String storeCode, Person people, Address address) {
			this.storeId = null;
			this.storeCode = storeCode;
			this.manager = people;
			this.address = address;
		}
		
		public Store(Integer storeId, String storeCode, Person people, Address address) {
			this.storeId = storeId;
			this.storeCode = storeCode;
			this.manager = people;
			this.address = address;
		}
	
		public Integer getStoreId() {
			return storeId;
		}

		public String getStoreCode() {
			return this.storeCode;
		}
		
		public void addInvoice(Invoice invoice) {
			this.invoices.add(invoice);
		}
		
		public List<Invoice> getInvoices() {
			return this.invoices;
		}

		public Person getManager() {
			return this.manager;
		}

		public Address getAddress() {
			return this.address;
		}
		
		// calculates the sales total by combining the item totals for each store.
		public double getSaleTotal() {
			double total = 0.0;
			for(Invoice i : invoices) {
				total += i.getItemTotal();
			}
			return total;
		}

}

