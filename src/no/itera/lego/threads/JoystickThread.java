package no.itera.lego.threads;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import no.itera.lego.model.RobotState;
import no.itera.lego.util.BinaryHelper;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.LastState;

import static no.itera.lego.model.RobotState.DEADZONE;
import static no.itera.lego.model.RobotState.JOYSTICK_DOWNSCALING_FACTOR;

public class JoystickThread implements Runnable {

	private RobotState robotState;
	private EV3Helper ev3Helper;
	private LastState lastState;

	public JoystickThread(RobotState robotState, EV3Helper ev3Helper, LastState lastState) {
		this.robotState = robotState;
		this.ev3Helper = ev3Helper;
		this.lastState = lastState;
	}

	@Override
	public void run() {
		try {

			//float motors (sorry, no force feedback yet)
			ev3Helper.getMotorLeft().flt(true);
			ev3Helper.getMotorRight().flt(true);

			while (!robotState.connected) {
				//wait for connection to other robot
			}

			//we are ready;
			System.out.println("Center stick and press button");
			ev3Helper.playBeep();
			EV3TouchSensor trigger = new EV3TouchSensor(SensorPort.S1);
			float[] triggerState = new float[1];
			do {
				trigger.fetchSample(triggerState, 0);
			} while (triggerState[0] != 1);
			ev3Helper.getMotorLeft().resetTachoCount();
			ev3Helper.getMotorRight().resetTachoCount();

			robotState.calibrated = true;

			System.out.println("Fight!");
			ev3Helper.playBeep();

			do {
				int tachoY = ev3Helper.getMotorLeft().getTachoCount();
				int tachoX = ev3Helper.getMotorRight().getTachoCount();

				boolean forward = tachoY < 0 ? true : false;
				boolean left = tachoX < 0 ? true : false;

				int turn = Math.abs(tachoX) < DEADZONE ? 0 : (int) (tachoX / JOYSTICK_DOWNSCALING_FACTOR);
				int speed = Math.abs(tachoY) < DEADZONE ? 0 : (int) (tachoY / JOYSTICK_DOWNSCALING_FACTOR);

				if (speed == 0) {
					forward = true;
				}
				lastState.setLastByte(BinaryHelper.encodeByte(forward, left, turn, speed));

			} while (robotState.shouldRun);


		} catch (Exception e) {
			robotState.shouldRun = false;
			e.printStackTrace();
		}
		robotState.latch.countDown();

	}
}
