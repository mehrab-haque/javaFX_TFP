package client.viewer.main;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public interface ViewerInterface {
    void onError();
    void onSuccess(JSONObject data) throws IOException, JSONException;
}
