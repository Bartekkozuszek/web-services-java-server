package server;

import api.HTTPModule;
import api.RESTHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

import static server.HTTPServer.PORT;
import static server.HTTPServer.verbose;

public class Main {

    public static void main(String[] args) {

        HTTPModule files = new FileModule();
        HTTPModule calculator = new Calculator();
        HTTPModule reverse = new ReverseModule();
        HTTPModule greetings = new  GreetingsApp();
        HTTPModule resthandler = new  RESTHandler();
        
        
        HTTPServer.getFunctions().put("reverse", reverse);
        HTTPServer.getFunctions().put("files", files);
        HTTPServer.getFunctions().put("calculator", calculator);
        HTTPModule personAge = new PersonAge();
        HTTPServer.getFunctions().put("personage", personAge);
        HTTPServer.getFunctions().put("greetings", greetings);
        HTTPServer.getFunctions().put("api", resthandler);

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port: "
                    + PORT + "...");

            while (true){
                HTTPServer server = new HTTPServer(serverSocket.accept());

                if(verbose) System.out.println("Connection established. " + new Date());

                Thread thread = new Thread(server);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server connection error" + e.getMessage());
        }
    }
}
