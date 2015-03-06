package no.itera.lego;

import lejos.hardware.motor.Motor;

public class HelloWorld {
  public static void main (String[] args) {
    System.out.println("Hello World");

    System.out.println("Running motors");

    Motor.A.setSpeed(720);// 2 RPM
    Motor.B.setSpeed(720);
    Motor.A.forward();
    Motor.B.forward();
    

    try{
    	Thread.sleep(1200);	
    }catch(Exception e){
    	//--
    }


    Motor.A.stop();
    Motor.B.stop();
    
  }
}
