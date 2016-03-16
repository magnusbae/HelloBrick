package no.itera.lego;

import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable {

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
}
