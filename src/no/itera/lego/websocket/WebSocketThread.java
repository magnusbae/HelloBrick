package no.itera.lego.websocket;

import no.itera.lego.color.Color;
import no.itera.lego.message.Message;
import no.itera.lego.message.MessageReader;
import no.itera.lego.message.Register;
import no.itera.lego.message.Status;
import no.itera.lego.message.Update;
import no.itera.lego.util.RobotState;

public class WebSocketThread implements Runnable {

    private RobotState robotState;
    private BrickSocket socket;
    private Color lastHandledColor;

    public WebSocketThread(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() {
        connectToSocket();

        while (robotState.shouldRun) {
            if (robotState.lastColor != lastHandledColor) {
                sendMessage(new Update(robotState.lastColor));
                lastHandledColor = robotState.lastColor;
            }
        }

        socket.close();
        robotState.latch.countDown();
    }

    public void onSocketClose() {
        if (robotState.shouldRun) {
            System.out.println("Lost connection, reconnecting");
            connectToSocket();
        }
    }

    public void onSocketMessage(String receivedString) {
        Message message = MessageReader.readJson(receivedString);

        if (message.getClass() == Status.class) {
            robotState.lastStatus = (Status) message;
        }
    }

    public void sendMessage(Message message) {
        if (robotState.webSocketOpen) {
            System.out.println(String.format("Sending: %s", message.toJson()));
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
