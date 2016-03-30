package no.itera.lego.util;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

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

    private static final int DEFAULT_MOTOR_SPEED = 900;
    private static final int SLOW_MOTOR_SPEED = 300;

    private RegulatedMotor motorRight;
    private RegulatedMotor motorLeft;

    private enum Direction {
        FORWARD, BACKWARD
    }

    /**
     * Instantiates a new helper class.
     * Robot is ready for operation after completion.
     */
    public EV3Helper() {
        motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.D);

        //Sets default motor speed for driving motors
        motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
        motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);
    }

    public RegulatedMotor getMotorRight() {
        return motorRight;
    }

    public RegulatedMotor getMotorLeft() {
        return motorLeft;
    }

    /**
     * Plays a beep with the speaker.
     */
    public void playBeep() {
        Sound.beep();
    }

    /**
     * Drives forward until stop is called.
     * Returns immediately
     */
    public void forward(){
        motorLeft.forward();
        motorRight.forward();
        motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);
        motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
    }

    public void rotateLeft() {
        motorLeft.backward();
        motorRight.forward();
        motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);
        motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
    }

    public void rotateRight(){
        motorLeft.forward();
        motorRight.backward();
        motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);
        motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
    }

    public void leftForward() {
        motorLeft.forward();
        motorRight.forward();
        motorLeft.setSpeed(SLOW_MOTOR_SPEED);
        motorRight.setSpeed(DEFAULT_MOTOR_SPEED);
    }

    public void rightForward() {
        motorLeft.forward();
        motorRight.forward();
        motorLeft.setSpeed(DEFAULT_MOTOR_SPEED);
        motorRight.setSpeed(SLOW_MOTOR_SPEED);
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

}
