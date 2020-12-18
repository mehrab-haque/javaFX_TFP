package client.viewer.main;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.Profile;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;

import java.io.IOException;

public class Login implements Runnable {

    private String userName;
    private Thread thread;

    public Login(String userName){
        this.userName=userName;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_VIEWER_LOGIN_REQUEST);
            jsonObject.put("username",userName);
            Viewer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
