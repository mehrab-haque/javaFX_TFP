package client.manufacturer.view.logic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Util;

import javax.print.DocFlavor;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Login {

    private Stage stage;

    public Login(Stage stage) throws IOException {
        this.stage=stage;
        stage.setTitle("Manufacturer");
        Util.jumpTo(stage,"src\\client\\manufacturer\\view\\fxml\\login.fxml");
    }
}
