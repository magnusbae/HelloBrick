package no.itera.sumo.example;

import com.jcraft.jsch.ForwardedTCPIPDaemon;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;
import no.itera.lego.EV3Helper;
import no.itera.lego.example.SampleSet;

public class SumoMain {
	private final RegulatedMotor motorLeft;
	private final RegulatedMotor motorRight;
	private SampleSet lastSampleSet;
	private EV3Helper ev3Helper;

	public SumoMain(){
		ev3Helper = new EV3Helper(true);
		motorLeft = ev3Helper.getMotorLeft();
		motorRight = ev3Helper.getMotorRight();
		lastSampleSet = new SampleSet(ev3Helper);
	}
	
	private void drive() {
		boolean run = true;
		ev3Helper.tankTurn();
		while(run) {
			lastSampleSet.takeSamples();
			if (lastSampleSet.getLastColor().equals("BROWN")) { //edge of the ring
				ev3Helper.backward(5);
			}  
			if (lastSampleSet.getLastDistance() < 100) { //see opponent	
				Sound.beep();
			}	
		}
	}
	
	public static void main(String[] args){
		SumoMain sumo = new SumoMain();
		System.out.println("startup complete.\nClick any button to drive");
		Button.waitForAnyPress();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumo.drive();
	}

	
}
