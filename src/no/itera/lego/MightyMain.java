package no.itera.lego;

import lejos.hardware.Button;
import no.itera.lego.color.Color;
import no.itera.lego.message.Status;
import no.itera.lego.robot.Robot;
import no.itera.lego.robot.RobotController;
import no.itera.lego.robot.RobotState;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.StatusHistory;
import no.itera.lego.websocket.WebSocketThread;

import java.util.concurrent.CountDownLatch;

public class MightyMain {

    private static RobotController robotController = new EV3Helper();
    private static RobotState robotState = new RobotState(robotController);
    private static StatusHistory statusHistory = new StatusHistory();
    private static Color userOverrideTarget;

    public static void main(String[] args) throws InterruptedException {
        robotState.latch = new CountDownLatch(2);

        WebSocketThread webSocketThread = new WebSocketThread(robotState, statusHistory);
        SensorThread sensorThread = new SensorThread(robotState);
        Robot robot = new Robot(robotState);
        ControlThread controlThread = new ControlThread(robot, robotState);

        statusHistory.addListener(robot);

        Thread webSocketThreadRunner = new Thread(webSocketThread);
        Thread sensorThreadRunner = new Thread(sensorThread);
        Thread controlThreadRunner = new Thread(controlThread);

        webSocketThreadRunner.start();
        sensorThreadRunner.start();
        controlThreadRunner.start();

        System.out.println("\nPress enter to exit program");
        System.out.println("\nTesting: \nLeft = RED target. \nRight = GREEN");

        while (robotState.shouldRun) {
            if (robotState.lastStatus.isActive) { //If server hasn't started game, give user option to start game manually
                checkUserOverride(webSocketThread);
            }
            if (Button.ENTER.isDown()) {
                robotController.playBeep();
                robotState.shouldRun = false;
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Bye!");
        robotState.latch.await();

        statusHistory.clearListeners();

        robotController.playBeep();

    }

    /**
     * Checks if user asks for server override ("dry run") for testing purposes
     * Will disconnect the websocket and stop the websocket thread.
     *
     * @param webSocketThread
     */
    private static void checkUserOverride(WebSocketThread webSocketThread) {
        userOverrideTarget = null;
        if (Button.LEFT.isDown()) {
            userOverrideTarget = Color.RED;
        } else if (Button.RIGHT.isDown()) {
            userOverrideTarget = Color.GREEN;
        }
        if (userOverrideTarget != null && lastColorIsAColor()) { //will wait for color-reading after thread init
            webSocketThread.stopThread();
            robotState.lastStatus = Status.createTestingStatus(true, userOverrideTarget, robotState.lastColor);
        }
    }

    private static boolean lastColorIsAColor() {
        return robotState.lastColor != null && robotState.lastColor != Color.UNDEFINED;
    }
}
