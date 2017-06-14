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
		
		List<String> wordsOfTheFile = readInputFile("operation"); //Y SI EL FICHERO NO EXISTE?? (ALGO APARTE DEL TRY/CATCH?)
		
		for (int i=1; i< wordsOfTheFile.size()/3; i++) {
			Operation operation = new Operation();
			operation.setAmount(Integer.parseInt(wordsOfTheFile.get(3*i)));
			operation.setDate(new GregorianCalendar());
			
			SrvOperation srvOperation = SrvOperation.getINSTANCE();
			srvOperation.setDbManager(SQLiteManager.getInstance());
			try {
				srvOperation.create(operation); //save EN VEZ DE create?
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String accountNumber = wordsOfTheFile.get(3*i+1);
			Account account = new Account (accountNumber);
			if(operation.getAmount() < 0)
				account.removeMoneyToBalance(Math.abs(operation.getAmount())); 
			else if(operation.getAmount() > 0)
				account.addMoneyToBalance(Math.abs(operation.getAmount()));
			
			SrvAccount srvAccount = SrvAccount.getINSTANCE();
			srvAccount.setDbManager(SQLiteManager.getInstance());
			try {
				srvAccount.update(account);//save EN VEZ DE update? O NO VALE NINGUNA DE LAS DOS?
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

	public static void main(String[] args) {
		OperationThread ot = new OperationThread();
		ot.start();
	}
}
