package util;

import org.json.JSONArray;
import org.json.JSONException;

public interface CarListInterface {
    public void onListUpdated(JSONArray jsonArray) throws JSONException;
}
