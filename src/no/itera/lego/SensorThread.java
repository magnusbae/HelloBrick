package no.itera.lego;

import lejos.hardware.port.SensorPort;
import no.itera.lego.color.Color;
import no.itera.lego.color.ColorSensor;
import no.itera.lego.robot.RobotState;
import no.itera.lego.util.DistanceSensor;

public class SensorThread implements Runnable {

    private final ColorSensor colorSensor;
    private final DistanceSensor distanceSensor;
    private RobotState robotState;

    public SensorThread(RobotState robotState) {
        this.robotState = robotState;

        colorSensor = new ColorSensor(SensorPort.S4);
        distanceSensor = new DistanceSensor(SensorPort.S1);
    }

    @Override
    public void run() {
        while (robotState.shouldRun) {
            readColor();
            robotState.lastDistance = distanceSensor.readDistance();
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
