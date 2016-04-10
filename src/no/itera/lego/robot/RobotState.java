package no.itera.lego.robot;

import java.util.concurrent.CountDownLatch;

import no.itera.lego.color.Color;
import no.itera.lego.message.Status;

public class RobotState {

    /**
     * SET THE FOLLOWING VALUES
     *
     * HOST and PORT:
     *      Enter the server address that the robot should connect to
     * name:
     *      The name of your robot
     * simulation:
     *      false - Set it to false when you are playing the real game
     *      true - when you're testing your robot you can control it
     *      through the simulation panel available from the server
     */
    public static final String HOST = "192.168.43.168";
    public static final int PORT = 3004;
    public final String name = "YOUR ROBOT NAME";
    public boolean simulation = true;

    /**
     * READ ONLY
     * The server will set this to true when your round starts
     */
    public boolean shouldRun;
    /**
     * READ ONLY
     * This is the last status of the game received from the server,
     * it contains information about your robot, as well as the position
     * of the other robots on the map
     */
    public Status lastStatus;
    /**
     * READ ONLY
     * This is your robot's last read color, i.e. your position on the map.
     * It is set automatically from the sensor thread
     * Note that this color will be slightly more  up to date than your
     * color in lastStatus (lastStatus.color.get(0)), although it shouldn't
     * matter much
     */
    public Color lastColor = Color.UNDEFINED;
    /**
     * READ ONLY
     * This is your robot's last read distance, the distance seams to vary
     * between these values (note that these values are indicative, and may
     * vary slightly with your robot):
     *
     * Constants:
     * INFINITY: no object in front
     * 0:  object is very close
     *
     * Range between 50 and 5:
     * 50: object is approximately 50-40 cm ahead
     * 5:  object is approximately 9-6 cm ahead
     */
    public float lastDistance;
    /**
     * you use the robotController to control your robot
     * it contains a few helper functions that let you control the robot with
     * _non blocking_ function calls, which means that the robot will keep
     * driving in the specified direction until you tell it to do something else
     */
    public final RobotController robotController;

    // you don't need to worry about these fields
    public CountDownLatch latch;
    public boolean webSocketOpen;
    public boolean webSocketConnecting;

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
