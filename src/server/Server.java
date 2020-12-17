package server;

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
import java.util.Map;

public class Server {

    private static Server instance=null;

    public static synchronized Server getInstance() {
        if(instance==null)
            instance=new Server();
        return instance;
    }

    List<Listener> listeners;

    private Server() {
        try {
            listeners=new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException, JSONException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        listeners.add(new Listener(networkUtil));
        //new ReadThread(networkUtil);
        //new WriteThread(networkUtil, "Server");
    }

    public void notifyAllClients() throws SQLException, JSONException {
        for(Listener listener:listeners){
            listener.notifyListeners();
        }
    }

    public static void main(String args[]) {
        DB.getInstance();
        Server.getInstance();
    }
}
