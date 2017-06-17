package bankproject.readers;

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
			Customer customer = new Customer();
			customer.setName(wordsOfTheFile.get(4*i+2)) ;
			customer.setSurname(wordsOfTheFile.get(4*i+1));

			SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();

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

				try {
					accountDB = srvAccount.getAccountByAccountNumber(account.getAccountNumber());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} while(!(accountDB == null));

			account.setBalance(Integer.parseInt(wordsOfTheFile.get(4*i+3)));

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

