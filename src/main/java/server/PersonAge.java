package server;

import api.Database;
import api.HTTPModule;
import java.util.Calendar;
import java.util.Map;

public class PersonAge extends HTTPModule {

        @Override
        public ResponseObject get(RequestObject request, ResponseObject response){
            Map<String,String> p = request.getParams();
            String name = p.get("name").substring(0,1).toUpperCase() + p.get("name").substring(1);
            Database db = new Database();
            db.addPersonQuery(p.get("name"));
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html> <html>")
                    .append("<head>")
                    .append("<style>\n" +
                            "\t@import url(https://fonts.googleapis.com/css?family=Montserrat);")
                    .append("body {\n" +
                            "  overflow: hidden;\n" +
                            "  margin: 0px;\n" +
                            "}\n" +
                            ".background {\n" +
                            "  background-size: cover;\n" +
                            "  background-repeat: no-repeat;\n" +
                            "  background-position: center center;\n" +
                            "  overflow: hidden;\n" +
                            "  position: fixed;\n" +
                            "  width: 100%;\n" +
                            "  background-image: url(/files/images/birthday.jpg);\n" +
                            "\n" +
                            "}\n" +
                            "\n" +
                            ".content-wrapper {\n" +
                            "  height: 100vh;\n" +
                            "  display: flex;\n" +
                            "  justify-content: center;\n" +
                            "  text-align: center;\n" +
                            "  flex-flow: column nowrap;\n" +
                            "  color: #fff;\n" +
                            "  font-family: Montserrat;\n" +
                            "  text-transform: uppercase;\n" +
                            "}\n" +
                            "\n" +
                            ".content-title {\n" +
                            "  font-size: 12vh;\n" +
                            "  line-height: 1.4;\n" +
                            "}\n" +
                            "\n" +
                            ".personage {\n" +
                            "\tbackground: #292f36;\n" +
                            "\tpadding: 10px 20px;\n" +
                            "\tmargin: 0 auto;\n" +
                            "\tcolor: #5cc8ff;\n" +
                            "\tfont-weight: 600;\n" +
                            "}\n" +
                            "\n" +
                            ".years {\n" +
                            "color: #c41c4f;\n" +
                            "}")
                    .append("</style>")
                    .append("</head>")
                    .append("<body>")
                    .append("<div class=\"container\">")
                    .append("<section class=\"background\">")
                    .append("<div class=\"content-wrapper\">")
                    .append("<h1 class=\"content-title\">" + "Know your age" + "</h1>" + "<div class=\"personage\">" + "<p>Hello " + name + ", you are " + "<span class=\"years\">" + (Calendar.getInstance().get(Calendar.YEAR) -
                            Integer.parseInt(p.get("year"))) + "</span>" + " years old this year!</p>")
                    .append("</div>")
                    .append("</div>")
                    .append(" </section>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>");
            System.out.println("Creating html file");

        response = new ResponseObject();
        response.setData(html.toString().getBytes());
        response.setContentType("text/html");
        response.setContentLength(html.length());

        return response;
    }

    @Override
    public ResponseObject post(RequestObject request, ResponseObject response) {

            return super.post(request, response);
    }
}
