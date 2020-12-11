package server;

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
}
