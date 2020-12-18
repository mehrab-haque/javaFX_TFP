package client.manufacturer.view.controller;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.ManufacturerInterface;
import client.manufacturer.view.logic.DashBoard;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.Toast;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML public TextField username;
    @FXML public TextField password;
    @FXML public Button login;
    @FXML public Button add;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login.setOnMouseClicked(event->{
            String uname=username.getText();
            String pass=password.getText();
            try {
                Manufacturer.getInstance().setManufacturerInterface(new ManufacturerInterface(){

                    @Override
                    public void onError() {
                        Toast.makeText((Stage)login.getScene().getWindow(),"Wrong Credentials",1000,1000,500);
                    }

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Toast.makeText((Stage)login.getScene().getWindow(),"Logged In Successfully",1000,1000,500);
                        try {
                            new DashBoard((Stage)login.getScene().getWindow());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Manufacturer.getInstance().login(uname,pass);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
