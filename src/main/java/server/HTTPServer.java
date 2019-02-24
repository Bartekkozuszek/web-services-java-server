package server;

import api.HTTPMethods;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class HTTPServer implements Runnable{

    static final int PORT = 8081;
    static final String FILE_NOT_FOUND ="resources/404.html";
    //static final String DEFAULT_FILE = "src/index.html";
    static final File WEB_ROOT = new File(".");
    static final boolean verbose = true;

    private static Map<String, HTTPMethods> functions = new HashMap<String, HTTPMethods>();

    private int index;

    private Socket clientSocket;

    public HTTPServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void run() {

        System.out.println(Thread.currentThread().getName());
        BufferedReader rawRequest = null;

        try {
            rawRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            RequestObject request = requestToObject(rawRequest);
            ResponseObject response = new ResponseObject();
            String destination = request.getHeader().get("destination");
            String httpMethod = request.getHeader().get("method");
            System.out.println("method: " + httpMethod);

            if (functions.containsKey(destination)) {
                HTTPMethods m = functions.get(destination);// hämta ut en factory och instantiera ett objeckt av vald destination

                if (httpMethod.equals("GET")) {
                    response = m.get(request, response);
                } else if (httpMethod.equals("HEAD")) {
                    response = m.head(request, response);
                } else if (httpMethod.equals("POST")) {
                    response = m.post(request, response);
                }
                //else fileNotFound();
            } else{
                response = fileNotFound(response);
            }
            handleOutput(response, clientSocket);
            clientSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private ResponseObject fileNotFound(ResponseObject response) {
        File file = new File(WEB_ROOT, FILE_NOT_FOUND);
        System.out.println("file: " + file);

        int fileLength = (int)file.length();
        byte [] requestedFile = readFileData(file, fileLength);

        response.setContentType("text/plain");
        response.setContentLength(fileLength);
        response.setData(requestedFile);

        return response;
    }


    private String parseRequest(String request){
        System.out.println("request length: " + request.length());
        System.out.println("request: " + request);

        index = request.indexOf("/", 1);
        System.out.println("index: " + index);

        return request.substring(1, index);
    }


    private Map<String, String> parseParams(String request) throws UnsupportedEncodingException {

        Map<String, String> params = new HashMap<String, String>();
      //  request = URLDecoder.decode(request, "UTF-8");

     //   if (request.contains("?") && request.contains("=")) {
     //       request = request.substring(request.indexOf("?") + 1);
            String[] tempParams = request.split("&");
            for (String item : tempParams) {
                String[] tempParam = item.split("=");
                params.put(tempParam[0], tempParam[1]);
            }
            params.forEach((a, b) -> System.out.println(a + " : " + b));
            return params;
        }
     //   return null;


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


    private RequestObject requestToObject(BufferedReader rawRequest){

        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> requestData = new HashMap<String, String>();
        RequestObject request = new RequestObject();

        try{

            //rawRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringTokenizer parse = new StringTokenizer(rawRequest.readLine());
            requestData.put("method", parse.nextToken());
            String requestString = parse.nextToken();
            requestData.put("requestString", requestString); //Original browser request
            
            if(requestString.contains("?")){
                index = requestString.indexOf("?");
                params = parseParams(requestString.substring(index+1));
                requestString = requestString.substring(0, index); //Without ?+parameters extension
                
            }
            
            
            requestData.put("request", requestString); //save Without ?+parameters extension
            
            requestString= requestString.substring(1);           
            
            index = requestString.indexOf("/");
            if(index>0){               
            	requestData.put("destination", requestString.substring(0, index));
            }
            else {
            	requestData.put("destination", requestString);
            }
            
            if(requestData.get("request").equals("/")) {
            	requestData.put("destination", "files");
            	requestData.put("request", "/files/index.html");
            	requestData.put("requestString", "/files/index.html");
            }
            
            System.out.println("requeststring: " + requestData.get("requestString")) ;
            System.out.println("request: " + requestData.get("request")) ;
            System.out.println("destination: " + requestData.get("destination")) ;
            
            requestData.put("version", parse.nextToken());
            
            String line;

            while(rawRequest.ready()) {
                line = rawRequest.readLine();

                if(line.contains(": ")){
                    String [] keyValue = line.split(": ");
                    requestData.put(keyValue[0], keyValue[1]);
                }
                else if (!line.contains(": ") && !line.equals("")) {
                    requestData.put("body", line);
                }
                else if(line.equals("")&& requestData.containsKey("Content-Length")){
                    char[] body = new char[(Integer.parseInt(requestData.get("Content-Length")))];
                    rawRequest.read(body);
                    String bodyString = new String(body);
                    System.out.println(bodyString);
                    requestData.put("body", bodyString);
                }
            }
            System.out.println("request: " + requestData.get("request"));
            System.out.println("requestData:-----------------------");
            requestData.forEach((a,b)-> System.out.println(a + " : " + b));
            System.out.println("requestData------------------------");
        }catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
        request.setHeader(requestData);
        request.setParams(params);

        return request;
    }

    //TODO parseParams() kan parsa både från body och url, samma resultat

    //TODO CopyOnWrite arraylist är thread safe
//TODO use AtomicLong or Int osv for threadsafe counters, counter.incrementAndGet()
//TODO possible to overload a method for params?

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

            System.out.println("data in response: " + response.getData());
            //[B@1f97e758
            if(response.getData() != null){
                byte[] content = response.getData();
                System.out.println("content: " + content);
                dataOut.write(content);
                dataOut.flush();
                dataOut.close();
//            dataOut.write(content);
//            dataOut.flush();
//            dataOut.close();
            }

        }catch (java.io.IOException e){
            System.out.println(e.getMessage());
        }
    }


    public static Map<String, HTTPMethods> getFunctions() {
        return functions;
    }
}
