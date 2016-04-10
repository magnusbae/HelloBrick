package no.itera.lego.message;

import no.itera.lego.color.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Status implements Message {

    public static final String TYPE = "status";

    /**
     * this is set to true when the server activates a match
     */
    public final boolean isActive;
    /**
     * the current color of your robot
     */
    public final Color target;
    /**
     * the current positions on the colored section of the map of all robots
     * 0: you
     * 1: your teammate
     * 2: opponent one
     * 3: opponent two
     */
    public final List<Color> colors;

    public Status(boolean isActive, Color target, List<Color> colors) {
        this.isActive = isActive;
        this.target = target;
        this.colors = colors;
    }

    private Status(boolean isActive, Color target, final Color currentColor) {
        this.isActive = isActive;
        this.target = target;
        this.colors = new ArrayList<Color>() {
            {
                add(currentColor);
                add(Color.BLUE);
                add(Color.WHITE);
                add(Color.YELLOW);
            }
        };
    }

    public static Status createTestingStatus(boolean isActive, Color target, final Color currentColor){
        return new Status(isActive, target, currentColor);
    }

    public static Status fromJson(JSONObject object) {
        boolean isActive = (boolean) object.get("isActive");

        Color target = Color.valueOf((String) object.get("target"));

        JSONArray jsonColorsArray = (JSONArray) object.get("status");

        List<Color> colors = new ArrayList<>();
        for (Object objColor : jsonColorsArray) {
            colors.add(Color.valueOf((String) objColor));
        }
        return new Status(isActive, target, colors);
    }

    public String toJson() {
        throw new UnsupportedOperationException(String.format("Cannot convert message type '%s' to JSON", TYPE));
    }

    @Override
    public String toString() {
        return String.format("%s{%s, %s, %s}",
                getClass().getSimpleName(),
                isActive,
                target,
                colors);
    }

}
