package bankproject.services;

import java.util.GregorianCalendar;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
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
	public void create(Operation entity) throws SQLException {
		String str = "INSERT INTO " + getEntitySqlTable() + " (date, amount) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setString(1, entity.getDate().getGregorianChange().toString());//FUNCIONA, PERO NO ESTOY SEGURA DE QUE SEA CORRECTO, XQ ES STRING Y EN LA TABLA ES DATE	
			ps.setInt(2, entity.getAmount());
	
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
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	private void update(Operation entity) throws SQLException {
		String sql = "UPDATE " + getEntitySqlTable() + " SET date = ?, amount = ? WHERE idoperation = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setDate(1, (Date) entity.getDate().getGregorianChange());
			ps.setInt(2, entity.getAmount());
			ps.setInt(3, entity.getIdOperation());
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
	
	private void delete(Operation entity) throws SQLException {
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idoperation = ? AND date = ? AND amount = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdOperation());
			ps.setDate(2, (Date) entity.getDate().getGregorianChange());
			ps.setInt(2, entity.getAmount());
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
		if (entity instanceof Operation) {
			Operation operation = (Operation)entity;
			if (operation.getIdOperation() == null) {
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
		operation.setIdOperation(rs.getInt("idoperation"));
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(rs.getDate("date"));
		operation.setDate(gc);
		
		operation.setAmount(rs.getInt("amount"));

		return operation;
	}
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS operation ( ")
			.append("idoperation INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("date DATE, ")
			.append("amount INT, ")
			.append("FOREIGN KEY (idoperation) REFERENCES account (idaccount) ON DELETE CASCADE")
			.append(")");
		
		return sb.toString();
	}

	
}
