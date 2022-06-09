package fullGame;

/**
 * Models the anti-trust square for the game
 * @author AdamLogan
 */
public class AntiTrust extends Square {
	private double levelOfFine;
	
	/**
	 * Constructor for the AntiTrust class
	 * @author AdamLogan
	 * @param lvlOfFine - The percentage of how much to fine a Player (e.g. 0.1 for a 10% fine) 
	 * @param nextSqr - The next square on the 'board'
	 */
	public AntiTrust(double lvlOfFine, Square nextSqr) {
		super("Anti Trust", nextSqr);
		setLevelOfFine(lvlOfFine);
	}
	
	/**
	 * This method will take the percentage (defined by 'levelOfFine') of the 
	 * players balance and remove it from their account
	 * @author AdamLogan
	 * @param plyr - the player being fined
	 * @return - true if fine has been successful and false otherwise
	 */
	public boolean fine(Player plyr) {
		return plyr.moneyOut((int) Math.ceil(plyr.getBalance() * levelOfFine));
	}

	public double getLevelOfFine() {
		return levelOfFine;
	}

	public void setLevelOfFine(double levelOfFine) {
		this.levelOfFine = levelOfFine;
	}
	
	public String toString() {
		String output = "";
		
		output += "This is the " + this.getName() + " square\n"
				+ "The penalty for landing on this square is " + this.levelOfFine;
		
		return output;
	}
}