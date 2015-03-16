# HelloBrick

This is a simple project for running code on Lego Mindstorms EV3, using
[leJOS][lejos]. The project contains a class with helper methods, EV3Helper,
which may make it easier to use the device. It also contains a main class and
some examples which utilizes the helper methods for some simple movement.

[lejos]: http://www.lejos.org/ev3/docs/

## Requirements

This project requires that you have [Java Development Kit 7][jdk7] installed on
your system.

[jdk7]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

## Development

There exists a plugin for leJOS to Eclipse, which makes development easy. For
convenience we are distributing Eclipse bundled with the Lejos EV3 plugin.

- [Eclipse for Windows 64-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-win32-x86_64.zip)
- [Eclipse for Windows 32-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-win32.zip)
- [Eclipse for OS X 64-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-macosx-cocoa-x86_64.zip)
- [Eclipse for OS X 32-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-macosx-cocoa.zip)
- [Eclipse for Linux 64-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-linux-gtk-x86_64.tar.gz)
- [Eclipse for Linux 32-bit](http://www.aaberge.net/stuff/eclipse/eclipse-java-luna-SR2-linux-gtk.tar.gz)

After launching Eclipse, you may have to set `EV3_HOME`, if it is not set. You
can do this by selecting `Window -> Preferences -> leJOS EV3`. Set `EV3_HOME`
to the `leJOS_EV3_0.9.0-beta` directory inside the `eclipse` directory you
unpacked.

After creating a leJOS-project, if the leJOS-libraries can't be found, right
click on your project and select `leJOS EV3 -> Convert to leJOS EV3 project`.

## IDE/Eclipse install and configuration

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

Note that if you create the project before specifying `EV3_HOME` as described
in the previous section, the project will be created as a normal Java project,
and the leJOS library will not be available. In this case, set the `EV3_HOME`,
then right click on the project and select `leJOS EV3 -> Convert to leJOS EV3
project`.
