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

        String input = request.getHeader().get("requestString");
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
            File file = new File(WEB_ROOT, "resources/reverse.html");
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
        html.append("<!DOCTYPE html>\n<html><head><meta charset=\"utf-8\"><title>Reverse Writing </title></head>\n")
                .append("<body bgcolor=\"#E6E6FA\">")
                .append("<h1><font color=\"blue\"/>Reverse Word!</h1>")
                .append("<h2>Here is the text you wrote from right to left:</h2>")
                .append("<bdo dir=\"rtl\"><font color=\"red\" size=\"6\">").append(param).append("</bdo> \n")
                .append("<p></p>")
                .append("<p></p>")
                .append("<style>\n" +
                        "img {\n" +
                        "  display: block;\n" +
                        "  margin-left: auto;\n" +
                        "  margin-right: auto;\n" +
                        "}\n" +
                        "* {box-sizing: border-box;}\n" +
                                "body {font-family: Verdana, sans-serif; text-align:center;}\n" +
                                ".mySlides {display: none;}\n" +
                                "img {vertical-align: middle;}\n" +
                                "\n" +
                                "/* Slideshow container */\n" +
                                ".slideshow-container {\n" +
                                "  max-width: 1000px;\n" +
                                "  position: relative;\n" +
                                "  margin: auto;\n" +
                                "}\n" +
                                "\n" +
                                "/* Caption text */\n" +
                                ".text {\n" +
                                "  color: #f2f2f2;\n" +
                                "  font-size: 15px;\n" +
                                "  padding: 8px 12px;\n" +
                                "  position: absolute;\n" +
                                "  bottom: 8px;\n" +
                                "  width: 100%;\n" +
                                "  text-align: center;\n" +
                                "}\n" +
                                "\n" +
                                "/* Number text (1/3 etc) */\n" +
                                ".numbertext {\n" +
                                "  color: #f2f2f2;\n" +
                                "  font-size: 12px;\n" +
                                "  padding: 8px 12px;\n" +
                                "  position: absolute;\n" +
                                "  top: 0;\n" +
                                "}\n" +
                                "\n" +
                                "/* The dots/bullets/indicators */\n" +
                                ".dot {\n" +
                                "  height: 15px;\n" +
                                "  width: 15px;\n" +
                                "  margin: 0 2px;\n" +
                                "  background-color: #bbb;\n" +
                                "  border-radius: 50%;\n" +
                                "  display: inline-block;\n" +
                                "  transition: background-color 0.6s ease;\n" +
                                "}\n" +
                                "\n" +
                                ".active {\n" +
                                "  background-color: #717171;\n" +
                                "}\n" +
                                "\n" +
                                "/* Fading animation */\n" +
                                ".fade {\n" +
                                "  -webkit-animation-adress: fade;\n" +
                                "  -webkit-animation-duration: 1.5s;\n" +
                                "  animation-adress: fade;\n" +
                                "  animation-duration: 1.5s;\n" +
                                "}\n" +
                                "\n" +
                                "@-webkit-keyframes fade {\n" +
                                "  from {opacity: .4} \n" +
                                "  to {opacity: 1}\n" +
                                "}\n" +
                                "\n" +
                                "@keyframes fade {\n" +
                                "  from {opacity: .4} \n" +
                                "  to {opacity: 1}\n" +
                                "}\n" +
                                "\n" +
                                "/* On smaller screens, decrease text size */\n" +
                                "@media only screen and (max-width: 300px) {\n" +
                                "  .text {font-size: 11px}\n" +
                                "}"+
                        "</style>")
                .append("<div class=\"slideshow-container\">\n" +
                        "\n" +
                        "<div class=\"mySlides fade\">\n" +
                        "  <div class=\"numbertext\"></div>\n" +
                        "  <img src=\"/files/Images/l1.jpg\" style=\"width:100%\" height=\"400\">\n" +
                        "  <div class=\"text\"></div>\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"mySlides fade\">\n" +
                        "  <div class=\"numbertext\"></div>\n" +
                        "  <img src=\"/files/Images/l2.jpg\" style=\"width:100%\" height=\"400\">\n" +
                        "  <div class=\"text\"></div>\n" +
                        "</div>\n" +
                        "\n" +
                        "<div class=\"mySlides fade\">\n" +
                        "  <div class=\"numbertext\"></div>\n" +
                        "  <img src=\"/files/Images/l3.jpg\" style=\"width:100%\" height=\"400\">\n" +
                        "  <div class=\"text\"></div>\n" +
                        "</div>\n" +
                        "\n" +
                        "</div>\n" +
                        "<br>\n" +
                        "\n" +
                        "<div style=\"text-align:center\">\n" +
                        "  <span class=\"dot\"></span> \n" +
                        "  <span class=\"dot\"></span> \n" +
                        "  <span class=\"dot\"></span> \n" +
                        "</div>")
                .append("<script>\n" +
                        "var slideIndex = 0;\n" +
                        "showSlides();\n" +
                        "\n" +
                        "function showSlides() {\n" +
                        "  var i;\n" +
                        "  var slides = document.getElementsByClassName(\"mySlides\");\n" +
                        "  var dots = document.getElementsByClassName(\"dot\");\n" +
                        "  for (i = 0; i < slides.length; i++) {\n" +
                        "    slides[i].style.display = \"none\";  \n" +
                        "  }\n" +
                        "  slideIndex++;\n" +
                        "  if (slideIndex > slides.length) {slideIndex = 1}    \n" +
                        "  for (i = 0; i < dots.length; i++) {\n" +
                        "    dots[i].className = dots[i].className.replace(\" active\", \"\");\n" +
                        "  }\n" +
                        "  slides[slideIndex-1].style.display = \"block\";  \n" +
                        "  dots[slideIndex-1].className += \" active\";\n" +
                        "  setTimeout(showSlides, 5000); // Change image every 2 seconds\n" +
                        "}\n" +
                        "</script>")
                .append("</body></html>");
        System.out.println("Creating html file");
        File htmlFileResult = new File(WEB_ROOT, "resources/reverseResult.html");
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
