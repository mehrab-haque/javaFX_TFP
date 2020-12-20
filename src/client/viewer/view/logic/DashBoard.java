package client.viewer.view.logic;


import client.manufacturer.view.controller.ListItemController;
import client.manufacturer.view.logic.EditCar;
import client.viewer.main.Viewer;
import client.viewer.main.ViewerInterface;
import client.viewer.view.controller.CarCardController;
import client.viewer.view.controller.DashboardController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.CarListInterface;
import util.Toast;
import util.Util;

import java.io.IOException;
import java.util.Date;

public class DashBoard {
    private Stage stage;
    private DashboardController dashboardController;
    private JSONArray carList;

    public DashBoard(Stage stage) throws IOException {
        carList=new JSONArray();
        this.stage=stage;
        dashboardController= Util.jumpTo(stage,"src\\client\\viewer\\view\\fxml\\dashboard.fxml").getController();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            dashboardController.flowPane.setPrefWidth(newVal.doubleValue());
        });

        dashboardController.search.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                liveSearch(newValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });



        Viewer.getInstance().setViewerInterface(new ViewerInterface() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess(JSONObject data) throws IOException, JSONException {
                Toast.makeText(stage,"Car baught successfully !!!",1000,1000,500);
            }
        });


        Viewer.getInstance().setCarListInterface(new CarListInterface() {
            @Override
            public void onListUpdated(JSONArray jsonArray) throws JSONException {
                carList=jsonArray;
                liveSearch(dashboardController.search.getText());
            }
        });

        try {
            Viewer.getInstance().requestCarList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void liveSearch(String searchString) throws JSONException {
        JSONArray jsonArray=new JSONArray();
        String[] searchTokens=searchString.split(" ");
        for(int i=0;i<carList.length();i++){
            JSONObject car=carList.getJSONObject(i);
            for(int j=0;j<searchTokens.length;j++){
                String token=searchTokens[j].trim().toLowerCase();
                String stringSequence=car.getString("make").trim().toLowerCase()+car.getString("model").trim().toLowerCase()+car.getInt("id")+car.getString("manufacturerName").trim().toLowerCase()+car.getInt("price");
                if(stringSequence.contains(token)){
                    jsonArray.put(car);
                    break;
                }
            }
        }
        plotCars(jsonArray);
    }


    private void plotCars(JSONArray jsonArray) throws JSONException {
        dashboardController.flowPane.getChildren().clear();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject car=jsonArray.getJSONObject(i);
            car.put("id",car.getString("carId"));
            if(car.getInt("quantity")>0) {
                try {
                    FXMLLoader fxmlLoader = Util.getFXMLLoader("src\\client\\viewer\\view\\fxml\\car_card.fxml");
                    Parent itemUI = fxmlLoader.load();
                    dashboardController.flowPane.getChildren().add(itemUI);

                    CarCardController carCardController = fxmlLoader.getController();
                    carCardController.image.setImage(new Image(car.getString("image"), 217, 144.6667, false, false));
                    carCardController.reg.setText("Reg No : "+car.getInt("id"));
                    carCardController.manufacturer.setText("-added by "+car.getString("manufacturerName")+" on "+new Date(car.getLong("timestamp")).toLocaleString());
                    carCardController.make.setText("Make : "+car.getString("make"));
                    carCardController.model.setText("Model : "+car.getString("model"));
                    carCardController.price.setText("Price : $"+car.getInt("price")+"/=");
                    carCardController.color.setStyle("-fx-background-color: #"+car.getString("color").substring(2,10)+";");

                    if(car.getInt("quantity")==1)
                        carCardController.available.setText("1 Car Available");
                    else
                        carCardController.available.setText(car.getInt("quantity")+" cars available");

                    carCardController.buy.setOnMouseClicked(event->{
                        try {
                            Viewer.getInstance().buyCar(car.getInt("id"));
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
