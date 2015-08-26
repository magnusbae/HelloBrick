package no.itera.lego.model;

import java.util.concurrent.CountDownLatch;

public class RobotState {

	public RobotState() {
		shouldRun = true;
	}

	public boolean shouldRun;
	public Direction direction;
	public CountDownLatch latch;
}
