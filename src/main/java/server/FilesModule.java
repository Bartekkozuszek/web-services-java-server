package server;

import api.HTTPModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//TODO make base fileobject that can have extend file and files? includes the same WEB_ROOT and so on..
//TODO maybe a base HTTPModule object with readfileData() and other methods, all modules might want to convert a file to byte[]
//TODO base object can have default files for method not supported, file not found etc..

public class FilesModule extends HTTPModule {


    static final File WEB_ROOT = new File(".");


    @Override
    public ResponseObject get(RequestObject request, ResponseObject response) {

        String input = request.getRequestData().get("requestString");
        System.out.println(input);
        File file = new File(WEB_ROOT, input);
        System.out.println("file: " + file);

        if(!file.exists()) {

            file = new File(WEB_ROOT, "404.html");
        }

        int fileLength = (int)file.length();
        byte [] requestedFile = readFileData(file, fileLength);

        response.setContentType(getContentType(input));
        response.setContentLength(fileLength);
        response.setData(requestedFile);

        return response;
    }

    @Override
    public ResponseObject head(RequestObject request, ResponseObject response) {

        ResponseObject getResponse = get(request, response);
        response.setContentType(getResponse.getContentType());
        response.setContentLength(getResponse.getContentLength());
        response.setData(null);

        return response;
    }

    private byte [] readFileData(File file, int fileLength){

        byte [] data = new byte [fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
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
}
