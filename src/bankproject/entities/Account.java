package bankproject.entities;

public class Account {

	private AccountNumber accountNumber;
	private int balance;
	
	/**
	 * Constructeur par défaut
	 */
	public Account() {
		accountNumber = null;
		setBalance(0);
	}
	
	/**
	 * Constructeur
	 */

	public Account(AccountNumber accountNumber){
		this.accountNumber = accountNumber; //a completar
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

	
}
