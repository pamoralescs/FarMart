package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a class that pulls information from the database and builds my java
 * objects for report output.
 * 
 * Name: Peter Morales Date: 2023-04-14
 *
 */
public class DatabaseLoader {

	private static final Logger LOGGER = LogManager.getLogger(DatabaseLoader.class);

	public static List<String> getEmailByPersonId(int personId) {
		List<String> email = new ArrayList<>();

		Connection conn = ConnectionFactory.getConn();

		String query = """

				select e.emailId, e.personId, e.emailAddress
					from Email e
					where personId = ?

				""";
		try {
			PreparedStatement ps = conn.prepareCall(query);
			ps.setInt(1, personId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String emailAddress = rs.getString("emailAddress");
				email.add(emailAddress);
			}

			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return email;

	}

	public static Address getAddressById(int addressId) {
		Address a = null;

		Connection conn = ConnectionFactory.getConn();

		String query = """

				select a.addressId, a.street, a.city, a.state, a.zip, a.country
					from Address a
					where addressId = ?

				""";
		try {
			PreparedStatement ps = conn.prepareCall(query);
			ps.setInt(1, addressId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");
				a = new Address(street, city, state, zip, country);
			}

			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return a;

	}

	public static Person getPersonById(int personId) {
		Person p = null;

		Connection conn = ConnectionFactory.getConn();

		String query = """

				select p.personId, p.personCode, p.lastName, p.firstName, p.addressId
					from Person p
					where personId = ?

				""";
		try {
			PreparedStatement ps = conn.prepareCall(query);
			ps.setInt(1, personId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String personCode = rs.getString("personCode");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				int addressId = rs.getInt("addressId");
				Address address = getAddressById(addressId);
				int personIdForEmails = rs.getInt("personId");
				List<String> emails = getEmailByPersonId(personIdForEmails);
				p = new Person(personCode, lastName, firstName, address, emails);
			}

			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return p;

	}

	public static Map<Integer, Store> getStoreSummaries() {
		List<Store> store = new ArrayList<>();
		Store s = null;

		LOGGER.info("loading up stores...");

		Connection conn = ConnectionFactory.getConn();

		String query = """

				select s.storeId, s.storeCode, s.managerId, s.addressId
					from Store s

				""";
		try {
			PreparedStatement ps = conn.prepareCall(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int storeId = rs.getInt("storeId");
				String storeCode = rs.getString("storeCode");
				int managerId = rs.getInt("managerId");
				Person person = getPersonById(managerId);
				int addressId = rs.getInt("addressId");
				Address address = getAddressById(addressId);
				s = new Store(storeId, storeCode, person, address);
				store.add(s);
			}

			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Map<Integer, Store> storeMap = new HashMap<>();
		for (Store str : store) {
			storeMap.put(str.getStoreId(), str);
		}

		LOGGER.info("Done loading up stores.");
		return storeMap;

	}

	public static Map<Integer, Invoice> getInvoiceSummary(Map<Integer, Store> store) {
		List<Invoice> invoice = new ArrayList<>();
		Invoice inv = null;
		Item itm = null;

		LOGGER.info("loading up invoices...");

		Connection conn = ConnectionFactory.getConn();

		String query = """

				select inv.invoiceId, inv.invoiceCode, inv.storeId, inv.customerId, inv.salesPersonId, inv.invoiceDate
					from Invoice inv

				""";
		try {
			PreparedStatement ps = conn.prepareCall(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				String invoiceCode = rs.getString("invoiceCode");
				int storeId = rs.getInt("storeId");
				Store storeID = store.get(storeId);
				int customerId = rs.getInt("customerId");
				Person customer = getPersonById(customerId);
				int salesPersonId = rs.getInt("salesPersonId");
				Person salesPerson = getPersonById(salesPersonId);
				String invoiceDate = rs.getString("invoiceDate");
				LocalDate date = LocalDate.parse(invoiceDate);
				inv = new Invoice(invoiceId, invoiceCode, storeID, customer, salesPerson, date);

				// a second query is called in order to populate the items per invoice.
				query = """

						select itm.itemCode, itm.type, itm.name, itm.model, itm.unit, itm.unitPrice, itm.hourlyRate, invitm.hours, invitm.typePL, invitm.fee, invitm.purchasePrice, invitm.quantity, invitm.startDate, invitm.endDate
							from InvoiceItems invitm
						       join Item itm on invitm.itemId = itm.itemId
						       where invitm.invoiceId = ?

						""";

				ps = conn.prepareCall(query);
				ps.setInt(1, invoiceId);
				ResultSet rs2 = ps.executeQuery();

				while (rs2.next()) {
					String itemCode = rs2.getString("itm.itemCode");
					String type = rs2.getString("itm.type");
					String name = rs2.getString("itm.name");
					String typePL = rs2.getString("invitm.typePL");

					if (type.equals("E")) {
						if (typePL.equals("Purchase")) {
							String model = rs2.getString("itm.model");
							double purchasePrice = rs2.getDouble("invitm.purchasePrice");
							itm = new Purchase(itemCode, name, model, purchasePrice);
						} else if (typePL.equals("Lease")) {
							String model = rs2.getString("itm.model");
							double fee = rs2.getDouble("invitm.fee");
							String sDate = rs2.getString("invitm.startDate");
							LocalDate startDate = LocalDate.parse(sDate);
							String eDate = rs2.getString("invitm.endDate");
							LocalDate endDate = LocalDate.parse(eDate);
							itm = new Lease(itemCode, name, model, fee, startDate, endDate);
						}

					} else if (type.equals("P")) {
						String unit = rs2.getString("itm.unit");
						double unitPrice = rs2.getDouble("itm.unitPrice");
						int quantity = rs2.getInt("invitm.quantity");
						itm = new Product(itemCode, name, unit, unitPrice, quantity);

					} else if (type.equals("S")) {
						double hourlyRate = rs2.getDouble("itm.hourlyRate");
						double hours = rs2.getDouble("invitm.hours");
						itm = new Service(itemCode, name, hourlyRate, hours);
					}
					inv.addItem(itm);

				}
				invoice.add(inv);
				rs2.close();

			}
			ps.close();
			rs.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Map<Integer, Invoice> invoiceMap = new HashMap<>();
		for (Invoice i : invoice) {
			invoiceMap.put(i.getInvoiceId(), i);
		}
		LOGGER.info("Done loading up invoices.");
		return invoiceMap;

	}

}
