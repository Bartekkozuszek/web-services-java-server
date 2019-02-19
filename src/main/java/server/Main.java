package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Date;

import static server.HTTPServer.PORT;
import static server.HTTPServer.httpMethods;
import static server.HTTPServer.verbose;

public class Main {

    public static void main(String[] args) {

        //httpMethods.addAll(Arrays.asList(new HTTPGet(), new HTTPHead(), new HTTPPost()));
        //HTTPServer.getFunctions().add(new Calculator());

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
