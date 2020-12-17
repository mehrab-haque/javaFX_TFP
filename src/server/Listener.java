package server;

import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Listener implements Runnable {
    private Thread thread;
    private NetworkUtil networkUtil;

    public Listener(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thread = new Thread(this);
        thread.start();
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
                    System.out.println(dbResult.toString());
                    networkUtil.write(dbResult.toString());
                }
            }
        } catch (Exception e) {
            //System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



