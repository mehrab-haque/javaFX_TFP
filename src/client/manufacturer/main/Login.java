package client.manufacturer.main;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Login implements Runnable {

    private String userName;
    private String password;
    private Thread thread;

    public Login(String userName, String password){
        this.userName=userName;
        this.password=password;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_MANUFACTURER_LOGIN_REQUEST);
            jsonObject.put("username",userName);
            jsonObject.put("password",password);
            long timestamp=System.currentTimeMillis();
            jsonObject.put("timestamp",timestamp);
            Manufacturer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
