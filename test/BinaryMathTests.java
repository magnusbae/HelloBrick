import org.junit.Test;

import static no.itera.lego.util.BinaryHelper.*;
import static org.junit.Assert.assertEquals;

public class BinaryMathTests {

	@Test
	public void setBitSevenToOne(){
		assertEquals(128, SEVEN);
		assertEquals("10000000", Integer.toBinaryString(SEVEN));
	}

	@Test
	public void setBitSixToOne(){
		assertEquals(64, SIX);
		assertEquals("1000000", Integer.toBinaryString(SIX));
	}

	@Test
	public void setBitFiveToOne(){
		assertEquals(32, FIVE);
		assertEquals("100000", Integer.toBinaryString(FIVE));
	}

	@Test
	public void binaryAddBitFiveAndSeven(){
		int added = SEVEN | FIVE;
		assertEquals(160, added);
		assertEquals("10100000", Integer.toBinaryString(added));
	}

	@Test
	public void binaryAddBitFiveAndSevenWithTheNumber12(){
		int added = SEVEN | FIVE | 12;
		assertEquals(172, added);
		assertEquals("10101100", Integer.toBinaryString(added));
	}

	@Test
	public void binarySubTractThreeMostSignificantBitsGives31(){
		int binary = Integer.parseInt("11111111", 2);
		assertEquals(255, binary);

		int fiveBits = binary & FIVE_LSB_BITMASK;
		assertEquals(31, fiveBits);
	}

	@Test
	public void binarySubTractThreeMostSignificantBitsGives12(){
		int binary = Integer.parseInt("11101100", 2);
		assertEquals(236, binary);

		int fiveBits = binary & FIVE_LSB_BITMASK;
		assertEquals(12, fiveBits);
	}

	@Test
	public void binaryMaskBitSevenGivesValue128(){
		int binary = Integer.parseInt("11101100", 2);
		assertEquals(236, binary);

		int bitSeven = binary & BIT_SEVEN_BITMASK;
		assertEquals(128, bitSeven);
	}

	@Test
	public void binaryMaskBitSevenGivesValueZero(){
		int binary = Integer.parseInt("1101100", 2);
		assertEquals(108, binary);

		int bitSeven = binary & BIT_SEVEN_BITMASK;
		assertEquals(0, bitSeven);
	}

	@Test
	public void binaryMaskBitSixGivesValue64(){
		int binary = Integer.parseInt("11101100", 2);
		assertEquals(236, binary);

		int bitSix = binary & BIT_SIX_BITMASK;
		assertEquals(64, bitSix);
	}

	@Test
	public void binaryMaskBitSixGivesValueZero(){
		int binary = Integer.parseInt("10101100", 2);
		assertEquals(172, binary);

		int bitSix = binary & BIT_SIX_BITMASK;
		assertEquals(0, bitSix);
	}

	@Test
	public void binaryMaskBitFiveGivesValue32(){
		int binary = Integer.parseInt("11101100", 2);
		assertEquals(236, binary);

		int bitFive = binary & BIT_FIVE_BITMASK;
		assertEquals(32, bitFive);
	}

	@Test
	public void binaryMaskBitFiveGivesValueZero(){
		int binary = Integer.parseInt("10001100", 2);
		assertEquals(140, binary);

		int bitFive = binary & BIT_FIVE_BITMASK;
		assertEquals(0, bitFive);
	}
}
