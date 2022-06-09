package fullGame;

public class Product extends Square {
	private Company cmpny;
	private int warehousesBought = 0;
	private int amountToCharge;
	private int price;
	private Country countriesOwned[] = new Country[0];
	
	public Product(String sqrName, Square nextSqr, Company cmpnyItBelongsTo, 
			int amntToChrg, int prc) {
		super(sqrName, nextSqr);
		cmpny = cmpnyItBelongsTo;
		amountToCharge = amntToChrg;
		price = prc;
	}
	
	public boolean buildWarehouse(Player plyr) {
		warehousesBought++;
		amountToCharge += 50;
		return plyr.moneyOut(25);
	}
	
	public boolean ownCountry(Player plyr, Country cntry) {
		// the product is now sold in a country
		return false;
		
	}
	
	public boolean chargeForUse(Player plyr) {
		// charges the player for using the product
		return true;
	}

	public Company getCmpny() {
		return cmpny;
	}

	public void setCmpny(Company cmpny) {
		this.cmpny = cmpny;
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
		this.warehousesBought = warehousesBought;
	}
}
