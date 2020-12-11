package client.manufacturer.view.controller;

import client.manufacturer.main.Profile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML public Label welcome;
    @FXML public Button logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
