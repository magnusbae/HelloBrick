package no.itera.lego;

import no.itera.lego.util.RobotState;
import no.itera.lego.websocket.WebSocketThread;

public class ControlThread implements Runnable, MessageReceiver, SensorReceiver {

    private RobotState robotState;
    private WebSocketThread webSocketThread;

    public ControlThread(RobotState robotState, WebSocketThread webSocketThread) {
        this.robotState = robotState;
        this.webSocketThread = webSocketThread;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
        }
        robotState.latch.countDown();
    }

    public void receiveMessage(String message) {
    }

    public void receiveColor(String color) {
        webSocketThread.sendMessage("Got color: " + color);
        System.out.println("Got color: " + color);
    }

    public void receiveDistance(float distance) {
    }
}
