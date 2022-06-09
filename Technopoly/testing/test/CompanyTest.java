package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import fullGame.Company;
import fullGame.Product;

public class CompanyTest {
	Company cmp;
	Product[] allPrdct = new Product[2];

	@Before
	public void setUp() throws Exception {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {10,20,30,40}, 1, 1);
		Product prdct2 = new Product("Test Product 2", null, 100, 100, new int[] {10,20,30,40}, 1, 1);
		
		allPrdct[0] = prdct1;
		allPrdct[1] = prdct2;
		cmp = new Company("Test 1", allPrdct);
	}
	
	@Test
	/**
	 * Tests if two Company's with the same names and products are equal
	 * @author AdamLogan
	 */
	public void sameCmpTest() {
		Company cmp2 = new Company("Test 1", allPrdct);
		
		assertTrue(cmp.equals(cmp2));
	}
	
	@Test
	/**
	 * Tests if two Player's with the different names are not equal
	 * @author AdamLogan
	 */
	public void differentNameCmpTest() {
		Company cmp2 = new Company("Test 2", allPrdct);
		
		assertFalse(cmp.equals(cmp2));
	}
	
	@Test
	/**
	 * Tests if two Player's with the different products are not equal
	 * @author AdamLogan
	 */
	public void differentPrdctCmpTest() {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {10,20,30,40}, 1, 1);
		Product prdct2 = new Product("Test Product 2", null, 100, 100, new int[] {10,20,30,40}, 1, 1);
		Product prdct3 = new Product("Test Product 3", null, 100, 100, new int[] {10,20,30,40}, 1, 1);

		Product[] allPrdct2 = {prdct1, prdct2, prdct3};
		
		Company cmp2 = new Company("Test 1", allPrdct2);
		
		assertFalse(cmp.equals(cmp2));
	}
	
}
