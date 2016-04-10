package no.itera.lego.websocket;

import no.itera.lego.color.Color;
import no.itera.lego.message.Message;
import no.itera.lego.message.MessageReader;
import no.itera.lego.message.Register;
import no.itera.lego.message.Status;
import no.itera.lego.message.Update;
import no.itera.lego.robot.RobotState;
import no.itera.lego.util.StatusHistory;

public class WebSocketThread implements Runnable {

    private RobotState robotState;
    private StatusHistory statusHistory;
    private BrickSocket socket;
    private Color lastHandledColor;
    private boolean stop;

    public WebSocketThread(RobotState robotState, StatusHistory statusHistory) {
        this.robotState = robotState;
        this.statusHistory = statusHistory;
    }

    @Override
    public void run() {
        connectToSocket();

        while (robotState.shouldRun && !stop) {
            if (robotState.lastColor != lastHandledColor) {
                sendColor();
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
        System.out.println(String.format("Received: %s", receivedString));
        Message message = MessageReader.readJson(receivedString);

        if (message.getClass() == Status.class) {
            robotState.lastStatus = (Status) message;

            notifyStatusListenersOrStopIfRoundIsOver();
        }
    }

    private void notifyStatusListenersOrStopIfRoundIsOver() {
        if (robotState.lastStatus != null) {
            if (robotState.lastStatus.isActive) {
                statusHistory.addNewStatus(robotState.lastStatus);
            } else {
                robotState.robotController.stop();
            }
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

        while (!robotState.webSocketOpen) {
        }

        System.out.println("Connected");

        Register register = new Register(robotState.name);
        sendMessage(register);
        sendColor();
    }

    private void sendColor() {
        if (!robotState.simulation) {
            sendMessage(new Update(robotState.lastColor));
            lastHandledColor = robotState.lastColor;
        }
    }

    public void stopThread(){
        stop = true;
    }
}
