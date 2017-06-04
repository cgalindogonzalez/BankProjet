package bankproject.entities;

import java.util.GregorianCalendar;

public class Operation extends AbstractEntity {
	
	private Integer idOperation;
	private GregorianCalendar date;
	private TypeEnum type;
	private int amount;
	

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
	public GregorianCalendar getDate() {
		return date;
	}

	/**
	 * setter
	 * @param date
	 */
	public void setDate(GregorianCalendar date) {
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

}
