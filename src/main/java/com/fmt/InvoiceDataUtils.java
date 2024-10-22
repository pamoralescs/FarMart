package com.fmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a collection of utility methods for InvoiceData.
 * 
 * Name: Peter Morales
 * Date: 2023-04-26
 * 
 */
public class InvoiceDataUtils {

	/**
	 * returns a personId from the database based on the personCode
	 * 
	 * @param personCode
	 * @param conn
	 * @return
	 */
	public static int getPersonId(String personCode, Connection conn) {

		String query = """

				select p.personId
					from Person p
					where p.personCode = ?;

				""";

		int personId = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				personId = rs.getInt("p.personId");
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return personId;
	}

	
	/**
	 * returns a invoiceId from the database based on the invoiceCode
	 * 
	 * @param invoiceCode
	 * @param conn
	 * @return
	 */
	public static int getInvocieId(String invoiceCode, Connection conn) {

		String query = """

				select i.invoiceId
					from Invoice i
				    where invoiceCode = ?;

				""";

		int invoiceId = 0;
		try {

			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				invoiceId = rs.getInt("i.invoiceId");
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return invoiceId;
	}

	
	/**
	 * returns a itemId from the database based on the itemCode
	 * 
	 * @param itemCode
	 * @param conn
	 * @return
	 */
	public static int getItemId(String itemCode, Connection conn) {

		String query = """

				select itm.itemId
					from Item itm
				    where itemCode = ?;

				""";

		int itemId = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, itemCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				itemId = rs.getInt("itm.itemId");
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return itemId;
	}

	
	/**
	 * returns a storeId from the database based on the storeCode
	 * 
	 * @param storeCode
	 * @param conn
	 * @return
	 */
	public static int getStoreId(String storeCode, Connection conn) {

		String query = """

				select s.storeId
					from Store s
					where s.storeCode = ?;

				""";

		int storeId = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				storeId = rs.getInt("s.storeId");
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return storeId;
	}
}
