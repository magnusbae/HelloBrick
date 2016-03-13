package no.itera.lego;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import no.itera.lego.model.RobotState;
import no.itera.lego.threads.ControlThread;
import no.itera.lego.threads.JoystickThread;
import no.itera.lego.threads.MasterEv3Thread;
import no.itera.lego.threads.ServerSocketThread;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.LastState;

import java.util.concurrent.CountDownLatch;

import static no.itera.lego.util.EV3Helper.getColorName;

public class MightyMain {

	private static EV3Helper ev3Helper = new EV3Helper();
	private static LastState lastState = new LastState();
	private static RobotState robotState = new RobotState();

	public static void main(String[] args) throws InterruptedException {

//		Thread t1;
//		Thread t2;
//
//		Brick brick = LocalEV3.ev3;
//		String name = brick.getName();
//
//		if (name.startsWith("R")) {
//			t1 = new Thread(new ControlThread(robotState, lastState, ev3Helper));
//			t2 = new Thread(new ServerSocketThread(robotState, lastState));
//		} else {
//			t1 = new Thread(new JoystickThread(robotState, ev3Helper, lastState));
//			t2 = new Thread(new MasterEv3Thread(robotState, ev3Helper, lastState));
//		}
//
//		robotState.latch = new CountDownLatch(2);
//
//		t1.start();
//		t2.start();
//
//
//		System.out.println("\nPress enter to exit program");

		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SensorMode rgb = cs.getRGBMode();
		final char[] colorLetters = new char[]{'R', 'G', 'B'};

		int sampleSize = rgb.sampleSize();
		float[] sample = new float[sampleSize];

		while (robotState.shouldRun) {
			if (Button.ENTER.isDown()) {
				ev3Helper.playBeep();
				robotState.shouldRun = false;
			} else {
				int colorID = cs.getColorID();
				rgb.fetchSample(sample, 0);
				LCD.clear(0,0,4);
				LCD.drawString(getColorName(colorID), 0, 0);
				for (int i = 0; i < sampleSize; i++) {
					LCD.drawString(colorLetters[i] + ": " + sample[i] + "", 0, i + 1);
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

//		robotState.latch.await();

		ev3Helper.playBeep();
		System.out.println("Bye!");

	}
}
