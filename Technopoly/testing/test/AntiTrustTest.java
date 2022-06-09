package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.AntiTrust;
import fullGame.Player;

public class AntiTrustTest {
	AntiTrust antiTrustSqr;
	
	@Before
	public void setUp() throws Exception {
		antiTrustSqr = new AntiTrust(0.1, null);;
	}
	
	@Test
	/**
	 * Tests if the 'fine' method will subtract 10% from a Player's balance 
	 * @author AdamLogan
	 */
	public void fineTest() {
		Player plyr = new Player("AdamLogan");
		
		antiTrustSqr.fine(plyr);
		
		assertEquals(900, plyr.getBalance());
	}
}
