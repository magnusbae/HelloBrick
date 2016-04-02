package no.itera.lego;

import no.itera.lego.color.Color;
import no.itera.lego.util.Speed;
import no.itera.lego.message.Status;
import no.itera.lego.util.RobotController;
import no.itera.lego.util.RobotState;

public class ControlThread implements Runnable, WebSocketListener {

    public static final int LOOP_SLEEP_TIME = 1000;
    private RobotState robotState;
    private RobotController robot;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
        this.robot = robotState.robotController;
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            if (serverHasNotYetStartedTheGame()) {
                continue;
            }

            System.out.println(String.format("backward=%s, forward=%s, left=%s, right=%s",
                    robot.isGoingBackward(),
                    robot.isGoingForward(),
                    robot.isGoingLeft(),
                    robot.isGoingRight()));

            sleep(LOOP_SLEEP_TIME);
        }
        robotState.latch.countDown();
    }

    @Override
    public void initialStatusFired(Status initial) {
        System.out.println(String.format("Status: initial=%s", initial));
        if (serverHasNotYetStartedTheGame()) {
            return;
        }
        robot.forward(Speed.MEDIUM, Speed.MEDIUM);
    }

    @Override
    public void newStatusFired(Status oldStatus, Status newStatus) {
        System.out.println(String.format("Status: old=%s, new=%s", oldStatus, newStatus));

        if (serverHasNotYetStartedTheGame()) {
            return;
        }

        Color oldColor = oldStatus.colors.get(0);
        Color newColor = newStatus.colors.get(0);

        if (newColor == Color.RED) {
            robot.stop();
        }

        if (newColor == Color.BLACK) {
            robot.backward();
        }

        if (oldColor == Color.WHITE && newColor == Color.BLUE) {
            robot.forward(Speed.SLOW, Speed.FASTEST);
        }

    }

    private boolean serverHasNotYetStartedTheGame() {
        return robotState.lastStatus == null || !robotState.lastStatus.isActive;
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Failed to sleep: " + e.getMessage());
        }
    }

}
