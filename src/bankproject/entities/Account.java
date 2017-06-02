package bankproject.entities;

public class Account extends AbstractEntity {
	
	private Integer id; 
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
	 * Constructor
	 */

	public Account(CountryEnum country){
		
		accountNumber = new AccountNumber (country);
	}
	
	/**
	 * getter
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * setter
	 * @param id
	 */
	public void setId(int id) {
		id = id;
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

public static void main(String[] args) {
		
		Account account = new Account(CountryEnum.SPAIN);
		System.out.println(account.getAccountNumber().toString()); //NO FUNCIONA COMO YO QUIERO!!!
	}

	
}
