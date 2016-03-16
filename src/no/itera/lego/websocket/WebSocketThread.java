package no.itera.lego.websocket;

import java.util.ArrayList;
import java.util.List;

import no.itera.lego.message.Message;
import no.itera.lego.message.MessageReader;
import no.itera.lego.message.MessageReceiver;
import no.itera.lego.message.Register;
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
        connectToSocket();

        while (robotState.shouldRun) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public void onSocketClose() {
        if (robotState.shouldRun) {
            System.out.println("Lost connection, reconnecting");
            connectToSocket();
        }
    }

    public void onSocketMessage(String message) {
        System.out.println(String.format("Received: %s", message));
        for (MessageReceiver eventListener : eventListeners) {
            eventListener.receiveMessage(MessageReader.readJson(message));
        }
    }

    public void sendMessage(Message message) {
        if (robotState.webSocketOpen) {
            System.out.println(String.format("Sending: %s", message));
            socket.send(message.toJson());
        }
    }

    private void connectToSocket() {
        String url = String.format("ws://%s:%s", RobotState.HOST, RobotState.PORT);
        System.out.println("Connecting to: " + url);

        socket = new BrickSocket(url, robotState, this);
        socket.connect();

        System.out.println("Connected");

        while(!robotState.webSocketOpen) {
        }

        Register register = new Register("Robot 1");
        sendMessage(register);
    }
}
