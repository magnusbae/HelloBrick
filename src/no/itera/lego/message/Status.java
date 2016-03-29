package no.itera.lego.message;


import java.lang.UnsupportedOperationException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import no.itera.lego.color.Color;

public class Status implements Message {

  public static final String TYPE = "status";

  public final List<Color> colors;

  public Status(List<Color> colors) {
    this.colors = colors;
  }

  public static Status fromJson(JSONObject object) {

    JSONArray jsonColorsArray = (JSONArray)object.get("status");

    List<Color> colors = new ArrayList<Color>();
    for (Object objColor : jsonColorsArray) {
      colors.add(Color.valueOf((String) objColor));
    }

    return new Status(colors);
  }

  public String toJson() {
    throw new UnsupportedOperationException(String.format("Cannot convert message type '%s' to JSON", TYPE));
  }

}
