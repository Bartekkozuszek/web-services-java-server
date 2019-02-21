
package server;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Writer;

public class JSONObject {

    private String requestBody;
    private JsonObject jsonFile;

    public JsonObject getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(JsonObject jsonFile) {
        this.jsonFile = jsonFile;
    }

    public JSONObject(String requestBody){
        this.requestBody = requestBody;
    }

    public JsonObject readFromJson() throws FileNotFoundException {
        JsonReader jReader = null;
            jReader = Json.createReader(new FileReader(requestBody));
            JsonStructure jStructure = jReader.read();
            JsonObject jObject = (JsonObject) jStructure; //not to be confused with the class JSONObject
            JsonArray jArray = jObject.getJsonArray("body");
            for (JsonValue jv: jArray) {
                if(((JsonObject)jv).keySet().contains("name")){
                    String name = ((JsonObject)jv).getString("name");
                    System.out.println(name);
                } else if(((JsonObject)jv).keySet().contains("age")){
                    String age = ((JsonObject)jv).getString("age");
                    System.out.println(age);
                }
            }
            setJsonFile(jObject);
            return jObject;

    }

    public void writeToJson(JsonObject obj){
        Writer writer = new PrintWriter((Writer) obj);
        JsonWriter jwriter = Json.createWriter(writer);
        JsonObject jObject = Json.createObjectBuilder().add("name", "age").build();
        jwriter.writeObject(jObject);
        jwriter.close();
    }


}
