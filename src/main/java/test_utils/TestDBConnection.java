package test_utils;

import java.sql.SQLException;
import java.util.Scanner;

import personregister.DBHandler;
import personregister.Person;

public class TestDBConnection {

	public static void main(String[] args) throws SQLException {
		
		DBHandler testdb_handler = new DBHandler();
		
		String fn = "Gabor";
		String en = "Bellak";
		String stad = "Götet";
		int year = 1962;

		Person person1 = new Person(fn,en,year,stad);	
		testdb_handler.addPerson(person1);
		System.out.println(testdb_handler.listPerson(""));
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter record to fetch:");
		int i =scan.nextInt();	
		
		Person person2 = testdb_handler.getPerson_by_ID(i);
		
		System.out.println(person2);

		
		person2.setCity("Haga");
		testdb_handler.updatePerson(person2);
		System.out.println(testdb_handler.listPerson(""));
		
		scan = new Scanner(System.in);
		System.out.println("Enter record to delete:");
		i =scan.nextInt();
		
		
		
		testdb_handler.deletePerson(i);

		



	}

}
