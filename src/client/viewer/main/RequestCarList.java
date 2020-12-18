package client.viewer.main;


import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;

import java.io.IOException;

public class RequestCarList implements Runnable{
    Thread thread;

    public RequestCarList(){
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_CAR_LIST_REQUEST);
            Viewer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
}
