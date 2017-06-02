package bankproject.entities;

import java.sql.Date;

public class Operation extends AbstractEntity {
	
	private Integer id;
	private Date date;
	private TypeEnum type;
	private int amount;
	

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
	public void setId(Integer id) {
		this.id = id;
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

}
