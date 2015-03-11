# HelloBrick

[Lejos](http://www.lejos.org/ev3/docs/) + Ant + HelloWorld

# Requirements

This requires that you have [Apache Ant][ant] installed on your system.

[ant]: http://ant.apache.org/

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
