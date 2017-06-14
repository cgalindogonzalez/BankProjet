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
	 * 
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
			//e.printStackTrace();
			System.out.println("Error del método create");
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
	 * 
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
	
	private void delete(Customer entity) throws SQLException {
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idcustomer = ? AND name = ? AND surname = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdCustomer());
			ps.setString(2, entity.getName());
			ps.setString(2, entity.getSurname());
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
			throw new SrvException("Utilisation du mauvais service");
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
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS customer ( ")
			.append("idcustomer INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("name TEXT, ")
			.append("surname TEXT ")
			.append(")");
		
		return sb.toString();
	}

	public static void main (String[] args) {
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		srvCustomer.setDbManager(SQLiteManager.getInstance());
		
		Customer customer = new Customer();
		customer.setName("Cecilia");
		customer.setSurname("Galindo");
		
		System.out.println(customer.getName()+customer.getSurname());
		
		try {
			srvCustomer.create(customer);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
