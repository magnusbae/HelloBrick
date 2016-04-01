package no.itera.lego;

import no.itera.lego.util.RobotController;
import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable {

    private RobotState robotState;
    private RobotController robotController;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
        this.robotController = robotState.robotController;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {

            if (robotState.lastStatus == null || !robotState.lastStatus.isActive) {
                continue;
            }

            switch (robotState.lastColor) {
                case BLACK:
                case BLUE:
                case RED:
                case YELLOW:
                    robotController.rotateLeft();
                    break;
                case GREEN:
                    robotController.stop();
                    break;
                default:
                    robotController.forward();
            }
        }
        robotState.latch.countDown();
    }
}
