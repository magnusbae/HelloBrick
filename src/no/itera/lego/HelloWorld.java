package no.itera.lego;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class HelloWorld {

  public static final int SPEED = 720;

  public static void main(String[] args) {
    System.out.println("LoopBot reporting for duty");

    long start = System.currentTimeMillis();


    EV3IRSensor rangeSensor = new EV3IRSensor(SensorPort.S1);
    SampleProvider rangeSampler = rangeSensor.getDistanceMode();
    float[] lastRange = new float[rangeSampler.sampleSize()];

    int count = 1;
    boolean run = true;


    long stop = System.currentTimeMillis();

    System.out.println("Setup took " + (stop - start) + " ms");

    Motor.A.setSpeed(SPEED);
    Motor.B.setSpeed(SPEED);
    Motor.A.forward();
    Motor.B.forward();

    while (run) {
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      start = System.currentTimeMillis();
      rangeSampler.fetchSample(lastRange, 0);
//      stop = System.currentTimeMillis();

      // System.out.println("Sampling took " + (stop - start) + " ms");

      if (lastRange[0] <= 5) {
        run = false;
      } else if (lastRange[0]
          < 40) {
        if (count % 4 != 0) {
          count++;
          Motor.A.stop();
        } else {
          count = 1;
          Motor.B.stop();
        }
        try {
          Thread.sleep(500);
        } catch (Exception e) {
          run = false;
          System.out.println("Stopping due to exception");
        }
        Motor.A.forward();
        Motor.B.forward();
      }
    }

    Motor.A.stop(true);
    Motor.B.stop();

    try {
      Thread.sleep(1200);
    } catch (Exception e) {
    }
  }
}
