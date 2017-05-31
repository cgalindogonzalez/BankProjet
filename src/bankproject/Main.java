package bankproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import bankproject.services.SQLiteManager;
import bankproject.services.SrvCustomer;

public class Main {

	public static void main(String[] args) throws SQLException {
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		srvCustomer.setDbManager(SQLiteManager.getInstance());
		Connection connection = srvCustomer.getDbManager().getConnection();
		Statement st = connection.createStatement();
		st.execute(srvCustomer.createTableInDB());

	}

}
