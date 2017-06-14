package bankproject.readers;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import bankproject.entities.Account;
import bankproject.entities.AccountNumber;
import bankproject.entities.CountryEnum;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;

public class AccountCustomerThread extends ReaderThread  {
	
	public void run(){
		
		List<String> wordsOfTheFile = readInputFile("accounts_customers");//Y SI EL FICHERO NO EXISTE?? (ALGO APARTE DEL TRY/CATCH?)
		
		
		for (int i=1; i< wordsOfTheFile.size()/4; i++) {
			Customer customer = new Customer();
			customer.setName(wordsOfTheFile.get(4*i+2)) ;
			customer.setSurname(wordsOfTheFile.get(4*i+1));
			
			CountryEnum country = CountryEnum.valueOf(wordsOfTheFile.get(4*i).toUpperCase());
			//NO FUNCIONA PORQUE NO DA EL NUMERO DE CUENTA
			Account account = new Account(country); //I HAVE TO CHECK IF THE GENERATED RANDOM accountNumber ALREADY EXISTS IN THE DB AND IF SO GENERATE ANOTHER ONE AND BACK TO CHECK 
			account.setBalance(Integer.parseInt(wordsOfTheFile.get(4*i+3)));
			
			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvCustomer.setDbManager(SQLiteManager.getInstance());
			
			try {
			srvCustomer.create(customer); //save EN VEZ DE create?
			srvAccount.create(account); //save EN VEZ DE create?
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
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
	
	public static void main (String[] args) {
		AccountCustomerThread act = new AccountCustomerThread();
		act.start();
		
		
		
	}
	
}
	
