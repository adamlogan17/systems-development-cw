package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fullGame.Player;
import fullGame.Product;
import fullGame.Company;
import fullGame.Game;

public class GameTest {
	Player plyr1 = new Player("Test player 1");
	Player plyr2 = new Player("Test player 2");
	Player plyr3 = new Player("Test player 3");
	Player[] allPlayers = {plyr1, plyr2, plyr3};
	
	Company cmp;
	Company[] allCmps = new Company[1];
	
	@Before
	public void setUp() throws Exception {
		Product prdct1 = new Product("Test Product 1", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		Product prdct2 = new Product("Test Product 2", null, 100, 100, new int[] {1,2,3,4}, 1, 1);
		Product[] allPrdcts = {prdct1, prdct2};
		
		cmp = new Company("Test Company", allPrdcts);
		
		allCmps[0] = cmp;
		
		plyr1.setOwnedProducts(allPrdcts);
	}
	
	@Test
	/**
	 * Tests if the order of the array has been changed correctly
	 * @author AdamLogan
	 */
	public void changingOrderOfPlay() {
		Player[] newOrder = {plyr2, plyr3, plyr1};
		
		allPlayers = Game.orderOfPlay(allPlayers, 1);
		
		assertTrue(arryEqual(newOrder, allPlayers));
	}
	
	@Test
	/**
	 * Tests if the two Players with the highest balance are returned
	 * @author AdamLogan
	 */
	public void twoPlyrsWithHighBalance() {
		plyr1.setBalance(1001);
		plyr2.setBalance(1001);
		
		ArrayList<Player> highestPlayers = Game.highestBalance(allPlayers);
		
		ArrayList<Player> crrtHighestPlyrs = new ArrayList<Player>();
		crrtHighestPlyrs.add(plyr1);
		crrtHighestPlyrs.add(plyr2);
		
		assertTrue(highestPlayers.equals(crrtHighestPlyrs));
	}
	
	@Test
	/**
	 * Tests if all the Players are returned if they have the same balance
	 * @author AdamLogan
	 */
	public void allPlyrsSameBalance() {
		ArrayList<Player> highestPlayers = Game.highestBalance(allPlayers);
		
		ArrayList<Player> crrtHighestPlyrs = new ArrayList<Player>();
		crrtHighestPlyrs.add(plyr1);
		crrtHighestPlyrs.add(plyr2);
		crrtHighestPlyrs.add(plyr3);
		
		assertTrue(highestPlayers.equals(crrtHighestPlyrs));
	}
	
	@Test
	/**
	 * Tests if the Player with the highest balance is returned
	 * @author AdamLogan
	 */
	public void onePlyrWithHighBalance() {
		plyr2.setBalance(1001);
		
		ArrayList<Player> highestPlayers = Game.highestBalance(allPlayers);
		
		ArrayList<Player> crrtHighestPlyrs = new ArrayList<Player>();
		crrtHighestPlyrs.add(plyr2);
		
		assertTrue(highestPlayers.equals(crrtHighestPlyrs));
	}
	
	@Test
	/**
	 * Tests if the returned ArrayList is empty if the Player owns no Products
	 * @author AdamLogan
	 */
	public void plyrOwnsNoPrdctsForRelocate() {
		ArrayList<Product> prdcts = Game.prdctsToRelocate(plyr2, allCmps);
		assertEquals(0, prdcts.size());
	}
	
	@Test
	/**
	 * Tests if the required warehouses are not built for the Products 
	 * no Products are returned
	 */
	public void plyrOwnsCmpForRelocate() {
		ArrayList<Product> prdcts = Game.prdctsToRelocate(plyr1, allCmps);
		assertEquals(0, prdcts.size());
	}
	
	@Test
	/**
	 * Tests if a Player does own a Company with all the Products having 
	 * the required warehouses dedicated too them that the Products are
	 * returned in the ArrayList
	 * @author AdamLogan
	 */
	public void plyrOwnsPrdctsForRelocate() {
		ArrayList<Product> crrctPrdcts = new ArrayList<Product>();
		for(Product prdct: plyr1.getOwnedProducts()) {
			prdct.setWarehousesBought(4);
			crrctPrdcts.add(prdct);
		}
		ArrayList<Product> prdcts = Game.prdctsToRelocate(plyr1, allCmps);
		
		assertTrue(prdcts.equals(crrctPrdcts));
	}
	
	@Test
	/**
	 * Tests if the returned ArrayList is empty if the Player does not own 
	 * all the Products in a Company
	 * @author AdamLogan
	 */
	public void plyrOwnsNoCmpsTest() {
		ArrayList<Company> ownedCmps = Game.companiesOwned(plyr2, allCmps);
		assertEquals(0, ownedCmps.size());
	}
	
	@Test
	/**
	 * Tests if the returned ArrayList contains the Companies which the Player
	 * owns all the Products in
	 * @author AdamLogan
	 */
	public void plyrOwnsCmpTest() {
		ArrayList<Company> ownedCmps = Game.companiesOwned(plyr1, allCmps);
		ArrayList<Company> crrctCmps = new ArrayList<Company>();
		crrctCmps.add(cmp);
		
		assertTrue(ownedCmps.equals(crrctCmps));
	}
	
	
	@Test
	/**
	 * Tests if the Player being eliminated is removed from the array 
	 * and the their Products are cleared of their values
	 * @author AdamLogan
	 */
	public void eliminatePlyrTest() {
		Product prdct = plyr1.getOwnedProducts()[0];
		prdct.setWarehousesBought(2);
		
		Player[] crrctPlyrs = {plyr2, plyr3};
		
		allPlayers = Game.eliminatePlayer(plyr1, allPlayers);
		
		assertTrue(arryEqual(allPlayers, crrctPlyrs));
		assertEquals(0, prdct.getWarehousesBought());
	}

	/**
	 * Checks if two arrays are equal
	 * @author AdamLogan
	 * @return - true if equal and false otherwise
	 */
	public boolean arryEqual(Player[] arry1, Player[] arry2) {
		if(arry1.length != arry2.length) {
			return false;
		}
		for(int i=0; i<arry1.length; i++) {
			if(!arry1[i].equals(arry2[i])) {
				return false;
			}
		}
		return true;
	}
}
