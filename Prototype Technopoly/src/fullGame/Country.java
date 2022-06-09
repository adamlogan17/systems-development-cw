package fullGame;

public enum Country {
	UK(0), USA(1);
	
	private int cNum;
	private String names[] = {"U.K", "United States"};
	
	private Country(int num) {
		cNum = num;
	}
	
	public String toString() {
		return names[cNum];
	}
}
