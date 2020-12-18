package server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static Server instance=null;
    private List<NetworkUtil> clients;

    public static synchronized Server getInstance() {
        if(instance==null)
            instance=new Server();
        return instance;
    }


    private Server() {
        clients=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    ServerSocket serverSocket = new ServerSocket(33333);
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        serve(clientSocket);
                    }
                } catch (Exception e) {
                    System.out.println("Server starts:" + e);
                }
            }
        }).start();
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException, JSONException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        clients.add(networkUtil);
        new Listener(networkUtil);
    }

    public void notifyAllClients() throws SQLException, JSONException {
        JSONArray cars=DB.getInstance().getAllCars();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("type", Constants.TYPE_CAR_LIST_NOTIFICATION);
        jsonObject.put("cars",cars);
        for(NetworkUtil client:clients){
            try {
                client.write(jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        DB.getInstance();
        Server.getInstance();
    }
}
