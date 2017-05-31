package bankproject.entities;

public class Customer extends AbstractEntity {
	
	
	private String surname;
	private String name;

	public Customer() {
		// TODO Auto-generated constructor stub
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
