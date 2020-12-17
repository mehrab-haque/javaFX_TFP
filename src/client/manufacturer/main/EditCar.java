package client.manufacturer.main;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;

import java.io.IOException;

public class EditCar implements Runnable{
    Thread thread;
    JSONObject jsonObject;

    public EditCar(JSONObject jsonObject){
        this.jsonObject=jsonObject;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            jsonObject.put("type", Constants.TYPE_CAR_EDIT_REQUEST);
            long timestamp=System.currentTimeMillis();
            jsonObject.put("timestamp",timestamp);
            Profile.getInstance().setTimestamp(timestamp);
            Manufacturer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
