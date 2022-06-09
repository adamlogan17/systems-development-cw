package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Country;
import fullGame.Player;
import fullGame.Product;

public class ProductTest {
	Product prdct;
	
	@Before
	public void setUp() throws Exception {
		prdct = new Product("Test Product", null, 100, 100, new int[] {10,20,30,40}, 1, 1);
	}
	
	@Test
	/**
	 * Tests if the correct values are updated when a warehouse is built
	 * @author AdamLogan
	 */
	public void buildingWarehouseTest() {
		Player plyr = new Player("AdamLogan");
		
		assertTrue(prdct.buildWarehouse(plyr));
		assertEquals(10, prdct.getAmountToCharge());
		assertEquals(995, plyr.getBalance());
	}
	
	@Test
	/**
	 * Tests if a warehouse is not built when the max has been reached
	 * @author AdamLogan
	 */
	public void buildingInvalidWarehouseTest() {
		Player plyr = new Player("AdamLogan");
		prdct.setWarehousesBought(4);
		
		assertFalse(prdct.buildWarehouse(plyr));
		assertEquals(4, prdct.getWarehousesBought());
	}
	
	@Test
	/**
	 * Tests if all the values are reset on the Product to their original values
	 * @author AdamLogan
	 */
	public void clearPrdctTest() {
		prdct.setWarehousesBought(2);
		prdct.updateCostOfWarehouse();
		prdct.clearPrdct();
		
		assertEquals(15, prdct.getCostOfWarehouse());
		assertEquals(100, prdct.getAmountToCharge());
		assertEquals(1, prdct.getCostOfRelocation());
		assertEquals(0, prdct.getWarehousesBought());
	}
	
	@Test
	/**
	 * Tests if the correct values are updated when a Country is added
	 * @author AdamLogan
	 */
	public void addCountryTest() {
		Country testCntry = Country.GERMANY;
		
		prdct.addCountry(testCntry);
		assertTrue(containsCntry(prdct.getCountriesOwned(), testCntry));
		assertEquals(101, prdct.getAmountToCharge());
	}
	
	/**
	 * Checks if a Product is in an array of Products
	 * @author AdamLogan
	 * @param prdctsToCheck - the array of Products
	 * @param prdct - the Product which is being checked 
	 * @return - true if the Product is in the array and false otherwise
	 */
	public boolean containsCntry(Country[] cntrysToCheck, Country cntry) {
		for(Country currntCntry: cntrysToCheck) {
			if(currntCntry.equals(cntry)) {
				return true;
			}
		}
		return false;
	}
}
