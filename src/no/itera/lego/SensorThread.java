package no.itera.lego;

import static no.itera.lego.util.EV3Helper.getColorName;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
//import lejos.hardware.sensor.EV3ColorSensor;

import no.itera.lego.color.ColorSensor;
import no.itera.lego.util.RobotState;

public class SensorThread implements Runnable {

    private final ColorSensor colorSensor;
    private RobotState robotState;
    private List<SensorReceiver> eventListeners = new ArrayList<>();
    //    private EV3ColorSensor colorSensor;

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;

//        colorSensor = new EV3ColorSensor(SensorPort.S1);
        colorSensor = new ColorSensor(SensorPort.S1);
    }

    @Override
    public void run() {
        while(robotState.shouldRun) {
//            readColor();

            LCD.clear(0,0,1);
            LCD.drawString(colorSensor.readColor().name(), 0, 0);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
