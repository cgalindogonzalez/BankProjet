package bankproject.entities;

import java.sql.Date;

public class Operation extends AbstractEntity {
	
	private Date date;
	private Customer customer;
	private TypeEnum type;
	private int amount;
	

	public Operation() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * getter
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * setter
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * getter
	 * @return customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * setter
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * getter
	 * @return type
	 */
	public TypeEnum getType() {
		return type;
	}

	/**
	 * setter
	 * @param type
	 */
	public void setType(TypeEnum type) {
		this.type = type;
	}

	/**
	 * getter
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * setter
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
