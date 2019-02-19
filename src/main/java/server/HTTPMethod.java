package server;

import java.net.Socket;

public interface HTTPMethod {

    ResponseObject execute(RequestObject request, ResponseObject response);
}
