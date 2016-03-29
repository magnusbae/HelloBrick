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
            switch (robotState.lastColor){
                case BLACK:
                    ev3Helper.turnRight(120);
                    break;
                case GREEN:
                case RED:
                    ev3Helper.stop();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    ev3Helper.forward();
            }
        }
        robotState.latch.countDown();
    }
}
