package no.itera.lego.color;

import lejos.hardware.port.SensorPort;
import no.itera.lego.util.RobotState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.format;

public class ColorLoggerThread implements Runnable {

    private final RobotState robotState;

    public ColorLoggerThread(RobotState robotState){
        this.robotState = robotState;
    }

    @Override
    public void run() {
        ColorSensor colorSensor = new ColorSensor(SensorPort.S1);
        long currentTimeMillis = System.currentTimeMillis();
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("colors-" + currentTimeMillis + ".csv", true)))) {
            while(robotState.shouldRun){
                int[] sample = colorSensor.readSensorRgb();
                out.println(format("%d;%d;%d", sample[0], sample[1], sample[2]));
            }
        }catch (IOException e) {
            System.out.println(e);
            System.out.println("Terminating " + this.getClass().getName());
        }

        robotState.latch.countDown();


    }
}
