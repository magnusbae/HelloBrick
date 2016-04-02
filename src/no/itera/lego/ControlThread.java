package no.itera.lego;

import no.itera.lego.message.Status;
import no.itera.lego.util.*;

public class ControlThread implements Runnable, WebSocketListener {

    public static final int LOOP_SLEEP_TIME = 1000;

    private RobotState robotState;
    private RobotController robot;

    public ControlThread(RobotState robotState) {
        this.robotState = robotState;
        this.robot = robotState.robotController;
    }

    /**
     * !!! NOTE !!!
     * You are not allowed to remove the first if-statement in the main loop.
     * This check prevents the robot from moving before the start signal is given.
     *
     * You can choose to implement your logic within this run method (e.g. as a giant
     * switch-case, or with a bunch of different if statements), or you can
     * choose to implement all your logic within the event listeners below. You
     * can also implement a combination of the two.
     */
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

            blockExecutionFor(LOOP_SLEEP_TIME);
        }
        robotState.latch.countDown();
    }

    @Override
    public void initialStatusFired(Status initial) {
        robot.forward(Speed.FAST, Speed.FAST);
    }

    @Override
    public void newStatusFired(Status oldStatus, Status newStatus) {
        Position oldPosition = PositionHelper.currentPosition(oldStatus);
        Position newPosition = PositionHelper.currentPosition(newStatus);

        if (newPosition == Position.TARGET) {
            robot.stop();
        }

        if (newPosition == Position.OUT_OF_MAP) {
            backupAndTurnAround();
        }

        if (oldPosition == Position.LOWER_RIGHT && newPosition == Position.UPPER_RIGHT) {
            robot.forward(Speed.SLOW, Speed.FASTEST);
        }
    }

    private void backupAndTurnAround() {
        robot.backward();
        blockExecutionFor(1000);
        robot.rotateLeft();
        blockExecutionFor(1000);
        robot.forward(Speed.MEDIUM, Speed.MEDIUM);
    }

    private boolean serverHasNotYetStartedTheGame() {
        return robotState.lastStatus == null || !robotState.lastStatus.isActive;
    }

    /**
     * !!! NOTE !!!
     * Be careful to not over-use this function. If you block the execution for too long
     * at a time, you might end up queueing up your moves!
     *
     * This function sleeps the _CURRENT_ thread running, so it is dependant upon the
     * context of the function it is called form.
     *
     * If you call this from the run() method within this class, you will sleep the main
     * thread routine.
     *
     * However, if you call this function from within one of the event listeners, you will
     * block the execution of the separate thread that are executing these functions.
     */
    private static void blockExecutionFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Failed to sleep thread: " + e.getMessage());
        }
    }

}
