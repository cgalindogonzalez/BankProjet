package bankproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import bankproject.readers.AccountCustomerThread;
import bankproject.readers.OperationThread;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;
import bankproject.writers.BankStatementThread;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		//Creation of tables in the DB
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		srvCustomer.setDbManager(SQLiteManager.getInstance());
		Connection connection = srvCustomer.getDbManager().getConnection();
		Statement st = connection.createStatement();
		st.execute(srvCustomer.createTableInDB());
		connection.close();

		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		srvAccount.setDbManager(SQLiteManager.getInstance());
		Connection connection1 = srvAccount.getDbManager().getConnection();
		Statement st1 = connection1.createStatement();
		st1.execute(srvAccount.createTableInDB());
		connection1.close();
		
		SrvOperation srvOperation = SrvOperation.getINSTANCE();
		srvOperation.setDbManager(SQLiteManager.getInstance());
		Connection connection2 = srvOperation.getDbManager().getConnection();
		Statement st2 = connection2.createStatement();
		st2.execute(srvOperation.createTableInDB());
		connection2.close();
		
		//Threads launching
		
		AccountCustomerThread act = new AccountCustomerThread();
		act.start();
		
		OperationThread ot = new OperationThread();
		ot.start();
		
		BankStatementThread bst = new BankStatementThread();
		bst.start();
		
		
		
	}

}
