package bankproject.entities;

public class Customer extends AbstractEntity {

	private Integer idCustomer;
	private String surname;
	private String name;



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



}
