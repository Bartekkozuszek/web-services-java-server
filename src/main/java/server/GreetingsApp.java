package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.HTTPModule;

public class GreetingsApp extends HTTPModule {
	RequestObject request;
	ResponseObject response;


	String htmlPrefix = "<!DOCTYPE html><html><head><meta charset='ISO-8859-1'><title>Greetings!</title></head><body>"
			+ "<h2>Welcome to the Greeting App</h2><h3>The most friendly app on the Internet</h3><hr><br>";

	String htmlPostfix = "<hr></body></html>";

	@Override
	public ResponseObject get(RequestObject request, ResponseObject response) {

		String input = request.getHeader().get("requestString");

		ArrayList<String> route = new ArrayList<>();
		//assemble the route array list from url:

		String[] parts = input.split("/");
		route = new ArrayList<>();
		for (int i = 0; i < parts.length; i++) {
			if (!parts[i].isEmpty()) {   //skip empty route parts
				route.add(parts[i]);
			}
		}

		StringBuilder contentBuilder = new StringBuilder();

		contentBuilder.append(htmlPrefix);

		if (route.size() > 1) {

			contentBuilder.append("<h1>A really warm Welcome to you: ");

			for (int i = 1; i < route.size(); i++) {
				contentBuilder.append(route.get(i) + " ");
			}
		} else {
			contentBuilder.append("<h2>Greeting Request</h2><form action='' method='post'>Salutation:<br><input type='text' name='salutation' value='' placeholder = 'Mr'>");
			contentBuilder.append("<br>First name:<br><input type='text' name='firstname' value='' placeholder = 'Bob'>");
			contentBuilder.append("<br>Last name:<br><input type='text' name='lastname' value='' placeholder = 'Dobalina'><br><br>");
			contentBuilder.append("<input type='submit' value='Submit'></form>");
		}

		contentBuilder.append("</h1>" + htmlPostfix);

//		response = new ResponseObject();
		response.setData(contentBuilder.toString().getBytes());
		response.setContentType("text/html");
		response.setContentLength(contentBuilder.toString().length());
		return response;

	}



	@Override
	public ResponseObject post(RequestObject request, ResponseObject response) {
		String body = request.getHeader().get("body");
		StringBuilder locationBuilder = new StringBuilder();

		locationBuilder.append("/greetings/");
		String[] bodyparts = body.split("&");
		for (String part : bodyparts) {

			Integer index = part.indexOf("=");
			locationBuilder.append(part.substring(index + 1) + "/"); //skip the =
		}


	//	response = new ResponseObject();

		response.setContentType("text/html");
		response.setStatusLine("HTTP/1.1 302 Found");
		response.setLocation(locationBuilder.toString());

		return response;



	}
}
