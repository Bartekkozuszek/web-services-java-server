package server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HTTPServer implements Runnable{

    static final int PORT = 8081;
    static final String FILE_NOT_FOUND ="404.html";
    static final String DEFAULT_FILE = "src/index.html";
    static final File WEB_ROOT = new File(".");
    static final boolean verbose = true;
    static List<HTTPMethods> httpMethods = new ArrayList();
    //private static List<RequestHandler> functions = new ArrayList<RequestHandler>();

    private static Map<String, String> functions = new HashMap<String, String>();

    private int index;

    private Socket clientSocket;

    public HTTPServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        System.out.println(Thread.currentThread().getName());

        RequestObject request = requestToObject(clientSocket);
    }


    private void handleRequest() {

        RequestObject request = requestToObject(clientSocket);
        String destination = request.getRequestData().get("destination");

        if(functions.containsKey(destination)){
            functions.get(destination); // hämta ut en factory och instantiera ett objeckt av vald destination

        }

    }


    private ResponseObject checkIntent(String requestUrl){

        ResponseObject response = new ResponseObject();

        String intention = parseRequest(requestUrl);
        System.out.println("intention: " + intention);
        if (intention.equals("function")){

            Map<String, String> params = parseParams(requestUrl);
            System.out.println("params: ");
            for (String param: params.values()) {
                System.out.println(param);
            }
            String funcName = parseFuncName(requestUrl);
            System.out.println("funcName: " + funcName);

            response = runFunction(funcName, requestUrl, params);

        }else{

            File file = new File(WEB_ROOT,requestUrl);

            if(!file.exists()) {

                file = new File(WEB_ROOT, "404.html");
            }

            int fileLength = (int)file.length();
            byte [] requestedFile = readFileData(file, fileLength);

            System.out.println("request: " + requestUrl);
            System.out.println("file: " + file);

            response.setContentType(getContentType(requestUrl));
            response.setContentLength(fileLength);
            response.setData(requestedFile);
        }
        return response;
    }


    private String parseRequest(String request){
        System.out.println("request length: " + request.length());
        System.out.println("request: " + request);

        index = request.indexOf("/", 1);
        System.out.println("index: " + index);

        return request.substring(1, index);
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


    private String parseFuncName(String request){

        return request.substring(index + 1, request.indexOf("?", index + 1));
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


    private ResponseObject runFunction(String functionName, String request, Map<String, String> params){

//        for (RequestHandler function: HTTPServer.getFunctions()) {
//
//            if(function.getClass().getSimpleName().toLowerCase().equals(functionName)){
//                return function.handleRequest(request, params);
//            }
//        }
        return null;
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


    private RequestObject requestToObject(Socket clientSocket){

        List<String> requestStrings = new ArrayList<String>();
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> requestData = new HashMap<String, String>();
        BufferedReader rawRequest = null;
        RequestObject request = new RequestObject();

        try{

            rawRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringTokenizer parse = new StringTokenizer(rawRequest.readLine());
            requestData.put("method", parse.nextToken());
            String requestString = parse.nextToken();
            params = parseParams(requestString);
            requestData.put("request", requestString);
            int index = requestString.indexOf("/", 2);
            String destination = requestString.substring(1, index);
            requestData.put("destination", destination);
            requestData.put("version", parse.nextToken());
            String line = null;

            while((line = rawRequest.readLine()) != null) {
                if (line.contains("content-type")) {
                    String type = line.substring(line.indexOf(": "), line.length());
                    requestData.put("content-type", type);
                } else if (line.contains("content-length")) {
                    String length = line.substring(line.indexOf(": ", line.length()));
                    requestData.put("content-length", length);

                } else if (!line.contains(":") && !line.contains("")) {
                    requestData.put("body", line);
                }
            }

        }catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
//TODO parseParams() kan parsa både från body och url, samma resultat

        request.setRequestData(requestData);
        request.setParams(params);

        return request;
    }


    private Map<String, String> parseStrings(List<String> strings){
        String body = null;
        for (String line : strings) {
            if(line.contains("content-type")){
                line.substring(line.indexOf(": "), line.length());
            }
            if(!line.contains(":") && !line.contains("")){
                body = line;
            }

        }
        System.out.println("body: " + body);
        return null;
    }


    private void handleOutput(ResponseObject response, Socket clientSocket){

        try{
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream());

            out.println("HTTP/1.1 200 OK");
            out.println("Server: Java HTTP Server from mrjohansson : 1.0");
            out.println("date: " + new Date());
            out.println("content-type: " + response.getContentType());
            out.println("content-length: " + response.getContentLength());
            out.println();
            out.flush();


            if(response.getData() != null){
                byte[] content = response.getData();
                //byte[] content = respons.getHtmlObject().getBytes(Charset.forName("UTF-8"));
                dataOut.write(content);
                dataOut.flush();
                dataOut.close();
            }

        }catch (java.io.IOException e){

        }

    }


//    public static List<RequestHandler> getFunctions() {
//        return functions;
//    }
//
//
//    public static void setFunctions(List<RequestHandler> functions) {
//        HTTPServer.functions = functions;
//    }
}
