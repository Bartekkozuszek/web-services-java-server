package api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import personregister.DBHandler;
import personregister.Person;
import server.RequestObject;
import server.ResponseObject;

public class RESTHandler extends HTTPModule{

	DBHandler db_handler;
	List<Person> persons;
	Gson gson;
	JsonParser jsonParser;
	ArrayList<String> route;
	
	@Override
	public ResponseObject get(RequestObject request, ResponseObject response){
		gson = new Gson(); //prep for GSON output
		
		String input = request.getHeader().get("requestString");
		try {
			db_handler = new DBHandler();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		route = parseRoute(request.getHeader().get("requestString"));
		
		
		
		if(route.get(0).equals("api") && route.get(1).equals("person")) {
			
			if(route.size() == 2) {//list of persons to be returned
				
				persons= new ArrayList<Person>();
				try {
					persons = db_handler.listPerson("");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String jsonResponse = gson.toJson(persons);
				
			    response.setContentType("application/json");	   	        
			    byte [] requestedFile = jsonResponse.getBytes(StandardCharsets.UTF_8);
		        response.setContentLength(requestedFile.length);
		        response.setData(requestedFile);
			    
		        return response;
				
				
			}
			
			else { //single person to be returned
				Person person = new Person();
				try {
					person = db_handler.getPerson_by_ID(Integer.parseInt(route.get(2)));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			    String jsonResponse = gson.toJson(person);
			    
			    response.setContentType("application/json");	   	        
			    byte [] requestedFile = jsonResponse.getBytes(StandardCharsets.UTF_8);
		        response.setContentLength(requestedFile.length);
		        response.setData(requestedFile);
			    
		        return response;
			   			
			}
					
		}
		
		return response;
		
	}
	
	
	@Override
	public ResponseObject post(RequestObject request, ResponseObject response){//Add Single New Person Record to db
		route = parseRoute(request.getHeader().get("requestString"));
		if(route.get(0).equals("api") && route.get(1).equals("person") && route.size()>2) {
				Person person;
				Person personTemp;
				gson = new Gson();
				try {
					db_handler = new DBHandler();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				personTemp= gson.fromJson(request.getHeader().get("body"), Person.class);
				person = new Person(personTemp.getFirstName(),personTemp.getLastName(), personTemp.getBirthYear(), personTemp.getCity() );
				
				try {
					db_handler.addPerson(person);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}	
		return response;
	}
	
	@Override
	public ResponseObject put(RequestObject request, ResponseObject response){//Update an existing record
		route = parseRoute(request.getHeader().get("requestString"));
		
		if(route.get(0).equals("api") && route.get(1).equals("person") && route.size()>2) {
			
			gson = new Gson();
			
			try {
				db_handler = new DBHandler();
				Person person;
				Person personTemp;
				
				person = db_handler.getPerson_by_ID(Integer.parseInt(route.get(2)));
				personTemp= gson.fromJson(request.getHeader().get("body"), Person.class);
				
				person.setFirstName(personTemp.getFirstName());
				person.setLastName(personTemp.getLastName());
				person.setBirthYear(personTemp.getBirthYear());
				person.setCity(personTemp.getCity());
				
				db_handler.updatePerson(person);
				
				
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
		}
		return response;
	}
	
	
	
	@Override
	public ResponseObject delete(RequestObject request, ResponseObject response){ //Delete a person from DB
		route = parseRoute(request.getHeader().get("requestString"));		
		if(route.get(0).equals("api") && route.get(1).equals("person") && route.size()>2) {
			
			try {
				db_handler.deletePerson(Integer.parseInt(route.get(2))); //No questions asked :-)
			
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}
		return response;
	}
	
	
	
	private ArrayList<String> parseRoute(String input) {
	
    
	ArrayList<String> route = new ArrayList<>();
//assemble the route array list from url:
	
	route =	new ArrayList<>();
	
	String[] parts = input.split("/");
	route = new ArrayList<>();
	for (int i = 0; i < parts.length; i++) { 
		if(!parts[i].isEmpty()) {   //skip empty route parts
			route.add(parts[i]);
		}
	}
	
	return route;
	
	}

}
