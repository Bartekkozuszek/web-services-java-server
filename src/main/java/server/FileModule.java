package server;

import api.HTTPModule;

import java.io.File;


public class FileModule extends HTTPModule {

    private static final File WEB_ROOT = new File(".");
    private static final String FILE_NOT_FOUND ="resources/404.html";



    @Override
    public ResponseObject get(RequestObject request, ResponseObject response) {

        String input = request.getHeader().get("requestString");
        File file = new File(WEB_ROOT, input);

        if(!file.exists()) {

            file = new File(WEB_ROOT, FILE_NOT_FOUND);
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
}
