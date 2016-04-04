package no.itera.lego.robot;

import no.itera.lego.websocket.WebSocketListener;
import no.itera.lego.message.Status;
import no.itera.lego.util.*;

public class Robot implements WebSocketListener {

    public static final int LOOP_SLEEP_TIME = 1000;

    private RobotState state;
    private RobotController robot;

    public Robot(RobotState robotState) {
        this.state = robotState;
        this.robot = robotState.robotController;
    }

    /**
     * !!! NOTE !!!
     * This method gets called from the ControlThread class, and acts as the main
     * loop of the robot. This method will be called over again immediately after
     * it returns, until the round is over.
     *
     * You can choose to implement your logic within this loop method (e.g. as a giant
     * switch-case, or with a bunch of different if statements), or you can
     * choose to implement all your logic within the event listeners below. You
     * can also implement a combination of the two.
     */
    public void loop() {
        System.out.println(String.format("backward=%s, forward=%s, left=%s, right=%s",
                robot.isGoingBackward(),
                robot.isGoingForward(),
                robot.isGoingLeft(),
                robot.isGoingRight()));

        // EXAMPLE: This function gets executed once every second, and ensures that
        // the robot starts moving forward if it is not already on the target
        Position position = PositionHelper.currentPosition(state.lastStatus);
        if (position != Position.TARGET && !robotIsMoving()) {
            robot.forward();
        }

        // EXAMPLE: turn around if there's someone very close ahead of you
        if (state.lastDistance < 12) {
            backupAndTurnAround();
        }

        blockExecutionFor(LOOP_SLEEP_TIME);
    }

    @Override
    public void initialStatusFired(Status initial) {
        robot.forward(Speed.FAST, Speed.FAST);
    }

    @Override
    public void newStatusFired(Status oldStatus, Status newStatus) {
        Position oldPosition = PositionHelper.currentPosition(oldStatus);
        Position newPosition = PositionHelper.currentPosition(newStatus);

        // EXAMPLE: make a decision based only upon the new position
        switch (newPosition) {
            case TARGET:
                robot.stop();
                return;
            case OUT_OF_MAP:
                backupAndTurnAround();
                return;
        }

        // EXAMPLE: if the robot is in the upper right corner of the map, _after_
        // having been in the lower left corner _and_ it is moving forward (i.e. not
        // backing up), we make an educated guess at going left, because the TARGET
        // is probably going to be in that direction.
        if (oldPosition == Position.LOWER_RIGHT &&
                newPosition == Position.UPPER_RIGHT &&
                robot.isGoingForward()) {
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

    private boolean robotIsMoving() {
        return robot.isGoingBackward() ||
                robot.isGoingForward() ||
                robot.isGoingLeft() ||
                robot.isGoingRight();
    }

    /**
     * !!! NOTE !!!
     * Be careful to not over-use this function. If you block the execution for too long
     * at a time, you might end up queueing up your moves!
     *
     * This function sleeps the _CURRENT_ thread running, so it is dependant upon the
     * context of the function it is called form.
     *
     * If you call this from the loop() method within this class, you will sleep the main
     * thread routine.
     *
     * However, if you call this function from within one of the event listeners, you will
     * block the execution of the separate thread that are executing these functions.
     */
     public static void blockExecutionFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Failed to sleep thread: " + e.getMessage());
        }
    }

}
