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


	String htmlPrefix = "<!DOCTYPE html><html><head><meta charset='ISO-8859-1'><title>Greetings!</title><style>\n" +
			"@import url(https://fonts.googleapis.com/css?family=Montserrat);\n" +
			"html,\n" +
			"body {\n" +
			"  overflow: hidden;\n" +
			"  margin: 0px;\n" +
			"}\n" +
			".background {\n" +
			"  background-size: cover;\n" +
			"  background-repeat: no-repeat;\n" +
			"  background-position: center center;\n" +
			"  overflow: hidden;\n" +
			" position: fixed;\n" +
			"  width: 100%;\n" +
			"  background-image: url(/files/images/hello.jpg);\n" +
			"\n" +
			"}\n" +
			"\n" +
			".content-wrapper {\n" +
			"  height: 100vh;\n" +
			"  display: flex;\n" +
			"  justify-content: center;\n" +
			"  text-align: center;\n" +
			"  flex-flow: column nowrap;\n" +
			"  color: #fff;\n" +
			"  font-family: Montserrat;\n" +
			"  text-transform: uppercase;\n" +
			"}\n" +
			"\n" +
			".content-title {\n" +
			"  font-size: 12vh;\n" +
			"  line-height: 1.4;\n" +
			"}\n" +
			"\n" +
			"input[type=submit] {\n" +
			"\tbackground: #42846c;\n" +
			"    padding: 10px 20px;\n" +
			"    text-transform: uppercase;\n" +
			"    color: #ffffff;\n" +
			"    font-family: montserrat;\n" +
			"    font-weight: 600;\n" +
			"    border: 0px;\n" +
			"    transition: 0.2s;\n" +
			"    cursor: pointer;\n" +
			"}\n" +
			"\n" +
			"input[type=text] {\n" +
			"padding: 10px 5px;\n" +
			"text-transform: uppercase;\n" +
			"font-family: montserrat;\n" +
			"opacity: 0.8;\n" +
			"}\n" +
			"\n" +
			"\n" +
			"input[type=submit]:hover {\n" +
			"background: #388267;\n" +
			"}\n" +
			"</style></head><body>"
			+ "<div class=\"container\">\n" +
			"<section class=\"background\">\n" +
			"<div class=\"content-wrapper\"><h1 class=\"content-title\">The Greeting App</h1><p>The most friendly app on the Internet</p>";

	String htmlPostfix = "</div>\n" +
			"</section>\n" +
			"</div></body></html>";

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

			contentBuilder.append("<p>A really warm welcome to you</p> ");

			for (int i = 1; i < route.size(); i++) {
				contentBuilder.append(route.get(i) + " ");
			}
		} else {
			contentBuilder.append("<p>Greeting Request</p>\n" +
					"<form action='' method='post'>\n" +
					"<input type='text' name='salutation' value='' placeholder = 'Title'>\n" +
					"<input type='text' name='firstname' value='' placeholder = 'First Name'>\n" +
					"<input type='text' name='lastname' value='' placeholder = 'Last Name'><br><br>\n" +
					"<input type='submit' value='Submit'>\n" +
					"</form>");

		}

		contentBuilder.append(htmlPostfix);

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
