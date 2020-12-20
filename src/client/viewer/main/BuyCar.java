package client.viewer.main;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;

import java.io.IOException;

public class BuyCar implements Runnable{

    private Thread thread;
    private int carId;

    public BuyCar(int carId){
        this.carId=carId;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_CAR_BUY_REQUEST);
            jsonObject.put("id", carId);
            Viewer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
