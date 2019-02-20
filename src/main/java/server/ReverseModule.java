package server;

import api.HTTPModule;

import java.io.*;

public class ReverseModule extends HTTPModule {

    private static final File WEB_ROOT = new File(".");

    @Override
    public ResponseObject get(RequestObject request, ResponseObject response){

        String input = request.getRequestData().get("requestString");
        System.out.println(input);
        String param = input.substring(input.lastIndexOf("=") + 1);
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html><head><title>Reverse Writing </title></head>\n")
                .append("<body>")
                .append("<h3>Here is the text you wrote from right to left:</h3>")
                .append(" <bdo dir=\"rtl\">").append(param).append("</bdo> \n")
                .append("</body></html>");
        System.out.println("Creating html file");
        File htmlFile = new File(WEB_ROOT, "reverse.html");
        try {
            OutputStream out = new FileOutputStream(htmlFile.getAbsoluteFile());
            Writer writer = new OutputStreamWriter(out);
            writer.write(html.toString());
            writer.close();
            System.out.println("html file created successfully");
        }catch (IOException e){
            e.getMessage();
        }
        int fileLength = (int)htmlFile.length();
        byte [] requestedFile = readFileData(htmlFile, fileLength);

        response.setContentType(getContentType(input));
        response.setContentLength(fileLength);
        response.setData(requestedFile);
        return response;
    }

    private void WriteToFile(String content) {

//        try {
//            OutputStream out = new FileOutputStream(file.getAbsoluteFile());
//            Writer writer = new OutputStreamWriter(out);
//            writer.write(content);
//            writer.close();
//            System.out.println("html file created successfully");
//        }catch (IOException e){
//            e.getMessage();
//        }
    }

    public byte [] readFileData(File file, int fileLength){

        byte [] data = new byte [fileLength];
        try (FileInputStream fileIn = new FileInputStream(file)) {
            fileIn.read(data);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public String getContentType(String request){

        return super.getContentType(request);
    }

}
