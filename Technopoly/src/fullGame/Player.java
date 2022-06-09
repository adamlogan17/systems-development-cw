package fullGame;

/**
 * Models a Player within the game
 * @author AdamLogan
 */
public class Player {
	private String name;
	private	int balance = 1000;
	private int timesPassedStart = 0;
	private Square presentSquare;
	private Product ownedProducts[] = new Product[0];
	

	public Player(String playersName) {
		name = playersName;
	}
	
	public boolean moneyIn(int amount) {
		if(amount > 0 && balance >= 0) {
			balance += amount;
			return true;
		} else return false;
	}
	
	/**
	 * This method checks if the player has the funds to remove the 
	 * amount entered and if it does it removes the amount from the 
	 * funds.
	 * @author AdamLogan
	 * @param amount - the amount of funds to be removed
	 * @return true if the balance is above -1 and false otherwise
	 */
	public boolean moneyOut(int amount) {
		if (amount < 0) {
			return true;
		} else if (balance >= amount) {
			balance -= amount;
			return true;
		} else {
			balance = -1;
			return false;
		}
	}
	
	/**
	 * This method changes the 'presentSquare' attribute to the correct square
	 * @author AdamLogan
	 * @param spaces
	 * @return  true if player has successfully moved and false if unsuccessful 
	 */
	public int movePlayer(int spaces) {
		int bonusAmnt = 0;
 		for(int i = 0; i < spaces; i++) {
			presentSquare = presentSquare.getNextSquare();
			if(presentSquare instanceof StartOfQuarter) {
				bonusAmnt = ((StartOfQuarter) presentSquare).passedQuarter(this);
			}
		}
		return bonusAmnt;
	}
	
	/**
	 * This method will add a product to the owned products of the player
	 * @author AdamLogan
	 * @param requestedProduct - the object of the product to be added
	 */
	public void addProduct(Product requestedProduct) {
		Product temp[] = new Product[ownedProducts.length+1];
		for(int i = 0; i<ownedProducts.length; i++) {
			temp[i] = ownedProducts[i];
		}
		temp[ownedProducts.length] = requestedProduct;
		
		ownedProducts = temp;
	}
	
	/**
	 * This method will remove a product from the player
	 * @author AdamLogan
	 * @param productToRemove - the product to be removed
	 * @return - true if productToRemove has been has been successfully removed and false otherwise
	 */
	public boolean removeProduct(Product productToRemove) {
		boolean removed = false;
		
		if(ownedProducts.length == 0) {
			return removed;
		}
		
		Product[] tempOwned = new Product[ownedProducts.length - 1];
		int tempOwnedIndex = 0;
		
		for(int i=0; i<ownedProducts.length; i++) {
			if(ownedProducts[i] != productToRemove) {
				tempOwned[tempOwnedIndex++] = ownedProducts[i];
			} else {
				productToRemove.clearPrdct();
				removed = true;
			}
		}
		
		ownedProducts = tempOwned;
		return removed;
	}
	
	/**
	 * This checks if this Player is the same as another Player 
	 * by checking if the name of the Players are the same
	 * @author AdamLogan
	 * @param plyr - The other Player which is being checked
	 * @return - true if the same and false otherwise
	 */
	public boolean equals(Player plyr) {
		if(this.getName().equals(plyr.getName())) {
			return true;
		}
		return false;
	}
	
	public void incrementTimesPassedStart() {
		timesPassedStart++;
	}

	public int getTimesPassedStart() {
		return timesPassedStart;
	}

	public void setTimesPassedStart(int timesPassedStart) {
		this.timesPassedStart = timesPassedStart;
	}
	
	public Square getPresentSquare() {
		return presentSquare;
	}
	
	public void setPresentSquare(Square presentSquare) {
		this.presentSquare = presentSquare;
	}
	
	public Product[] getOwnedProducts() {
		return ownedProducts;
	}

	public void setOwnedProducts(Product ownedProducts[]) {
		this.ownedProducts = ownedProducts;
	}
	
	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	/**
	 * This displays the Player's details and some of the details of the Products they own
	 * @author XXXXX
	 */
	public String toString() {
		String output = "";
		
		output += "Your name is " + name
				+ " and your balance is �" + balance + "\n";
		
		if(ownedProducts.length > 0) {
			output += "Owned Products: \n";
			for(Product prdct: ownedProducts) {
				output += "-" + prdct.getName() + " with " + prdct.getWarehousesBought() + " warehouse(s) ";
				Country[] cntrysOwnd = prdct.getCountriesOwned();
				if(cntrysOwnd.length > 0) {
					output += "and operates in:";
				}
				output += "\n";
				for(Country cntry: cntrysOwnd) {
					output += "   -" + cntry + "\n";
				}
			}
		}
		
		return output;
	}

	public String getName() {
		return name;
	}
}
