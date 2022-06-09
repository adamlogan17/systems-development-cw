package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Player;
import fullGame.Product;

public class PlayerTest {
	Player plyr;
	
	@Before
	public void setUp() throws Exception {
		plyr = new Player("AdamLogan");
	}
	
	@Test
	/**
	 * Tests if 100 is added to the player's balance
	 * and if the method 'moneyIn()' will return true
	 * @author AdamLogan
	 */
	// Not sure if I should split this test up
	public void moneyInValidAmnt() {
		assertTrue(plyr.moneyIn(100));
		assertEquals(1100, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if a negative value is rejected 
	 * by 'moneyIn()' and that no amount is 
	 * added into the Player's balance
	 * @author AdamLogam
	 */
	public void moneyInNegativeInput() {
		assertFalse(plyr.moneyIn(-100));
		assertEquals(1000, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if nothing is added to a Player's
	 * balance when 0 is passed to 'moneyIn()'
	 * @author AdamLogan
	 */
	public void moneyInAddNothing() {
		plyr.moneyIn(0);
		assertEquals(1000, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if an amount the user has more 
	 * than, in their balance, can be removed
	 * @author AdamLogan
	 */
	public void moneyOutInBalance() {
		assertTrue(plyr.moneyOut(100));
		assertEquals(900, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if a negative value is rejected 
	 * by 'moneyOut()' and that no amount is 
	 * removed from the Player's balance
	 * @author AdamLogam
	 */
	public void moneyOutNegativeInput() {
		assertTrue(plyr.moneyOut(-100));
		assertEquals(1000, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if nothing is removed from a Player's
	 * balance when 0 is passed to 'moneyOut()'
	 * @author AdamLogan
	 */
	public void moneyOutRemoveNothing() {
		plyr.moneyOut(0);
		assertEquals(1000, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if everything is removed from
	 * the user's balance that the method 
	 * returns true and the new balance is 0
	 * @author AdamLogan
	 */
	public void moneyOutRemoveMax() {
		assertTrue(plyr.moneyOut(plyr.getBalance()));
		assertEquals(0, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if more than the user's balance is 
	 * removed that the method returns false 
	 * @author AdamLogan
	 */
	public void moneyOutRemoveOverBalance() {
		assertFalse(plyr.moneyOut(plyr.getBalance()+1));
		assertEquals(-1, plyr.getBalance());
	}

	@Test
	/**
	 * Tests if a Product can be added to the Player's owned Products
	 * @author AdamLogan
	 */
	public void addPrdctTest() {
		Product prdct = new Product("Test Product", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		plyr.addProduct(prdct);
		assertTrue(containsPrdct(plyr.getOwnedProducts(), prdct));
	}
	
	@Test
	/**
	 * Tests if a Product can be removed from the Player's owned Products
	 * @author AdamLogan
	 */
	public void removeProductTest() {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		Product prdct2 = new Product("Test Product 2", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		plyr.addProduct(prdct1);
		plyr.addProduct(prdct2);
		
		assertTrue(plyr.removeProduct(prdct1));
		assertFalse(containsPrdct(plyr.getOwnedProducts(), prdct1));
	}
	
	@Test
	/**
	 * Tests if two Player's with the same names are equal
	 * @author AdamLogan
	 */
	public void samePlyrTest() {
		Player plyr2 = new Player("AdamLogan");
		assertTrue(plyr.equals(plyr2));
	}
	
	@Test
	/**
	 * Tests if two Player's with the different names are not equal
	 * @author AdamLogan
	 */
	public void differentPlyrTest() {
		Player plyr2 = new Player("Different Player");
		assertFalse(plyr.equals(plyr2));
	}
	
	/**
	 * Checks if a Product is in an array of Products
	 * @author AdamLogan
	 * @param prdctsToCheck - the array of Products
	 * @param prdct - the Product which is being checked 
	 * @return - true if the Product is in the array and false otherwise
	 */
	public boolean containsPrdct(Product[] prdctsToCheck, Product prdct) {
		for(Product currntPrdct: prdctsToCheck) {
			if(currntPrdct.equals(prdct)) {
				return true;
			}
		}
		return false;
	}
	
}