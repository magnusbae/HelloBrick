# HelloBrick

This is a simple project for running code on Lego Mindstorms EV3, using
[leJOS][lejos]. It contains a build script for [Apache Ant][ant], which can
download the leJOS library, compile the code and run it on the device. The
project also contains a class with helper methods, EV3Helper. This may make it
easier to use the device. At last, it contains a main class which utilizes the
helper methods for some simple movement.

[lejos]: http://www.lejos.org/ev3/docs/
[ant]: http://ant.apache.org/

# Requirements

This project requires that you have [Java Development Kit 7][jdk7] and
[Apache Ant][ant-download] installed on your system.

[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[ant-download]: http://ant.apache.org/bindownload.cgi

# Installing dependencies

The necessary dependencies can be downloaded and installed by running:

    ant install

You will then have a directory named `leJOS_EV3_0.9.0-beta` containing some
tools, the leJOS library and docs.

Of the tools, you will most likely find the `ev3control` utility most
useful. You can run it with:

    ./leJOS_EV3_0.9.0-beta/bin/ev3control

# Compiling and uploading

You may compile your program by running:

    ant compile

Or compile and upload to the unit by running:

    ant upload

# Running

You may run the uploaded program by running:

    ant run

If you want to compile, upload and run:

    ant upload run

# Stopping

You may stop the program you started with `ant run` by running:

    ant stop

# Configuration

The configuration is defined in `build.properties`. If you want to change e.g.
the class name or the filename of the jar, you may change it there.
