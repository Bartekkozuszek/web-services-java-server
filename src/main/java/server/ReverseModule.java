package server;

import api.HTTPModule;

import java.io.*;

public class ReverseModule extends HTTPModule {

    private static final File WEB_ROOT = new File(".");

    private File getHtmlFile() {
        return htmlFile;
    }

    private void setHtmlFile(File htmlFile) {
        this.htmlFile = htmlFile;
    }

    private File htmlFile;

    @Override
    public ResponseObject get(RequestObject request, ResponseObject response){

        String input = request.getRequestData().get("requestString");
        System.out.println(input);
        if(input.contains("?") && input.contains("=")) {
            String param = input.substring(input.lastIndexOf("=") + 1);
            createNewHtml(param);
            int fileLength = (int) getHtmlFile().length();
            byte[] requestedFile = readFileData(getHtmlFile(), fileLength);

            response.setContentType(request.getContentType());
            response.setContentLength(fileLength);
            response.setData(requestedFile);
        } else {
            File file = new File(WEB_ROOT, "reverse.html");
            int fileLength = (int) file.length();
            byte[] requestedFile = readFileData(file, fileLength);

            response.setContentType(request.getContentType());
            response.setContentLength(fileLength);
            response.setData(requestedFile);
        }
        return response;
    }

    public ResponseObject head(RequestObject request, ResponseObject response) {

        return super.head(request, response);
    }

    private void createNewHtml(String param) {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html><head><title>Reverse Writing </title></head>\n")
                .append("<body>")
                .append("<h3>Here is the text you wrote from right to left:</h3>")
                .append(" <bdo dir=\"rtl\">").append(param).append("</bdo> \n")
                .append("</body></html>");
        System.out.println("Creating html file");
        File htmlFileResult = new File(WEB_ROOT, "reverseResult.html");
        try {
            OutputStream out = new FileOutputStream(htmlFileResult.getAbsoluteFile());
            Writer writer = new OutputStreamWriter(out);
            writer.write(html.toString());
            writer.close();
            System.out.println("html file created successfully");
            setHtmlFile(htmlFileResult);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public ResponseObject post(RequestObject request, ResponseObject response) {
        return super.post(request, response);
    }


    public byte [] readFileData(File file, int fileLength){

        return getBytes(file, fileLength);
    }

    public String getContentType(String request){

        return super.getContentType(request);
    }

}
