package fullGame;

/**
 * Models the Product Bidding Square for the game
 * @author AdamLogan
 */
public class ProductBidding extends Square{
	private int lowestBid;
	
	public ProductBidding(int lwstBd, String nameOfAuction, Square nextSqr) {
		super((nameOfAuction + " Auction"), nextSqr);
		lowestBid = lwstBd;
	}
	
	/**
	 * Chooses a random product from an array
	 * @author AdamLogan and XXXXX
	 * @param ownedPrdcts - Products to choose from
	 * @return - The randomly chosen product, null if no Products are owned
	 */
	public Product prdctToBid(Product[] allPrdcts) {
		int min = 1;
		int max = allPrdcts.length;
		
		int randNum = (int) Math.floor(Math.random() * (max - min + 1) + min);
		
		if(max > 0) {
			return allPrdcts[randNum - 1];
		} else {
			return null;
		}
	}
	
	/**
	 * This method will transfer a product to one player to another
	 * @author AdamLogan
	 * @param prevOwner - the player who landed on bidding
	 * @param newOwner - the player who won bidding
	 * @param prdct - the product to be transferred
	 * @return - if true then transfer was successful, false otherwise
	 */
	public boolean transferPrdct(Player prevOwner, Player newOwner, Product prdct) {
		if (prevOwner.removeProduct(prdct)) {
			newOwner.addProduct(prdct);
			return true;
		}
		return false;
	}
	
	public int getLowestBid() {
		return lowestBid;
	}
	
	public void setLowestBid(int lowestBid) {
		this.lowestBid = lowestBid;
	}
	
	public String toString() {
		String output = "";
		
		output += "This is " + this.getName() + "\n"
				+ "The lowest bid is �" + this.lowestBid;
		
		return output;
	}
}