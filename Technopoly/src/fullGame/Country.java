package fullGame;

/**
 * Models the Country's which a Product can be located in
 * @author AdamLogan
 */
public enum Country {
	UK(0), USA(1), JAPAN(2), CANADA(3), FRANCE(4), ITALY(5), GERMANY(6), IRELAND(7);
	
	private int cNum;
	private String names[] = {"United Kingdom", "United States", "Japan", 
			"Canada", "France", "Italy", "Germany", "Ireland"};
	
	private Country(int num) {
		cNum = num;
	}
	
	public String toString() {
		return names[cNum];
	}
}