package api;

import api.HTTPMethods;
import server.RequestObject;
import server.ResponseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    public String getContentType(String request){

        if(request.endsWith(".htm") || request.endsWith(".html")){

            return "text/html";
        }else if (request.endsWith(".jpg") || request.endsWith(".jpeg")){
            return "image/jpg";
        }else if (request.endsWith(".png")){
            return "image/png";
        }else{
            return "text/plain";
        }
    }

    public byte [] readFileData(File file, int fileLength){

        byte [] data = new byte [fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

}
