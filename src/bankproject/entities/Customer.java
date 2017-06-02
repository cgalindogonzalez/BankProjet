package bankproject.entities;

public class Customer extends AbstractEntity {
	
	private Integer id;
	private String surname;
	private String name;


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
