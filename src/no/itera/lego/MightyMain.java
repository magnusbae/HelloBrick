package no.itera.lego;

import lejos.hardware.Button;
import no.itera.lego.model.RobotState;
import no.itera.lego.threads.ControlThread;
import no.itera.lego.threads.MotorThread;
import no.itera.lego.threads.SensorThread;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.SampleSet;

import java.util.concurrent.CountDownLatch;

public class MightyMain {

	private static EV3Helper ev3Helper = new EV3Helper();
	private static SampleSet sampleSet = new SampleSet(ev3Helper);
	private static RobotState robotState = new RobotState();

	public static void main(String[] args) throws InterruptedException {
		ev3Helper.playBeep();
		System.out.println("startup complete.\n\nClick any button to fight");
		Button.waitForAnyPress();
		ev3Helper.playBeep();

		Thread.sleep(3000);

		Thread mt = new Thread(new MotorThread(robotState, ev3Helper));
		Thread st = new Thread(new SensorThread(robotState, ev3Helper, sampleSet));
		Thread ct = new Thread(new ControlThread(robotState, sampleSet));

		mt.start();
		st.start();
		ct.start();

		robotState.latch = new CountDownLatch(3);

		ev3Helper.playBeep();

		while (robotState.shouldRun) {
			Button.waitForAnyPress();
			ev3Helper.playBeep();
			robotState.shouldRun = false;
		}

		robotState.latch.await();

		ev3Helper.playBeep();
		System.out.println("Bye!");

		System.exit(0);

	}
}
