package no.itera.lego.threads;

import no.itera.lego.util.EV3Helper;
import no.itera.lego.model.RobotState;

public class MotorThread implements Runnable {

	private RobotState robotState;
	private EV3Helper ev3Helper;

	public MotorThread(RobotState robotState, EV3Helper ev3Helper) {
		this.robotState = robotState;
		this.ev3Helper = ev3Helper;
	}

	@Override
	public void run() {
		while (robotState.shouldRun){
			if(robotState.direction != null) {
				switch (robotState.direction) {
					case FORWARD:
						ev3Helper.forward();
						break;
					case LEFT_FORWARD:
						ev3Helper.leftForward();
						break;
					case LEFT:
						ev3Helper.rotateLeft();
						break;
					case RIGHT:
						ev3Helper.rotateRight();
						break;
					case RIGHT_FORWARD:
						ev3Helper.rightForward();
						break;
					case BACKWARDS:
						ev3Helper.backward();
						break;
					case LEFT_BACKWARD:
						break;
					case RIGHT_BACKWARD:
						break;
					case STOP:
						ev3Helper.stop();
						break;
					default:
						ev3Helper.playBeep();
				}
			}
		}
		ev3Helper.stop();
		robotState.latch.countDown();
	}
}
