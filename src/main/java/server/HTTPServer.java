package server;

import api.HTTPMethods;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HTTPServer implements Runnable{

    static final int PORT = 8081;

    private static Map<String, HTTPMethods> functions = new HashMap<String, HTTPMethods>();

    private int index;

    private Socket clientSocket;

    public HTTPServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void run() {

        BufferedReader clientRequest = null;

        try {
            clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            HTTPRequestParser HTTPParser = new HTTPRequestParser();
            RequestObject request = HTTPParser.parse(clientRequest);
            ResponseObject response = new ResponseObject();
            String destination = request.getHeader().get("destination");
            String httpMethod = request.getHeader().get("method");

            if (functions.containsKey(destination)) {
                HTTPMethods m = functions.get(destination);// hämta ut en factory och instantiera ett objeckt av vald destination

                switch (httpMethod) {
                    case "GET":
                        response = m.get(request, response);
                        break;

                    case "HEAD":
                        response = m.head(request, response);
                        break;

                    case "POST":
                        response = m.post(request, response);
                        break;
                    default:
                        System.out.println("");
                }

                handleOutput(response, clientSocket);
                clientSocket.close();
            }
        }catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }


    private void handleOutput(ResponseObject response, Socket clientSocket){

        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

            out.println("HTTP/1.1 200 OK");
            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
            out.println("date: " + new Date());
            out.println("content-type: " + response.getContentType());
            out.println("content-length: " + response.getContentLength());
            out.println();
            out.flush();

            if(response.getData() != null){
                byte[] content = response.getData();
                System.out.println("content: " + content);
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
