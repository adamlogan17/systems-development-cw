package fullGame;

/**
 * Models the basic Square for the game
 * @author AdamLogan
 *
 */
public class Square {
	private Square nextSquare;
	private String name;

	/**
	 * This is the constructor for the Square class
	 * @author AdamLogan
	 * @param sqrName - The name of the Square
	 * @param nextSqr - The next square on the 'board'
	 */
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
	
	/**
	 * This checks if this square is the same as another square 
	 * by checking if the name of the squares are the same
	 * @author AdamLogan
	 * @param sqr - The other square which is being checked
	 * @return - true if the same and false otherwise
	 */
	public boolean equals(Square sqr) {
		if(this.getName().equals(sqr.getName())) {
			return true;
		}
		return false;
	}

	public String toString() {
		String output = "";
		
		String nxtSqrMssg = "";
		if(nextSquare == null) {
			nxtSqrMssg = "There is no next square.";
		} else {
			nxtSqrMssg = "The next square is " + nextSquare.getName();
		}
		output += "The square's name is " + this.name + "\n"
				+ nxtSqrMssg;
		
		return output;
	}
}
