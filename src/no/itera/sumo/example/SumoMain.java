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
		//boolean turning = true;
		ev3Helper.tankTurn();
		while(run) {
			lastSampleSet.takeSamples();
			//System.out.println(lastSampleSet.getLastColor());
			if (lastSampleSet.getLastColor().equals("BROWN")) {
			//	ev3Helper.stop();
			//	Sound.beep();
			//	Button.waitForAnyPress();
				ev3Helper.backward(5);
				//ev3Helper.backward(5); 
				ev3Helper.tankTurn(100);
			}  
			System.out.println(lastSampleSet.getLastDistance());
			if (lastSampleSet.getLastDistance() < 100) { //see opponent	
				ev3Helper.backward();
				//ev3Helper.forward();
			} else {
				ev3Helper.tankTurn();
			}
			
			
			
		}
	}
	
	public static void main(String[] args){
		SumoMain sumo = new SumoMain();
		System.out.println("startup complete.\nClick any button to drive");
		Button.waitForAnyPress();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sumo.drive();
	}

	
}
