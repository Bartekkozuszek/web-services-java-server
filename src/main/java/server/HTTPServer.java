package server;

import api.HTTPMethods;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HTTPServer implements Runnable {

    static final int PORT = 8081;
    static final String FILE_NOT_FOUND = "resources/404.html";
    static final File WEB_ROOT = new File(".");
    static final boolean verbose = true;
    private static Map<String, HTTPMethods> functions = new HashMap<String, HTTPMethods>();
    private Socket clientSocket;

    public HTTPServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void run() {

        BufferedReader rawRequest = null;

        try {
            rawRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            HTTPRequestParser parser = new HTTPRequestParser();

            RequestObject request = parser.parse(rawRequest);
            ResponseObject response = new ResponseObject();
            String destination = request.getHeader().get("destination");
            String httpMethod = request.getHeader().get("method");

            if (functions.containsKey(destination)) {

                HTTPMethods service = functions.get(destination);

                switch (httpMethod) {

                    case "GET":
                        response = service.get(request, response);
                        break;
                    case "HEAD":
                        response = service.head(request, response);
                        break;
                    case "POST":
                        response = service.post(request, response);
                        break;
                    case "PUT":
                        response = service.put(request, response);
                        break;
                    case "DELETE":
                        response = service.put(request, response);
                        break;
                    default:
                        break;
                }
            } else {
                response = fileNotFound(response);
            }
            handleOutput(response, clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private ResponseObject fileNotFound(ResponseObject response) {

        File file = new File(WEB_ROOT, FILE_NOT_FOUND);
        int fileLength = (int) file.length();
        byte[] requestedFile = readFileData(file, fileLength);

        response.setContentType("text/html");
        response.setContentLength(fileLength);
        response.setData(requestedFile);

        return response;
    }


    private byte [] readFileData(File file, int fileLength){

        FileInputStream fileIn = null;
        byte [] data = new byte [fileLength];

        try{
            fileIn = new FileInputStream(file);
            fileIn.read(data);

        }catch(IOException e){

        }finally {
            if(fileIn != null){
                try{
                    fileIn.close();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return data;
    }


    private void handleOutput(ResponseObject response, Socket clientSocket){

        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
            
            if(response.getStatusLine()== null) {
            	out.println("HTTP/1.1 200 OK");
            }
            else {
            	out.println(response.getStatusLine());
            }
            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
            out.println("date: " + new Date());
            out.println("content-type: " + response.getContentType());
            if(response.getContentLength()>0) {
            	 out.println("content-length: " + response.getContentLength());
            	}

            if(response.getLocation()!= null) {
            	out.println("Location: " + response.getLocation());
            }
            out.println();
            out.flush();

            if(response.getData() != null){
                byte[] content = response.getData();
                dataOut.write(content);
                dataOut.flush();
                dataOut.close();
            }

        }catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static Map<String, HTTPMethods> getFunctions() {
        return functions;
    }
}
