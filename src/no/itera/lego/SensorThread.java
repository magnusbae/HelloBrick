package no.itera.lego;


import java.util.ArrayList;
import java.util.List;

import lejos.hardware.port.SensorPort;

import no.itera.lego.color.ColorSensor;
import no.itera.lego.util.RobotState;

public class SensorThread implements Runnable {

    private final ColorSensor colorSensor;
    private RobotState robotState;
    private List<SensorReceiver> eventListeners = new ArrayList<>();
    //    private EV3ColorSensor colorSensor;

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;

        colorSensor = new ColorSensor(SensorPort.S4);
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            robotState.lastColor = colorSensor.readColor();
        }
        robotState.latch.countDown();
    }

    public void addEventListener(SensorReceiver eventListener) {
        eventListeners.add(eventListener);
    }

    public void removeEventListener(SensorReceiver eventListener) {
        eventListeners.remove(eventListener);
    }

//    private void readColor() {
//        String color = getColorName(colorSensor.getColorID());
//
//        if (color.equals(robotState.lastColor)) {
//            return;
//        }
//
//        for (SensorReceiver eventListener : eventListeners) {
//            eventListener.receiveColor(color);
//        }
//
//        robotState.lastColor = color;
//    }
}
