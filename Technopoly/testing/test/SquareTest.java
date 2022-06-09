package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Square;

public class SquareTest {
	Square sqr;
	
	@Before
	public void setUp() throws Exception {
		sqr = new Square("Test Square", null);
	}
	
	@Test
	/**
	 * Tests if two Square's with the same names are equal
	 * @author AdamLogan
	 */
	public void sameSqrTest() {
		Square sqr2 = new Square("Test Square", null);
		assertTrue(sqr.equals(sqr2));
	}
	
	@Test
	/**
	 * Tests if two Square's with the different names are not equal
	 * @author AdamLogan
	 */
	public void differentSqrTest() {
		Square sqr2 = new Square("Test Square 2", null);
		assertFalse(sqr.equals(sqr2));
	}
}
