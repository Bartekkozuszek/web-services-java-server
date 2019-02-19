package api;

import server.RequestObject;
import server.ResponseObject;

public interface HTTPMethods {

    ResponseObject get(RequestObject request, ResponseObject response);
    ResponseObject head(RequestObject request, ResponseObject response);
    ResponseObject post(RequestObject request, ResponseObject response);
    ResponseObject put(RequestObject request, ResponseObject response);
    ResponseObject delete(RequestObject request, ResponseObject response);
}
