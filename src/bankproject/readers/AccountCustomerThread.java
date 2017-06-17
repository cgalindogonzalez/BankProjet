package bankproject.readers;

import java.sql.SQLException;
import java.util.List;
import bankproject.entities.Account;
import bankproject.entities.CountryEnum;
import bankproject.entities.Customer;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;

public class AccountCustomerThread extends ReaderThread  {

	public void run(){

		List<String> wordsOfTheFile = readInputFile("accounts_customers");


		for (int i=1; i< wordsOfTheFile.size()/4; i++) {
			//Customer with name and surname read from the file 
			Customer customer = new Customer();
			customer.setName(wordsOfTheFile.get(4*i+2)) ;
			customer.setSurname(wordsOfTheFile.get(4*i+1));

			//Check if customer exists in the DB and save it if not
			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();

			Customer customerDB = null;
			try {
				customerDB = srvCustomer.getCustomerByNameSurname(customer.getName(), customer.getSurname());
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (customerDB == null) {
				try {
					srvCustomer.save(customer);
					customer = srvCustomer.getCustomerByNameSurname(customer.getName(), customer.getSurname());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				customer = customerDB;
			}

			//Create new account number for the customer and check if this account number already exists (and then generate another one if so)
			CountryEnum country = CountryEnum.valueOf(wordsOfTheFile.get(4*i).toUpperCase());
			Account account = null;
			Account accountDB = null;

			do {
				account = new Account(country, customer); 
				SrvAccount srvAccount = SrvAccount.getINSTANCE();

				try {
					accountDB = srvAccount.getAccountByAccountNumber(account.getAccountNumber());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} while(!(accountDB == null)); 

			account.setBalance(Integer.parseInt(wordsOfTheFile.get(4*i+3)));

			//Save the new account in the DB
			SrvAccount srvAccount = SrvAccount.getINSTANCE();

			try {
				srvAccount.save(account);			

			} catch (Exception e1) {
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

