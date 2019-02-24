package api;

import com.google.gson.Gson;
import server.RequestObject;
import server.ResponseObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.*;

public abstract class HTTPModule implements HTTPMethods {

    private static final String WEB_ROOT = ".";

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
        ResponseObject getResponse = get(request, response);
        response.setContentType(getResponse.getContentType());
        response.setContentLength(getResponse.getContentLength());
        response.setData(null);
        return response;
    }
    public ResponseObject post(RequestObject request, ResponseObject response){

//        File file = new File(WEB_ROOT, "resources/jsonInfo.html");
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//        Writer writer = new OutputStreamWriter(out);
//        writer.write(request.getHeader().get("body"));
//        writer.close();
//        int fileLength = (int) file.length();
//        byte[] requestedFile = readFileData(file, fileLength);
//        ResponseObject getResponse = get(request, response);
//        response.setContentType(getResponse.getContentType());
//        response.setContentLength(fileLength);
//        response.setData(requestedFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return response;
        return setResponse(response);
    }
    public ResponseObject put(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject delete(RequestObject request, ResponseObject response){
        return setResponse(response);
    }

    protected String getContentType(String request){

        if(request.endsWith(".htm") || request.endsWith(".html")){
            return "text/html";
        }else if (request.endsWith(".jpg") || request.endsWith(".jpeg")){
            return "image/jpg";
        }else if (request.endsWith(".json")){
            return "application/json";
        }
        else if (request.endsWith(".png")){
            return "image/png";
        } else if(request.endsWith(".pdf")){
            return "application/pdf";
        }
        else{
            return "text/plain";
        }
    }

    protected byte [] readFileData(File file, int fileLength){

        return getBytes(file, fileLength);
    }

    protected static byte[] getBytes(File file, int fileLength) {
        byte [] data = new byte [fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }


    protected void writeToJson(String obj){
        Writer writer = null;
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        try {
            writer = new PrintWriter(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonWriter jwriter = Json.createWriter(writer);
        JsonObject jObject = Json.createObjectBuilder().add("name", "age").build();
        jwriter.writeObject(jObject);
        jwriter.close();
    }

}
