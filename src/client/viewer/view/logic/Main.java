package client.viewer.view.logic;


import client.viewer.main.Viewer;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.text.View;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Viewer.getInstance();
        new Login(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
