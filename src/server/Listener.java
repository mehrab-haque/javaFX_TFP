package server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;
import java.sql.SQLException;

public class Listener implements Runnable {
    private Thread thread;
    private NetworkUtil networkUtil;

    public Listener(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thread = new Thread(this);
        thread.start();
    }

    public void notifyListeners(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject=new JSONObject();
                    JSONArray cars=DB.getInstance().getAllCars();
                    jsonObject.put("type", Constants.TYPE_CAR_LIST_NOTIFICATION);
                    jsonObject.put("cars",cars);
                    networkUtil.write(jsonObject.toString());
                } catch (JSONException | SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void run() {
        try {
            while (true) {
                JSONObject jsonObject = new JSONObject((String) networkUtil.read());
                if (jsonObject != null && jsonObject.getString("type").equals(Constants.TYPE_MANUFACTURER_LOGIN_REQUEST)) {
                    JSONObject dbResult=DB.getInstance().manufacturerLogin(jsonObject.getString("username"),jsonObject.getString("password"));
                    dbResult.put("type", Constants.TYPE_SERVER_LOGIN_RESULT);
                    dbResult.put("timestamp",jsonObject.getLong("timestamp"));
                    networkUtil.write(dbResult.toString());
                }
                else if(jsonObject!=null && jsonObject.getString("type").equals(Constants.TYPE_CAR_ADD_REQUEST)){
                    JSONObject dbResult=DB.getInstance().addCar(jsonObject.getInt("id"),jsonObject.getLong("timestamp"));
                    dbResult.put("type",Constants.TYPE_CAR_ADD_RESPONSE);
                    dbResult.put("timestamp",jsonObject.getLong("timestamp"));
                    networkUtil.write(dbResult.toString());
                }
                else if(jsonObject!=null && jsonObject.getString("type").equals(Constants.TYPE_CAR_EDIT_REQUEST)){
                    JSONObject dbResult=DB.getInstance().editCar(jsonObject.getInt("id"),jsonObject.getString("model"),jsonObject.getString("make"),jsonObject.getString("color"),jsonObject.getInt("price"),jsonObject.getString("image"),jsonObject.getLong("timestamp"));
                    dbResult.put("type",Constants.TYPE_CAR_EDIT_RESPONSE);
                    dbResult.put("timestamp",jsonObject.getLong("timestamp"));
                    networkUtil.write(dbResult.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}



