package no.itera.lego;

import lejos.hardware.Button;

public class Main {

  public static void main(String[] args) {
    EV3Helper helper = new EV3Helper();

    System.out.println("IteraBot reporting for duty");

    Button.waitForAnyPress();

//    helper.forward(13);
//    helper.backward(10);
//    helper.forward(10);
//    helper.backward(13);

    boolean run = true;
    while(run){
      helper.forward(13);
      helper.backward(10);
      helper.forward(10);
      helper.backward(13);
    }
  }
}
