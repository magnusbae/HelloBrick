package no.itera.lego.message;


import java.lang.UnsupportedOperationException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import no.itera.lego.color.Color;

public class Status implements Message {

  public static final String TYPE = "status";

  public final boolean isActive;
  public final Color target;
  public final List<Color> colors;

  public Status(boolean isActive, Color target, List<Color> colors) {
    this.isActive = isActive;
    this.target = target;
    this.colors = colors;
  }

  public static Status fromJson(JSONObject object) {
    boolean isActive = (boolean) object.get("isActive");

    Color target = Color.valueOf((String) object.get("target"));

    JSONArray jsonColorsArray = (JSONArray)object.get("status");

    List<Color> colors = new ArrayList<Color>();
    for (Object objColor : jsonColorsArray) {
      colors.add(Color.valueOf((String) objColor));
    }

    return new Status(isActive, target, colors);
  }

  public String toJson() {
    throw new UnsupportedOperationException(String.format("Cannot convert message type '%s' to JSON", TYPE));
  }

}
