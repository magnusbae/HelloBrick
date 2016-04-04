# 'Capture the Flag' base robot

This code contains the functions needed for basic operation. It is your goal to improve on this code to
create the best robots for taking points from your opponent, and stopping them in taking points from you!

For game rules - see [RULES.md][rules].

[rules]: RULES.md

## Do's
* Test color detection early! Your robots' color sensor might need to have some custom calibration tweaks
* Use multi-threading and clever state handling to make a better performing robot.
* Fine-tune your robots' navigation to create a near optimum navigation algorithm.
* Detect the other teams robot and push it out of your circle.
* Detect being pushed
* Customize your robot - maybe you can use a bumper and a switch to do some collision detection?
* More power - can you do something clever with another motor?

## Don'ts
* Use blocking code; your robot will miss-behave (and most likely put you at a disadvantage)
* Cheat. You will be disqualified

# Programming

The program starts in the main method found in `MightyMain`, it sets up and initialized three threads:

1. the WebSocketThread, it communicates with the server (sending its position and receiving the game state)
2. the SensorThread, it reads the color under your robot and the distance to the object in front of the robot
3. the ControlThread, this thread runs the main loop in the `Robot` class - _this is the only place where 
you need to make changes to the code!_

The `no.itera.lego.robot` package contains three important classes where you should read the documentation and implement
the robot AI:

1. `RobotState` - this class contains most of data that you interact with within the application, this includes the state
of the game board, as well as the last read color and distance obtained by the sensor thread.
2. `RobotController` - this interface shows you a handful of useful functions that can be used to control the robot. All
move functions are non-blocking.
3. `Robot` - this class is _the_ implementation of the logic that controls your robot. It already contains a simple
boilerplate implementation of a simple robot. Read the comments and the examples included in this class to get an 
understanding of how it works to control the robots.

Remember to configure the following properties correctly:

1. [RobotState.java][robotstate]
  * `HOST` and `PORT` - address to the server
  * `name` - set the name of your robot
  * `simulation` - set this to false before starting a real match
2. [build.properties][build-properties]
  * `device.host` - enter the IP address of your robot here. After you have connected both your robot and your machine to
    the network, you'll be able to upload your code to the robot by running the `ant upload`.

*Good luck and have fun! :)*

_See the below chapters for how to compile the code and upload it to your robot_

## Building

It is recommended to build using Apache Ant instead of Eclipse, you may follow the guide in `README-ANT.md`.
The handout computers are already pre-configured with `ant`.

### Killing a stalled process with ant

Sometimes you migth encounter a crashed app that locks your brick. In stead of rebooting your brick you may use the
```ant stop``` command.

## Requirements

### Motors and Sensors connections

EV3 Large regulated motors connected to `A` and `D`
EV3 Distance sensor (IR) connected to `S1`
EV3 Color Sensor connected to `S4`

_You may reconfigure this, but it requires you to alter the corresponding code as well_

### Dependencies and libraries

#### Java and Ant
You need to have [Java Development Kit 7][jdk7] (or [higher][jdk8]) and [Ant][ant] installed on
your system.

[jdk8]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[ant]: http://ant.apache.org/

If you want to use IntelliJ, you may follow the guide in `README-INTELLIJ.md`.

#### WebSockets
The robots use WebSockets to communicate with the server. The WebSocket implementation used is the [Java-WebSocket][java-websocket]
library.

Communication can be over USB, Bluetooth or Wi-Fi. The IP address of the server is configured in the file
[RobotState.java][robotstate].

[lejos]: http://www.lejos.org/ev3/docs/
[java-websocket]: http://java-websocket.org/
[robotstate]: src/no/itera/lego/robot/RobotState.java
[build-properties]: build.properties
