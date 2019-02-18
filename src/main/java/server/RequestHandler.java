package server;

import java.util.Map;

public interface RequestHandler {
    ResponseObject handleRequest(String request, Map<String, String> params);
}
