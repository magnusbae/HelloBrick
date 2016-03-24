package no.itera.lego.websocket;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import no.itera.lego.color.Color;
import no.itera.lego.message.MessageReader;
import no.itera.lego.message.Message;
import no.itera.lego.message.Update;
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
        String url = String.format("ws://%s:%s", RobotState.HOST, RobotState.PORT);
        socket = new BrickSocket(url, robotState, this);
        System.out.println(String.format("Connecting to: %s:%s", RobotState.HOST, RobotState.PORT));
        socket.connect();
        System.out.println("Connected");

        while(!robotState.webSocketOpen) {
        }

        Register register = new Register("Robot 1");
        sendMessage(register);

        while (robotState.shouldRun) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMessage(new Update(Color.randomColor()));
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
        System.out.println(String.format("Recieved: %s", message));
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
}
