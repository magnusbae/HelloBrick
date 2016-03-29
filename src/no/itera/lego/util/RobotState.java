package no.itera.lego.util;

import java.util.concurrent.CountDownLatch;

import no.itera.lego.color.Color;
import no.itera.lego.message.Status;

public class RobotState {

    public static final String HOST = "10.0.1.3";
    public static final int PORT = 3004;
    public boolean shouldRun;
    public CountDownLatch latch;
    public Status lastStatus;
    public Color lastColor = Color.UNDEFINED;
    public boolean webSocketOpen;
    public boolean webSocketConnecting;
    public final EV3Helper ev3Helper;

    public RobotState(EV3Helper ev3Helper) {
        this.ev3Helper = ev3Helper;
        shouldRun = true;
    }
}
