package no.itera.lego.util;

import no.itera.lego.model.TwoAxisInputModel;

public class BinaryHelper {
	public static final int SEVEN = 1 << 7;
	public static final int SIX = 1 << 6;
	public static final int FIVE = 1 << 5;
	public static final int FIVE_LSB_BITMASK = 31;
	public static final int BIT_SEVEN_BITMASK = 128;
	public static final int BIT_SIX_BITMASK = 64;
	public static final int BIT_FIVE_BITMASK = 32;

	public static TwoAxisInputModel decodeByte(int byteValue){
		boolean forward = (byteValue & BIT_SEVEN_BITMASK) > 0;
		boolean turn = (byteValue & BIT_SIX_BITMASK) > 0;
		boolean left = (byteValue & BIT_FIVE_BITMASK) > 0;
		int speed = byteValue & FIVE_LSB_BITMASK;

		return new TwoAxisInputModel(forward, turn, left, speed);
	}

	public static int encodeByte(boolean forward, boolean turn, boolean left, int speed){
		int byteValue = speed > 31 ? 31 : speed;
		if(forward){
			byteValue = byteValue | SEVEN;
		}
		if(turn){
			byteValue = byteValue | SIX;
		}
		if(left){
			byteValue = byteValue | FIVE;
		}
		return byteValue;
	}
}
