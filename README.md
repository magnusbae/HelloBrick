# HelloBrick

This is a simple project for running code on Lego Mindstorms EV3, using
[leJOS][lejos]. It contains a build script for [Apache Ant][ant], which can
download the leJOS library, compile the code and run it on the device. The
project also contains a class with helper methods, EV3Helper. This may make it
easier to use the device. At last, it contains a main class which utilizes the
helper methods for some simple movement.

[lejos]: http://www.lejos.org/ev3/docs/
[ant]: http://ant.apache.org/

## Requirements

This project requires that you have [Java Development Kit 7][jdk7] and
[Apache Ant][ant-download] installed on your system.

[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[ant-download]: http://ant.apache.org/bindownload.cgi

## Installing other dependencies

The necessary dependencies can be downloaded and installed by running:

    ant install

You will then have a directory named `leJOS_EV3_0.9.0-beta` containing some
tools, the leJOS library and docs.

Of the tools, you will most likely find the `ev3control` utility most
useful. You can run it with:

    ./leJOS_EV3_0.9.0-beta/bin/ev3control

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

## IDE configuration

### IntelliJ

If you want, you may develop this project using IntelliJ. This will give you
code completion and documentation, but you will still need to build and deploy using Ant. 
To facilitate IntelliJ we have created a separate branch that contains an IntelliJ project. 
To use IntelliJ just type ```git checkout intellij-project``` and open the repository from IntelliJ's open dialog.

### Eclipse

If you want, you may develop this project using Eclipse. This will give you
code completion and documentation. By using the leJOS plugin for Eclipse, this
will be set up automatically. In addition, you will have the possibility to
compile and run the code on the device from Eclipse. This means that you may
use this instead of Ant if you want.

#### Install leJOS

If you have run `ant install`, you may skip this step. If you don't have ant,
you may instead download leJOS yourself. Download this file:

Linux/Mac: <http://sourceforge.net/projects/lejos/files/lejos-EV3/0.9.0-beta/leJOS_EV3_0.9.0-beta.tar.gz>

Windows: <http://sourceforge.net/projects/lejos/files/lejos-EV3/0.9.0-beta/leJOS_EV3_0.9.0-beta_win32.zip>

Unpack the file somewhere and take note of the location.

#### Install leJOS plugin for Eclipse

In Eclipse, select `Help -> Install New Software...`. Add the site for the plugin:

<http://lejos.sourceforge.net/tools/eclipse/plugin/ev3>

Check `leJOS EV3 Support` and follow the installation procedure.

After installation, you will have to specify the path to leJOS to the plugin.
In Eclipse, select `Window -> Preferences -> leJOS EV3`. Set `EV3_HOME` to the
location of leJOS. If using `ant install` this will be
`<project>/leJOS_EV3_0.9.0-beta`. Otherwise, select the location you unpacked
the leJOS file to. After this, make sure you also check `Run Tools in a
separate JVM` in the same window.

#### Create a leJOS project in Eclipse

To add your code as a project in Eclipse select `New -> Project -> LeJOS EV3 ->
LeJOS EV3 Project`. In the next dialog, uncheck `Use default location` and
select the location of your project. Select finish, and you should be ready to
go.
