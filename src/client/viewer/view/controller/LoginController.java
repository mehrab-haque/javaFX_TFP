package client.viewer.view.controller;


import client.viewer.main.Viewer;
import client.viewer.main.ViewerInterface;
import client.viewer.view.logic.DashBoard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import util.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML public TextField username;
    @FXML public Button login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login.setOnMouseClicked(event->{
            String uname=username.getText();
            try {
                Viewer.getInstance().setViewerInterface(new ViewerInterface(){
                    @Override
                    public void onError() {
                        Toast.makeText((Stage)login.getScene().getWindow(),"Wrong Credentials",1000,1000,500);
                    }
                    @Override
                    public void onSuccess(JSONObject data) throws IOException, JSONException {
                        Toast.makeText((Stage)login.getScene().getWindow(),"Logged In Successfully",1000,1000,500);
                        try {
                            new DashBoard((Stage)login.getScene().getWindow());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Viewer.getInstance().login(uname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
