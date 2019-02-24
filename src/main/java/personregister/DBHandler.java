package personregister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;


public class DBHandler {
	
	String DB_URI = "jdbc:sqlite:./database/test.db";

	Connection connection;
	
	public	DBHandler() throws SQLException{
		this.connection = DriverManager.getConnection(DB_URI);
	}
	

	public  List<Person> listPerson(String whereclause) throws SQLException {
		String queryString;
		
		if(whereclause.length() >0) {
			queryString = "SELECT id ,firstname ,lastname, birthyear,city FROM person " +"WHERE "+whereclause+";";
			}
		else {
			queryString = "SELECT id ,firstname ,lastname, birthyear,city FROM person;";
		}
			
		
		List<Person> persons = new ArrayList<Person>();
		
		try( //try with resources
					
				PreparedStatement statement = connection.prepareStatement(queryString);
				ResultSet resultset = statement.executeQuery();
						){
				
				while(resultset.next()) {
					Person person = new Person();
					person.setPersonID(resultset.getInt("id"));
					person.setFirstName(resultset.getString("firstName"));
					person.setLastName(resultset.getString("lastName"));
					person.setBirthYear( resultset.getInt("birthYear"));
					person.setCity(resultset.getString("city"));
					
					persons.add(person);
					

				
			
				}
			
						} //end of try with resources
		
		
				return persons;
				}
	
	public Person getPerson_by_ID(int id) throws SQLException{
		String queryString = "SELECT id ,firstname ,lastname, birthyear,city FROM person WHERE id= ?;";
		Person person;
		
				
		try(    
				Connection connection = DriverManager.getConnection(DB_URI);
				PreparedStatement statement = connection.prepareStatement(queryString);
					){
				
				statement.setInt(1, id);
				ResultSet resultset = statement.executeQuery();	
				
				person = new Person(resultset.getInt("id"),
											resultset.getString("firstName"),
											resultset.getString("lastName"),
											resultset.getInt("birthYear"),
											resultset.getString("city"));
				}
		
				return person;

		}
	
	
	public void deletePerson(int id) throws SQLException{
		String queryString= "DELETE FROM person WHERE id = ?";
		
		
				
		try(    
				Connection connection = DriverManager.getConnection(DB_URI);
				PreparedStatement statement = connection.prepareStatement(queryString)
			
						){
				statement.setInt(1, id);
				System.out.println(statement.toString());	
				statement.executeUpdate();
				statement.close();

				
				}

		}
	
	public void addPerson(Person person) throws SQLException{
		String queryString= "INSERT INTO person (firstName, lastName, birthYear, city) VALUES (?,?,?,?);";
			
		try(    
				Connection connection = DriverManager.getConnection(DB_URI);
				PreparedStatement statement = connection.prepareStatement(queryString)
			
						){
				statement.setString(1, person.getFirstName());
				statement.setString(2, person.getLastName());
				statement.setInt(3, person.getBirthYear());
				statement.setString(4, person.getCity());
				
				statement.executeUpdate();
				statement.close();
				
				}

		} 
	
	public void updatePerson(Person person) throws SQLException{
		String queryString= "UPDATE person SET id =?, firstName =?, lastName = ?, birthYear = ?, city = ? WHERE id = ?;";
			
		try(    
				Connection connection = DriverManager.getConnection(DB_URI);
				PreparedStatement statement = connection.prepareStatement(queryString)
			
						){
				statement.setInt(1, person.getPersonID());
				statement.setString(2, person.getFirstName());
				statement.setString(3, person.getLastName());
				statement.setInt(4, person.getBirthYear());
				statement.setString(5, person.getCity());
				statement.setInt(6, person.getPersonID());

				
				statement.executeUpdate();
				statement.close();
				
				}

		} 


}



	



