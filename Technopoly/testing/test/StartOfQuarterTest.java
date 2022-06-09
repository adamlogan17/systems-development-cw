package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Player;
import fullGame.StartOfQuarter;

public class StartOfQuarterTest {
	StartOfQuarter start;
	
	@Before
	public void setUp() throws Exception {
		start = new StartOfQuarter(null, 200);
	}
	
	@Test
	/**
	 * Tests if the player's balance and the number of times they 
	 * passed start is correct when they pass the start
	 * @author AdamLogan
	 */
	public void passingQuarterTest() {
		Player plyr = new Player("AdamLogan");
		
		start.passedQuarter(plyr);
		
		assertEquals(1200, plyr.getBalance());
		assertEquals(1, plyr.getTimesPassedStart());
	}
}
