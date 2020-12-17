package client.manufacturer.main;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class AddCar implements Runnable {

    private Thread thread;

    public AddCar(){
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_CAR_ADD_REQUEST);
            jsonObject.put("id",Profile.getInstance().getId());
            long timestamp=System.currentTimeMillis();
            jsonObject.put("timestamp",timestamp);
            Profile.getInstance().setTimestamp(timestamp);
            Manufacturer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
