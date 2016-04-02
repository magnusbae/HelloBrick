package no.itera.lego;

import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable {

    private final Controller controller;
    private final RobotState robotState;

    public ControlThread(Controller controller, RobotState robotState) {
        this.controller = controller;
        this.robotState = robotState;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            if (theGameIsNotActive()) {
                continue;
            } else {
                controller.loop();
            }
        }
        robotState.latch.countDown();
    }

    private boolean theGameIsNotActive() {
        return robotState.lastStatus == null || !robotState.lastStatus.isActive;
    }

}
