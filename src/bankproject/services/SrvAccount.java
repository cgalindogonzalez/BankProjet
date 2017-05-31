package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
import bankproject.entities.AccountNumber;
import bankproject.entities.Customer;
import bankproject.exceptions.SrvException;

public class SrvAccount extends AbstractService {
	
	/**
	 * 
	 */
	private static SrvAccount INSTANCE = new SrvAccount();
	
	/**
	 * private constructor
	 */
	private SrvAccount() {
		setEntitySqlTable("account");
	}

	/**
	 * getter
	 * @return INSTANCE
	 */
	public static SrvAccount getINSTANCE() {
		return INSTANCE;
	}

	/**
	 * 
	 * @param entity
	 * @throws SQLException
	 */
	private void create(Account entity) throws SQLException {
		String str = "INSERT INTO" + getEntitySqlTable() + "(accountnumber, balance) VALUES (?,?)";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setObject(1, entity.getAccountNumber());;
			ps.setInt(2, entity.getBalance());
			
			ps.execute();
		} catch (SQLException e) {
			
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
	
	private void update (Account entity) throws SQLException {
		String str = "UPDATE" + getEntitySqlTable() + "SET accountnumber = ?, balance = ? WHERE Id = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setObject(1, entity.getAccountNumber());
			ps.setInt(2, entity.getBalance());
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

		if (entity instanceof Account) {
			Account account = (Account)entity;
			if (account.getId() == null) {
				create(account);
			} else {
				update(account);
			}
		} else {
			throw new SrvException("Utilisation du mauvais service");
		}
	}
		
		
	

	@Override
	protected AbstractEntity populateEntity(ResultSet rs) throws SQLException, Exception {
		Account account = new Account();
		account.setId(rs.getInt("id"));
		account.setAccountNumber((AccountNumber) rs.getObject("accountnumber"));
		account.setBalance(rs.getInt("balance"));
		
		return account;
	}
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS account ( ")
			.append("id INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("accountnumber TEXT, ")
			.append("balance INTEGER ")
			.append(")");
		
		return sb.toString();
	}

	

}
