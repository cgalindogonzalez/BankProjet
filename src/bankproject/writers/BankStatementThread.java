package bankproject.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.services.SrvOperation;

public class BankStatementThread extends WriterThread {

	public String header = "Date\t\tAccount\t\tCustomer\t\tAmount\r\n";

	public void run() {
		writeHeaderOutputFile ("statements", header);
		writeContentsOutputFile("statements");
		try {
			Thread.sleep(780000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *add the contents to the statements.txt file (without header) 
	 */
	public void writeContentsOutputFile(String file) {

		String fn = getOutputTxtFilePath() + System.getProperty("file.separator") + file +".txt";

		try {
			FileWriter output = new FileWriter (fn, true);
			BufferedWriter myBuffer = new BufferedWriter (output);

			SrvOperation srvOperation = SrvOperation.getINSTANCE();

			try {
				Collection<AbstractEntity> allOperations = srvOperation.getAll(); 

				Iterator<AbstractEntity> it = allOperations.iterator();

				while(it.hasNext()){

					Operation operation = (Operation) it.next();
					Date date = operation.getDate();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Account account = operation.getAccount();
					String accountNumber = account.getAccountNumber();
					Customer customer = account.getCustomer();
					String customerNameSurname = customer.getName() + " " + customer.getSurname();
					Integer amount = operation.getAmount();

					String str = df.format(date) + "\t\t"+ accountNumber + "\t\t" + customerNameSurname + "\t\t" + amount + "\r\n";
					myBuffer.write(str);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			myBuffer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}