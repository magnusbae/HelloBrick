package no.itera.lego;

import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable, MessageReceiver, SensorReceiver {

    private RobotState robotState;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
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
        System.out.println("Got color: " + color);
    }

    public void receiveDistance(float distance) {
    }
}
