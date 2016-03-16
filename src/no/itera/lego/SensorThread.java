package no.itera.lego;

import java.util.ArrayList;
import java.util.List;

import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.RobotState;

public class SensorThread implements Runnable {

    private RobotState robotState;
    private List<SensorReceiver> eventListeners = new ArrayList<>();

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;
    }

    @Override
    public void run() {
        while(robotState.shouldRun) {
        }
        robotState.latch.countDown();
    }

    public void addEventListener(SensorReceiver eventListener) {
        eventListeners.add(eventListener);
    }

    public void removeEventListener(SensorReceiver eventListener) {
        eventListeners.remove(eventListener);
    }
}
