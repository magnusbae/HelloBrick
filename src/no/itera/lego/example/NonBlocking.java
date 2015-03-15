package no.itera.lego.example;

import lejos.hardware.Button;
import lejos.robotics.RegulatedMotor;
import no.itera.lego.EV3Helper;

/**
 * Example of using the EV3 with non-blocking code
 * This changes the program logic when compared to driving given distances and then stopping to check sensors
 * Eg. Checking the sensors and altering the driving pattern based on the current sensor state
 */
public class NonBlocking {
  private final RegulatedMotor motorLeft;
  private final RegulatedMotor motorRight;
  private SampleSet lastSampleSet;
  private EV3Helper ev3Helper;


  public static void main(String[] args) {
    NonBlocking self = new NonBlocking();
    System.out.println("Startup complete.\nClick any button to drive");
    Button.waitForAnyPress();
    self.drive();
  }

  public NonBlocking() {
    ev3Helper = new EV3Helper(true);
    motorLeft = ev3Helper.getMotorLeft();
    motorRight = ev3Helper.getMotorRight();
    lastSampleSet = new SampleSet(ev3Helper);
  }


  /**
   * Drives forward until the color black is seen, then turns left until it is no longer registered.
   * Stops if distance sensor sees an object closer than 20~cm.
   */
  private void drive() {
    boolean run = true;
    boolean turning = false;

    ev3Helper.forward();

    while (run) {
      lastSampleSet.takeSamples();

      if (lastSampleSet.getLastDistance() < 20) { //stops when close to something
        ev3Helper.stop();
        run = false;
      } else if (lastSampleSet.getLastColor().equals("BLACK")) { //turns left while reading the color black
        motorLeft.stop(true); //immediate return
        turning = true;
      } else if (turning) { //No black color registered, driving in a straight line again.
        ev3Helper.forward();
        turning = false;
      }
    }
  }

}
