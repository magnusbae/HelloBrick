package no.itera.lego;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.Menu;
import lejos.remote.ev3.RemoteRequestMenu;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import no.itera.lego.model.TwoAxisInputModel;
import no.itera.lego.util.BinaryHelper;
import no.itera.lego.util.EV3Helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DualMain {

	static final int PORT = 5678;
	static final long DELAY = 10;

	public static void runSecondary() {
		EV3Helper ev3Helper = new EV3Helper();
		final int SPEED_FACTOR = EV3Helper.MAX_MOTOR_SPEED/30;
		try {
			System.out.println("Loaded");
			ev3Helper.playBeep();
			ServerSocket cmdSock = new ServerSocket(PORT);
			Socket s = cmdSock.accept();

			int pos = s.getInputStream().read();
			while (pos > 0) {
				pos = s.getInputStream().read();
				TwoAxisInputModel inputModel = BinaryHelper.decodeByte(pos);
				if(inputModel.getSpeed() == 1 && inputModel.isTurn()){
					if(inputModel.isLeft()){
						ev3Helper.rotateLeft();
					}else{
						ev3Helper.rotateRight();
					}
				}else if(inputModel.getSpeed() > 1){
					int speed = inputModel.getSpeed() * SPEED_FACTOR;
					if(inputModel.isTurn()){
						if(inputModel.isForward() && inputModel.isLeft()){
							ev3Helper.leftForward(speed);
						}else if(inputModel.isForward()){
							ev3Helper.rightForward(speed);
						}else if(inputModel.isLeft()){
							ev3Helper.leftBackward(speed);
						}else{
							ev3Helper.rightBackward(speed);
						}
					}else{
						if(inputModel.isForward()){
							ev3Helper.forward(speed);
						}else{
							ev3Helper.backward(speed);
						}
					}
				}else {
					ev3Helper.stop();
				}
			}
			s.close();
			cmdSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void runPrimary() {
		EV3Helper ev3Helper = new EV3Helper();
		try {
			System.out.println("Loaded");
			ev3Helper.playBeep();
			// first we need to upload and run the secondary on each EV3
			File jarFile = new File("MightyBot.jar");
			FileInputStream in = new FileInputStream(jarFile);
			byte[] data = new byte[(int) jarFile.length()];
			in.read(data);
			in.close();
			String[] names = {"R1"};
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

			//float motors
			ev3Helper.getMotorLeft().flt(true);
			ev3Helper.getMotorRight().flt(true);

			//we are ready;
			System.out.println("Center stick and press button");
			ev3Helper.playBeep();
			EV3TouchSensor trigger = new EV3TouchSensor(SensorPort.S1);
			float[] triggerState = new float[1];
			do{
				trigger.fetchSample(triggerState, 0);
			}while (triggerState[0] != 1);
			ev3Helper.getMotorLeft().resetTachoCount();
			ev3Helper.getMotorRight().resetTachoCount();

			System.out.println("Fight!");
			ev3Helper.playBeep();

			int inputModelByte = 1;
			for (Socket s : slaves) {
				s.getOutputStream().write(inputModelByte++);
				s.getOutputStream().flush();
			}
			do {
				// now tell them all to go
				for (Socket s : slaves) {
					s.getOutputStream().write(inputModelByte++);
					s.getOutputStream().flush();
				}
				int tachoY = ev3Helper.getMotorLeft().getTachoCount();
				int tachoX = ev3Helper.getMotorRight().getTachoCount();

				boolean forward = false;

				if(tachoY < 0){
					forward = true;
				}
				int speed = (int) Math.abs(tachoY/2.1);
				boolean turn = Math.abs(tachoX) > 6;
				boolean left = tachoX < 0;
				if(speed < 6){
					speed = 1;
				}
				inputModelByte = BinaryHelper.encodeByte(forward, turn, left, speed);

			} while (Button.ENTER.isUp());

			// time to pack up! Bye bye!
			for (Socket s : slaves) {
				s.getOutputStream().write(0);
				s.getOutputStream().flush();
			}

			for (Socket s : slaves) {
				s.close();
			}

		} catch (Exception e) {
			System.out.println("Got exception " + e);
		}
	}

	public static void main(String[] args) {
		Brick brick = LocalEV3.ev3;
		String name = brick.getName();
		if(name.startsWith("R")){
			runSecondary();
		}else{
			runPrimary();
		}
	}
}
