package no.itera.lego;

import no.itera.lego.color.Color;
import no.itera.lego.util.RobotState;
import no.itera.lego.websocket.WebSocketThread;
import no.itera.lego.message.Update;
import no.itera.lego.message.Message;
import no.itera.lego.message.MessageReceiver;

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

    public void receiveMessage(Message message) {
    }

    public void receiveColor(Color color) {
        webSocketThread.sendMessage(new Update(color));
    }

    public void receiveDistance(float distance) {
    }
}
