package no.itera.lego.util;

import lejos.robotics.RegulatedMotor;

public interface RobotController {

    RegulatedMotor getMotorRight();

    RegulatedMotor getMotorLeft();

    void playBeep();

    void forward();

    void rotateLeft();

    void rotateRight();

    void leftForward();

    void rightForward();

    void backward();

    void stop();

}
