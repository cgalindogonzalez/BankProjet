package bankproject.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BankStatementThread extends AbstractWriterThread {
	//Cambiar a privado??
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
			myBuffer.write("prueba");
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