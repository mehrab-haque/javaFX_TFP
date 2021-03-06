package client.manufacturer.view.logic;

import util.CarListInterface;
import client.manufacturer.main.Manufacturer;
import client.manufacturer.main.Profile;
import client.manufacturer.view.controller.DashboardController;
import client.manufacturer.view.controller.ListItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;

import java.io.IOException;

public class DashBoard {
    private Stage stage;
    private DashboardController dashboardController;

    public DashBoard(Stage stage) throws IOException {
        this.stage=stage;
        dashboardController=Util.jumpTo(stage,"src\\client\\manufacturer\\view\\fxml\\dashboard.fxml").getController();
        Manufacturer.getInstance().setCarListInterface(new CarListInterface() {
            @Override
            public void onListUpdated(JSONArray jsonArray) throws JSONException {
                dashboardController.vBox.getChildren().clear();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject car=jsonArray.getJSONObject(i);
                    car.put("id",car.getString("carId"));
                    if(car.getInt("manufacturerId")== Profile.getInstance().getId()) {
                        try {
                            FXMLLoader fxmlLoader = Util.getFXMLLoader("src\\client\\manufacturer\\view\\fxml\\list_item.fxml");
                            Parent itemUI = fxmlLoader.load();
                            dashboardController.vBox.getChildren().add(itemUI);
                            ListItemController listItemController=fxmlLoader.getController();
                            listItemController.image.setImage(new Image(car.getString("image")));
                            listItemController.reg.setText("Reg No : "+car.getInt("carId"));
                            listItemController.make.setText(car.getString("make"));
                            listItemController.model.setText(car.getString("model"));
                            listItemController.price.setText(car.getInt("price")+"");
                            listItemController.quantity.setText("Qty : "+car.getInt("quantity"));
                            listItemController.timestamp.setText(car.getLong("timestamp")+"");

                            listItemController.edit.setOnMouseClicked(event->{
                                try {
                                    new EditCar(car,stage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });

                            listItemController.delete.setOnMouseClicked(event->{
                                try {
                                    Manufacturer.getInstance().deleteCar(car.getInt("id"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        try {
            Manufacturer.getInstance().requestCarList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
