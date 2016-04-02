package no.itera.lego.util;

import no.itera.lego.color.Color;
import no.itera.lego.message.Status;

public final class PositionHelper {

    private PositionHelper() {
    }

    /*
    * This is a helper function to determine the robots position based
    * on the given status.
    *
    * The position is relative to the target in the status object.
    *
    * !!! NOTE !!!
    * If the color is Color.UNDEFINED it will return Position.OUT_OF_MAP.
    * If this is a stupid decision (and it very well might be), you are
    * encouraged to add a Position.UNDEFINED as well!
    */
    public static Position currentPosition(Status status) {
        Color target = status.target;
        Color currentColor = status.colors.get(0);

        switch (currentColor) {
            case GREY:
                return target == Color.RED ?
                        Position.LOWER_LEFT : Position.UPPER_RIGHT;
            case WHITE:
                return target == Color.RED ?
                        Position.LOWER_RIGHT : Position.UPPER_LEFT;
            case BLUE:
                return target == Color.GREEN ?
                        Position.LOWER_LEFT : Position.UPPER_RIGHT;
            case YELLOW:
                return target == Color.GREEN ?
                        Position.LOWER_RIGHT : Position.UPPER_LEFT;
            case RED:
                return target == Color.RED ?
                        Position.TARGET : Position.ENEMY_TARGET;
            case GREEN:
                return target == Color.GREEN ?
                        Position.TARGET : Position.ENEMY_TARGET;
            case BLACK:
                return Position.OUT_OF_MAP;
            default:
                return Position.OUT_OF_MAP;
        }
    }

}
