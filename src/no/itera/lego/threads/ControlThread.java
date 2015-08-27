package no.itera.lego.threads;

import lejos.utility.Stopwatch;
import no.itera.lego.model.Direction;
import no.itera.lego.model.RobotState;
import no.itera.lego.util.SampleSet;

import static no.itera.lego.model.Direction.*;

public class ControlThread implements Runnable {

	public static final int MIN_REVERSE_TIME = 300;
	private static final int REVERSE_TIME = 1000;
	private static final int MAX_TURN_TIME = 3000;
	private static final int MAX_TIMES_RIGHT = 2;
	private RobotState robotState;
	private SampleSet sampleSet;
	private Stopwatch reverseTimer = null;
	private Stopwatch turnTimer = null;
	private int timesRight = 0;


	public ControlThread(RobotState robotState, SampleSet sampleSet) {
		this.robotState = robotState;
		this.sampleSet = sampleSet;
	}


	@Override
	public void run() {
		robotState.direction = FORWARD;
		while (robotState.shouldRun) {

//			NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE and BROWN.
			if (sampleSet.getLastColor() != null && sampleSet.getLastColor().equals("WHITE")) {
				if (reverseTimer == null) {
					reverseTimer = new Stopwatch();
					robotState.direction = BACKWARDS;
				} else if (reverseTimer.elapsed() > REVERSE_TIME) {
					robotState.direction = RIGHT;
				} else if (reverseTimer.elapsed() > MIN_REVERSE_TIME) {
					robotState.direction = FORWARD;
					reverseTimer = null;
				}
			} else {
				if (sampleSet.getLastDistance() < 50) {
					robotState.direction = FORWARD;
					reverseTimer = null;
				} else if (doneTurningAndReversing() && directionIsNotForwards() && turnTimer == null) {
					turnTimer = new Stopwatch();
					reverseTimer = null;
					if (timesRight < MAX_TIMES_RIGHT) {
						robotState.direction = RIGHT_FORWARD;
						timesRight++;
					} else {
						robotState.direction = LEFT_FORWARD;
						timesRight = 0;
					}
				} else if (turnTimer != null && turnTimer.elapsed() > MAX_TURN_TIME) {
					turnTimer = null;
					robotState.direction = FORWARD;
				}
			}
		}
		robotState.latch.countDown();
	}

	private boolean doneTurningAndReversing() {
		return (reverseTimer != null && reverseTimer.elapsed() > (REVERSE_TIME * 2)) || reverseTimer == null;
	}

	private boolean directionIsNotForwards() {
		Direction d = robotState.direction;
		return d != FORWARD && d != LEFT_FORWARD && d != RIGHT_FORWARD;
	}
}
