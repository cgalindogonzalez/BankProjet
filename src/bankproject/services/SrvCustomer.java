package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Customer;
import bankproject.exceptions.SrvException;

public class SrvCustomer extends AbstractService{

	/**
	 * 
	 */
	private static SrvCustomer INSTANCE = new SrvCustomer();

	/**
	 * private constructor
	 */
	private SrvCustomer() {
		setEntitySqlTable("customer");
	}

	/**
	 * getter
	 * @return INSTANCE
	 */
	public static SrvCustomer getINSTANCE() {
		return INSTANCE;
	}

	/**
	 * create new customer in the DB
	 * @param entity
	 * @throws SQLException
	 */
	public void create(Customer entity) throws SQLException {
		String sql = "INSERT INTO " + getEntitySqlTable() + " (name, surname) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());

			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * update new customer in the DB
	 * @param entity
	 * @throws SQLException
	 */
	private void update(Customer entity) throws SQLException {
		String sql = "UPDATE " + getEntitySqlTable() + " SET name = ?, surname = ? WHERE idcustomer = ?";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setInt(3, entity.getIdCustomer());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * delete customer from the DB
	 * @param entity
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void delete(Customer entity) throws SQLException {
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idcustomer = ?";
		Connection connection = null;
		PreparedStatement ps = null;

		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdCustomer());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	@Override
	public void save(AbstractEntity entity) throws SrvException, SQLException {
		if (entity instanceof Customer) {
			Customer customer = (Customer)entity;
			if (customer.getIdCustomer() == null) {
				create(customer);
			} else {
				update(customer);
			}
		} else {
			throw new SrvException("Wrong service");
		}
	}

	@Override
	protected Customer populateEntity(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		customer.setIdCustomer(rs.getInt("idcustomer"));
		customer.setName(rs.getString("name"));
		customer.setSurname(rs.getString("surname"));

		return customer;
	}

	/**
	 * get customer from the DB by name and surname 
	 * @param name
	 * @param surname
	 * @return
	 * @throws SQLException
	 */
	public Customer getCustomerByNameSurname(String name, String surname) throws SQLException {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = null;

		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getEntitySqlTable());
		query.append(" WHERE name = ? AND surname = ?");
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(query.toString());
			ps.setString(1, name);
			ps.setString(2, surname);
			rs = ps.executeQuery();

			while (rs.next()) {
				customer = populateEntity(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

		return customer;
	}

	/**
	 * generate the string to the query to create the customer table in the DB
	 * @return
	 */
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS customer ( ")
		.append("idcustomer INTEGER PRIMARY KEY AUTOINCREMENT, ")
		.append("name TEXT, ")
		.append("surname TEXT ")
		.append(")");

		return sb.toString();
	}

}
