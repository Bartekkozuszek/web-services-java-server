package api;

import server.RequestObject;
import server.ResponseObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        //Map<String, String> people = new HashMap<>();
        //try {
          //  Reader in = new FileReader("files/people.bin");
            //ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/people.bin")));
            //people = (Map<String, String>) in.read();

        Database db = new Database();
        try{
        File file = new File(WEB_ROOT, "jsonInfo.html");

        //db.addPersonQuery(request.getRequestData().get("body"));

        OutputStream out = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(out);
        writer.write(db.queryToString(db.selectAllQuery()));
        writer.close();
        int fileLength = (int) file.length();
        byte[] requestedFile = readFileData(file, fileLength);
        ResponseObject getResponse = get(request, response);
        response.setContentType(getResponse.getContentType());
        response.setContentLength(fileLength);
        response.setData(requestedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public ResponseObject put(RequestObject request, ResponseObject response){
        return setResponse(response);
    }
    public ResponseObject delete(RequestObject request, ResponseObject response){
        return setResponse(response);
    }

    public String getContentType(String request){

        if(request.endsWith(".htm") || request.endsWith(".html") || request.endsWith("application/x-www-form-urlencoded")){
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

    public byte [] readFileData(File file, int fileLength){

        return getBytes(file, fileLength);
    }

    public static byte[] getBytes(File file, int fileLength) {
        byte [] data = new byte [fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }



}
