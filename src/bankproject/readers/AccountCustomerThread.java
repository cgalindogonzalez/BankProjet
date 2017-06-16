package bankproject.readers;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import bankproject.entities.Account;
import bankproject.entities.CountryEnum;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.exceptions.SrvException;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;

public class AccountCustomerThread extends ReaderThread  {
	
	public void run(){
		
		List<String> wordsOfTheFile = readInputFile("accounts_customers");
		
		
		for (int i=1; i< wordsOfTheFile.size()/4; i++) {
			Customer customer = new Customer();
			customer.setName(wordsOfTheFile.get(4*i+2)) ;
			customer.setSurname(wordsOfTheFile.get(4*i+1));
			
			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
			srvCustomer.setDbManager(SQLiteManager.getInstance());	
			try {
				srvCustomer.save(customer);
				customer = srvCustomer.getCustomerByNameSurname(customer.getName(), customer.getSurname());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			CountryEnum country = CountryEnum.valueOf(wordsOfTheFile.get(4*i).toUpperCase());
			Account account = null;
			Account accountDB = null;
			do {
				account = new Account(country, customer); 
				SrvAccount srvAccount = SrvAccount.getINSTANCE();
				srvAccount.setDbManager(SQLiteManager.getInstance());
				try {
					accountDB = srvAccount.getAccountByAccountNumber(account.getAccountNumber());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} while(!(accountDB == null));
			
			account.setBalance(Integer.parseInt(wordsOfTheFile.get(4*i+3)));

			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());
			try {
				srvAccount.save(account);			
			
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	
			}
			
		}
		
		System.out.println((wordsOfTheFile.size()/4-1) + " customers and accounts created and saved");
		
		deleteInputFile("accounts_customers");
		
		try {
			Thread.sleep(420000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
			
		
		
		
	
	
	public static void main (String[] args) {
		AccountCustomerThread act = new AccountCustomerThread();
		act.start();
		
		
		
	}
	
}
	
