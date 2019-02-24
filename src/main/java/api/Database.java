package api;

import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {

    private static Connection dbCon;
    private final static String DB_CON = "jdbc:sqlite:people";

    public Database(){
        getConnection();
        String create_table = "CREATE table IF NOT EXISTS people(" +
                "ID integer PRIMARY KEY," + "name TEXT" + ");";
        try {
            Statement st = dbCon.createStatement();
            st.execute(create_table);
            st.close();
            //dbCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load driver " + e.getMessage());
        }
    }

    private void getConnection(){
        try {
            dbCon = DriverManager.getConnection(DB_CON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPersonQuery(String person){
        if(hasConnection()){
            Statement st = null;
            try{
                String query = "INSERT INTO people(NAME )" +
                        " VALUES ('" + person + "');";
                st = dbCon.createStatement();
                st.executeQuery(query);
                st.close();
                //dbCon.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void prunt(){

    }

    public ResultSet selectAllQuery()
    {
        if (hasConnection()) {
            Statement st = null;
            ResultSet rs = null;
            String query = "SELECT * FROM people";
            try {
                st = dbCon.createStatement();
                rs = st.executeQuery(query);

                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public boolean hasConnection(){
        return dbCon != null;
    }


    public String queryToString(ResultSet r){
        Map<Integer , String > result = new HashMap<>();

        int counter = 1;

        try {
            while (r.next()){
                result.put(counter, r.getString("name"));
                counter++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String content = result.entrySet().stream().map(e -> e.getKey() + "=\"" +
                e.getValue() + "\"").collect(Collectors.joining(", \n"));

        String text = "{\n"  + content + "\n}";

        return text;
    }

}
