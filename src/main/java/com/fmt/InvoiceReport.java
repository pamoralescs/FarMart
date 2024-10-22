package com.fmt;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

/**
 * This calls the various reports for output.
 * 
 * Name: Peter Morales
 * Date: 2023-03-10
 * 
 */
public class InvoiceReport {

	public static void main(String args[]) {
		Configurator.initialize(new DefaultConfiguration());
		Configurator.setRootLevel(Level.INFO);
		
		ReportMaker reports = new ReportMaker();
		reports.getSalesByCustomer();
		reports.getSalesByTotal();
		reports.getSalesByStore();
		
	}
}
		
	
	

