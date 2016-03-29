package no.itera.lego.util;

import java.util.concurrent.CountDownLatch;

import no.itera.lego.color.Color;

public class RobotState {

    public static final String HOST = "10.0.1.3";
    public static final int PORT = 3004;
    public boolean shouldRun;
    public CountDownLatch latch;
    public String lastMessage;
    public Color lastColor;
    public boolean webSocketOpen;
    public boolean webSocketConnecting;

    public RobotState() {
        shouldRun = true;
    }
}
