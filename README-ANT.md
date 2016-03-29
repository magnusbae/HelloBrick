#Building with Apache Ant

This project also contains a build script for [Apache Ant][ant], which can
download the leJOS library, compile the code, upload, and run it on the device.

## Requirements

To build with Ant you need to have [Java Development Kit 7][jdk] and
[Apache Ant][ant-download] installed on your system.

[ant]: http://ant.apache.org/
[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[ant-download]: http://ant.apache.org/bindownload.cgi

## TL;DR

First time, run: `ant install`
To compile and run: `ant upload run`
To stop: `ant stop`

## Installing other dependencies

The necessary dependencies can be downloaded and installed by running:

    ant install

You will then have a directory named `leJOS_EV3_0.9.1-beta` containing some
tools, the leJOS library and docs.

Of the tools, you will most likely find the `ev3control` utility most
useful. You can run it with:

    ./leJOS_EV3_0.9.1-beta/bin/ev3control

## Compiling and uploading

You may compile your program by running:

    ant compile

Or compile and upload to the unit by running:

    ant upload

## Running

You may run the uploaded program by running:

    ant run

If you want to compile, upload and run:

    ant upload run

## Stopping

You may stop the program you started with `ant run` by running:

    ant stop

## Configuration

The configuration is defined in `build.properties`. If you want to change e.g.
the class name or the filename of the jar, you may change it there.
