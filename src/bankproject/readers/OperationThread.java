package bankproject.readers;

import java.util.Date;
import java.util.List;

import bankproject.entities.Account;
import bankproject.entities.Operation;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvOperation;

public class OperationThread extends ReaderThread {

	public void run () {

		List<String> wordsOfTheFile = readInputFile("operation"); 

		for (int i=1; i< wordsOfTheFile.size()/4; i++) {
			//Operation with amount read from the file 
			Operation operation = new Operation();
			operation.setAmount(Integer.parseInt(wordsOfTheFile.get(4*i)));
			operation.setDate(new Date());

			//Look into the DB the account with the account number read from the file
			String accountNumber = wordsOfTheFile.get(4*i+1);

			Account account = new Account();

			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());

			try {
				account = srvAccount.getAccountByAccountNumber(accountNumber);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if(account == null) 
				System.out.println("Wrong operation. This account does not exist"); //if the account is not in the DB

			//add or remove the amount from the balance 
			else {

				if (operation.getAmount() < 0){
					if (operation.getAmount() < account.getBalance())	
						account.removeMoneyToBalance(Math.abs(operation.getAmount())); 
					else 
						System.out.println("Balance can not be negative");
				}
				else {
					account.addMoneyToBalance(Math.abs(operation.getAmount()));
				}

				//Update the account in the DB
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
					srvOperation.save(operation);//create the operation in the DB
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		deleteInputFile("operation");

		try {
			Thread.sleep(660000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
