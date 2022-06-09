package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Player;
import fullGame.Product;
import fullGame.ProductBidding;

public class ProductBiddingTest {
	ProductBidding auction;
	
	@Before
	public void setUp() throws Exception {
		auction = new ProductBidding(100, "Test", null);
	}
	
	@Test
	/**
	 * Tests if a random product is selected from the array
	 * @author AdamLogan
	 */
	public void getValidPrdctTest() {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		Product prdct2 = new Product("Test Product 2", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		Product[] allPrdcts = {prdct1, prdct2};
		
		Product chosenPrdct = auction.prdctToBid(allPrdcts);
		
		assertTrue(chosenPrdct.equals(prdct1) || chosenPrdct.equals(prdct2));
	}
	
	@Test
	/**
	 * Tests if null is returned when an empty array is passed to 'prdctToBid()'
	 * @author AdamLogan
	 */
	public void getInvalidPrdctTest() {
		Product[] allPrdcts = new Product[0];
		
		assertEquals(null, auction.prdctToBid(allPrdcts));
	}
	
	@Test
	/**
	 * Tests if a product is moved to another player and the previous 
	 * player no longer owns the product
	 * @author AdamLogan
	 */
	public void transferPrdctTest() {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		
		Player prevOwner = new Player("Previous Owner");
		prevOwner.addProduct(prdct1);
		
		Player newOwner = new Player("New Owner");
		
		assertTrue(auction.transferPrdct(prevOwner, newOwner, prdct1));
		assertTrue(containsPrdct(newOwner.getOwnedProducts(), prdct1));
		assertFalse(containsPrdct(prevOwner.getOwnedProducts(), prdct1));
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
