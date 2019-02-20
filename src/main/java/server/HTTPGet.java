//<<<<<<< HEAD
//package server;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//
//public class HTTPGet implements HTTPMethod {
//
//
//
//    private ResponseObject response = new ResponseObject();
//    private int index;
//
//    static final File WEB_ROOT = new File(".");
//    static final String DEFAULT_FILE = "index.html";
//
//
//
//    public ResponseObject execute(String request, Socket clientSocket) {
//
//        String intention = parseRequest(request);
//        System.out.println("intention: " + intention);
//        if (intention.equals("function")){
//
//            Map<String, String> params = parseParams(request);
//            System.out.println("params: ");
//            for (String param: params.values()) {
//                System.out.println(param);
//            }
//            String funcName = parseFuncName(request);
//            System.out.println("funcName: " + funcName);
//
//            response = runFunction(funcName, request, params);
//
//
//        }else{
//
//            File file = new File(WEB_ROOT,request);
//
//            if(!file.exists()) {
//
//                file = new File(WEB_ROOT, "404.html");
//            }
//
//            int fileLength = (int)file.length();
//            byte [] requestedFile = readFileData(file, fileLength);
//
//            System.out.println("request: " + request);
//            System.out.println("file: " + file);
//
//            response.setContentType(getContentType(request));
//            response.setContentLength(fileLength);
//            response.setData(requestedFile);
//        }
//        return response;
//    }
//
//
//
//    private byte [] readFileData(File file, int fileLength){
//
//        FileInputStream fileIn = null;
//
//        byte [] data = new byte [fileLength];
//
//        try{
//            fileIn = new FileInputStream(file);
//            fileIn.read(data);
//
//        }catch(IOException e){
//
//        }finally {
//            if(fileIn != null){
//                try{
//                    fileIn.close();
//                }catch (IOException e){
//
//                }
//
//            }
//        }
//        return data;
//    }
//
//
//
//    private Map<String, String> parseParams(String request){
//
//        Map<String, String> params = new HashMap<String, String>();
//
//        if(request.contains("?") && request.contains("=")){
//            String [] tempParams = request.split("&");
//            for (String item: tempParams) {
//                String [] tempParam = item.split("=");
//                params.put(tempParam[0], tempParam[1]);
//            }
//            return params;
//        }
//        return null;
//    }
//
//
//
//    private String parseRequest(String request){
//        System.out.println("request length: " + request.length());
//        System.out.println("request: " + request);
//
//        index = request.indexOf("/", 1);
//        System.out.println("index: " + index);
//
//        return request.substring(1, index);
//    }
//
//
//
//    private String parseFuncName(String request){
//
//        return request.substring(index + 1, request.indexOf("?", index + 1));
//    }
//
//
//
//    private ResponseObject runFunction(String functionName, String request, Map<String, String> params){
//
//        for (RequestHandler function: HTTPServer.getFunctions()) {
//
//            if(function.getClass().getSimpleName().toLowerCase().equals(functionName)){
//                return function.handleRequest(request, params);
//            }
//        }
//        return null;
//    }
//
//
//
//    private String getContentType(String request){
//
//        if(request.endsWith(".htm") || request.endsWith(".html")){
//
//            return "text/html";
//        }else if (request.endsWith(".jpg") || request.endsWith(".jpeg")){
//            return "image/jpg";
//        }else{
//            return "text/plain";
//        }
//    }
//
////    private void handleOutput(ResponseObject response, Socket clientSocket){
////
////        try{
////            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
////            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
////
////            out.println("HTTP/1.1 200 OK");
////            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
////            out.println("date: " + new Date());
////            out.println("content-type: " + response.getContentType());
////            out.println("content-length: " + response.getContentLength());
////            out.println();
////            out.flush();
////
////            byte[] content = response.getData();
////            //byte[] content = respons.getHtmlObject().getBytes(Charset.forName("UTF-8"));
////            dataOut.write(content);
////            dataOut.flush();
////
////        }catch (java.io.IOException e){
//=======
////package server;
//>>>>>>> restructure-3
////
////import java.io.*;
////import java.util.HashMap;
////import java.util.Map;
////
////public class HTTPGet implements HTTPMethod {
////
////
////
////    private ResponseObject response = new ResponseObject();
////    private int index;
////
////    static final File WEB_ROOT = new File(".");
////    static final String DEFAULT_FILE = "index.html";
////
////
////
////    public ResponseObject execute(RequestObject request, ResponseObject response) {
////
//////TODO om file: sätt ihop ett responsobject och ett request object och lägg request obj i respons obj, gör detta i httpserver
////// eller i en egen klass
//////TODO när objecten är klara skickes de till rätt metod
//////TODO om function anges körs en param function sen hämtas rätt function som tar request obj bara som körs och fyller i response
//////TODO detta respons object går sedan till rätt http metod
//////TODO ex. function echoJson tar request object och bygger ett response object som sedan går till GET och hanteras där.
//////TODO ex. function/addcostumer är post function. request object sätts i http server och skickas till function addcostumer som hanterar
////// hämtar och parsar bodyn från request object utför operationer på en lista och stoppar in relevant data i response object
////// som sedan skickas till POST som hämtar och outputtar relevant info.
//////TODO skillnaden på post och get är att vid post hämtar vi info från bodyn istället för i url?
//////TODO behövs en implementation för både get och post eller kan de skötas från samma?
////
//////        String intention = parseRequest(request);
//////        System.out.println("intention: " + intention);
//////        if (intention.equals("function")){
//////
//////            Map<String, String> params = parseParams(request);
//////            System.out.println("params: ");
//////            for (String param: params.values()) {
//////                System.out.println(param);
//////            }
//////            String funcName = parseFuncName(request);
//////            System.out.println("funcName: " + funcName);
//////
//////            response = runFunction(funcName, request, params);
//////
//////            //handleOutput(response, clientSocket);
//////
//////        }else{
//////
//////            File file = new File(WEB_ROOT,request);
//////
//////            if(!file.exists()) {
//////
//////                file = new File(WEB_ROOT, "404.html");
//////            }
//////
//////            int fileLength = (int)file.length();
//////            byte [] requestedFile = readFileData(file, fileLength);
//////
//////            System.out.println("request: " + request);
//////            System.out.println("file: " + file);
//////
//////            response.setContentType(getContentType(request));
//////            response.setContentLength(fileLength);
//////            response.setData(requestedFile);
//////        }
////        return response;
////    }
////
////
////
//////    private byte [] readFileData(File file, int fileLength){
//////
//////        FileInputStream fileIn = null;
//////
//////        byte [] data = new byte [fileLength];
//////
//////        try{
//////            fileIn = new FileInputStream(file);
//////            fileIn.read(data);
//////
//////        }catch(IOException e){
//////
//////        }finally {
//////            if(fileIn != null){
//////                try{
//////                    fileIn.close();
//////                }catch (IOException e){
//////
//////                }
//////
//////            }
//////        }
//////        return data;
//////    }
////
////
////
////    private Map<String, String> parseParams(String request){
////
////        Map<String, String> params = new HashMap<String, String>();
////
////        if(request.contains("?") && request.contains("=")){
////            String [] tempParams = request.split("&");
////            for (String item: tempParams) {
////                String [] tempParam = item.split("=");
////                params.put(tempParam[0], tempParam[1]);
////            }
////            return params;
////        }
////        return null;
////    }
////
////
////
////
////
////
////
////    private String parseFuncName(String request){
////
////        return request.substring(index + 1, request.indexOf("?", index + 1));
////    }
////
////
////
//////    private ResponseObject runFunction(String functionName, String request, Map<String, String> params){
//////
//////        for (RequestHandler function: HTTPServer.getFunctions()) {
//////
//////            if(function.getClass().getSimpleName().toLowerCase().equals(functionName)){
//////                return function.handleRequest(request, params);
//////            }
//////        }
//////        return null;
//////    }
////
////
////
////    private String getContentType(String request){
////
////        if(request.endsWith(".htm") || request.endsWith(".html")){
////
////            return "text/html";
////        }else if (request.endsWith(".jpg") || request.endsWith(".jpeg")){
////            return "image/jpg";
////        }else{
////            return "text/plain";
////        }
////    }
////
//////    private void handleOutput(ResponseObject response, Socket clientSocket){
//////
//////        try{
//////            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//////            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
//////
//////            out.println("HTTP/1.1 200 OK");
//////            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
//////            out.println("date: " + new Date());
//////            out.println("content-type: " + response.getContentType());
//////            out.println("content-length: " + response.getContentLength());
//////            out.println();
//////            out.flush();
//////
//////            byte[] content = response.getData();
//////            //byte[] content = respons.getHtmlObject().getBytes(Charset.forName("UTF-8"));
//////            dataOut.write(content);
//////            dataOut.flush();
//////
//////        }catch (java.io.IOException e){
//////
//////        }
//////
//////    }
////}
