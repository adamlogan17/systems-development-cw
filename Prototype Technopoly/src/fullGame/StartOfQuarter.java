package fullGame;

public class StartOfQuarter extends Square {
	int bonusAmount = 150;
	int threshold = 1500;
	
	public StartOfQuarter(Square nextSqr) {
		super("Start Of Quarter", nextSqr);
	}
	
	public boolean passedQuarter(Player plyr) {
		// adds the bonus to the player's balance and checks if they have passed start 4 times
		plyr.moneyIn(bonusAmount);
		return false;
	}

	private boolean incomeEvent(Player plyr) {
		// checks if the player is above the threshold and if not they get eliminated
		return false;
	}
}
