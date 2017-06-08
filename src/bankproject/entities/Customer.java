package bankproject.entities;

import java.util.HashSet;
import java.util.Set;

public class Customer extends AbstractEntity {
	
	private Integer idCustomer;
	private String surname;
	private String name;
	private Set<Account> accountsList = new HashSet<Account>();


	/**
	 * getter
	 * @return idCustomer
	 */
	public Integer getIdCustomer() {
		return idCustomer;
	}

	/**
	 * setter
	 * @param idCustomer
	 */
	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	/**
	 * getter
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * setter
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * getter
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter
	 * @return accountsList
	 */
	public Set<Account> getAccountsList() {
		return accountsList;
	}

	/**
	 * setter
	 * @param accountsList
	 */
	public void setAccountsList(Set<Account> accountsList) {
		this.accountsList.addAll(accountsList);
	}
	
	public void addAccountToAccountsList(Account account) {
		this.accountsList.add(account);
	}

	
}
