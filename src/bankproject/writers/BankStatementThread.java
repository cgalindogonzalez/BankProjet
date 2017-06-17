package bankproject.writers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import bankproject.entities.AbstractEntity;
import bankproject.entities.Account;
import bankproject.entities.Customer;
import bankproject.entities.Operation;
import bankproject.services.AbstractService;
import bankproject.services.SQLiteManager;
import bankproject.services.SrvAccount;
import bankproject.services.SrvCustomer;
import bankproject.services.SrvOperation;

public class BankStatementThread extends WriterThread {
	
	public String header = "Date\t\tAccount\t\tCustomer\t\tType\t\tAmount\r\n";//HABRÍA Q QUITAR EL TYPE
	
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
	 *Method to add the contents to the statements.txt file (without headers) 
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
			    	Customer customer = account.getCustomer();//?????
			    	String customerNameSurname = customer.getName() + " " + customer.getSurname();
			    	Integer amount = operation.getAmount();
			    
			    	String str = df.format(date) + "\t\t"+ accountNumber + "\t\t" + customerNameSurname + "\t\t" + amount;
			    	myBuffer.write(str);
			    	myBuffer.newLine();
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
	
	public static void main(String[] args){
		
		SrvOperation srvOperation = SrvOperation.getINSTANCE();
		srvOperation.setDbManager(SQLiteManager.getInstance());
		SrvAccount srvAccount = SrvAccount.getINSTANCE();
		srvAccount.setDbManager(SQLiteManager.getInstance());
		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
		srvCustomer.setDbManager(SQLiteManager.getInstance());
		
		BankStatementThread bst = new BankStatementThread();
		bst.start();
		
		//String idcustomer = "idcustomer"; 
		
//		Collection<Integer> ids = new HashSet<Integer>();
//		for (int i=1; i<5;i++ ){
//			ids.add(i);
//		}
//		SrvOperation srvOperation = SrvOperation.getINSTANCE();
//		srvOperation.setDbManager(SQLiteManager.getInstance());
//		SrvAccount srvAccount = SrvAccount.getINSTANCE();
//		srvAccount.setDbManager(SQLiteManager.getInstance());
//		SrvCustomer srvCustomer = SrvCustomer.getINSTANCE();
//		srvCustomer.setDbManager(SQLiteManager.getInstance());
//		
//		Collection <AbstractEntity> allOperations = new LinkedHashSet<AbstractEntity>();
//		
//		try {
//			allOperations = srvOperation.get(ids, "idoperation"); 
//			
//			 Iterator<AbstractEntity> it = allOperations.iterator();
//			    while(it.hasNext()){
//			    	Operation operation = (Operation) it.next();
//			      System.out.println(operation.getIdOperation() + " " + operation.getAmount());
//			    }
//			    
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
//		for (int i = 0; i< customer.length; i++) {
//			System.out.println(i + " " + customer[i].getIdOperation()  + " " + customer[i].getAmount() + " " + customer[i].getDate());
//		}
	}
}