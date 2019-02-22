package server;

import api.HTTPModule;
import java.util.Calendar;
import java.util.Map;

public class PersonAge extends HTTPModule {

    @Override
    public ResponseObject get(RequestObject request, ResponseObject response){
        Map<String,String> p = request.getParams();
        String name = p.get("name").substring(0,1).toUpperCase() + p.get("name").substring(1);

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html> <html>")
                .append("<head><title>Person age</title></head>")
                .append("<body>")
                .append("<h2>Hello " + name + ", you are " + (Calendar.getInstance().get(Calendar.YEAR) -
                        Integer.parseInt(p.get("year"))) + " years old this year!</h2>")
                .append("</body>")
                .append("</html>");
        System.out.println("Creating html file");

        response = new ResponseObject();
        response.setData(html.toString().getBytes());
        response.setContentType("text/html");
        response.setContentLength(html.length());

        return response;
    }


}
