package client.manufacturer.main;

import util.Constants;
import util.NetworkUtil;

public class Manufacturer {
    public Manufacturer(String userName,String password,ManufacturerInterface manufacturerInterface) {
        try {
            NetworkUtil networkUtil = new NetworkUtil(Constants.SERVER_ADDRESS, Constants.SERVER_PORT);
            //new ReadThread(networkUtil);
            //new WriteThread(networkUtil, "Client");
            new Listener(networkUtil,manufacturerInterface);
            new Login(networkUtil,userName,password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
