package no.itera.lego.model;

import java.util.concurrent.CountDownLatch;

public class RobotState {
	public static final int PORT = 5678;
	public static final long DELAY = 10;
	public static final double JOYSTICK_DOWNSCALING_FACTOR = 8.5;
	public static final int DEADZONE = 6;
	public boolean shouldRun;
	public CountDownLatch latch;
	public boolean calibrated = false;
	public boolean connected = false;

	public RobotState() {
		shouldRun = true;
	}
}
