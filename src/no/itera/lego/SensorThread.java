package no.itera.lego;

import lejos.hardware.port.SensorPort;

import no.itera.lego.color.Color;
import no.itera.lego.color.ColorSensor;
import no.itera.lego.util.RobotState;

public class SensorThread implements Runnable {

    private final ColorSensor colorSensor;
    private RobotState robotState;

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;

        colorSensor = new ColorSensor(SensorPort.S4);
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            readColor();
        }
        robotState.latch.countDown();
    }

   private void readColor() {
       Color color = colorSensor.readColor();

       if (color == robotState.lastColor || color == Color.UNDEFINED) {
           return;
       }

       System.out.println("Read color: " + color);

       robotState.lastColor = color;
   }
}
