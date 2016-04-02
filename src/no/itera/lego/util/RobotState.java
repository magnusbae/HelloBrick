package no.itera.lego.util;

import java.util.concurrent.CountDownLatch;

import no.itera.lego.color.Color;
import no.itera.lego.message.Status;

public class RobotState {

    // enter the server address:port that the robot should connect to
    public static final String HOST = "192.168.43.168";
    public static final int PORT = 3004;
    public final String name = "YOUR ROBOT NAME";
    // set this to false when you are playing the real game
    public boolean simulation = true;

    // the server will set this to true when your round starts
    public boolean shouldRun;
    // this is the last status received by the server
    public Status lastStatus;
    // this is your robot's last read color, i.e. your position on the map
    // it is set automatically from the sensor thread
    // note that this color will be slightly more  up to date than your
    // color in lastStatus (lastStatus.color.get(0)), although not by much
    public Color lastColor = Color.UNDEFINED;
    // this is your robot's last read distance
    public float lastDistance;
    // you don't need to worry about these fields
    public CountDownLatch latch;
    public boolean webSocketOpen;
    public boolean webSocketConnecting;
    // you use the robotController to control your robot
    // it contains a few helper functions that let you control the robot with
    // _non blocking_ function calls, which means that the robot will keep
    // driving in the specified direction until you tell it to do something else
    public final RobotController robotController;

    public RobotState(RobotController robotController) {
        this.robotController = robotController;
        shouldRun = true;
    }

    @Override
    public String toString() {
        return String.format("%s{%s, %s, %s, %s, %s, %s, %s}",
                getClass().getSimpleName(),
                shouldRun,
                lastStatus,
                lastColor,
                lastDistance,
                webSocketOpen,
                webSocketConnecting,
                robotController);
    }

}
