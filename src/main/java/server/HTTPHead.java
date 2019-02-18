package server;

import java.io.*;
import java.net.Socket;


public class HTTPHead implements HTTPMethod {

    static final File WEB_ROOT = new File(".");

    public ResponseObject execute(String request, Socket clientSocket) {

        ResponseObject response  = new HTTPGet().execute(request, clientSocket);
        ResponseObject headResponse = new ResponseObject();
        headResponse.setContentLength(response.getContentLength());
        headResponse.setContentType(response.getContentType());

        return response;
    }
}
