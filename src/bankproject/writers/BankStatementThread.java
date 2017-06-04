package bankproject.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BankStatementThread extends WriterThread {
	
	public String header = "Date\t\tAccount\t\tCustomer\t\tType\t\tAmount\r\n";
	
	public void run() {
		writeHeaderOutputFile ("statements", header);
		writeContentsOutputFile();
		try {
			Thread.sleep(780000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Method to add the contents to the statements.txt file (without headers) 
	 */
	public void writeContentsOutputFile() {
		String fn = getOutputTxtFilePath() + System.getProperty("file.separator") + "statements.txt";
		try {
			FileWriter output = new FileWriter (fn, true);
			BufferedWriter myBuffer = new BufferedWriter (output);
			myBuffer.write("contents"); 
			/*I HAVE TO FINISH THIS TO ADD THE CONTENT OF THE FILE statements.txt :
			 * make the query to the DB with the getters to get entities or collections of entities of AbstractService class (with entity = operation)
			 * print out the fields' values (date, account, customer, type, amount) line by line as content 
			*/
			
			
			
			myBuffer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		//System.out.println(headline);
		BankStatementThread bst = new BankStatementThread();
		bst.start();
	}
}