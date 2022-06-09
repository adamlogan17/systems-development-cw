package fullGame;

public class AntiTrust extends Square {
	private double levelOfFine;
	
	public AntiTrust(double lvlOfFine, Square nextSqr) {
		super("Anti Trust", nextSqr);
		setLevelOfFine(lvlOfFine);
	}
	
	public void fine(Player plyr) {
		// fines the player a certain amount of money
	}

	public double getLevelOfFine() {
		return levelOfFine;
	}

	public void setLevelOfFine(double levelOfFine) {
		this.levelOfFine = levelOfFine;
	}
}
