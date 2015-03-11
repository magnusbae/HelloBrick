package no.itera.lego;

import java.lang.reflect.Field;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;

public class Helpers {

  private static final int DEGREES_PER_CENTIMETER = 36;

  private RegulatedMotor motorRight;
  private RegulatedMotor motorLeft;
  private RegulatedMotor motorCanon;
  private EV3IRSensor irSensor;
  private EV3ColorSensor colorSensor;

  private SampleProvider rangeSampler;
  private float[] lastRange;

  private String[] colors;

  private enum Direction{
    FORWARD, BACKWARD;
  }

  public Helpers() {
    motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
    motorLeft = new EV3LargeRegulatedMotor(MotorPort.B);
    motorCanon = new EV3LargeRegulatedMotor(MotorPort.C);
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

  public RegulatedMotor getMotorCanon() {
    return motorCanon;
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

  public void forward(int cm){
    drive(cm, Direction.FORWARD);
  }

  public void backward(int cm){
    drive(cm, Direction.BACKWARD);
  }

  private void drive(int cm, Direction direction){
    if(direction == Direction.BACKWARD){
      cm *= -1;
    }
    motorLeft.rotate(cm * DEGREES_PER_CENTIMETER, true);
    motorRight.rotate(cm * DEGREES_PER_CENTIMETER);
  }
}
