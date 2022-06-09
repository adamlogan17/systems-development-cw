package fullGame;

/**
 * Models the Start Of Quarter square for the game
 * @author AdamLogan & XXXXX
 */
public class StartOfQuarter extends Square {
	int bonusAmount;
	
	/**
	 * This is the constructor for the StartOfQuarter class
	 * @author AdamLogan
	 * @param nextSqr - The next square on the 'board'
	 * @param bnsAmnt - The bonus for passing this square
	 */
	public StartOfQuarter(Square nextSqr, int bnsAmnt) {
		super("Start Of Quarter", nextSqr);
		bonusAmount = bnsAmnt;
	}
	
	/**
	 * Adds the bonus to the player's balance and increments the times the 
	 * player has passed the Start Of Quarter Square
	 * @author XXXXX
	 * @param plyr
	 * @return - the amount added to the Player's balance
	 */
	public int passedQuarter(Player plyr) {
		plyr.moneyIn(bonusAmount);
		plyr.incrementTimesPassedStart();
		return bonusAmount;
	}
	
	public String toString() {
		String output = "";
		
		output += "This is the " + this.getName() + " square\n"
				+ "The bonus for passing this square is " + this.bonusAmount;
		
		return output;
	}
}