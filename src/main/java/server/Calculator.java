package server;

import api.HTTPModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Calculator extends HTTPModule {

    //Map<String, String> params;
    //private char operator;
    private String answerType;
    double output;



    public ResponseObject handleRequest(String request, Map<String, String> params) {

        char operator = params.get("operator").charAt(0);
        params.remove("operator");

        List<String> nums = new ArrayList<String>(params.values());
        calculate(nums, operator);

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html>");
        htmlBuilder.append("<body>");
        htmlBuilder.append("<h1>Calculator</>");
        htmlBuilder.append("<h2>"+ answerType + " output: " + output + "</h2>");
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        ResponseObject response = new ResponseObject();
        response.setData(htmlBuilder.toString().getBytes());
        response.setContentType("text/html");
        response.setContentLength(htmlBuilder.length());

        return response;
    }


    private void calculate(List<String> nums, char operator){
        output = Double.parseDouble(nums.get(0));
        nums.remove(0);

        for (String num: nums) {
            double tempNum;
            try{

                switch (operator) {
                    case '*':
                        System.out.println("multiplication");
                        output = output * Double.parseDouble(num);
                        answerType = "Product";
                        break;
                    case '+':
                        output = output + Double.parseDouble(num);
                        answerType = "Sum";
                        break;
                    case '-':
                        output = output - Double.parseDouble(num);
                        answerType = "Difference";
                        break;

                    default:
                        break;

                }

            }catch (Exception e) {
                System.out.println("error parsing to int: " + e.getMessage());
            }
        }
    }
}
