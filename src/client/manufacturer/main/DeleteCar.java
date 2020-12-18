package client.manufacturer.main;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;

import java.io.IOException;

public class DeleteCar implements Runnable {

    Thread thread;
    int id;

    public DeleteCar(int id){
        thread=new Thread(this);
        this.id=id;
        thread.start();
    }

    @Override
    public void run() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", Constants.TYPE_CAR_DELETE_REQUEST);
            jsonObject.put("id",id);
            Manufacturer.getInstance().getNetworkUtil().write(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
