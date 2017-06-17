package bankproject.services;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
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
	 * create operation in the DB
	 * @param entity
	 * @throws SQLException
	 */ 
	public void create(Operation entity) throws SQLException {
		String str = "INSERT INTO " + getEntitySqlTable() + " (date, amount, idaccount) VALUES (dateTime('NOW'), ?, ?)";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(str);
			ps.setInt(1, entity.getAmount());
			ps.setInt(2, entity.getAccount().getIdAccount());

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
	 * update operation in the DB
	 * @param entity
	 * @throws SQLException
	 */
	private void update(Operation entity) throws SQLException {
		String sql = "UPDATE " + getEntitySqlTable() + " SET amount = ?, account = ? WHERE idoperation = ?";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getAmount());
			ps.setInt(2, entity.getAccount().getIdAccount());
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

	/**
	 * delete operation from the DB
	 * @param entity
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void delete(Operation entity) throws SQLException {
		String sql ="DELETE FROM" + getEntitySqlTable() + "WHERE idoperation = ?";
		Connection connection = null;
		PreparedStatement ps = null;

		try{
			connection = getDbManager().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, entity.getIdOperation());
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
			throw new SrvException("Wrong service");
		}

	}

	@Override
	protected AbstractEntity populateEntity(ResultSet rs) throws SQLException, Exception {
		Operation operation = new Operation();
		operation.setIdOperation(rs.getInt("idoperation"));

		String sqlDate = rs.getString("date");
		Timestamp ts = Timestamp.valueOf(sqlDate);
		long tst = ts.getTime();
		Date date = new Date(tst);
		operation.setDate(date);

		operation.setAmount(rs.getInt("amount"));

		Integer idaccount = rs.getInt("idaccount");
		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		Account account = (Account)srvAccount.get("idaccount", idaccount);
		operation.setAccount(account);

		return operation;
	}

	/**
	 * get collection of operations from the DB by the idaccount
	 * @param idaccount
	 * @return
	 * @throws Exception
	 */
	public Collection<Operation> getOperationsByIdAccount(Integer idaccount) throws Exception {
		Connection connexion = null;
		Statement st = null;
		ResultSet rs = null;
		Collection<Operation> allAccountOperations = new LinkedHashSet<Operation>();

		StringBuilder query = new StringBuilder("SELECT * FROM operation WHERE idaccount = ");
		query.append(idaccount);

		try {
			connexion = getDbManager().getConnection();
			st = connexion.createStatement();
			rs = st.executeQuery(query.toString());

			while (rs.next()) {
				allAccountOperations.add((Operation) populateEntity(rs));
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

		return allAccountOperations;

	}

	/**
	 * generate the string to the query to create the operation table in the DB
	 * @return
	 */
	public String createTableInDB () {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS operation ( ")
		.append("idoperation INTEGER PRIMARY KEY AUTOINCREMENT, ")
		.append("date TIMESTAMP, ")
		.append("amount INT, ")
		.append("idaccount,")
		.append("FOREIGN KEY (idaccount) REFERENCES account (idaccount) ON DELETE CASCADE")
		.append(")");

		return sb.toString();
	}

}
