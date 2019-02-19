package server;

import api.HTTPMethods;

public abstract class HTTPModule implements HTTPMethods {

    private String notSupported(){
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<body>");
        html.append("<h1>405 Method Not Allowed</h1>");
        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }


    private ResponseObject setResponse(ResponseObject response){
        String html = notSupported();
        response.setContentLength(html.length());
        response.setContentType("text/html");
        response.setData(html.getBytes());

        return response;
    }


    public ResponseObject get(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject head(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject post(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject put(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject delete(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
}
