package bankproject.entities;

public enum CountryEnum {
	FRANCE("FR"), NETHERLANDS("NL"), GREAT_BRITAIN("GB"), GERMANY("DE"), SPAIN("ES");

	/**
	 * Constructor
	 */
	private String abbreviation;
	private CountryEnum (String abbreviation) {
		this.setAbbreviation(abbreviation);
	}

	/**
	 * Getter
	 * @return abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Setter
	 * @param abbreviation
	 */
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
