package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Util {
    public static FXMLLoader jumpTo(Node fromNode, String to) throws IOException {
        File file = new File(to);
        URL url = new URL("file:\\"+file.getAbsolutePath());
        FXMLLoader loader=new FXMLLoader(url);
        Parent loginParent = loader.load();
        Scene scene=new Scene(loginParent);
        Stage stage= (Stage)fromNode.getScene().getWindow();
        stage.setScene(scene);
        return loader;
    }
    public static FXMLLoader jumpTo(Stage fromStage, String to) throws IOException {
        File file = new File(to);
        URL url = new URL("file:\\"+file.getAbsolutePath());
        FXMLLoader loader=new FXMLLoader(url);
        Parent loginParent = loader.load();
        Scene scene=new Scene(loginParent);
        fromStage.setScene(scene);
        return loader;
    }
}
