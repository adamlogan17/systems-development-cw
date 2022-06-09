package fullGame;

/**
 * Models the Company in the game, which is the 'Fields' of the Products
 * @author AdamLogan & XXXXX
 */
public class Company {
	private Product prdcts[];
	private String name;
	
	/**
	 * his is the constructor for the Company class
	 * @param nm - The name of the Company
	 * @param products - The Products which the Company owns
	 */
	public Company(String nm, Product[] products) {
		prdcts = products;
		name = nm;
	}
	
	public Product[] getPrdcts() {
		return prdcts;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String output = "";
		
		output += "The comapnay's name is " + this.getName() + "\n";
		
		if(this.prdcts.length > 0) {
			output += "Products in company: \n";
			for(Product prdct: prdcts) {
				output += "-" + prdct.getName() + "\n";
			}
		}
		
		return output;
	}
	
	/**
	 * Checks if this company object is the same as another Company object, 
	 * which they are equal if the name is the same and they have the same Products
	 * @author AdamLogan
	 * @param cmp - the Company that is being checked
	 * @return - true if equal and false otherwise
	 */
	public boolean equals(Company cmp) {
		boolean result = false;
		
		if(!this.name.equals(cmp.getName())) {
			return false;
		}
		
		for(Product crrntPrdct: this.prdcts) {
			for(Product othrPrdct: cmp.getPrdcts()) {
				if(crrntPrdct.equals(othrPrdct)) {
					result = true;
					break;
				} else {
					result = false;
				}
			}
		}
		
		if(this.prdcts.length != cmp.getPrdcts().length) {
			result = false;
		}
		
		return result;
	}
}