package server;

import api.HTTPModule;

import java.io.File;

//TODO make base fileobject that can have extend file and files? includes the same WEB_ROOT and so on..
//TODO maybe a base HTTPModule object with readfileData() and other methods, all modules might want to convert a file to byte[]
//TODO base object can have default files for method not supported, file not found etc..
//TODO om läser in module factory var ska modules ligga, vilket paket?

public class FilesModule extends HTTPModule {
//annotation med runtime

    private static final File WEB_ROOT = new File(".");


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

        return super.head(request, response);
    }

    @Override
    public ResponseObject post(RequestObject request, ResponseObject response) {
        ResponseObject getResponse = get(request, response);
        response.setContentType(getResponse.getContentType());
        response.setContentLength(getResponse.getContentLength());
//        if (request.getBody().isEmpty()) {
//            return getResponse;
//        } else{
//            JSONObject j = new JSONObject(request.getBody());
//            try {
//                j.writeToJson(j.readFromJson());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
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
