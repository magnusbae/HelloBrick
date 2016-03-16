package no.itera.lego.websocket;

import java.util.ArrayList;
import java.util.List;

import no.itera.lego.MessageReceiver;
import no.itera.lego.util.RobotState;

public class WebSocketThread implements Runnable {

    private RobotState robotState;
    private BrickSocket socket;
    private List<MessageReceiver> eventListeners = new ArrayList<>();

    public WebSocketThread(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() {
        String url = String.format("ws://%s:%s", RobotState.HOST, RobotState.PORT);
        socket = new BrickSocket(url, robotState, this);
        socket.connect();

        while (robotState.shouldRun) {
        }

        socket.close();
        robotState.latch.countDown();
    }

    public void addEventListener(MessageReceiver eventListener) {
        eventListeners.add(eventListener);
    }

    public void removeEventListener(MessageReceiver eventListener) {
        eventListeners.remove(eventListener);
    }

    public void onSocketMessage(String message) {
        for (MessageReceiver eventListener : eventListeners) {
            eventListener.receiveMessage(message);
        }
    }

    public void sendMessage(String message) {
        if (robotState.webSocketOpen) {
            socket.send(message);
        }
    }
}
