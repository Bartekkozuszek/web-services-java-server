package server;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HTTPRequestParser {

    private int index;

    public RequestObject parse(BufferedReader clientRequest){

        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> requestData = new HashMap<String, String>();
        RequestObject request = new RequestObject();

        try{

            //clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            StringTokenizer parse = new StringTokenizer(clientRequest.readLine());
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

            while(clientRequest.ready()) {
                line = clientRequest.readLine();

                if(line.contains(": ")){
                    String [] keyValue = line.split(": ");
                    requestData.put(keyValue[0], keyValue[1]);
                }
                else if (!line.contains(": ") && !line.equals("")) {
                    requestData.put("body", line);
                }
                else if(line.equals("")&& requestData.containsKey("Content-Length")){
                    //String bodyString = clientRequest.lines().collect(Collectors.joining(System.lineSeparator()));
                    char[] body = new char[(Integer.parseInt(requestData.get("Content-Length")))];
                    clientRequest.read(body);
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
}
