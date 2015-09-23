package no.itera.lego.util;

import no.itera.lego.model.TwoAxisInputModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class BinaryHelperTest {


	@Test
	public void encodeByteGives204(){
		int aByte = BinaryHelper.encodeByte(true, true, false, 12);

		assertEquals(204, aByte);
		assertEquals("11001100", Integer.toBinaryString(aByte));
	}

	@Test
	public void encodeWithHigherSpeedThan31GivesNoSideEffects(){
		int aByte = BinaryHelper.encodeByte(false, false, false, 255);

		assertEquals(31, aByte);
		assertEquals("11111", Integer.toBinaryString(aByte));
	}

	@Test
	public void encodeWithSpeed32GivesMaxSpeed(){
		int aByte = BinaryHelper.encodeByte(false, false, false, 32);

		assertEquals(31, aByte);
		assertEquals("11111", Integer.toBinaryString(aByte));
	}

	@Test
	public void decodeByte204GivesCorrectValues(){
		TwoAxisInputModel decoded = BinaryHelper.decodeByte(204);
		assertTrue(decoded.isForward());
		assertTrue(decoded.isTurn());
		assertFalse(decoded.isLeft());
		assertEquals(12, decoded.getSpeed());
	}

	@Test
	public void decodeByte31GivesCorrectValues(){
		TwoAxisInputModel decoded = BinaryHelper.decodeByte(31);
		assertFalse(decoded.isForward());
		assertFalse(decoded.isTurn());
		assertFalse(decoded.isLeft());
		assertEquals(31, decoded.getSpeed());
	}

	@Test
	public void decodeByte0GivesCorrectValues(){
		TwoAxisInputModel decoded = BinaryHelper.decodeByte(0);
		assertFalse(decoded.isForward());
		assertFalse(decoded.isTurn());
		assertFalse(decoded.isLeft());
		assertEquals(0, decoded.getSpeed());
	}
}
