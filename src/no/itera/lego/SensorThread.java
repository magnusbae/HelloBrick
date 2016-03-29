package no.itera.lego;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;

import no.itera.lego.color.Color;
import no.itera.lego.color.ColorSensor;
import no.itera.lego.util.RobotState;

public class SensorThread implements Runnable {

    private final ColorSensor colorSensor;
    private RobotState robotState;
    private List<SensorReceiver> eventListeners = new ArrayList<>();

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;

        colorSensor = new ColorSensor(SensorPort.S1);
    }

    @Override
    public void run() {
        while(robotState.shouldRun) {
            readColor();

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

   private void readColor() {
       Color color = colorSensor.readColor();

       if (color.equals(robotState.lastColor)) {
           return;
       }

       LCD.clear(0,0,1);
       LCD.drawString(color.name(), 0, 0);

       for (SensorReceiver eventListener : eventListeners) {
           eventListener.receiveColor(color);
       }

       robotState.lastColor = color;
   }
}
