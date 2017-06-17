package bankproject.entities;

import java.util.Date;

public class Operation extends AbstractEntity {

	private Integer idOperation;
	private Date date;
	private TypeEnum type;
	private int amount;
	private Account account;


	/**
	 * getter
	 * @return idOperation
	 */
	public Integer getIdOperation() {
		return idOperation;
	}

	/**
	 * setter
	 * @param idOperation
	 */
	public void setIdOperation(Integer idOperation) {
		this.idOperation = idOperation;
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

	/**
	 * getter
	 * @return account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * setter
	 * @param account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

}
