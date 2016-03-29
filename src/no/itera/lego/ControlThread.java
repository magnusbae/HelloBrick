package no.itera.lego;

import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable {

    private RobotState robotState;
    private EV3Helper ev3Helper;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
        this.ev3Helper = robotState.ev3Helper;
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
                    ev3Helper.rotateLeft();
                    break;
                case GREEN:
                    ev3Helper.stop();
                    break;
                default:
                    ev3Helper.forward();
            }
        }
        robotState.latch.countDown();
    }
}
