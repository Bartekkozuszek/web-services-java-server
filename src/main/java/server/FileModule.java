package server;

import api.HTTPModule;

import java.io.File;


public class FileModule extends HTTPModule {

    private static final File WEB_ROOT = new File(".");

    @Override
    public ResponseObject get(RequestObject request, ResponseObject response) {

        String input = request.getHeader().get("requestString");
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

        return super.head(request, response);
    }


    @Override
    public ResponseObject post(RequestObject request, ResponseObject response) {
        ResponseObject getResponse = get(request, response);
        response.setContentType(getResponse.getContentType());
        response.setContentLength(getResponse.getContentLength());

        return response;
    }


    @Override
    public byte [] readFileData(File file, int fileLength){

        return getBytes(file, fileLength);
    }


    public String getContentType(String request){

        return super.getContentType(request);
    }
}
