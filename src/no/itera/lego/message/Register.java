package no.itera.lego.message;

import java.lang.UnsupportedOperationException;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class Register implements Message {

  public static final String TYPE = "register";

  private final String name;

  public Register(String name) {
    this.name = name;
  }

  public String toJson() {
    Map<String, String> map = new HashMap<>();
    map.put("type", TYPE);
    map.put("name", name);

    return new JSONObject(map).toJSONString();
  }

  public static Register fromJson(JSONObject object) {
    throw new UnsupportedOperationException(String.format("Cannot parse message type '%s'", TYPE));
  }

}
