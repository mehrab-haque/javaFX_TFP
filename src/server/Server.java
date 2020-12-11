package server;

import org.json.JSONException;
import org.json.JSONObject;
import util.Constants;
import util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {


    Server() {
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

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException, JSONException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new Listener(networkUtil);
        //new ReadThread(networkUtil);
        //new WriteThread(networkUtil, "Server");
    }

    public static void main(String args[]) {
        DB.getInstance();
        Server server = new Server();
    }
}
