package fullGame;

public enum Company {
	MICROSOFT(0), SONY(1), AMAZON(2), INTEL(3), GOOGLE(4), APPLE(5), 
	SAMSUNG(6), AMD(7);
	
	private int cNum;
	private String names[] = {"Micorsoft", "Sony", "Amazon", "Intel",
			"Google", "Apple", "Samsung", "AMD"};
	
	private Company(int num) {
		cNum = num;
	}
	
	public String toString() {
		return names[cNum];
	}
}
