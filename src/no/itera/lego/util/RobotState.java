package no.itera.lego.util;

import java.util.concurrent.CountDownLatch;

public class RobotState {
	public static final int PORT = 8080;
	public static final String HOST = "10.0.1.3";
	public boolean shouldRun;
	public CountDownLatch latch;
	public String lastMessage;
	public boolean webSocketOpen;
	public boolean webSocketConnecting;

	public RobotState() {
		shouldRun = true;
	}
}
