package no.itera.lego;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.*;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

public class HelloWorld {
  public static void main (String[] args) {
    System.out.println("LoopBot reporting for duty");

    long start = System.currentTimeMillis();
        

    EV3IRSensor rangeSensor = new EV3IRSensor(SensorPort.S1);
    SampleProvider rangeSampler = rangeSensor.getDistanceMode();
    float[] lastRange = new float[rangeSampler.sampleSize()];

    int count = 0;
    boolean run = true;


    long stop = System.currentTimeMillis();

    System.out.println("Setup took " + (stop - start) + " ms");

    Motor.A.setSpeed(720);
    Motor.B.setSpeed(720);
    Motor.A.forward();
    Motor.B.forward();

    while(run){
        start = System.currentTimeMillis();
        rangeSampler.fetchSample(lastRange, 0);
        stop = System.currentTimeMillis();

        // System.out.println("Sampling took " + (stop - start) + " ms");

        if(lastRange[0] < 40 && lastRange[0] > 5){
            if(count % 3 != 0){
                count++;
                Motor.A.setSpeed(0);
            }else{
                count = 0;
                Motor.B.setSpeed(0);
            }
            try{
                Thread.sleep(500);            
            }catch (Exception e){
                run = false;
                System.out.println("Stopping due to exception");
            }
            Motor.A.setSpeed(720);
            Motor.B.setSpeed(720);
        }else if(lastRange[0] <= 5){
            run = false;
        }
    }

    Motor.A.stop();
    Motor.B.stop();

    try{
        Thread.sleep(1200);
    }catch(Exception e){}
  }
}
