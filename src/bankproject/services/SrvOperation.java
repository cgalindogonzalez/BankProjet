package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.exceptions.SrvException;

public class SrvOperation extends AbstractService {

	/**
	 * 
	 */
	private static SrvOperation INSTANCE = new SrvOperation();
	
	/**
	 * private constructor
	 */
	private SrvOperation() {
		setEntitySqlTable("operation");
	}
	
	/**
	 * getter
	 * @return INSTANCE
	 */
	public static SrvOperation getINSTANCE() {
		return INSTANCE;
	}

	/**
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	private void create(Operation entity) throws SQLException {
		String str = "INSERT INTO " + getEntitySqlTable() + " (date, amount) VALUES (?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setDate(1, entity.getDate());
			ps.setInt(3, entity.getAmount());
	
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			
			if (ps != null) {
				ps.close();
			}
		}
	}
	
	/**
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	private void update(Operation entity) throws SQLException {
		String sql = "UPDATE " + getEntitySqlTable() + " SET date = ?, amount = ? WHERE id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setDate(1, entity.getDate());
			ps.setInt(2, entity.getAmount());
			ps.setInt(3, entity.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
			
			if (ps != null) {
				ps.close();
			}
		}
	}
	
	@Override
	public void save(AbstractEntity entity) throws SrvException, SQLException {
		if (entity instanceof Operation) {
			Operation operation = (Operation)entity;
			if (operation.getId() == null) {
				create(operation);
			} else {
				update(operation);
			}
		} else {
			throw new SrvException("Utilisation du mauvais service");
		}
		
	}

	@Override
	protected AbstractEntity populateEntity(ResultSet rs) throws SQLException, Exception {
		Operation operation = new Operation();
		operation.setId(rs.getInt("id"));
		operation.setDate(rs.getDate("date"));
		operation.setAmount(rs.getInt("amount"));

		return operation;
	}
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS operation ( ")
			.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("date DATE, ")
			.append("amount INT ")
			.append(")");
		
		return sb.toString();
	}

}
