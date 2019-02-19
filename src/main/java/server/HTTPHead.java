package server;

import java.io.*;
import java.net.Socket;


public class HTTPHead implements HTTPMethod {

    static final File WEB_ROOT = new File(".");

    public ResponseObject execute(RequestObject request, ResponseObject response) {

        return response;
    }
}
