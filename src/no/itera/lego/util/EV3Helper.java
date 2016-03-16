package no.itera.lego.util;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;

import java.lang.reflect.Field;

/**
 * Helper functions for basic operation of our pre-configured robot.
 * This class provides useful wrappers around Lejos code and examples for you to build on.
 * This code is by design not optimized. You will have to figure out where to improve this code, and how.
 * <p/>
 * For the complete Lejos API take a look at:
 * http://www.lejos.org/ev3/docs/
 */
public class EV3Helper {

    public static final int MAX_MOTOR_SPEED = 900;
    private static final String[] colors = getColors();

    private RegulatedMotor motorRight;
    private RegulatedMotor motorLeft;

    /**
     * Instantiates a new helper class and optionally calibrates the cannon.
     * Robot is ready for operation after completion.
     */
    public EV3Helper() {
        motorRight = new EV3LargeRegulatedMotor(MotorPort.D);
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
    }

    public RegulatedMotor getMotorRight() {
        return motorRight;
    }

    public RegulatedMotor getMotorLeft() {
        return motorLeft;
    }

    /**
     * Plays a beep with the speaker.
     */
    public void playBeep() {
        Sound.beep();
    }

    /**
     * Stops both motors immediately
     */
    public void stop() {
        motorLeft.stop(true);
        motorRight.stop(true);
    }

    /**
     * Get a array of the colors in the Color class. Note that the EV3 does not
     * support all of these colors. The colors it supports are: NONE, BLACK,
     * BLUE, GREEN, YELLOW, RED, WHITE and BROWN.
     * @return array of the colors
     */
    private static String[] getColors() {
        Field[] names = Color.class.getFields();
        String[] list = new String[names.length];
        for (int i = 0; i < list.length; i++) {
            list[i] = names[i].getName();
        }
        return list;
    }

    /**
     * Fetches a color sample from the EV3 color sensor.
     * Supported colors are: NONE, BLACK,
     * BLUE, GREEN, YELLOW, RED, WHITE and BROWN.
     * @return name of measured color
     */
    public static String getColorName(int colorID) {
        if (colorID < 0 || colorID >= colors.length) {
            return "NONE";
        }
        return colors[colorID];
    }
}
