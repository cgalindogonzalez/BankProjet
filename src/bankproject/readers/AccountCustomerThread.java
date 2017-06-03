package bankproject.readers;

import java.util.List;

import bankproject.entities.Account;
import bankproject.entities.Customer;

public class AccountCustomerThread extends AbstractReaderThread {
	
	public void run(){
		
		List<String> wordsOfTheFile = readInputFile("accounts_customers");
		Customer customer;
		Account account;
		
		
		
		deleteInputFile("accounts_customers");
		try {
			Thread.sleep(420000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args){
		AccountCustomerThread act = new AccountCustomerThread();
		act.start();
		
	}
}
	
