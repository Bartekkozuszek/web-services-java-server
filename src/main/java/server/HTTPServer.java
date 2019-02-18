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
    static List<HTTPMethod> httpMethods = new ArrayList();
    private static List<RequestHandler> functions = new ArrayList<RequestHandler>();

    private Socket clientSocket;

    public HTTPServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        System.out.println(Thread.currentThread().getName());
        handleRequest();
    }


    private void handleRequest() {

        BufferedReader in = null;
        PrintWriter out = null;
        BufferedInputStream dataOut = null;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input = in.readLine();
            System.out.println("input: " + input + "-------");
            StringTokenizer parse = new StringTokenizer(input);
            String HTTPMethod = parse.nextToken().toUpperCase();
            System.out.println("http method: " + HTTPMethod);
            String request = parse.nextToken().toLowerCase();

            for (HTTPMethod method: httpMethods) {
                String name = method.getClass().getSimpleName().toUpperCase();
                name = name.substring(name.indexOf("P") + 1);
                if(name.equals(HTTPMethod)){
                   ResponseObject response = method.execute(request, clientSocket);
                   handleOutput(response, clientSocket);
                }
                System.out.println(name);
                System.out.println(httpMethods.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static List<RequestHandler> getFunctions() {
        return functions;
    }


    public static void setFunctions(List<RequestHandler> functions) {
        HTTPServer.functions = functions;
    }
}
