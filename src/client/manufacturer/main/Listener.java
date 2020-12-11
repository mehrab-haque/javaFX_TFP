package client.manufacturer.main;

import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Listener implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private  ManufacturerInterface manufacturerInterface;

    public Listener(NetworkUtil networkUtil,ManufacturerInterface manufacturerInterface) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        this.manufacturerInterface=manufacturerInterface;
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                JSONObject data = new JSONObject((String) networkUtil.read());
                if (data != null) {
                    if(data.getString("type").equals(Constants.TYPE_SERVER_LOGIN_RESULT) && data.getLong("timestamp")==Profile.getInstance().getTimestamp())
                        if(data.getBoolean("status")){
                            Profile.getInstance().setId(data.getInt("id"));
                            Profile.getInstance().setDisplayName(data.getString("displayName"));
                            manufacturerInterface.onSuccess();
                        }
                        else manufacturerInterface.onError();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}




