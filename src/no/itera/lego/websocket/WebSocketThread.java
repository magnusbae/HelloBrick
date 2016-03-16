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
        socket = new BrickSocket(url, robotState);
        socket.connect();

        while (robotState.shouldRun){
            while (robotState.shouldRun && robotState.webSocketOpen) {
                //FIXME Examplecode, robot should send more info than this:
                //FIXME (And preferably in a better format)

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //TODO Implement behaviour for what to do with the message received:
                //TODO NOTE: This does not have to be in this class, RobotState can easily be shared
                //TODO between threads
                if(robotState.lastMessage != null){
                    String message = robotState.lastMessage;
                    robotState.lastMessage = null;
                    System.out.println("Last message was: " + message);
                }
            }
            if(!robotState.webSocketOpen && !robotState.webSocketConnecting){
                System.out.println("Lost connection, reconnecting");
                socket.connect();
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

    public void sendMessage(String message) {
        if (robotState.webSocketOpen) {
            socket.send(message);
        }
    }
}
