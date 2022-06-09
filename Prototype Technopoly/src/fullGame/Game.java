package fullGame;

import java.util.Scanner;

public class Game {
	
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Hello, Welcome to Technopoly!");
		
		int totalPlayers = 0;
		
		do {
			System.out.print("How many players are there (2 - 4): ");
			
			try {
				totalPlayers = input.nextInt();
			} catch(Exception ex) {
				System.out.println("Please enter a number between 2 and 4.");
				input.nextLine();
			}
		} while(!(totalPlayers > 1 && totalPlayers < 5));
		
		
		Player allPlayers[] = new Player[totalPlayers];
		
		Square startSqr = createBoard();
		
		for(int i = 0; totalPlayers>i; i++) {
			System.out.print("Please enter player " + (i+1) + "s name: ");
			String name = input.next();
			allPlayers[i] = new Player(name, startSqr);
		}
		
		int userChoice;
		do {
			for(Player currentPlyr: allPlayers) {
				System.out.println();
				System.out.println("It is " + currentPlyr.getName() + "'s go!");
				System.out.println();
				
				allPlayers = playersTurn(currentPlyr, allPlayers);
				
			}
			
			// the following for loop is only for the demo
			System.out.println();
			for(Player currentPlyr: allPlayers) {
				System.out.println(currentPlyr);
			}
			
		} while(allPlayers.length > 1);
		
		if(allPlayers.length == 1) {
			System.out.println("Congragulations to " + allPlayers[0].getName() + " you have the game!");
		}
	}	
	
	private static Player[] playersTurn(Player plyr, Player allPlayers[]) {
		int userChoice;
		String[] options = {"Purchase a warehouse"
				+ "", "Purchase a country",
				"Roll dice", "Close Game", "Eliminate Player (this is only for demo)"};

				Menu myMenu = new Menu("Player Options", options);
		userChoice = myMenu.getUserChoice();
		
		switch (userChoice) {
			case 1:
				requestWarehouse(plyr);
				break;
			case 2:
				requestCountry(plyr);
				break;
			case 4:
				System.out.println("Goodbye!");
				input.close();
				System.exit(0);
				break;
			case 5:
				allPlayers = emliminatePlayer(plyr, allPlayers);
				return allPlayers;
		}
		rollDice(plyr);
		System.out.println();
		System.out.println("You Landed on " + plyr.getPresentSquare().getName());
		System.out.println();
		
		Square currentSqr = plyr.getPresentSquare();
		if(currentSqr instanceof Product) {
			landedOnProduct(plyr, allPlayers);
		} else if (currentSqr instanceof StockExchange) {
			landedOnStockExchange(plyr);
		} else if (currentSqr instanceof ProductBidding) {
			landedOnProductBidding(plyr, allPlayers);
		} else if (currentSqr instanceof AntiTrust) {
			System.out.println("Oh no! You are being fined £100 by the govt for violating anti trust law!");
			((AntiTrust) currentSqr).fine(plyr);
		} else if (currentSqr instanceof StartOfQuarter) {
			System.out.println("You have reached the end of the quarter, have a bonus!");
		} else {
			System.out.println("Have a rest it's a Bank Holiday!");
		}
		
		return allPlayers;
	}
	
	private static StartOfQuarter createBoard() {
		StockExchange se6 = new StockExchange(1, 20, null);
		Product i3 = new Product("Xeon E", se6, Company.INTEL, 26, 310);
		Product i2 = new Product("Core i7", i3, Company.INTEL, 29, 330);
		Product i1 = new Product("Pentium Gold", i2, Company.INTEL, 26, 310);
		StockExchange se5 = new StockExchange(1, 20, i1);
		Product amz2 = new Product("Kindle", se5, Company.AMAZON, 40, 370);
		Product amz1 = new Product("Prime", amz2, Company.AMAZON, 35, 410);
		
		ProductBidding pb2 = new ProductBidding(150, amz1);
		
		Product s3 = new Product("Sony Alpha III", pb2, Company.SONY, 19, 230);
		Product s2 = new Product("Sony Experia", s3, Company.SONY, 21, 240);
		Product s1 = new Product("PlayStation", s2, Company.SONY, 19, 230);
		StockExchange se4 = new StockExchange(1, 20, s1);
		Product m3 = new Product("LinkedIn", se4, Company.MICROSOFT, 15, 230);
		Product m2 = new Product("GitHub", m3, Company.MICROSOFT, 18, 250);
		Product m1 = new Product("Xbox", m2, Company.MICROSOFT, 15, 230);
		
		Square bankHoliday = new Square("Bank Holiday", m1);
		
		StockExchange se3 = new StockExchange(1, 20, bankHoliday);
		Product amd2 = new Product("Ryzen", se3, Company.AMD, 65, 270);
		Product amd1 = new Product("SeaMicro", amd2, Company.AMD, 70, 280);
		AntiTrust antiTrust1 = new AntiTrust(100, amd1);
		Product sm3 = new Product("Samsung M7", antiTrust1, Company.SAMSUNG, 10, 210);
		Product sm2 = new Product("Galaxy Tab", sm3, Company.SAMSUNG, 12, 220);
		Product sm1 = new Product("Galaxy Phone", sm2, Company.SAMSUNG, 10, 210);
		
		ProductBidding pb1 = new ProductBidding(150, sm1);
		
		StockExchange se2 = new StockExchange(1, 20, pb1);
		Product a3 = new Product("iPhone", se2, Company.APPLE, 7, 178);
		Product a2 = new Product("iPad", a3, Company.APPLE, 9, 195);
		Product a1 = new Product("iPod", a2, Company.APPLE, 7, 178);
		StockExchange se1 = new StockExchange(1, 20, a1);
		Product g2 = new Product("FitBit", se1, Company.GOOGLE, 5, 140);
		Product g1 = new Product("Google Pixel", g2, Company.GOOGLE, 5, 140);
		
		StartOfQuarter startingSquare = new StartOfQuarter(g1);
		
		se6.setNextSquare(startingSquare);
		
		return startingSquare;
	}
	
	private static void rollDice(Player plyr) {
		int num1 = (int) (Math.random() * 6) + 1;
		int num2 = (int) (Math.random() * 6) + 1;

		int diceTotal = num1 + num2;
		System.out.println("Dice 1: " + num1);
		System.out.println("Dice 2: " + num2);
		System.out.println("Your total roll: " + diceTotal);
		plyr.movePlayer(diceTotal);
		
		if (num1 == num2) {
			System.out.println("\nYou rolled doubles, roll again");
			System.out.println("Press \"ENTER\" to continue...");
			input.nextLine();
			rollDice(plyr);
		}
	}
	
	private static void requestWarehouse(Player plyr)  {
	    Product[] ownedProducts = plyr.getOwnedProducts();
	    if(ownedProducts.length == 0) {
	    	 System.out.println("You do not own any products");
	    } else {
	    	String[] options = new String[ownedProducts.length];
	    	for(int i=0; i < ownedProducts.length; i++) {
	    		options[i] = ownedProducts[i].getName();
	    	}
			
			Menu prdctMenu = new Menu("Owned Products", options);
			
			System.out.println("Choose a Product to dedict a warehouse too:");
			
			int choice = prdctMenu.getUserChoice();
	    	for(int i=0; i < ownedProducts.length; i++) {
	    		if(choice-1 == i) {
	    			ownedProducts[i].buildWarehouse(plyr);
	    		}
	    	}
	    }
	}
	
	private static void requestCountry(Player plyr)  {
	    Product[] ownedProducts = plyr.getOwnedProducts();
	    if(ownedProducts.length == 0) {
	    	 System.out.println("You do not own any products");
	    } else {
	    	// for demo purposes the user cannot purchase a country
	    }
	}
	
	private static void landedOnProduct(Player plyr, Player allPlayers[]) {
		Product currentPrdct = (Product) plyr.getPresentSquare();
		boolean owned = false;
		
		for(Player currentPlyr: allPlayers) {
			Product[] ownedProducts = currentPlyr.getOwnedProducts();
			for(Product prdct: ownedProducts) {
				if(prdct != null) {
					if(currentPlyr.equals(plyr) && prdct.equals(currentPrdct)) {
						System.out.println("You already own this product!");
						owned = true;
						break;
					} else if(prdct.equals(currentPrdct)) {
						System.out.println("You have landed on a product someone else owns!");
						System.out.println("You have been charged £" + prdct.getAmountToCharge() + " to use this product.");
						plyr.moneyOut(prdct.getAmountToCharge());
						currentPlyr.moneyIn(prdct.getAmountToCharge());
						owned = true;
						break;
					}
				}
			}
		}
		System.out.println();
		if(!owned) {
			System.out.println("This product costs ");
			
			String[] options = {"Buy!", "Skip"};

			Menu stockExchangeMenu = new Menu("Buy Product?", options);
			
			int choice = stockExchangeMenu.getUserChoice();
			
			System.out.println();
			
			if(choice == 1) {
				if(plyr.moneyOut(currentPrdct.getPrice())) {
					plyr.addProduct(currentPrdct);
					System.out.println("You have successfully bought this product!");
				} else {
					System.out.println("You do not have the funds to buy this product!");
				}
				
			} else if (choice == 2) {
				System.out.println("You have chosen not to buy this product :(");
			}
		}
	}
	
	private static void landedOnStockExchange(Player plyr) {	
		// for demo purposes if the player invests in the stock exchange they will always win and gain £100
		String[] options = {"Invest", "Skip"};

		Menu stockExchangeMenu = new Menu("Stock Exchange", options);
		
		int choice = stockExchangeMenu.getUserChoice();
		
		if(choice == 1) {
			StockExchange currentSE = (StockExchange) plyr.getPresentSquare();
			System.out.println("You have chosen to play the stock exchange game :)");
			System.out.print("Chose a number between " + currentSE.getLowNum() + " and " + currentSE.getHighNum() + ": ");
			input.nextInt();
			System.out.println("Congragualtions you have won £100!");
			plyr.moneyIn(100);
		} else if (choice == 2) {
			System.out.println("You have chosen not to play the stock exchange game :(");
		}
	}
	
	private static void landedOnProductBidding (Player plyr, Player allPlayers[]) {
		System.out.println("Your products are now up for auction!");
	}
	
	private static Player[] emliminatePlayer(Player eliminatedPlayer, Player allPlayers[]) {
		Player temp[] = new Player[allPlayers.length-1];

		int countForTemp = 0;
		
		for(int i = 0; allPlayers.length>i; i++) {
			if(!(allPlayers[i] == eliminatedPlayer)) {
				temp[countForTemp] = allPlayers[i];
				countForTemp++;
			}
		}
		
		allPlayers = temp;
		
		return allPlayers;
	}
}