package no.itera.lego;

import no.itera.lego.message.Status;
import no.itera.lego.util.RobotController;
import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable, WebSocketListener {

    private RobotState robotState;
    private RobotController robotController;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
        this.robotController = robotState.robotController;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            if (serverHasNotYetStartedTheGame()) {
                continue;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        robotState.latch.countDown();
    }

    @Override
    public void initialStatusFired(Status initial) {
        if (serverHasNotYetStartedTheGame()) {
            return;
        }
        robotController.leftForward();
    }

    @Override
    public void newStatusFired(Status oldStatus, Status newStatus) {
        if (serverHasNotYetStartedTheGame()) {
            return;
        }
    }

    private boolean serverHasNotYetStartedTheGame() {
        return robotState.lastStatus == null || !robotState.lastStatus.isActive;
    }

}
