package server;

import java.net.Socket;

public interface HTTPMethod {

    ResponseObject execute(String request, Socket clientSocket);

}
