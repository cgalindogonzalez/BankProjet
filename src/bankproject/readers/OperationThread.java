package bankproject.readers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import bankproject.entities.Account;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.exceptions.SrvException;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;

public class OperationThread extends ReaderThread {

	public void run () {
		
		List<String> wordsOfTheFile = readInputFile("operation"); //Intentar arreglar lo de nombre espacio apellido
		
		for (int i=1; i< wordsOfTheFile.size()/4; i++) {
			
			Customer customer = new Customer();
			customer.setName(wordsOfTheFile.get(4*i+2));
			customer.setSurname(wordsOfTheFile.get(4*i+3));
			String name = customer.getName();
			String surname = customer.getSurname();
			
			System.out.println(name + " " + surname);
			
			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
			srvCustomer.setDbManager(SQLiteManager.getInstance());	
			try {
				customer = srvCustomer.getCustomerByNameSurname(name, surname);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			System.out.println(customer.getIdCustomer());
			
			Operation operation = new Operation();
			operation.setAmount(Integer.parseInt(wordsOfTheFile.get(4*i)));
			operation.setDate(new GregorianCalendar());
			System.out.println(operation.getAmount());
			
			String accountNumber = wordsOfTheFile.get(4*i+1);
			System.out.println(accountNumber);
			Account account = new Account();
			
			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());
			
			try {
				account = srvAccount.getAccountByAccountNumber(accountNumber);//PROBLEMA A RESOLVER AQUÍ (PERO VIENE DEL get entity by Id)
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if(operation.getAmount() < 0)
				account.removeMoneyToBalance(Math.abs(operation.getAmount())); 
			else if(operation.getAmount() > 0)
				account.addMoneyToBalance(Math.abs(operation.getAmount()));
			
			try {
				srvAccount.save(account);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			operation.setAccount(account);
			
			
			SrvOperation srvOperation = SrvOperation.getINSTANCE();
			srvOperation.setDbManager(SQLiteManager.getInstance());
			try {
				srvOperation.save(operation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
		
		//deleteInputFile("operation");
		
		try {
			Thread.sleep(660000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		OperationThread ot = new OperationThread();
		ot.start();
	}
}
