package no.itera.lego;

import java.util.concurrent.CountDownLatch;

import lejos.hardware.Button;

import no.itera.lego.robot.Robot;
import no.itera.lego.robot.RobotController;
import no.itera.lego.robot.RobotState;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.StatusHistory;
import no.itera.lego.websocket.WebSocketThread;

public class MightyMain {

    private static RobotController robotController = new EV3Helper();
    private static RobotState robotState = new RobotState(robotController);
    private static StatusHistory statusHistory = new StatusHistory();

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

        while (robotState.shouldRun) {
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
}
