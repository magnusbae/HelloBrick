package no.itera.lego;

import no.itera.lego.robot.Robot;
import no.itera.lego.robot.RobotState;

public class ControlThread implements Runnable {

    private final Robot robot;
    private final RobotState robotState;

    public ControlThread(Robot robot, RobotState robotState) {
        this.robot = robot;
        this.robotState = robotState;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            if (theGameIsNotActive()) {
                continue;
            } else {
                robot.loop();
            }
        }
        robotState.latch.countDown();
    }

    private boolean theGameIsNotActive() {
        return robotState.lastStatus == null || !robotState.lastStatus.isActive;
    }

}
