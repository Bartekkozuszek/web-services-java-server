package personregister;



public class Person {
	

	
	private int id;
	private String firstName;
	private String lastName;
	private int birthYear;
	private String city;
	
	

	
	
	public Person() {
		//no argument constructor
	}
	
	public Person(String firstName, String lastName, int birthYear, String city) {
		//when creating new Person not yet in Db
		this.id = 0;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.city = city;
	}
	
	public Person(int id, String firstName, String lastName, int birthYear, String city) {
		//when creating new Person from DB result-set
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthYear = birthYear;
		this.city = city;
	}


	public int getPersonID() {
		return id;
	}


	public void setPersonID(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getBirthYear() {
		return birthYear;
	}


	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	@Override
	public String toString() {
		return "id : "+id+", firstName : " + firstName + ", lastName: " + lastName + ", birthYear : " + birthYear + ", city : "+ city;
	}
	

		
	}
	

