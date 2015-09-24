package no.itera.lego.util;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;


/**
 * Helper functions for basic operation of our pre-configured robot.
 * This class provides useful wrappers around Lejos code and examples for you to build on.
 * This code is by design not optimized. You will have to figure out where to improve this code, and how.
 * <p/>
 * For the complete Lejos API take a look at:
 * http://www.lejos.org/ev3/docs/
 */
public class EV3Helper {

	public static final int MAX_MOTOR_SPEED = 900;

	private RegulatedMotor motorRight;
	private RegulatedMotor motorLeft;


	/**
	 * Instantiates a new helper class and optionally calibrates the cannon.
	 * Robot is ready for operation after completion.
	 */
	public EV3Helper() {
		motorRight = new EV3LargeRegulatedMotor(MotorPort.D);
		motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
	}

	public RegulatedMotor getMotorRight() {
		return motorRight;
	}

	public RegulatedMotor getMotorLeft() {
		return motorLeft;
	}


	/**
	 * Plays a beep with the speaker.
	 */
	public void playBeep() {
		Sound.beep();
	}


	/**
	 * Stops both motors immediately
	 */
	public void stop() {
		motorLeft.stop(true);
		motorRight.stop(true);
	}


}
