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

		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		st.execute(srvAccount.createTableInDB());
				
		SrvOperation srvOperation = SrvOperation.getINSTANCE();
		st.execute(srvOperation.createTableInDB());
		
		st.close();
		connection.close();
		
		//Threads launching
		
//		AccountCustomerThread act = new AccountCustomerThread();
//		act.start();
//		
//		OperationThread ot = new OperationThread();
//		ot.start();
//		
//		BankStatementThread bst = new BankStatementThread();
//		bst.start();
		
		
		
	}

}
