package no.itera.lego;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import no.itera.lego.model.RobotState;
import no.itera.lego.threads.ControlThread;
import no.itera.lego.threads.JoystickThread;
import no.itera.lego.threads.MasterEv3Thread;
import no.itera.lego.threads.ServerSocketThread;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.LastState;

import java.util.concurrent.CountDownLatch;

public class MightyMain {

	private static EV3Helper ev3Helper = new EV3Helper();
	private static LastState lastState = new LastState();
	private static RobotState robotState = new RobotState();

	public static void main(String[] args) throws InterruptedException {

		Thread t1;
		Thread t2;

		Brick brick = LocalEV3.ev3;
		String name = brick.getName();

		if (name.startsWith("R")) {
			t1 = new Thread(new ControlThread(robotState, lastState, ev3Helper));
			t2 = new Thread(new ServerSocketThread(robotState, lastState));
		} else {
			t1 = new Thread(new JoystickThread(robotState, ev3Helper, lastState));
			t2 = new Thread(new MasterEv3Thread(robotState, ev3Helper, lastState));
		}

		robotState.latch = new CountDownLatch(2);

		t1.start();
		t2.start();


		System.out.println("\nPress enter to exit program");

		while (robotState.shouldRun) {
			if (Button.ENTER.isDown()) {
				ev3Helper.playBeep();
				robotState.shouldRun = false;
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		robotState.latch.await();

		ev3Helper.playBeep();
		System.out.println("Bye!");

	}
}
