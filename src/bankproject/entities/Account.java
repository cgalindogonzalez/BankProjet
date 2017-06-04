package bankproject.entities;

public class Account extends AbstractEntity {
	
	private Integer idAccount; 
	private AccountNumber accountNumber;
	private int balance;
	
	/**
	 * Default constructor
	 */
	public Account () {
		accountNumber = null;
		setBalance(0);
	}
	/**
	 * Constructor to create an account with the accountNumber generated by the random method (createAccountNumberFromRandomNumber)
	 */

	public Account(CountryEnum country){
		
		accountNumber = new AccountNumber (country);
		setBalance(0);
	}
	/**
	 * Constructor to create an account with the accountNumber done by a string (createAccountNumberFromString) ---> to be used in the operation Thread
	 */
	
	public Account(String str) {
		
		accountNumber = new AccountNumber (str);
	}
	
	/**
	 * getter
	 * @return idAccount
	 */
	public Integer getIdAccount() {
		return idAccount;
	}

	/**
	 * setter
	 * @param idAccount
	 */
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}
	
	/**
	 * getter
	 * @return accountNumber
	 */
	public AccountNumber getAccountNumber() {
		return accountNumber;
	}

	/**
	 * setter
	 * @param accountNumber
	 */
	public void setAccountNumber(AccountNumber accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * getter
	 * @return balance
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * setter
	 * @param balance
	 */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	/**
	 * 
	 * @param money
	 */
	public void addMoneyToBalance (int money){
		balance = balance + money;
	}
	
	/**
	 * 
	 * @param money
	 */
	public void removeMoneyToBalance(int money){
		balance = balance - money;
	}
	



	
}
