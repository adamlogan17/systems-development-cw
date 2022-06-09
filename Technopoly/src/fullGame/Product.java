package fullGame;

/**
 * Models the Product Square for the game
 * @author AdamLogan & XXXXX
 */
public class Product extends Square {
	private int warehousesBought = 0; // the number if warehouses which are dedicated to the Product
	private int amountToCharge;
	private int price;
	private Country countriesOwned[] = new Country[0]; // The countries in whcih the Product is located
	private int addedCountryCost;
	private int costOfRelocation;
	private int costOfWarehouse; // the cost to dedicate a warehouse to the Product
	
	// new attributes after design
	private int[] warehouseCosts;
	private int initialAmtToChrg; // the amount to charge when the Product object was created
	private int initialRelocationCost; // the cost of relocation when the Product object was created
	
	/**
	 * This is the constructor for the Product class
	 * @author AdamLogan
	 * @param sqrName - The name of the Product
	 * @param nextSqr - The next square on the 'board'
	 * @param amntToChrg - The amount to charge a Player when they land on a Product another Player owns
	 * @param prc - The price to purchase a Product
	 * @param wrhCsts - An array of the new amount to charge when warehouses are dedicate to a Product
	 * @param cstRelct - The cost to relocate a country
	 * @param addedCntryCst - The cost which is added to the amount to charge when a Country is relocated
	 */
	public Product(String sqrName, Square nextSqr, int amntToChrg, int prc, int[] wrhCsts, int cstRelct, int addedCntryCst) {
		super(sqrName, nextSqr);
		amountToCharge = amntToChrg;
		price = prc;
		initialAmtToChrg = amntToChrg;
		warehouseCosts = wrhCsts;
		costOfRelocation = cstRelct;
		initialRelocationCost = cstRelct;
		addedCountryCost = addedCntryCst;
		this.updateCostOfWarehouse();
	}
	
	/**
	 * This method will increase the amount to charge a player 
	 * for landing on the product and will charge the accordingly
	 * @author AdamLogan
	 * @param plyr - the Player who owns the Product
	 * @return - true if the warehouse is built and false otherwise
	 */
	public boolean buildWarehouse(Player plyr) {
		boolean result = false;
		if(warehousesBought < warehouseCosts.length) {
			this.setAmountToCharge(warehouseCosts[warehousesBought++]);
			result = plyr.moneyOut(costOfWarehouse);
			if(warehousesBought != warehouseCosts.length) {
				this.updateCostOfWarehouse();
			}
			return result; 
		}
		return result;
	}
	
	/**
	 * Relocates the Product to the Country and updates the appropriate values
	 * @author AdamLogan & XXXXX
	 * @param requestedCountry - the Country which the Product is being relocated too
	 */
	public void addCountry(Country requestedCountry) {
		Country temp[] = new Country[countriesOwned.length+1];
		for(int i = 0; i<countriesOwned.length; i++) {
			temp[i] = countriesOwned[i];
		}
		temp[countriesOwned.length] = requestedCountry;
		
		countriesOwned = temp;
		
		amountToCharge += addedCountryCost;
		
		costOfRelocation += 25;
	}
	
	/**
	 * This method will set the Product to its initial values
	 * @author AdamLogan
	 */
	public void clearPrdct() {
		warehousesBought = 0;
		amountToCharge = initialAmtToChrg;
		countriesOwned = new Country[0];
		costOfRelocation = initialRelocationCost;
	}

	/**
	 * Updates the costOfWarehouse to the appropriate value
	 * @author AdamLogan
	 */
	public void updateCostOfWarehouse() {
		if(warehouseCosts.length > warehousesBought) {
			this.setCostOfWarehouse((int) Math.ceil((warehouseCosts[warehousesBought]/2)));
		}
	}

	public int getAmountToCharge() {
		return amountToCharge;
	}

	public void setAmountToCharge(int amountToCharge) {
		this.amountToCharge = amountToCharge;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Country[] getCountriesOwned() {
		return countriesOwned;
	}

	public void setCountriesOwned(Country countriesOwned[]) {
		this.countriesOwned = countriesOwned;
	}

	public int getWarehousesBought() {
		return warehousesBought;
	}

	public void setWarehousesBought(int warehousesBought) {
		if(warehouseCosts.length > warehousesBought) {
			this.warehousesBought = warehousesBought;
			this.updateCostOfWarehouse();
		} else if (warehouseCosts.length == warehousesBought) {
			this.warehousesBought = warehousesBought;
		}
	}
	
	public int getCostOfWarehouse() {
		return costOfWarehouse;
	}
	
	public void setCostOfWarehouse(int costOfWarehouse) {
		this.costOfWarehouse = costOfWarehouse;
	}
	
	public int[] getWarehouseCosts() {
		return warehouseCosts;
	}
	
	public int getCostOfRelocation() {
		return costOfRelocation;
	}

	public void setCostOfRelocation(int costOfRelocation) {
		this.costOfRelocation = costOfRelocation;
	}

	public String toString() {
		String output = "";
		
		output += "Product name is " + this.getName() + "\n"
				+ "There is " + this.warehousesBought + " warehouse(s) dedicated to this product\n"
				+ "The price of the product is �" + this.price + "\n"
				+ "The cost to use this product is �" + this.amountToCharge + "\n"
		        + "The cost to relocate a country is �" + this.costOfRelocation + "\n";
		
		if(this.countriesOwned.length > 0) {
			output += "Owned Countries: \n";
			for(Country cntry: countriesOwned) {
				output += "-" + cntry + "\n";
			}
		}
		
		return output;
	}
	
}