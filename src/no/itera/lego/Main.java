package no.itera.lego;

import lejos.hardware.Button;

/**
 * Robot example using blocking code examples
 * For an example using non-blocking code, see {@link no.itera.lego.example.NonBlocking}
 */
public class Main {
  private static EV3Helper helper;

  public static void main(String[] args) {
    helper = new EV3Helper();

    System.out.println("IteraBot reporting for duty\nClick any button to start robot");

    Button.waitForAnyPress();

    /*
     * Drives forward 30cm, rotates left 90 degrees, drives forward 30cm.
     * Stops if the distance sensor registers an object closer than 20cm. 
     */
    while(stopIfDistanceLessThan(20)){
      helper.forward(30);
      if(stopIfDistanceLessThan(20)){
        break;
      }
      helper.turnLeft(90);
    }
  }

  private static boolean stopIfDistanceLessThan(float distance) {
    if(helper.getDistance() < distance){
      return false;
    }
    return true;
  }
}
