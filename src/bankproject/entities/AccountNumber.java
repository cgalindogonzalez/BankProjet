package bankproject.entities;

public class AccountNumber {
	
	private CountryEnum country;
	private int number;

	/**
	 * Constructor
	 * @param country
	 */
	public AccountNumber(CountryEnum country) {
		createAccountNumber(country);

	}


	/**
	 * getter
	 * @return country
	 */
	public CountryEnum getCountry() {
		return country;
	}

	/**
	 * setter
	 * @param country
	 */
	public void setCountry(CountryEnum country) {
		this.country = country;
	}
	
	/**
	 * Méthode pour générer un numero entier aleatoire de 6 chiffres 
	 * @return randNumber
	 */
	public int generateNumber() {
		int randNumber = (int)(Math.random()*1000000); 
		if (randNumber < 100000) {
			randNumber = randNumber*10; //pour tenir en compte le cas dans lequel la première chiffre est un zero (qui donne un entier de 5 chiffres)
		}
		return randNumber;
	}
	
	/**
	 * Méthode pour créer le numero de compte   
	 * @return 
	 */
	public String createAccountNumber(CountryEnum country) {
		number = generateNumber();
		String str = country.getAbbreviation() + String.valueOf(number);
		return str;
	}
	
	public String toString(){
		  return createAccountNumber(CountryEnum.SPAIN);
		  }
	
	
	// falta comprobar que el identificador no exista ya en la base de datos!!!!  

	public static void main(String[] args) {
		
		AccountNumber accountNumber = new AccountNumber(CountryEnum.SPAIN);
		System.out.println(accountNumber.toString()); 
	}
}
