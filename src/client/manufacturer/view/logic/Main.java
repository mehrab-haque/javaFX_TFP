package client.manufacturer.view.logic;

import client.manufacturer.main.Manufacturer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Manufacturer.getInstance();
        new Login(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
