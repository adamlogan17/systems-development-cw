package fullGame;

public class Player {
	private String name;
	private	int balance = 1000;
	private int timesPassedStart = 0;
	private Square presentSquare;
	private Product ownedProducts[] = new Product[0];
	

	public Player(String playersName, Square startSquare) {
		name = playersName;
		presentSquare = startSquare;
	}
	
	public void moneyIn(int amount) {
		if(amount > 0) {
			balance += amount;
		}		
	}
	
	public boolean moneyOut(int amount) {
		if (balance >= amount) {
			balance -= amount;
			return true;
		} else {
			// eliminate player
			return false;
		}
		
	}
	
	public boolean movePlayer(int spaces) {
 		for(int i = 0; i < spaces; i++) {
			presentSquare = presentSquare.getNextSquare();
			if(presentSquare instanceof StartOfQuarter) {
				timesPassedStart++;
				((StartOfQuarter) presentSquare).passedQuarter(this);
			}
		}
		return false;
	}
	
	public void addProduct(Product requestedProduct) {
		Product temp[] = new Product[ownedProducts.length+1];
		for(int i = 0; i<ownedProducts.length; i++) {
			temp[i] = ownedProducts[i];
		}
		temp[ownedProducts.length] = requestedProduct;
		
		ownedProducts = temp;
	}
	
	public void removeProduct(Product productToRemove) {
		// removes the product from ownedProducts
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

	public String toString() {
		String output = "";
		
		output += "Player's name is " + name
				+ " and their balance is = " + balance + "\n";
		
		return output;
	}

	public String getName() {
		return name;
	}
}
