package server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HTTPGet implements HTTPMethod {



    private ResponseObject response = new ResponseObject();
    private int index;

    static final File WEB_ROOT = new File(".");
    static final String DEFAULT_FILE = "index.html";



    public ResponseObject execute(String request, Socket clientSocket) {

        String intention = parseRequest(request);
        System.out.println("intention: " + intention);
        if (intention.equals("function")){

            Map<String, String> params = parseParams(request);
            System.out.println("params: ");
            for (String param: params.values()) {
                System.out.println(param);
            }
            String funcName = parseFuncName(request);
            System.out.println("funcName: " + funcName);

            response = runFunction(funcName, request, params);


        }else{

            File file = new File(WEB_ROOT,request);

            if(!file.exists()) {

                file = new File(WEB_ROOT, "404.html");
            }

            int fileLength = (int)file.length();
            byte [] requestedFile = readFileData(file, fileLength);

            System.out.println("request: " + request);
            System.out.println("file: " + file);

            response.setContentType(getContentType(request));
            response.setContentLength(fileLength);
            response.setData(requestedFile);
        }
        return response;
    }



    private byte [] readFileData(File file, int fileLength){

        FileInputStream fileIn = null;

        byte [] data = new byte [fileLength];

        try{
            fileIn = new FileInputStream(file);
            fileIn.read(data);

        }catch(IOException e){

        }finally {
            if(fileIn != null){
                try{
                    fileIn.close();
                }catch (IOException e){

                }

            }
        }
        return data;
    }



    private Map<String, String> parseParams(String request){

        Map<String, String> params = new HashMap<String, String>();

        if(request.contains("?") && request.contains("=")){
            String [] tempParams = request.split("&");
            for (String item: tempParams) {
                String [] tempParam = item.split("=");
                params.put(tempParam[0], tempParam[1]);
            }
            return params;
        }
        return null;
    }



    private String parseRequest(String request){
        System.out.println("request length: " + request.length());
        System.out.println("request: " + request);

        index = request.indexOf("/", 1);
        System.out.println("index: " + index);

        return request.substring(1, index);
    }



    private String parseFuncName(String request){

        return request.substring(index + 1, request.indexOf("?", index + 1));
    }



    private ResponseObject runFunction(String functionName, String request, Map<String, String> params){

        for (RequestHandler function: HTTPServer.getFunctions()) {

            if(function.getClass().getSimpleName().toLowerCase().equals(functionName)){
                return function.handleRequest(request, params);
            }
        }
        return null;
    }



    private String getContentType(String request){

        if(request.endsWith(".htm") || request.endsWith(".html")){

            return "text/html";
        }else if (request.endsWith(".jpg") || request.endsWith(".jpeg")){
            return "image/jpg";
        }else{
            return "text/plain";
        }
    }

//    private void handleOutput(ResponseObject response, Socket clientSocket){
//
//        try{
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
//
//            out.println("HTTP/1.1 200 OK");
//            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
//            out.println("date: " + new Date());
//            out.println("content-type: " + response.getContentType());
//            out.println("content-length: " + response.getContentLength());
//            out.println();
//            out.flush();
//
//            byte[] content = response.getData();
//            //byte[] content = respons.getHtmlObject().getBytes(Charset.forName("UTF-8"));
//            dataOut.write(content);
//            dataOut.flush();
//
//        }catch (java.io.IOException e){
//
//        }
//
//    }
}
