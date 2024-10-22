package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 
 * Name: Peter Morales
 * Date: 2023-04-26
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {

		Connection conn = ConnectionFactory.getConn();

		String query = "delete from InvoiceItems";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Invoice";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Store";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Item";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Item";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Email";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Person";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();

			query = "delete from Address";

			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {

		Connection conn = ConnectionFactory.getConn();

		String query = """

				insert into Address (street, city, state, zip, country) values (?, ?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();

			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			int addressId = keys.getInt(1);
			keys.close();
			ps.close();

			query = """

					insert into Person (personCode, lastName, firstName, addressId) values (?, ?, ?, ?);

					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setInt(4, addressId);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 *
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {

		Connection conn = ConnectionFactory.getConn();

		int personId = InvoiceDataUtils.getPersonId(personCode, conn);

		String query = """

				insert into Email (personId, emailAddress) values (?, ?);

				""";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			ps.setString(2, email);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state, String zip, String country) {

		Connection conn = ConnectionFactory.getConn();

		String query = """

				insert into Address (street, city, state, zip, country) values (?, ?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();

			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			int addressId = keys.getInt(1);
			keys.close();
			ps.close();

			int managerId = InvoiceDataUtils.getPersonId(managerCode, conn);

			query = """

					insert into Store (storeCode, managerId, addressId) values (?, ?, ?);

					""";

			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			ps.setInt(2, managerId);
			ps.setInt(3, addressId);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a product record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>unit</code> and <code>pricePerUnit</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addProduct(String code, String name, String unit, double pricePerUnit) {

		Connection conn = ConnectionFactory.getConn();

		String query = """

				insert into Item (itemCode, type, name, unit, unitPrice) values (?, ?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "P");
			ps.setString(3, name);
			ps.setString(4, unit);
			ps.setDouble(5, pricePerUnit);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * Adds an equipment record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>modelNumber</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addEquipment(String code, String name, String modelNumber) {

		Connection conn = ConnectionFactory.getConn();

		String query = """

				insert into Item (itemCode, type, name, model) values (?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "E");
			ps.setString(3, name);
			ps.setString(4, modelNumber);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a service record to the database with the given <code>code</code>,
	 * <code>name</code> and <code>costPerHour</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param modelNumber
	 */
	public static void addService(String code, String name, double costPerHour) {

		Connection conn = ConnectionFactory.getConn();

		String query = """

				insert into Item (itemCode, type, name, hourlyRate) values (?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ps.setString(2, "S");
			ps.setString(3, name);
			ps.setDouble(4, costPerHour);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds an invoice record to the database with the given data.
	 *
	 * @param invoiceCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 * @param invoiceDate
	 */
	public static void addInvoice(String invoiceCode, String storeCode, String customerCode, String salesPersonCode, String invoiceDate) {

		Connection conn = ConnectionFactory.getConn();

		int storeId = InvoiceDataUtils.getStoreId(storeCode, conn);
		int customerId = InvoiceDataUtils.getPersonId(customerCode, conn);
		int salesPersonId = InvoiceDataUtils.getPersonId(salesPersonCode, conn);

		String query = """

				insert into Invoice (invoiceCode, storeId, customerId, salesPersonId, invoiceDate) values (?, ?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setInt(2, storeId);
			ps.setInt(3, customerId);
			ps.setInt(4, salesPersonId);
			ps.setString(5, invoiceDate);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified quantity.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToInvoice(String invoiceCode, String itemCode, int quantity) {

		Connection conn = ConnectionFactory.getConn();

		int invoiceId = InvoiceDataUtils.getInvocieId(invoiceCode, conn);
		int itemId = InvoiceDataUtils.getItemId(itemCode, conn);

		String query = """

				insert into InvoiceItems (invoiceId, itemId, quantity) values (?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, itemId);
			ps.setDouble(3, quantity);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a particular equipment <i>purchase</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) at the given <code>purchasePrice</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param purchasePrice
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double purchasePrice) {

		Connection conn = ConnectionFactory.getConn();

		int invoiceId = InvoiceDataUtils.getInvocieId(invoiceCode, conn);
		int itemId = InvoiceDataUtils.getItemId(itemCode, conn);

		String query = """

				insert into InvoiceItems (invoiceId, itemId, typePL, purchasePrice) values (?, ?, ?, ?);

				""";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, itemId);
			ps.setString(3, "Purchase");
			ps.setDouble(4, purchasePrice);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a particular equipment <i>lease</i> (identified by
	 * <code>itemCode</code>) to a particular invoice (identified by
	 * <code>invoiceCode</code>) with the given 30-day <code>periodFee</code> and
	 * <code>beginDate/endDate</code>.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addEquipmentToInvoice(String invoiceCode, String itemCode, double periodFee, String beginDate, String endDate) {

		Connection conn = ConnectionFactory.getConn();

		int invoiceId = InvoiceDataUtils.getInvocieId(invoiceCode, conn);
		int itemId = InvoiceDataUtils.getItemId(itemCode, conn);

		String query = """

				insert into InvoiceItems (invoiceId, itemId, typePL, fee, startDate, endDate) values (?, ?, ?, ?, ?, ?);

				""";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, itemId);
			ps.setString(3, "Lease");
			ps.setDouble(4, periodFee);
			ps.setString(5, beginDate);
			ps.setString(6, endDate);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	
	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular invoice (identified by <code>invoiceCode</code>) with the
	 * specified number of hours.
	 *
	 * @param invoiceCode
	 * @param itemCode
	 * @param billedHours
	 */
	public static void addServiceToInvoice(String invoiceCode, String itemCode, double billedHours) {

		Connection conn = ConnectionFactory.getConn();

		int invoiceId = InvoiceDataUtils.getInvocieId(invoiceCode, conn);
		int itemId = InvoiceDataUtils.getItemId(itemCode, conn);

		String query = """

				insert into InvoiceItems (invoiceId, itemId, hours) values (?, ?, ?);

				""";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, invoiceId);
			ps.setInt(2, itemId);
			ps.setDouble(3, billedHours);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
