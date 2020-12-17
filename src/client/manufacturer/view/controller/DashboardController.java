package client.manufacturer.view.controller;

import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.ManufacturerInterface;
import client.manufacturer.main.Profile;
import client.manufacturer.view.logic.EditCar;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import util.Toast;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML public Label welcome;
    @FXML public Button logout;
    @FXML public Button add;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        add.setOnMouseClicked(mouseEvent -> {
            try {
                Manufacturer.getInstance().setManufacturerInterface(new ManufacturerInterface(){

                    @Override
                    public void onError() {
                        Toast.makeText((Stage)add.getScene().getWindow(),"An error occurred",1000,1000,500);
                    }

                    @Override
                    public void onSuccess(JSONObject jsonObject) throws IOException {
                        new EditCar(jsonObject,(Stage)add.getScene().getWindow());
                    }
                });
                Manufacturer.getInstance().addCar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        welcome.setText("Welcome "+ Profile.getInstance().getDisplayName());
        logout.setOnMouseClicked(event->{
            try {
                Util.jumpTo(logout,"src\\client\\manufacturer\\view\\fxml\\login.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
