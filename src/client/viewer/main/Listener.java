package client.viewer.main;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.ManufacturerInterface;
import client.manufacturer.main.Profile;
import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Listener implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private ViewerInterface viewerInterface;

    public Listener(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void setViewerInterface(ViewerInterface viewerInterface){
        this.viewerInterface=viewerInterface;
    }

    public void run() {
        try {
            while (true) {
                JSONObject data = new JSONObject((String) networkUtil.read());
                if (data != null) {
                    if(data.getString("type").equals(Constants.TYPE_CAR_LIST_NOTIFICATION)){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Viewer.getInstance().getCarListInterface().onListUpdated(data.getJSONArray("cars"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }catch(NullPointerException e){

                                }
                            }
                        });
                    }else {
                        if (data.getBoolean("status")) {
                            if (data.getString("type").equals(Constants.TYPE_VIEWER_LOGIN_RESULT)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            viewerInterface.onSuccess(new JSONObject());
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            else if(data.getString("type").equals(Constants.TYPE_CAR_BUY_RESPONSE)){
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            viewerInterface.onSuccess(new JSONObject());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else if (!data.getBoolean("status")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    viewerInterface.onError();
                                }
                            });

                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}




