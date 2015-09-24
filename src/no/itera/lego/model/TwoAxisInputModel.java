package no.itera.lego.model;

import static no.itera.lego.util.EV3Helper.MAX_MOTOR_SPEED;


public class TwoAxisInputModel {

	private static final int SPEED_FACTOR = MAX_MOTOR_SPEED / 7;

	private final int motorLeft;
	private final int motorRight;

	public TwoAxisInputModel(boolean forward, boolean left, int steering, int speed) {
		int nominalSpeed = speed * SPEED_FACTOR * (forward ? 1 : -1);
		int steeringSpeed = nominalSpeed - (steering * (SPEED_FACTOR + 100));
		if(speed == 0 && steering != 0){
			nominalSpeed = steeringSpeed * -1;
		}
		motorLeft = left ? steeringSpeed : nominalSpeed;
		motorRight = !left ? steeringSpeed : nominalSpeed;
	}

	public int getMotorLeftSpeed() {
		return Math.abs(motorLeft);
	}

	public int getMotorRightSpeed() {
		return Math.abs(motorRight);
	}

	public boolean isMotorLeftDirectionForwards(){
		return motorLeft > -1;
	}

	public boolean isMotorRightDirectionForwards(){
		return motorRight > -1;
	}
}
