package client.manufacturer.main;

import util.Constants;
import util.NetworkUtil;

import java.io.IOException;

public class Manufacturer {

    public static Manufacturer instance=null;
    private NetworkUtil networkUtil;

    private Manufacturer() throws IOException {
        networkUtil = new NetworkUtil(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
    }

    public static synchronized Manufacturer getInstance() throws IOException {
        if(instance==null)
            instance=new Manufacturer();
        return instance;
    }

    public void login(String userName,String password,ManufacturerInterface manufacturerInterface){
        try {
            new Listener(networkUtil,manufacturerInterface);
            new Login(networkUtil,userName,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
