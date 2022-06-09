package fullGame;

/**
 * Models the Stock Exchange Square for the game
 * @author AdamLogan
 */
public class StockExchange extends Square {
	private int lowNum;
	private int highNum;
	private double risk;
	
	/**
	 * This is the constructor for the StockExchange class
	 * @author AdamLogan
	 * @param name - The name of the stock exchange
	 * @param lwNm - The lowest number that can be guessed
	 * @param hghNm - The highest number that can be guessed
	 * @param rsk - The risk of investments made as a percentage (e.g. 0.1 is 10%)
	 * @param nextSqr - The next square on the 'board'
	 */
	public StockExchange(String name, int lwNm, int hghNm, double rsk, Square nextSqr) {
		super((name + " Stock Exchange"), nextSqr);
		lowNum = lwNm;
		highNum = hghNm;
		risk = rsk;
	}
	
	/**
	 * This generates a random number within the range. If the user has guessed 
	 * random number correctly then they will get a return on their investment
	 * but if they guess incorrectly then they lose funds. They will lose is 
	 * a percentage of how much they got the answer wrong by. For example if
	 * they are wrong by 1 and invest 100 they loose 10% which is 10.
	 * @author AdamLogan
	 * @param guessedNum - the number which the user guessed
	 * @param invtAmnt - the amount a Player has invested
	 * @return - the amount the player has won *(positive value) or the amount they have lost (negative value)
	 */
	public int playGame(int guessedNum, int invtAmnt) {
		// Generates the random numbers and then compares this to the user's number
		int randNum = (int) (Math.random() * highNum) + lowNum;
		
		if(randNum == guessedNum) {
			return (int) Math.ceil((int) invtAmnt * (risk + 1));
		} else {
			return (int) -(invtAmnt * (Math.abs(randNum - guessedNum)/10.0));
		}
	}

	/**
	 * If the user has guessed the number correctly then they will get a return 
	 * on their investment but if they guess incorrectly then they lose funds. 
	 * They will lose is a percentage of how much they got the answer wrong by. 
	 * For example if they are wrong by 1 and invest 100 they loose 10% which is 10.
	 * @author AdamLogan
	 * @param guessedNum - the number which the user guessed
	 * @param invtAmnt - the amount a Player has invested
	 * @param correctGuess - the number which the Player has to guess
	 * @return - the amount the player has won (positive value) or the amount they have lost (negative value)
	 */
	public int playGame(int guessedNum, int invtAmnt, int correctGuess) {		
		if(correctGuess == guessedNum) {
			return (int) Math.ceil((int) invtAmnt * (risk + 1));
		} else {
			return (int) -(invtAmnt * (Math.abs(correctGuess - guessedNum)/10.0));
		}
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
	
	public String toString() {
		String output = "";
		
		output += "Stock Exchange name is " + this.getName() + "\n"
				+ "There range is " + this.lowNum + " - " + this.highNum + "\n"
				+ "The risk of this stock exchange is " + this.risk;
		
		return output;
	}
}