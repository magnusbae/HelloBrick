package no.itera.lego;

import no.itera.lego.message.Update;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.RobotState;
import no.itera.lego.websocket.WebSocketThread;

public class ControlThread implements Runnable {

    private RobotState robotState;
    private WebSocketThread webSocketThread;
    private EV3Helper ev3Helper;

    public ControlThread(RobotState robotState, WebSocketThread webSocketThread) {
        this.robotState = robotState;
        this.webSocketThread = webSocketThread;
        this.ev3Helper = robotState.ev3Helper;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            webSocketThread.sendMessage(new Update(robotState.lastColor));

            switch (robotState.lastColor){
                case BLACK:
                    ev3Helper.turnRight(120);
                    break;
                case GREEN:
                case RED:
                    ev3Helper.stop();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case UNDEFINED:
                    ev3Helper.backward(8);
                    //falls through
                default:
                    ev3Helper.forward();
            }
        }
        robotState.latch.countDown();
    }
}
