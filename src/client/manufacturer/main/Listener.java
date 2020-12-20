package client.manufacturer.main;

import javafx.application.Platform;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Listener implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private  ManufacturerInterface manufacturerInterface;

    public Listener(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void setManufacturerInterface(ManufacturerInterface manufacturerInterface){
        this.manufacturerInterface=manufacturerInterface;
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
                                    Manufacturer.getInstance().getCarListInterface().onListUpdated(data.getJSONArray("cars"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }else {
                        if (data.getBoolean("status")) {
                            if (data.getString("type").equals(Constants.TYPE_SERVER_LOGIN_RESULT)) {
                                Profile.getInstance().setId(data.getInt("id"));
                                Profile.getInstance().setDisplayName(data.getString("displayName"));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            manufacturerInterface.onSuccess(new JSONObject());
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (data.getString("type").equals(Constants.TYPE_CAR_ADD_RESPONSE)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            manufacturerInterface.onSuccess(data);
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (data.getString("type").equals(Constants.TYPE_CAR_EDIT_RESPONSE)) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            manufacturerInterface.onSuccess(new JSONObject());
                                        } catch (IOException | JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } else if (!data.getBoolean("status")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    manufacturerInterface.onError();
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




