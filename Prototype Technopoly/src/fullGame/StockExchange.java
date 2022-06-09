package fullGame;

public class StockExchange extends Square {
	private int lowNum;
	private int highNum;
	
	public StockExchange(int lwNm, int hghNm, Square nextSqr) {
		super("Stock Exchange", nextSqr);
		lowNum = lwNm;
		highNum = hghNm;
	}
	
	public void playGame(Player plyr, int guessedNum) {
		// Generates the random numbers and then compares this to the user's number
	}

	public int getLowNum() {
		return lowNum;
	}

	public void setLowNum(int lowNum) {
		this.lowNum = lowNum;
	}

	public int getHighNum() {
		return highNum;
	}

	public void setHighNum(int highNum) {
		this.highNum = highNum;
	}
}
