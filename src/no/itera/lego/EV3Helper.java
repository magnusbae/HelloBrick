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

public class EV3Helper {

  private static final int DISTANCE_DEGREES_FACTOR = 36;
  private static final double ROTATE_DEGREES_FACTOR = 11.3;
  private static final int CANON_MOTOR_ROUND_DEGREES = 1080;

  private RegulatedMotor motorRight;
  private RegulatedMotor motorLeft;
  private RegulatedMotor motorCannon;
  private EV3IRSensor irSensor;
  private EV3ColorSensor colorSensor;

  private SampleProvider rangeSampler;
  private float[] lastRange;

  private String[] colors;

  private enum Direction {
    FORWARD, BACKWARD;
  }

  public EV3Helper() {
    this(false);
  }

  public EV3Helper(boolean skipMotorCannonCalibration) {
    motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
    motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
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

  public float getDistance() {
    rangeSampler.fetchSample(lastRange, 0);
    return lastRange[0];
  }

  public String getColorName() {
    int color = colorSensor.getColorID();
    if (color < 0 || color >= colors.length) {
      return "NO_COLOR";
    }
    return colors[color];
  }

  private String[] getColors() {
    Field[] names = Color.class.getFields();
    String[] list = new String[names.length];
    for (int i = 0; i < list.length; i++) {
      list[i] = names[i].getName();
    }
    return list;
  }

  public void forward(int cm) {
    drive(cm, Direction.FORWARD);
  }

  public void backward(int cm) {
    drive(cm, Direction.BACKWARD);
  }

  private void drive(int cm, Direction direction) {
    if (direction == Direction.BACKWARD) {
      cm *= -1;
    }
    motorLeft.rotate(cm * DISTANCE_DEGREES_FACTOR, true);
    motorRight.rotate(cm * DISTANCE_DEGREES_FACTOR);
  }

  public void turnLeft(int degrees) {
    motorLeft.stop(true);
    motorRight.rotate((int) (ROTATE_DEGREES_FACTOR * degrees));
  }

  public void turnRight(int degrees) {
    motorRight.stop(true);
    motorLeft.rotate((int) (ROTATE_DEGREES_FACTOR * degrees));
  }

  public void fireCannon() {
    motorCannon.rotate(CANON_MOTOR_ROUND_DEGREES);
  }

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
