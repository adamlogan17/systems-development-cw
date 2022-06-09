package fullGame;

public class ProductBidding extends Square{
	private int lowestBid;
	
	public ProductBidding(int lwstBd, Square nextSqr) {
		super("Auction", nextSqr);
		lowestBid = lwstBd;
	}
	
	public void auction(int bid, Product selectedProduct, Player wngPlayer, Player orgOwner) {
		if (bid > lowestBid) {
			// transfers product to player
		}
	}
}
