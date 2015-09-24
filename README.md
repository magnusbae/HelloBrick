# EV3 to EV3 Remote

This [leJOS][lejos] application allows one EV3 to control another EV3 via bluetooth (or other networking).
To use Bluetooth PAN you need LeJOS post the 0.9.0-beta. At the time of writing (09/2015) you need to pull the LeJOS git-repository.
The repository contains a snapshot that can be used to install lejos on an SD card, and pre-built libraries that can be used for building.

This project requires two EV3 boxes, one built as a robot, one as a joystick.

[lejos]: http://www.lejos.org/ev3/docs/

## Requirements

You need to have [Java Development Kit 7][jdk7] and [Ant][ant]installed on
your system.

[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[ant]: http://ant.apache.org/

If you want to use IntelliJ, you may follow the guide in `README-INTELLIJ.md`.

##Building

It is recommended to build using Apache Ant instead of Eclipse, you may follow the guide in `README-ANT.md`.


## Motors and Sensors connections

When seeing the robot from the back, the leftmost motor should be connected to port A, and the right motor should be connected to port D.

On the joystick, connect Y-axis to port A, X to port D. Touch sensor to S1.

This is defined in src/no/itera/lego/EV3Helper.java.
