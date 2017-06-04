package bankproject.readers;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import bankproject.entities.Account;
import bankproject.entities.Operation;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvOperation;

public class OperationThread extends ReaderThread {

	public void run () {
		
		List<String> wordsOfTheFile = readInputFile("operation");
		
		for (int i=1; i< wordsOfTheFile.size()/3; i++) {
			Operation operation = new Operation();
			operation.setAmount(Integer.parseInt(wordsOfTheFile.get(3*i)));
			operation.setDate(new GregorianCalendar());
			
			SrvOperation srvOperation = SrvOperation.getINSTANCE();
			srvOperation.setDbManager(SQLiteManager.getInstance());
			try {
				srvOperation.create(operation);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String accountNumber = wordsOfTheFile.get(3*i+1);
			Account account = new Account (accountNumber);
			
			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());
			try {
				srvAccount.update(account);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			if(operation.getAmount() < 0)
				account.removeMoneyToBalance(Math.abs(operation.getAmount())); 
			else if(operation.getAmount() > 0)
				account.addMoneyToBalance(Math.abs(operation.getAmount()));
	
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
