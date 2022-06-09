package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.StockExchange;


public class StockExchangeTest {
	StockExchange se;
	
	@Before
	public void setUp() {
		se = new StockExchange("Test", 1, 10, 0.25, null);
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when a correct guess is given
	 * @author AdamLogan
	 */
	public void winningGameTest() {
		assertEquals(125, se.playGame(5, 100, 5));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy1() {
		assertEquals(-10, se.playGame(4, 100, 5));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy2() {
		assertEquals(-20, se.playGame(3, 100, 5));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy3() {
		assertEquals(-30, se.playGame(2, 100, 5));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy4() {
		assertEquals(-40, se.playGame(1, 100, 5));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy5() {
		assertEquals(-50, se.playGame(6, 100, 1));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy6() {
		assertEquals(-60, se.playGame(7, 100, 1));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy7() {
		assertEquals(-70, se.playGame(8, 100, 1));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy8() {
		assertEquals(-80, se.playGame(9, 100, 1));
	}
	
	@Test
	/**
	 * Tests if the correct number is returned when an incorrect guess is given
	 * @author AdamLogan
	 */
	public void loseBy9() {
		assertEquals(-90, se.playGame(10, 100, 1));
	}
}
