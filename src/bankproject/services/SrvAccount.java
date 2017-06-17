package bankproject.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashSet;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
import bankproject.entities.CountryEnum;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
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
		String str = "INSERT INTO " + getEntitySqlTable() + " (accountnumber, balance, idcustomer) VALUES (?,?,?)";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setObject(1, entity.getAccountNumber());;
			ps.setInt(2, entity.getBalance());
			ps.setInt(3, entity.getCustomer().getIdCustomer());
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
		String str = "UPDATE " + getEntitySqlTable() + " SET balance = ? WHERE idaccount = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setInt(1, entity.getBalance());
			ps.setInt(2, entity.getIdAccount());
			
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
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idaccount = ?";
		Connection connection = null;
		PreparedStatement ps = null;
		
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdAccount());
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
	protected Account populateEntity(ResultSet rs) throws Exception {
		Account account = new Account();
		
		Integer idcustomer = rs.getInt("idcustomer");
		
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		Customer customer = (Customer) srvCustomer.get("idcustomer", idcustomer);
     
		
		
		account.setIdAccount(rs.getInt("idaccount"));
		account.setAccountNumber(rs.getString("accountnumber"));
		account.setBalance(rs.getInt("balance"));
		account.setCustomer(customer);
		
		return account;
	}
	
	public Account getAccountByAccountNumber(String accountNumber) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account account = null;
		
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getEntitySqlTable());
		query.append(" WHERE accountnumber = ?");
		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(query.toString());
			ps.setString(1, accountNumber);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				account = populateEntity(rs);
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
		
		return account;
	}
	
	public Collection<Account> getAccountsByIdCustomer(Integer idcustomer) throws Exception {
		Connection connexion = null;
		Statement st = null;
		ResultSet rs = null;
		Collection<Account> allCustomerAccounts = new LinkedHashSet<Account>();
		
		StringBuilder query = new StringBuilder("SELECT * FROM account WHERE idcustomer = ");
		query.append(idcustomer);
		System.out.println(query.toString());
		
		try {
			connexion = getDbManager().getConnection();
			st = connexion.createStatement();
			rs = st.executeQuery(query.toString());
			
			while (rs.next()) {
				allCustomerAccounts.add((Account) populateEntity(rs));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (st != null) {
				st.close();
			}
			if (connexion != null) {
				connexion.close();
			}
			
			
		}
		
		return allCustomerAccounts;
		
	}
	
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS account ( ")
			.append("idaccount INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append("accountnumber TEXT, ")
			.append("balance INTEGER, ")
			.append("idcustomer INTEGER,")
			.append("FOREIGN KEY(idcustomer) REFERENCES customer(idcustomer) ON DELETE CASCADE")
			.append(")");
		
		return sb.toString();
	}

	public static void main(String[] args) {
		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		srvAccount.setDbManager(SQLiteManager.getInstance());
		Customer customer = new Customer();
		customer.setIdCustomer(3);
		Account account = new Account(CountryEnum.SPAIN, customer);
		String accountNumber = account.getAccountNumber();
		account.setBalance(50);
		int balance = account.getBalance();
		Integer idCustomer = account.getCustomer().getIdCustomer();
		System.out.println(accountNumber + " " + balance + " " + idCustomer);
		try {
			srvAccount.create(account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
