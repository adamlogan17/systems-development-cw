package fullGame;

public class Square {
	private Square nextSquare;
	private String name;

	
	public Square(String sqrName, Square nextSqr) {
		setNextSquare(nextSqr);
		setName(sqrName);
	}
	
	public Square getNextSquare() {
		return nextSquare;
	}

	public void setNextSquare(Square nextSquare) {
		this.nextSquare = nextSquare;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		// Will return the details os the Square object
		return "";
	}
}
