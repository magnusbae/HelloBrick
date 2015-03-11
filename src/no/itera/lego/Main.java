package no.itera.lego;

import lejos.hardware.Button;

public class Main {

  public static void main(String[] args) {
    EV3Helper helper = new EV3Helper();

    System.out.println("IteraBot reporting for duty");

    Button.waitForAnyPress();

    helper.forward(70);
    helper.turnLeft(90);
    helper.forward(20);
    helper.turnRight(90);
    helper.forward(40);
    helper.turnRight(90);
    helper.forward(20);
    helper.turnLeft(90);
    helper.forward(40);

    helper.fireCannon();
  }
}
