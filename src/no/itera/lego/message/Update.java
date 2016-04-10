package no.itera.lego.message;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import no.itera.lego.color.Color;

public class Update implements Message {

    public static final String TYPE = "update";

    private final Color color;

    public Update(Color color) {
        this.color = color;
    }

    public String toJson() {
        Map<String, String> map = new HashMap<>();
        map.put("type", TYPE);
        map.put("color", color.toString());

        return new JSONObject(map).toJSONString();
    }

    public static Update fromJson(JSONObject object) {
        throw new UnsupportedOperationException(String.format("Cannot parse message type '%s'", TYPE));
    }

}
