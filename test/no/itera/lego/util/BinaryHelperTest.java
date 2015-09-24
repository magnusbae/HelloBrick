package no.itera.lego.util;

import no.itera.lego.model.TwoAxisInputModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class BinaryHelperTest {

	@Test
	public void ZeroSteeringFullForwardGivesByte11000111(){
		int aByte = BinaryHelper.encodeByte(true, true, 0, 7);

		assertEquals("11000111", Integer.toBinaryString(aByte));
	}

	@Test
	public void ZeroSteeringOverflowingForwardGivesByte11000111(){
		int aByte = BinaryHelper.encodeByte(true, true, 0, 12);

		assertEquals("11000111", Integer.toBinaryString(aByte));
	}

	@Test
	public void SteeringOverflowAndNoSpeedGivesFullSteeringAndNoSpeedBits(){
		int aByte = BinaryHelper.encodeByte(true, true, 9, 0);

		assertEquals("11111000", Integer.toBinaryString(aByte));
	}

	@Test
	public void ZeroSteeringFullForwardGivesEqualWheelSpeedsAndMaxSpeed(){
		int aByte = BinaryHelper.encodeByte(true, true, 0, 7);

		TwoAxisInputModel decoded = BinaryHelper.decodeByte(aByte);

		assertTrue(decoded.isMotorLeftDirectionForwards());
		assertTrue(decoded.isMotorRightDirectionForwards());
		assertEquals(decoded.getMotorLeftSpeed(), decoded.getMotorRightSpeed());
		assertEquals(896, decoded.getMotorRightSpeed());
	}

	@Test
	public void ZeroSteeringFullBackwardsGivesEqualWheelSpeedsAndMaxSpeedAndMotorDirectionBackwards(){
		int aByte = BinaryHelper.encodeByte(false, true, 0, 7);

		TwoAxisInputModel decoded = BinaryHelper.decodeByte(aByte);

		assertFalse(decoded.isMotorLeftDirectionForwards());
		assertFalse(decoded.isMotorRightDirectionForwards());
		assertEquals(decoded.getMotorLeftSpeed(), decoded.getMotorRightSpeed());
		assertEquals(896, decoded.getMotorRightSpeed());
	}

	@Test
	public void FullSteeringLeftAndFullForwardGivesLeftWheelReverseAndRightWheelMaxSpeed(){
		int aByte = BinaryHelper.encodeByte(true, true, 7, 7);

		TwoAxisInputModel decoded = BinaryHelper.decodeByte(aByte);

		assertFalse(decoded.isMotorLeftDirectionForwards());
		assertTrue(decoded.isMotorRightDirectionForwards());
		assertNotEquals(decoded.getMotorLeftSpeed(), decoded.getMotorRightSpeed());
		assertEquals(896, decoded.getMotorRightSpeed());
		assertEquals(700, decoded.getMotorLeftSpeed());
	}

	@Test
	public void FullSteeringRightAndFullForwardGivesRightWheelReverseAndLeftWheelMaxSpeed(){
		int aByte = BinaryHelper.encodeByte(true, false, 7, 7);

		TwoAxisInputModel decoded = BinaryHelper.decodeByte(aByte);

		assertTrue(decoded.isMotorLeftDirectionForwards());
		assertFalse(decoded.isMotorRightDirectionForwards());
		assertNotEquals(decoded.getMotorLeftSpeed(), decoded.getMotorRightSpeed());
		assertEquals(896, decoded.getMotorLeftSpeed());
		assertEquals(700, decoded.getMotorRightSpeed());
	}

}
