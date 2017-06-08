package bankproject.readers;

import java.sql.SQLException;
import java.util.List;

import bankproject.entities.Account;
import bankproject.entities.CountryEnum;
import bankproject.entities.Customer;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;

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
				srvCustomer.create(customer);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			CountryEnum country = CountryEnum.valueOf(wordsOfTheFile.get(4*i).toUpperCase());
						
			Account account = new Account(country); //I HAVE TO CHECK IF THE GENERATED RANDOM accountNumber ALREADY EXISTS IN THE DB AND IF SO GENERATE ANOTHER ONE AND BACK TO CHECK 
			account.setBalance(Integer.parseInt(wordsOfTheFile.get(4*i+3)));   
		
			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());//CORREGIR!!!
			try {
				srvAccount.create(account);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		deleteInputFile("accounts_customers");
		
		try {
			Thread.sleep(420000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
	
