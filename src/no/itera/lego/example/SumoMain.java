package no.itera.lego.example;

import lejos.hardware.Button;
import lejos.robotics.RegulatedMotor;
import no.itera.lego.EV3Helper;
import no.itera.lego.SampleSet;

public class SumoMain {
	private final RegulatedMotor motorLeft;
	private final RegulatedMotor motorRight;
	private SampleSet lastSampleSet;
	private EV3Helper ev3Helper;

	public SumoMain() {
		ev3Helper = new EV3Helper(true);
		motorLeft = ev3Helper.getMotorLeft();
		motorRight = ev3Helper.getMotorRight();
		lastSampleSet = new SampleSet(ev3Helper);
	}

	/**
	 * Drives forward until the color white is seen, then turns left until it is
	 * no longer registered. Stops if distance sensor sees an object closer than
	 * 20~cm.
	 */
	private void drive() {
		boolean run = true;
		boolean turning = false;

		ev3Helper.forward();

		while (run) {
			lastSampleSet.takeSamples();

			if (lastSampleSet.getLastDistance() < 20) { // stops when close to something
				ev3Helper.stop();
				run = false;
			} else if (lastSampleSet.getLastColor().equals("WHITE")) { // turns left while reading the color white
				motorLeft.stop(true); // immediate return
				turning = true;
			} else if (turning) { // No black color registered, driving in a straight line again.
				ev3Helper.forward();
				turning = false;
			}
		}
	}

	public static void main(String[] args) {
		SumoMain sumo = new SumoMain();
		System.out.println("startup complete.\nClick any button to drive");
		Button.waitForAnyPress();
		try {
			Thread.sleep(3000); // wait 3 seconds
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumo.drive();
	}

}
