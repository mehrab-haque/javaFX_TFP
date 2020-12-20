package client.viewer.main;


import javafx.stage.Stage;
import util.CarListInterface;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Viewer {

    public static Viewer instance=null;
    private NetworkUtil networkUtil;
    private Listener listener;
    private ViewerInterface viewerInterface;
    private CarListInterface carListInterface;


    public CarListInterface getCarListInterface() {
        return carListInterface;
    }

    public void setCarListInterface(CarListInterface carListInterface) {
        this.carListInterface = carListInterface;
    }

    private Viewer() throws IOException {
        networkUtil = new NetworkUtil(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
        listener=new Listener(networkUtil);
    }

    public static synchronized Viewer getInstance() throws IOException {
        if(instance==null)
            instance=new Viewer();
        return instance;
    }

    public void login(String userName){
        try {
            new Login(userName);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public NetworkUtil getNetworkUtil(){
        return networkUtil;
    }

    public void setViewerInterface(ViewerInterface viewerInterface){
        this.viewerInterface=viewerInterface;
        listener.setViewerInterface(viewerInterface);
    }

    public void requestCarList(){
        new RequestCarList();
    }

    public void buyCar(int carId){
        new BuyCar(carId);
    }

}
