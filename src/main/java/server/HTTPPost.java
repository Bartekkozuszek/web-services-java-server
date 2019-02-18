package server;

import java.net.Socket;

public class HTTPPost implements HTTPMethod {
    public ResponseObject execute(String request, Socket clientSocket) {

        System.out.println("post: " + request);

        return null;
    }
}
