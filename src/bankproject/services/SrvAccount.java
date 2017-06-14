package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
import bankproject.entities.AccountNumber;
import bankproject.entities.CountryEnum;
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
	public void create(Account entity) throws SQLException {
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
			if (ps != null) {
			ps.close();
			}
			
			if (connection != null) {
			connection.close();
			}
		}
	}
	
	public void update (Account entity) throws SQLException {
		String str = "UPDATE" + getEntitySqlTable() + "SET accountnumber = ?, balance = ? WHERE idaccount = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setObject(1, entity.getAccountNumber());
			ps.setInt(2, entity.getBalance());
			ps.setInt(3, entity.getIdAccount());
			
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
	
	private void delete(Account entity) throws SQLException {
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idaccount = ? AND accountnumber = ? AND balance = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdAccount());
			ps.setObject(2, entity.getAccountNumber());
			ps.setInt(3, entity.getBalance());
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

		if (entity instanceof Account) {
			Account account = (Account)entity;
			if (account.getIdAccount() == null) {
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
		account.setIdAccount(rs.getInt("idaccount"));
		account.setAccountNumber((AccountNumber) rs.getObject("accountnumber"));
		account.setBalance(rs.getInt("balance"));
		
		return account;
	}
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS account ( ")
			.append("idaccount INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("accountnumber TEXT, ")
			.append("balance INTEGER, ")
			.append("FOREIGN KEY(accountnumber) REFERENCES customer(idcustomer) ON DELETE CASCADE")
			.append(")");
		
		return sb.toString();
	}

	public static void main(String[] args) {
		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		srvAccount.setDbManager(SQLiteManager.getInstance());
		Account account = new Account(CountryEnum.SPAIN);
		AccountNumber accountNumber = account.getAccountNumber();
		account.setBalance(50);
		int balance = account.getBalance();
		
		System.out.println(accountNumber + " " + balance);
//		try {
//			srvAccount.create(account);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}

}
