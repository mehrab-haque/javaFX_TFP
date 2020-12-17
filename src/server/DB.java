package server;

import javafx.scene.input.TouchPoint;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class DB {
    private static DB instance=null;
    private Connection connection;
    private DB(){
        try {
            connection = connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static synchronized DB getInstance(){
        if(instance==null)
            instance=new DB();
        return instance;
    }

    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","mehrab","zehady22448888");
        System.out.println("database connceted successfully");
        return connection;
    }

    public JSONObject manufacturerLogin(String userName, String password) throws SQLException, JSONException {
        JSONObject jsonObject=new JSONObject();
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from warehouse where username=\""+userName+"\" and password=\""+password+"\"");
        if(resultSet.next()){
            jsonObject.put("status",true);
            jsonObject.put("id",resultSet.getInt("id"));
            jsonObject.put("displayName",resultSet.getString("displayname"));
        }
        else
            jsonObject.put("status",false);
        return jsonObject;
    }

    public JSONObject addCar(int manufacturerId,long timestamp) throws SQLException, JSONException {
        JSONObject jsonObject=new JSONObject();
        Statement statement=connection.createStatement();
        String query="insert into car (warehouse_id,timestamp) values ("+manufacturerId+",\""+timestamp+"\")";
        statement.execute(query);
        ResultSet resultSet=statement.executeQuery("select * from car where warehouse_id="+manufacturerId+" and timestamp=\""+timestamp+"\"");
        resultSet.next();
        jsonObject.put("id",resultSet.getInt("id"));
        jsonObject.put("status",true);
        return jsonObject;
    }

    public JSONObject editCar(int manufacturerId,String model,String make,String color, int price,String image,long timestamp) throws SQLException, JSONException {
        JSONObject jsonObject=new JSONObject();
        Statement statement=connection.createStatement();
        String query="update car set model=\""+model+"\",make=\""+make+"\",color=\""+color+"\",price="+price+",image=\""+image+"\",timestamp=\""+timestamp+"\",isReady=true where id="+manufacturerId;
        try{
            statement.execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject.put("status",true);
        return jsonObject;
    }
}
