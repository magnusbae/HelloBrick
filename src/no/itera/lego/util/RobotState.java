package no.itera.lego.util;

import no.itera.lego.color.Color;

import java.util.concurrent.CountDownLatch;

public class RobotState {

    public static final int PORT = 8080;
    public static final String HOST = "10.0.1.3";
    public boolean shouldRun;
    public CountDownLatch latch;
    public String lastMessage;
    public Color lastColor = Color.UNDEFINED;
    public boolean webSocketOpen;
    public boolean webSocketConnecting;
    public final EV3Helper ev3Helper;

    public RobotState(EV3Helper ev3Helper) {
        this.ev3Helper = ev3Helper;
        shouldRun = true;
    }
}
