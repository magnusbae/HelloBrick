# HelloBrick
Lejos + Ant + HelloWorld


##Installing on OS X or Linux
Copy and paste the following line into your terminal. It will download and install Lejos to ~/.lejos.

```bash
curl -O https://raw.githubusercontent.com/magnusbae/HelloBrick/install-script/install.sh && chmod +x install.sh && sh install.sh
```

##Lejos in PATH
For the lejos tools to work from the commandline you should add the following two lines to your ```.bash_profile``` or ```.profile``` script:
```bash
export EV3_HOME=~/.lejos
export PATH=$EV3_HOME/bin:$PATH
```

You will most likely find the ```ev3control``` utility most useful.
