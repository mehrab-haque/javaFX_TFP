package client.manufacturer.view.controller;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.ManufacturerInterface;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    private boolean isResultRecieved=false;
    private boolean isValid=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(isResultRecieved){
                    isResultRecieved=false;
                    if(isValid){
                        Toast.makeText((Stage)login.getScene().getWindow(),"Logged In Successfully",1000,1000,500);
                        try {
                            Util.jumpTo(login,"src\\client\\manufacturer\\view\\fxml\\dashboard.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText((Stage)login.getScene().getWindow(),"Wrong Credentials",1000,1000,500);
                    }
                }
            }
        }.start();

        login.setOnMouseClicked(event->{
            String uname=username.getText();
            String pass=password.getText();
            try {
                Manufacturer.getInstance().login(uname,pass,new ManufacturerInterface(){

                    @Override
                    public void onError() {
                        isValid=false;
                        isResultRecieved=true;
                    }

                    @Override
                    public void onSuccess() {
                        isValid=true;
                        isResultRecieved=true;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
