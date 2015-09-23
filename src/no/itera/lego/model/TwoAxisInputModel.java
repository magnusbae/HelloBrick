package no.itera.lego.model;

public class TwoAxisInputModel {

	private final boolean forward;
	private final boolean turn;
	private final boolean left;
	private final int motorSpeed;

	public TwoAxisInputModel(boolean forward, boolean turn, boolean left, int motorSpeed) {
		this.forward = forward;
		this.turn = turn;
		this.left = left;
		this.motorSpeed = motorSpeed;
	}

	public boolean isForward() {
		return forward;
	}

	public boolean isTurn() {
		return turn;
	}

	public boolean isLeft() {
		return left;
	}

	public int getSpeed() {
		return motorSpeed;
	}
}
