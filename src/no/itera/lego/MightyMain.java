package no.itera.lego;

import java.util.concurrent.CountDownLatch;

import lejos.hardware.Button;

import no.itera.lego.color.ColorLoggerThread;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.RobotState;
import no.itera.lego.websocket.WebSocketThread;

public class MightyMain {

    private static EV3Helper ev3Helper = new EV3Helper();
    private static RobotState robotState = new RobotState();
    private static StandardStateReceiver standardStateReceiver = new StandardStateReceiver();

    public static void main(String[] args) throws InterruptedException {
        robotState.latch = new CountDownLatch(2);

        WebSocketThread webSocketThread = new WebSocketThread(robotState);
        webSocketThread.addEventListener(standardStateReceiver);

        ColorLoggerThread colorLoggerThread = new ColorLoggerThread(robotState);

        Thread wsThreadRunner = new Thread(webSocketThread);
        Thread cltThreadRunner = new Thread(colorLoggerThread);
        Thread sensorThread = new Thread(new SensorThread(robotState));
        Thread controlThread = new Thread(new ControlThread(robotState));

        wsThreadRunner.start();
        cltThreadRunner.start();
        sensorThread.start();
        controlThread.start();

        System.out.println("\nPress enter to exit program");


        while (robotState.shouldRun) {
            if (Button.ENTER.isDown()) {
                ev3Helper.playBeep();
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

        ev3Helper.playBeep();

    }
}
