package no.itera.lego.util;

import no.itera.lego.model.TwoAxisInputModel;

public class BinaryHelper {
	public static final int THREE_BIT_MAX_VALUE = 7;
	public static final int SEVEN = 1 << 7;
	public static final int SIX = 1 << 6;
	public static final int THREE_LSB_BITMASK = 7;
	public static final int BIT_FOUR_THROUGH_SIX_BITMASK = 7 << 3;
	public static final int BIT_SEVEN_BITMASK = 128;
	public static final int BIT_SIX_BITMASK = 64;

	public static TwoAxisInputModel decodeByte(int byteValue){
		boolean forward = (byteValue & BIT_SEVEN_BITMASK) == SEVEN;
		boolean left = (byteValue & BIT_SIX_BITMASK) == SIX;
		int speed = byteValue & THREE_LSB_BITMASK;
		int turn = (byteValue & BIT_FOUR_THROUGH_SIX_BITMASK) >> 3;

		return new TwoAxisInputModel(forward, left, turn, speed);
	}

	public static int encodeByte(boolean forward, boolean left, int turn, int speed){


		int byteValue = (speed > THREE_BIT_MAX_VALUE) ? THREE_BIT_MAX_VALUE : Math.abs(speed);
		int turnValue = (turn > THREE_BIT_MAX_VALUE) ? THREE_BIT_MAX_VALUE : Math.abs(turn);

		byteValue = byteValue | ((turnValue << 3) & BIT_FOUR_THROUGH_SIX_BITMASK);

		if(forward){
			byteValue = byteValue | SEVEN;
		}
		if(left){
			byteValue = byteValue | SIX;
		}

		return byteValue;
	}
}
