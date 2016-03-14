package no.itera.lego;

import lejos.hardware.Button;
import no.itera.lego.util.RobotState;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.websocket.WebSocketThread;

import java.util.concurrent.CountDownLatch;

public class MightyMain {

    private static EV3Helper ev3Helper = new EV3Helper();
    private static RobotState robotState = new RobotState();
    private static StandardStateReceiver standardStateReceiver = new StandardStateReceiver();

    public static void main(String[] args) throws InterruptedException {
        WebSocketThread webSocketThread = new WebSocketThread(robotState);
        webSocketThread.addEventListener(standardStateReceiver);

        Thread wsThreadRunner;
        wsThreadRunner = new Thread(webSocketThread);
        wsThreadRunner.start();
        robotState.latch = new CountDownLatch(1);

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
