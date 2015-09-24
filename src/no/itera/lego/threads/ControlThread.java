package no.itera.lego.threads;

import lejos.robotics.RegulatedMotor;
import no.itera.lego.model.RobotState;
import no.itera.lego.model.TwoAxisInputModel;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.LastState;

public class ControlThread implements Runnable {

	private final EV3Helper ev3Helper;
	private LastState lastState;
	private RobotState robotState;


	public ControlThread(RobotState robotState, LastState lastState, EV3Helper ev3Helper) {
		this.robotState = robotState;
		this.lastState = lastState;
		this.ev3Helper = ev3Helper;
	}


	@Override
	public void run() {
		System.out.println("Loaded");
		ev3Helper.playBeep();

		RegulatedMotor motorLeft = ev3Helper.getMotorLeft();
		RegulatedMotor motorRight = ev3Helper.getMotorRight();

		while (robotState.shouldRun) {
			TwoAxisInputModel inputModel = lastState.getLastInputModel();

			if (inputModel.isMotorLeftDirectionForwards()) {
				motorLeft.forward();
			} else {
				motorLeft.backward();
			}
			if (inputModel.isMotorRightDirectionForwards()) {
				motorRight.forward();
			} else {
				motorRight.backward();
			}

			motorLeft.setSpeed(inputModel.getMotorLeftSpeed());
			motorRight.setSpeed(inputModel.getMotorRightSpeed());

		}
		ev3Helper.stop();
		robotState.latch.countDown();
	}

}
