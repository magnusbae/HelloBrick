package no.itera.lego;

import java.lang.reflect.Field;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


/**
 * Helper functions for basic operation of our pre-configured robot.
 * This class provides useful wrappers around Lejos code and examples for you to build on.
 * This code is by design not optimized. You will have to figure out where to improve this code, and how.
 *
 * For the complete Lejos API take a look at:
 * http://www.lejos.org/ev3/docs/
 */
public class EV3Helper {

  private static final int DISTANCE_DEGREES_FACTOR = 36; //How many degrees the motors have to rotate for the robot to travel 1cm
  private static final double ROTATE_DEGREES_FACTOR = 11.3; //How many degrees one motor has to rotate for the robot to rotate 1 degree (while the other motor is stopped)
  private static final int CANNON_MOTOR_ROUND_DEGREES = 1080; //How many degrees of rotation needed for the cannon to fire one bullet.

  private static final int DEFAULT_MOTOR_SPEED = 350;

  private RegulatedMotor motorRight;
  private RegulatedMotor motorLeft;
  private RegulatedMotor motorCannon;
  private EV3IRSensor irSensor;
  private EV3ColorSensor colorSensor;

  private SampleProvider rangeSampler;
  private float[] lastRange;

  private String[] colors;

  private enum Direction {
    FORWARD, BACKWARD
  }

  /**
   * Instantiates a new helper class and calibrates the cannon.
   * Robot is ready for operation after completion.
   * If you're not going to use the cannon, you can skip calibration using
   * {@link #EV3Helper#EV3Helper(boolean)}
   */
  public EV3Helper() {
    this(false);
  }

  /**
   * Instantiates a new helper class and optionally calibrates the cannon.
   * Robot is ready for operation after completion.
   * @param skipMotorCannonCalibration Set to true if you don't want to calibrate the cannon
   */
  public EV3Helper(boolean skipMotorCannonCalibration) {
    motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
    motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);

    //Sets default motor speed for driving motors
    motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
    motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);

    motorCannon = instantiateMotorCannon(skipMotorCannonCalibration);
    irSensor = new EV3IRSensor(SensorPort.S1);
    colorSensor = new EV3ColorSensor(SensorPort.S4);

    rangeSampler = irSensor.getDistanceMode();
    lastRange = new float[rangeSampler.sampleSize()];

    colors = getColors();
  }

  public RegulatedMotor getMotorRight() {
    return motorRight;
  }

  public RegulatedMotor getMotorLeft() {
    return motorLeft;
  }

  public RegulatedMotor getMotorCannon() {
    return motorCannon;
  }

  public EV3IRSensor getIrSensor() {
    return irSensor;
  }

  public EV3ColorSensor getColorSensor() {
    return colorSensor;
  }


  /**
   * Fetches a distance sample from the EV3 infrared sensor.
   * Close to centimeters.
   * @return Distance from sensor in something that's quite close to centimeters
   */
  public float getDistance() {
    rangeSampler.fetchSample(lastRange, 0);
    return lastRange[0];
  }


  /**
   * Fetches a color sample from the EV3 color sensor.
   * @return name of measured color
   */
  public String getColorName() {
    int color = colorSensor.getColorID();
    if (color < 0 || color >= colors.length) {
      return "NO_COLOR";
    }
    return colors[color];
  }


  /**
   * Get a list of all supported colors for the Mindstorms EV3 Color Sensor
   * @return
   */
  private String[] getColors() {
    Field[] names = Color.class.getFields();
    String[] list = new String[names.length];
    for (int i = 0; i < list.length; i++) {
      list[i] = names[i].getName();
    }
    return list;
  }


  /**
   * Drives forward until stop is called.
   * Returns immediately
   */
  public void forward(){
    motorLeft.forward();
    motorRight.forward();
  }


  /**
   * Drives backward until stop is called.
   * Returns immediately
   */
  public void backward(){
    motorLeft.backward();
    motorRight.backward();
  }


  /**
   * Stops both motors immediately
   */
  public void stop(){
    motorLeft.stop(true);
    motorRight.stop(true);
  }


  /**
   * Drives forward the given centimeters and stops when complete
   * @param cm
   */
  public void forward(int cm) {
    drive(cm, Direction.FORWARD);
  }


  /**
   * Drives forward the given centimeters and stops when complete
   * @param cm
   */
  public void backward(int cm) {
    drive(cm, Direction.BACKWARD);
  }


  /**
   * Drives in the given direction (forward/backward)
   * @param cm How many centimeters to drive
   * @param direction Which direction to drive in
   */
  private void drive(int cm, Direction direction) {
    if (direction == Direction.BACKWARD) {
      cm *= -1;
    }
    motorLeft.rotate(cm * DISTANCE_DEGREES_FACTOR, true); //true = immediate return
    motorRight.rotate(cm * DISTANCE_DEGREES_FACTOR); //Waits for method to complete
  }


  /**
   * Turns left the given number of degrees
   * @param degrees
   */
  public void turnLeft(int degrees) {
    motorLeft.stop(true);
    motorRight.rotate((int) (ROTATE_DEGREES_FACTOR * degrees));
  }


  /**
   * Turns right the given number of degrees
   * @param degrees
   */
  public void turnRight(int degrees) {
    motorRight.stop(true);
    motorLeft.rotate((int) (ROTATE_DEGREES_FACTOR * degrees));
  }


  /**
   * Fire one bullet from the cannon.
   * This method blocks until completed
   */
  public void fireCannon() {
    motorCannon.rotate(CANNON_MOTOR_ROUND_DEGREES);
  }


  /**
   * Instantiates the cannon-motor, optionally skips calibration
   * @param skipMotorCannonCalibration
   * @return
   */
  private EV3MediumRegulatedMotor instantiateMotorCannon(
      boolean skipMotorCannonCalibration) {
    if (!skipMotorCannonCalibration) {
      calibrateMotorCannon();
    }
    EV3MediumRegulatedMotor motor = new EV3MediumRegulatedMotor(MotorPort.C);
    if (!skipMotorCannonCalibration) {
      motor.rotate(-360);
    }
    return motor;
  }


  /**
   * Calibrates the cannon and puts it in a read-to-fire state.
   */
  private void calibrateMotorCannon() {
    UnregulatedMotor motor = new UnregulatedMotor(MotorPort.C);
    motor.setPower(50);

    int maxIterations = 40;
    int lastTachoCount = 0;
    while (maxIterations-- > 0) {
      Delay.msDelay(100);
      int newTachoCount = motor.getTachoCount();
      if (newTachoCount == lastTachoCount) {
        break;
      }
      lastTachoCount = newTachoCount;
    }

    motor.close();
  }
}
