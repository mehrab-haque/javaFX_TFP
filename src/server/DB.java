package server;

import javafx.scene.input.TouchPoint;
import org.json.JSONArray;
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

    public synchronized JSONObject manufacturerLogin(String userName, String password) throws SQLException, JSONException {
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

    public synchronized JSONObject addCar(int manufacturerId,long timestamp) throws SQLException, JSONException {
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

    public synchronized JSONObject editCar(int carId,String model,String make,String color, int price,String image,long timestamp,int quantity) throws SQLException, JSONException {
        JSONObject jsonObject=new JSONObject();
        Statement statement=connection.createStatement();
        String query="update car set model=\""+model+"\",make=\""+make+"\",color=\""+color+"\",price="+price+",image=\""+image+"\",timestamp=\""+timestamp+"\",isReady=true,quantity="+quantity+" where id="+carId;
        try{
            statement.execute(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject.put("status",true);
        return jsonObject;
    }

    public synchronized JSONArray getAllCars() throws SQLException, JSONException {
        JSONArray result=new JSONArray();
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from car where isReady=true order by timestamp desc");
        while (resultSet.next()){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("carId",resultSet.getInt("id"));
            jsonObject.put("manufacturerId",resultSet.getInt("warehouse_id"));
            jsonObject.put("model",resultSet.getString("model"));
            jsonObject.put("make",resultSet.getString("make"));
            jsonObject.put("color",resultSet.getString("color"));
            jsonObject.put("image",resultSet.getString("image"));
            jsonObject.put("price",resultSet.getInt("price"));
            jsonObject.put("timestamp",Long.parseLong(resultSet.getString("timestamp")));
            jsonObject.put("quantity",resultSet.getInt("quantity"));
            Statement statement1=connection.createStatement();
            ResultSet resultSet1=statement1.executeQuery("select * from warehouse where id="+resultSet.getInt("warehouse_id"));
            resultSet1.next();
            jsonObject.put("manufacturerName",resultSet1.getString("displayname"));
            result.put(jsonObject);
        }
        return result;
    }

    public synchronized void deleteCar(int id) throws SQLException {
        Statement statement=connection.createStatement();
        statement.execute("delete from car where id="+id);
    }

    public synchronized void buyCar(int id) throws SQLException {
        Statement statement1=connection.createStatement();
        ResultSet resultSet=statement1.executeQuery("select quantity from car where id="+id);
        resultSet.next();
        Statement statement2=connection.createStatement();
        statement2.execute("update car set quantity="+(resultSet.getInt("quantity")-1)+" where id="+id);
    }
}
