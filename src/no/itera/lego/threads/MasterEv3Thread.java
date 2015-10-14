package no.itera.lego.threads;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.remote.ev3.Menu;
import lejos.remote.ev3.RemoteRequestMenu;
import lejos.utility.Delay;
import lejos.hardware.ev3.LocalEV3;
import no.itera.lego.model.RobotState;
import no.itera.lego.util.EV3Helper;
import no.itera.lego.util.LastState;

import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import static no.itera.lego.model.RobotState.PORT;

public class MasterEv3Thread implements Runnable {

	private RobotState robotState;
	private EV3Helper ev3Helper;
	private LastState lastState;

	public MasterEv3Thread(RobotState robotState, EV3Helper ev3Helper, LastState lastState) {
		this.ev3Helper = ev3Helper;
		this.robotState = robotState;
		this.lastState = lastState;
	}

	@Override
	public void run() {
		try {
			System.out.println("Loaded");
			ev3Helper.playBeep();
			// first we need to upload and run the secondary on each EV3
			File jarFile = new File("MightyBot.jar");
			FileInputStream in = new FileInputStream(jarFile);
			byte[] data = new byte[(int) jarFile.length()];
			in.read(data);
			in.close();
			
			Brick brick = LocalEV3.ev3;
			String localName = brick.getName();
			String remoteName = localName.equals("C2") ? "R2D2" : localName.replace("C", "R");
			String[] names = {remoteName};
			
			BrickInfo[] bricks = new BrickInfo[names.length];
			Menu[] menus = new Menu[names.length];
			for (int i = 0; i < bricks.length; i++) {
				bricks[i] = BrickFinder.find(names[i])[0];
				menus[i] = new RemoteRequestMenu(bricks[i].getIPAddress());
			}
			for (Menu m : menus) {
				m.uploadFile("/home/lejos/programs/MightyBot.jar", data);
				m.runProgram("MightyBot");
			}
			// allow time for secondary to start then connect to them

			System.out.println("uploaded, waiting 15s");
			Delay.msDelay(15000);
			Socket[] slaves = new Socket[names.length];
			for (int i = 0; i < bricks.length; i++) {
				slaves[i] = new Socket(bricks[i].getIPAddress(), PORT);
				slaves[i].setTcpNoDelay(true);
			}

			robotState.connected = true;
			while (!robotState.calibrated) {
				//wait for calibration
			}

			int inputModelByte = 1;
			for (Socket s : slaves) {
				s.getOutputStream().write(inputModelByte++);
				s.getOutputStream().flush();
			}
			do {
				// now tell them all to go
				for (Socket s : slaves) {
					s.getOutputStream().write(lastState.getLastByte());
					s.getOutputStream().flush();
				}

			} while (robotState.shouldRun);

			// time to pack up! Bye bye!
			for (Socket s : slaves) {
				s.getOutputStream().write(0);
				s.getOutputStream().flush();
			}

			for (Socket s : slaves) {
				s.close();
			}

		} catch (Exception e) {
			robotState.shouldRun = false;
			e.printStackTrace();
		}
		robotState.latch.countDown();
	}
}
