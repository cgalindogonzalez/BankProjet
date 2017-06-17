package bankproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

import bankproject.entities.Account;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.readers.AccountCustomerThread;
import bankproject.readers.OperationThread;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;
import bankproject.writers.BankStatementThread;

public class Main {

	/**
	 * Print in console the statements of a customer from his name and surname
	 */
	@SuppressWarnings("resource")
	public static void CLI() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter customer Name and Surname");

		while (true) {
			String str = sc.nextLine();
			String[] nameSurname = str.split(" ");
			String name = nameSurname[0];
			String surname = nameSurname[1];

			String header = "Date\t\tAccount\t\tCustomer\t\tAmount\r\n";
			System.out.println(header);

			Customer customer = new Customer();
			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
			srvCustomer.setDbManager(SQLiteManager.getInstance());	
			try {
				customer = srvCustomer.getCustomerByNameSurname(name, surname);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			Integer idCustomer = customer.getIdCustomer();

			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());
			Collection <Account> allThisCustomerAccounts = new LinkedHashSet<Account>();

			try {
				allThisCustomerAccounts = srvAccount.getAccountsByIdCustomer(idCustomer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Iterator<Account> it = allThisCustomerAccounts.iterator();

			while(it.hasNext()){

				Account account = (Account) it.next();
				String accountNumber = account.getAccountNumber();
				Integer idAccount = account.getIdAccount();

				SrvOperation srvOperation = SrvOperation.getINSTANCE();

				Collection <Operation> allThisAccountOperations = new LinkedHashSet<Operation>();

				try {
					allThisAccountOperations = srvOperation.getOperationsByIdAccount(idAccount);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Iterator<Operation> it1 = allThisAccountOperations.iterator();
				if (it1.hasNext()){
					while (it1.hasNext()) {
						Operation operation = (Operation) it1.next();
						Date date = operation.getDate();
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

						Integer amount = operation.getAmount();

						String line = df.format(date) + "\t\t"+ accountNumber + "\t\t" + str + "\t\t" + amount;

						System.out.println(line);

					}
				}
				else{
					System.out.println("(No operations)");
				}
			}
		}

	}

	public static void main(String[] args) throws SQLException {

		//Creation of tables in the DB
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		srvCustomer.setDbManager(SQLiteManager.getInstance());
		Connection connection = srvCustomer.getDbManager().getConnection();

		Statement st = connection.createStatement();
		st.execute(srvCustomer.createTableInDB());

		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		srvAccount.setDbManager(SQLiteManager.getInstance());
		st.execute(srvAccount.createTableInDB());

		SrvOperation srvOperation = SrvOperation.getINSTANCE();
		srvOperation.setDbManager(SQLiteManager.getInstance());
		st.execute(srvOperation.createTableInDB());

		st.close();
		connection.close();


		//Threads launching

		AccountCustomerThread act = new AccountCustomerThread();
		act.start();

		OperationThread ot = new OperationThread();
		ot.start();

		BankStatementThread bst = new BankStatementThread();
		bst.start();

		CLI();


	}

}
