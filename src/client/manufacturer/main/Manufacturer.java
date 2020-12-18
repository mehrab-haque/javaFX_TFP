package client.manufacturer.main;

import javafx.stage.Stage;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Manufacturer {

    public static Manufacturer instance=null;
    private NetworkUtil networkUtil;
    private Listener listener;
    private ManufacturerInterface manufacturerInterface;
    private List<Stage> stages;

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    private Manufacturer() throws IOException {
        stages=new ArrayList<>();
        networkUtil = new NetworkUtil(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
        listener=new Listener(networkUtil);
    }

    public static synchronized Manufacturer getInstance() throws IOException {
        if(instance==null)
            instance=new Manufacturer();
        return instance;
    }

    public void login(String userName,String password){
        try {
            new Login(userName,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addCar(){
        new AddCar();
    }

    public NetworkUtil getNetworkUtil(){
        return networkUtil;
    }

    public void setManufacturerInterface(ManufacturerInterface manufacturerInterface){
        this.manufacturerInterface=manufacturerInterface;
        listener.setManufacturerInterface(manufacturerInterface);
    }

    public void editCar(JSONObject jsonObject){
        new EditCar(jsonObject);
    }

    public void requestCarList(){
        new RequestCarList();
    }

}
