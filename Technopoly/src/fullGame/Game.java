package fullGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Game {
	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Hello, Welcome to Technopoly!");
		System.out.println();
		
		final int MAX_PLAYERS = 5;
		final int MIN_PLAYERS = 2;
		
		String[] options = {"yes", "no"};

		Menu instructionsMenu = new Menu("Would you like to see the instructions?", options);
		int instructionsChoice = instructionsMenu.getUserChoice();
		
		if(instructionsChoice == 1) {
			printInstructions();
		}
		
		System.out.println();
		
		// checks if there is currently a saved game, 
		// to see if the option to load the game should be given
		boolean isSavedGame = true;
		try {
			File plyrFile = new File("playerInfo.csv");
			Scanner plyrSc = new Scanner(plyrFile);
			plyrSc.close();
		} catch (FileNotFoundException e) {
			isSavedGame = false;
		}
		
		Player[] allPlayers;
		Company[] allComp = null;
		
		if(!isSavedGame) {
			allPlayers = inputPlyrNms(MIN_PLAYERS, MAX_PLAYERS);
			
			allComp = createBoard(allPlayers);
		} else {
			int userChoice;

			Menu loadGameMenu = new Menu("Would you like to load the last saved game?", options);
			userChoice = loadGameMenu.getUserChoice();
			
			if(userChoice == 1) {
				allPlayers = new Player[MAX_PLAYERS];
				
				for(int i=0; i<allPlayers.length; i++) {
					allPlayers[i] = new Player("Temp Player " + i);
				}

				allComp = createBoard(allPlayers);
				
				allPlayers = loadGame(allPlayers[0].getPresentSquare());
			} else {
				System.out.println();
				allPlayers = inputPlyrNms(MIN_PLAYERS, MAX_PLAYERS);
				allComp = createBoard(allPlayers);
			}

		}

		System.out.println();
		System.out.println("The first years limit is �" + generateIncomeThreshold(allPlayers.length));
		
		// Every iteration of the loop below is a 'round' within the game
		do {
			// Every iteration of the loop below is a player's turn
			for (Player currentPlyr : allPlayers) {
				System.out.println();
				System.out.println("It is " + currentPlyr.getName() + "'s go!");
				System.out.println();

				allPlayers = playersTurn(currentPlyr, allPlayers, allComp);
				
				if (allPlayers.length == 1) {
					System.out.println();
					System.out.println("Congratulations to " + allPlayers[0].getName() + " you have the game!");
					input.close();
					return;
				}
			}
		} while (allPlayers.length > 1);
		
		input.close();
	}
	
	/**
	 * This method creates the relevant objects to create the board
	 * @author AdamLogan
	 * @param allPlyrs - an array of all the players within the game
	 * @return - An array of the companies in the game
	 */
	public static Company[] createBoard(Player[] allPlyrs) {
		StockExchange se6 = new StockExchange("Berlin", 1, 25, 1.0, null);
		Product i3 = new Product("Xeon E", se6, 26, 310, new int[] { 130, 390, 805, 1170 }, 250, 500);
		Product i2 = new Product("Core i7", i3, 29, 330, new int[] { 155, 435, 900, 1305 }, 250, 500);
		Product i1 = new Product("Pentium Gold", i2, 26, 310, new int[] { 130, 390, 805, 1170 }, 220, 500);
	
		StockExchange se5 = new StockExchange("New York", 1, 20, 1.0, i1);
		Product amz2 = new Product("Kindle", se5, 40, 370, new int[] { 200, 600, 1120, 1600 }, 750, 1000);
		Product amz1 = new Product("Prime", amz2, 35, 410, new int[] { 185, 525, 980, 1400 }, 550, 800);
	
		ProductBidding pb2 = new ProductBidding(300, "Sotheby's", amz1);
	
		Product s3 = new Product("Sony Alpha III", pb2, 19, 230, new int[] { 95, 285, 685, 1000 }, 100, 210);
		Product s2 = new Product("Sony Experia", s3, 21, 240, new int[] { 105, 315, 755, 1090 }, 205, 440);
		Product s1 = new Product("PlayStation", s2, 19, 230, new int[] { 95, 285, 685, 1000 }, 100, 210);
		StockExchange se4 = new StockExchange("Paris", 1, 17, 0.75, s1);
		Product m3 = new Product("LinkedIn", se4, 15, 230, new int[] { 75, 225, 575, 825 }, 50, 175);
		Product m2 = new Product("GitHub", m3, 18, 250, new int[] { 90, 270, 660, 990 }, 150, 300);
		Product m1 = new Product("Xbox", m2, 15, 230, new int[] { 75, 225, 575, 825 }, 30, 125);
	
		Square bankHoliday = new Square("Bank Holiday", m1);
	
		StockExchange se3 = new StockExchange("Hong Kong", 1, 8, 0.1, bankHoliday);
		Product amd2 = new Product("Ryzen", se3, 65, 270, new int[] { 70, 210, 560, 800 }, 50, 180);
		Product amd1 = new Product("SeaMicro", amd2, 70, 280, new int[] { 65, 195, 520, 740 }, 45, 150);
		AntiTrust antiTrust1 = new AntiTrust(0.1, amd1);
		Product sm3 = new Product("Samsung M7", antiTrust1, 10, 210, new int[] { 60, 150, 450, 600 }, 145, 295);
		Product sm2 = new Product("Galaxy Tab", sm3, 12, 220, new int[] { 50, 180, 540, 720 }, 90, 220);
		Product sm1 = new Product("Galaxy Phone", sm2, 10, 210, new int[] { 60, 150, 450, 600 }, 145, 295);
	
		ProductBidding pb1 = new ProductBidding(150, "Christie's", sm1);
	
		StockExchange se2 = new StockExchange("London", 1, 15, 0.5, pb1);
		Product a3 = new Product("iPhone", se2, 7, 178, new int[] { 35, 105, 315, 460 }, 20, 50);
		Product a2 = new Product("iPad", a3, 9, 195, new int[] { 45, 135, 405, 595 }, 25, 70);
		Product a1 = new Product("iPod", a2, 7, 178, new int[] { 35, 105, 315, 460 }, 20, 50);
		StockExchange se1 = new StockExchange("Tokyo", 1, 10, 0.25, a1);
		Product g2 = new Product("FitBit", se1, 5, 140, new int[] { 25, 75, 225, 400 }, 10, 35);
		Product g1 = new Product("Google Pixel", g2, 5, 140, new int[] { 25, 75, 225, 400 }, 15, 45);
		
		StartOfQuarter startingSquare = new StartOfQuarter(g1, 150);
		
		se6.setNextSquare(startingSquare);
	
		// setting companies
		Company[] allCmp = new Company[8];
		Product[] ms = { m1, m2, m3 };
		Company micro = new Company("Microsoft", ms);
		allCmp[0] = micro;
	
		Product[] is = { i1, i2, i3 };
		Company intel = new Company("Intel", is);
		allCmp[1] = intel;
	
		Product[] amds = { amd1, amd2 };
		Company amd = new Company("AMD", amds);
		allCmp[2] = amd;
	
		Product[] ss = { s1, s2, s3 };
		Company son = new Company("Sony", ss);
		allCmp[3] = son;
	
		Product[] amzs = { amz1, amz2 };
		Company amaz = new Company("Amazon", amzs);
		allCmp[4] = amaz;
	
		Product[] as = { a1, a2, a3 };
		Company app = new Company("Apple", as);
		allCmp[5] = app;
	
		Product[] gs = { g1, g2 };
		Company goog = new Company("Google", gs);
		allCmp[6] = goog;
	
		Product[] sms = { m1, m2, m3 };
		Company sam = new Company("Samsung", sms);
		allCmp[7] = sam;
	
		for (Player plyr : allPlyrs) {
			plyr.setPresentSquare(startingSquare);
		}
				
		return allCmp;
		
	}

	public static void printInstructions() {
		System.out.println();
		System.out.println("Navigation:");
		System.out.println("   -Select menu options by entering the option's corresponding number.");
		System.out.println("Products:");
		System.out.println("   -Landing on a product that is not owned will allow you to purchase that product,");
		System.out.println("    as long as you have enough money.");
		System.out.println("   -If you land on a product someone else owns, you are charged for using that product");
		System.out.println("    and the player who owns the product recieves this money.");
		System.out.println("       -Even if the the player who landed on the product does not have the funds to pay,");
		System.out.println("        the player who owns the product will still recieve the full amount.");
		System.out.println("   -When a player is eliminated, their owned products can be purchased again by other players");
		System.out.println("   -You can invest in warehouses for your products and once you have all the warehouses on");
		System.out.println("    all products in a given company, you can export those products to another country.");
		System.out.println("        -This will increase the amount you can charge other players.");
		System.out.println("Stock Exchange Game:");
		System.out.println("   -The stock exchange game allows the player to invest some of their money by");
		System.out.println("    selecting a number from a given range, if you guess the correct number then");
		System.out.println("    you win money, with the amount depending on the initial investment.");
		System.out.println("   -If the incorrect number is guessed then you will lose money depending on how");
		System.out.println("    incorrect you are.");
		System.out.println("Anti-Trust:");
		System.out.println("   -If you land on the anti-trust square you will be fined a certain amount of your money.");
		System.out.println("Bank Holiday:");
		System.out.println("   -If you land on this square you can take a break!");
		System.out.println("End of Year:");
		System.out.println("   -Every fourth time a player makes a full lap of the board, the income event will");
		System.out.println("    be triggered.");
		System.out.println("   -The income event checks the players balance against a given number which changes through the game.");
		System.out.println("   -If the player's balance is too low they are eliminated");
		System.out.println("End of Quarter:");
		System.out.println("   -At the end of every quarter you will get a bonus!");
		System.out.println("Auction:");
        System.out.println("   -The bidding feature takes one property owned by the player and puts it up for");
        System.out.println("    auction to the rest of the players, allowing them to place bids.");
        System.out.println("   -If the product has warehouses or countries, these are lost when the product transferred.");
        System.out.println("   -There is a minimum bid for each auction.");
		System.out.println("Elimination:");
		System.out.println("   -A player is eliminated once their balance reaches 0.");
		System.out.println("Winning:");
		System.out.println("   -The last player left in the game will be the winner, meaning the ultimate goal of");
		System.out.println("    the game is to make the other players go bankrupt.");
		System.out.println("   -If the game ends early then the player(s) with the highest balance win the game.");
	}

	/**
	 * This allows the user's to create their profiles for the game
	 * @author AdamLogan
	 * @param MIN_PLAYERS - The minimum amount of players allowed
	 * @param MAX_PLAYERS - The maximum amount of players allowed
	 * @return - An array of the Player objects
	 */
	public static Player[] inputPlyrNms(final int MIN_PLAYERS, final int MAX_PLAYERS) { 
		String totalPlyrMessg = "How many players are there (" + MIN_PLAYERS + " - " + MAX_PLAYERS + "): ";
		
		int totalPlayers = validateRange(MIN_PLAYERS, MAX_PLAYERS, totalPlyrMessg);
		
		Player[] allPlayers = new Player[totalPlayers];

		for (int i = 0; totalPlayers > i; i++) {
			System.out.print("Please enter player " + (i + 1) + "s name: ");
			String name = input.nextLine();
			name = name.strip();
			boolean valid = true;
			char[] charArray = name.toCharArray();
			// checks for any special characters or digits in the name
		    for(char c:charArray) {
		        if (!Character.isLetter(c) && c != ' ') {
		            valid = false;
		            System.out.println("You cannot have special characters or numbers in your name, please choose another name.");
		            i--;
		            break;
		    	}
		    }
		
			Player newPlyr = new Player(name);
			
			// checks if the player with the same name has already been added
			for(int j = 0; j<i; j++) {
				if(allPlayers[j].equals(newPlyr)) {
					valid = false;
					i--;
					System.out.println("Another player has that name, please choose another name. ");
					break;
				}
			}
			
			if(valid) {
				allPlayers[i] = newPlyr;
			}
		}
		return allPlayers;
	}
	
	/**
	 * This method consists of giving the current players several options and,
	 * depending on the option that has been selected, executes the appropriate
	 * function
	 * 
	 * @author AdamLogan
	 * @param plyr - the current player
	 * @param allPlayers - an array of all the players within the game
	 * @return an updated array of the players within the game
	 */
	public static Player[] playersTurn(Player plyr, Player[] allPlayers, Company[] allCmps) {
		int userChoice;
		String[] options = validOptions(plyr, plyr.getOwnedProducts(), allCmps);
	
		Menu myMenu = new Menu("Player Options", options);
		userChoice = myMenu.getUserChoice();
	
		switch (userChoice) {
			case 2:
				System.out.println();
				System.out.println(plyr);
				return playersTurn(plyr, allPlayers, allCmps);
			case 3:
				printInstructions();
				System.out.println();
				return playersTurn(plyr, allPlayers, allCmps);
			case 4:
				System.out.println(plyr.getName() + " has left the game!");
				allPlayers = eliminatePlayer(plyr, allPlayers);
				return allPlayers;
			case 5:
				closeGame(allPlayers);
				return allPlayers;
			case 6:
				if(saveGameState(allPlayers, plyr)) {
					System.out.println("Goodbye!");
					input.close();
					System.exit(0);
				} else {
					System.out.println();
					return playersTurn(plyr, allPlayers, allCmps);
				}
				break;
			case 7:
				requestWarehouse(plyr);
				break;
			case 8:
				requestCountry(plyr, allCmps);
				break;
		}
		
		
		int bonusAmnt = plyr.movePlayer(rollDice(plyr, allPlayers.length));
		
		if(bonusAmnt != 0) {
			System.out.println();
			System.out.println("Congratulations for making it through a whole quarter, have a bonus of �" + bonusAmnt);
		}
		
		// the if statement below is code that relates to the income event
		if(plyr.getTimesPassedStart() % 4 == 0 && plyr.getTimesPassedStart() != 0) {
			if(incomeEvent(plyr, allPlayers.length)) {
				System.out.println(plyr.getName() + " has been eliminated :(");
				System.out.println();
				allPlayers = eliminatePlayer(plyr, allPlayers);
				System.out.println("The new limit for the year is �" + generateIncomeThreshold(allPlayers.length));
				return allPlayers;
			}
			plyr.setTimesPassedStart(0);
		}
		
		System.out.println();
		System.out.println("You Landed on " + plyr.getPresentSquare().getName());
		System.out.println();
		Square currentSqr = plyr.getPresentSquare();
	
		// Depending on which class the player's current square is a different method needs to be called
		boolean stillInGame = true;
		if (currentSqr instanceof Product) {
			stillInGame = landedOnProduct(plyr, allPlayers);
		} else if (currentSqr instanceof StockExchange) {
			stillInGame = landedOnStockExchange(plyr);
		} else if (currentSqr instanceof ProductBidding) {
			landedOnProductBidding(plyr, allPlayers);
		} else if (currentSqr instanceof AntiTrust) {
			System.out.println(
					"Oh no! You are being fined " + ((AntiTrust) currentSqr).getLevelOfFine() * 100
							+ "% by the govt for violating anti trust law!");
			stillInGame = ((AntiTrust) currentSqr).fine(plyr);
		} else {
			System.out.println("Have a rest it's a Bank Holiday!");
		}
	
		if(!stillInGame) {
			System.out.println(plyr.getName() + " has been eliminated :(");
			System.out.println();
			allPlayers = eliminatePlayer(plyr, allPlayers);
			System.out.println("The new limit for the year is �" + generateIncomeThreshold(allPlayers.length));
		}
	
		return allPlayers;
	}

	/**
	 * Rolls the dice for the user to move squares
	 * @author XXXXX
	 * @param plyr - the current player
	 * @param totalPlyrs - the number of players within the game
	 * @return - the number of spaces to move the player
	 */
	public static int rollDice(Player plyr, int totalPlyrs) {
		int num1 = (int) (Math.random() * 6) + 1; int num2 = (int) (Math.random() * 6) + 1;
		System.out.println();
		int diceTotal = num1 + num2; System.out.println("Dice 1: " + num1);
		System.out.println("Dice 2: " + num2); System.out.println("Your total roll: " + diceTotal); 
		if (num1 == num2) {  
			System.out.println("\nYou rolled doubles, roll again");
			System.out.println("Press \"ENTER\" to continue..."); input.nextLine();
			return diceTotal + rollDice(plyr, totalPlyrs); 
		} 
		return diceTotal;
	}

	/**
	 * This method decides what options are valid to display in the menu.
	 * 
	 * @author XXXXX 
	 * @param plyr
	 * @param ownedProducts
	 * @param allCmps
	 * @return
	 */
	public static String[] validOptions(Player plyr, Product ownedProducts[], Company[] allCmps) {
		int numOfOpts = 6;
		if (ownedProducts.length != 0) {
			numOfOpts = 8;
		}
		String options[] = new String[numOfOpts];
		options[0] = "Roll dice";
		options[1] = "Check inventory";
		options[2] = "Help";
		options[3] = "Leave Game";
		options[4] = "Close Game";
		options[5] = "Save and Close Game";
		if (ownedProducts.length != 0) {
			options[6] = "Purchase a warehouse";
			ArrayList<Product> validPrdcts= prdctsToRelocate(plyr, allCmps);
			if(validPrdcts.size() != 0) {
				options[7] = "Purchase a country";
			}
		} 
		return options;
	}

	/**
	 * This method contains the income event function which eliminates players whos
	 * income is below a given value.
	 * @author XXXXX
	 * @param currentPlayer - The player who triggers the event
	 * @param totalPlyrs - number of players in the game
	 * @return - true if player needs to be eliminated
	 */
	public static boolean incomeEvent(Player currentPlayer, int totalPlyrs) {
		System.out.println("\nIt is now the end of the year!");
		System.out.println("Lets see if you have enough funds to continue.");
		int limitForYear = generateIncomeThreshold(totalPlyrs);
		if (currentPlayer.getBalance() < limitForYear) {
			System.out.println("Sadly you have less than �" + limitForYear);
			return true;
		} else if (totalPlyrs - 1 != 0){
			System.out.println("Congratulations you have more than �" + limitForYear);
		}
		return false;
	}

	/**
	 * This is the formula for generating the threshold for the income event
	 * @author XXXXX
	 * @param totalPlyrs - the number of Players currently in the game
	 * @return - The threshold for the income event
	 */
	public static int generateIncomeThreshold(int totalPlyrs) {
		return (int) Math.floor(1000 / totalPlyrs);
	}

	/**
	 * Loads the saved game using the details from the .csv file
	 * @author AdamLogan
	 * @param baseSqr - Any Square that has been created for the game
	 * @return - The updated Player objects
	 */
	public static Player[] loadGame(Square baseSqr) {
		try {
			ArrayList<Player> tmpPlyrs = new ArrayList<Player>();
			
			File plyrFile = new File("playerInfo.csv");
			Scanner plyrSc = new Scanner(plyrFile);

			plyrSc.nextLine(); // to take out the header
			
			while (plyrSc.hasNextLine()) {
				String record = plyrSc.nextLine(); // Saves the current line in the Scanner to a String.
				String[] plyrDetails = record.split(",");
				
				Player plyr = new Player(plyrDetails[0]);
				
				Square nxtSqr = baseSqr;
				Square prsntSqr = null;
				
				String[] owndPrdctsNames = plyrDetails[1].split(";");
				Product[] owndPrdcts = new Product[owndPrdctsNames.length];
				
				do {
					if(nxtSqr instanceof Product) {
						Product recordedPrdct = (Product) nxtSqr; 
						// loads the necessary products into the game
						loadPrdct(recordedPrdct);
						
						for(int i=0; i<owndPrdcts.length; i++) {
							if(recordedPrdct.getName().equals(owndPrdctsNames[i])) {
								owndPrdcts[i] = recordedPrdct;
							}
						}
					} 
					
					if(nxtSqr.getName().equals(plyrDetails[4])) {
						prsntSqr = nxtSqr;
					}
					nxtSqr = nxtSqr.getNextSquare();
				} while(!baseSqr.equals(nxtSqr));
				
				if(!plyrDetails[1].equals("null")) {
					plyr.setOwnedProducts(owndPrdcts);
				}
				
				plyr.setBalance(Integer.parseInt(plyrDetails[2]));
				plyr.setTimesPassedStart(Integer.parseInt(plyrDetails[3]));
				plyr.setPresentSquare(prsntSqr);
				
				tmpPlyrs.add(plyr);
			}
			
			Player[] allPlyrs = new Player[tmpPlyrs.size()];
			for(int i=0; i<allPlyrs.length; i++) {
				allPlyrs[i] = tmpPlyrs.get(i);
			}
			
			plyrSc.close();
			
			return allPlyrs;
		} catch (FileNotFoundException e) {
			System.out.println("There is not a game to load.");
			System.out.println();
			return null;
		}
	}
	
	/**
	 * Updates the Product details from the last saved game
	 * @author AdamLogan
	 * @param prdct - The Product to be updated
	 */
	public static void loadPrdct(Product prdct) {
		try {
			File prdctFile = new File("productInformation.csv");
			Scanner prdctrSc = new Scanner(prdctFile);

			prdctrSc.nextLine(); // to take out the header 
			
			while (prdctrSc.hasNextLine()) {
				String record = prdctrSc.nextLine(); // Saves the current line in the Scanner to a String
				String[] prdctDetails = record.split(",");
				
				if(prdctDetails[0].equals(prdct.getName())) {
					prdct.setWarehousesBought(Integer.parseInt(prdctDetails[1]));
					prdct.updateCostOfWarehouse();
					prdct.setAmountToCharge(Integer.parseInt(prdctDetails[2]));
					prdct.setCostOfRelocation(Integer.parseInt(prdctDetails[4]));
					
					String[] owndCntryNms = prdctDetails[3].split(";");
					Country[] owndCountrys = new Country[owndCntryNms.length];
					
					// checks which Country object needs to be loaded into the product
					for(int i=0; i<owndCountrys.length; i++) {
						switch (owndCntryNms[i]) {
							case "United Kingdom":
								owndCountrys[i] = Country.UK;
								break;
							case "United States":
								owndCountrys[i] = Country.USA;
								break;
							case "Japan":
								owndCountrys[i] = Country.JAPAN;
								break;
							case "Canada":
								owndCountrys[i] = Country.CANADA;
								break;
							case "France":
								owndCountrys[i] = Country.FRANCE;
								break;
							case "Italy":
								owndCountrys[i] = Country.ITALY;
								break;
							case "Germany":
								owndCountrys[i] = Country.GERMANY;
								break;
						}
					}
					if(!prdctDetails[3].equals("null")) {
						prdct.setCountriesOwned(owndCountrys);
					}
					
				}
			}
			
			prdctrSc.close();
		} catch (FileNotFoundException e) {
			System.out.println("There are no products to load.");
			System.out.println();
		}
	}
	
	/**
	 * Saves the details of the current game
	 * @author AdamLogan
	 * @param allPlyrs - An array of the player's to be saved for the next game
	 * @param currntPlyr - The player who decided to save the game
	 * @return - If saved successfully returns true and false otherwise
	 */
	public static boolean saveGameState(Player[] allPlyrs, Player currntPlyr) {
		// should only need to update the products owned by the players as these should
		// be the only products changed from their default stances
		try {
			PrintWriter plyrInfo = new PrintWriter("playerInfo.csv");
			PrintWriter prdctInfo = new PrintWriter("productInformation.csv");
			
			plyrInfo.println("Player Name,Products Owned,Balance,Times Passed Start,Present Square");
			
			prdctInfo.println("Product Name,Warehouses Bought,Amount To Charge,Countries Owned,Cost Of Relocation");
			
			for (int i = 0; i < allPlyrs.length; i++) {
				if(currntPlyr.equals(allPlyrs[i])) {
					allPlyrs = orderOfPlay(allPlyrs, i);
					break;
				}
			}
			
			// saves the player details
			for (int i = 0; i < allPlyrs.length; i++) {
				plyrInfo.println(allPlyrs[i].getName() + "," + arryForCSV(allPlyrs[i].getOwnedProducts()) + "," + 
			            allPlyrs[i].getBalance() + "," + allPlyrs[i].getTimesPassedStart() + "," + 
						allPlyrs[i].getPresentSquare().getName());
				
				// saves the product details, only needs to save the owned products as these are the only
				// products that could change
				for(Product prdct: allPlyrs[i].getOwnedProducts()) {
					prdctInfo.println(prdct.getName() + "," + prdct.getWarehousesBought() + "," + 
							prdct.getAmountToCharge() + "," + arryForCSV(prdct.getCountriesOwned()) + "," 
							+ prdct.getCostOfRelocation());
				}
			}
			plyrInfo.close();
			prdctInfo.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Please close the files 'productInformation.csv' and 'playerInfo.csv'.");
			return false;
		}
	}
	
	/**
	 * Changes the order of the array
	 * @author AdamLogan
	 * @param currntOrder - an array of the current player's
	 * @param StartingPlyr - the index of the player to be at the beginning of the array
	 * @return - An array in the new order
	 */
	public static Player[] orderOfPlay(Player[] currntOrder, int StartingPlyr) {
		Player[] newOrder = new Player[currntOrder.length];
		
		for(int i=0; i<currntOrder.length+StartingPlyr; i++) {
			if(i >= currntOrder.length) {
				newOrder[i-StartingPlyr] = currntOrder[i-currntOrder.length];
			}
			else if(i >= StartingPlyr) {
				newOrder[i-StartingPlyr] = currntOrder[i];
			}
		}
		return newOrder;
	}
	
	/**
	 * Formats an array for the csv file
	 * @author AdamLogan
	 * @param prdcts - the array to be formatted
	 * @return - the formatted string to be inputed into the csv file
	 */
	public static String arryForCSV(Product[] prdcts) {
    	String output = "";
    	if(prdcts.length == 0) {
    		return "null";
    	}
		for(int i=0; i<prdcts.length; i++) {
			if(i+1 != prdcts.length) {
				output += prdcts[i].getName() + ";";
			} else {
				output += prdcts[i].getName();
			}
			
		}
		return output + "";
    }
    
	/**
	 * Formats an array for the csv file
	 * @author AdamLogan
	 * @param cntrys - the array to be formatted
	 * @return - the formatted string to be inputted into the csv file
	 */
	public static String arryForCSV(Country[] cntrys) {
    	String output = "";
    	if(cntrys.length == 0) {
    		return "null";
    	}
		for(int i=0; i<cntrys.length; i++) {
			if(i+1 != cntrys.length) {
				output += cntrys[i] + ";";
			} else {
				output += cntrys[i];
			}
		}
		return output;
    }

	
	/**
	 * Formats the final message and exits the program 
	 * @author AdamLogan
	 * @param allPlayers - all the players currently in the game
	 */
	public static void closeGame(Player allPlayers[]) {
		ArrayList<Player> winningPlyrs = highestBalance(allPlayers);
		String namesOfWinners = "";
		for(int i = 0; i<winningPlyrs.size(); i++) {
			if(i+1 == winningPlyrs.size()) {
				namesOfWinners += winningPlyrs.get(i).getName();
			} else if (i+2 == winningPlyrs.size()) {
				namesOfWinners += winningPlyrs.get(i).getName() + " and ";
			} else {
				namesOfWinners += winningPlyrs.get(i).getName() + ", ";
			}
		}
		System.out.println();
		System.out.println("Congratulations to " + namesOfWinners + " for winning the game!");
		System.out.println("Goodbye!");
		input.close();
		System.exit(0);
	}
	
	/**
	 * Gets the players with the highest balance
	 * @author AdamLogan
	 * @param allPlyrs - the array of players to be checked
	 * @return - An array list of the players with the highest balance
	 */
	public static ArrayList<Player> highestBalance(Player allPlyrs[]) {
		ArrayList<Player> winningPlyrs = new ArrayList<Player>();
		Player maxPlyr = new Player("");
		maxPlyr.setBalance(-2);
		
		for(Player plyr : allPlyrs) {
			if (maxPlyr.getBalance() <= plyr.getBalance()) {
				maxPlyr = plyr;
			}
		}
		
		// this second loop is needed to get all the players with the 
		// highest balance in an array list
		for(Player plyr : allPlyrs) {
			if (maxPlyr.getBalance() == plyr.getBalance()) {
				winningPlyrs.add(plyr);
			}
		}
		
		return winningPlyrs;
	}

	/**
	 * This method will allow the player to pick a property they own to dedicate a warehouse to
	 * @author AdamLogan
	 * @param plyr - the current player
	 */
	public static void requestWarehouse(Player plyr) {
		Product[] ownedProducts = plyr.getOwnedProducts();
		Product[] buildableProducts = new Product[ownedProducts.length];

		if (ownedProducts.length == 0) {
			System.out.println("You do not own any products");
			return;
		} 
		
		String[] options = new String[ownedProducts.length + 1];
		options[options.length-1] = "Cancel";
		// gets the names of all the products owned by the customer and
		// puts them into a menu for the user while also checking if the
		// product has reached the max capacity for warehouses
		int buildableCount = 0;
		for (int i = 0; i < ownedProducts.length; i++) {
			int warehouseCost = ownedProducts[i].getCostOfWarehouse();
			if (ownedProducts[i].getWarehousesBought() < 4 && plyr.getBalance() > warehouseCost) {
				buildableProducts[buildableCount] = ownedProducts[i];
				options[buildableCount] = ownedProducts[i].getName() + " (Cost = �" +  warehouseCost + ")";
				buildableCount++;
			}
		}

		if(buildableCount == 0) {
			System.out.println();
			System.out.println("You cannot build a warehouse on any of your products, as they have reached capacity or you do not have the funds to buy any warehouses.");
			System.out.println();
			return;
		}
			
		Menu prdctMenu = new Menu("Choose a Product to dedicate a warehouse too", options);

		System.out.println();
		int choice = prdctMenu.getUserChoice();
		
		if(choice == buildableProducts.length + 1) {
			System.out.println();
			System.out.println("You have chosen not to build a warehouse on any products.");
			return;
		}
		
		for (int i = 0; i < buildableProducts.length; i++) {
			if (choice - 1 == i) {
				if (!buildableProducts[i].buildWarehouse(plyr)) {
					System.out.println("You do not have the funds to build the warehouse.");
				} else {
					System.out.println();
					System.out.println("You have successfully dedicated a warehouse too " + buildableProducts[i].getName() + ".");
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * Presents he menus relevant to relocate a product, charges the user
	 * to relocate a country and relocates the country
	 * @author AdamLogan & XXXXX
	 * @param plyr - the current player
	 * @param allCmps - all the companies within the game
	 */
	public static void requestCountry(Player plyr, Company[] allCmps) {
		ArrayList<Product> prdcts = prdctsToRelocate(plyr, allCmps);
		
		Country[] allCntrys = {Country.UK, Country.USA, Country.CANADA,
				Country.FRANCE, Country.GERMANY, Country.IRELAND,
				Country.ITALY, Country.JAPAN};
		
		String[] prdctOptions = new String[prdcts.size() + 1];
		for(int i=0; i<prdcts.size(); i++) {
			prdctOptions[i] = prdcts.get(i).getName() + " (Cost = �" + prdcts.get(i).getCostOfRelocation() + ")";
		}
		
		prdctOptions[prdctOptions.length-1] = "Cancel";
		
		Menu prdctMenu = new Menu("Which product would you like to relocate?", prdctOptions);
		int prdctChoice = prdctMenu.getUserChoice();
		
		if(prdctChoice == prdctOptions.length) {
			System.out.println();
			System.out.println("You have decided not to relocate a country.");
			return;
		}
		Product prdctToRelocate = prdcts.get(prdctChoice-1);
		
		Country[] ownedCountries = prdctToRelocate.getCountriesOwned();
		
		Country[] validCntrys = new Country[allCntrys.length - ownedCountries.length];
		
		int validCntryCount = 0;
		String[] cntryOptions = new String[validCntrys.length];
		for(int i=0; i<allCntrys.length; i++) {
			if(!Arrays.asList(ownedCountries).contains(allCntrys[i])) {
				validCntrys[validCntryCount] = allCntrys[i];
				cntryOptions[validCntryCount] = validCntrys[validCntryCount].toString();
				validCntryCount++;
			}
		}
		
		Menu cntryMenu = new Menu("Which country would you like to relocate too?", cntryOptions);
		int cntryChoice = cntryMenu.getUserChoice();
		
		plyr.moneyOut(prdctToRelocate.getCostOfRelocation());
		
		System.out.println();
		System.out.println("�" + prdctToRelocate.getCostOfRelocation() + " has been withdrawn from your balance.");
		
		prdctToRelocate.addCountry(validCntrys[cntryChoice-1]);
	}
	
	/**
	 * This method gets all the products which the user can relocate
	 * @author XXXXX & AdamLogan
	 * @param plyr - the current player
	 * @param allCmps - all the companies within the game
	 * @return
	 */
	public static ArrayList<Product> prdctsToRelocate(Player plyr, Company[] allCmps) {
		ArrayList<Company> cmpnsOwned = companiesOwned(plyr, allCmps);
		ArrayList<Company> validCmpns = new ArrayList<Company>();
		
		ArrayList<Product> validPrdcts = new ArrayList<Product>();
		
		// An array of all the valid counties
		Country[] allCntrys = {Country.UK, Country.USA, Country.CANADA,
				Country.FRANCE, Country.GERMANY, Country.IRELAND,
				Country.ITALY, Country.JAPAN};
		
		for(Company cmp: cmpnsOwned) {
			int prdctsMaxWrhs = 0;
			Product[] prdctsInCmp = cmp.getPrdcts();
			for(Product prdct: prdctsInCmp) {
				if(prdct.getWarehouseCosts().length == prdct.getWarehousesBought()) {
					prdctsMaxWrhs++;
				}
			}
			
			if(prdctsMaxWrhs == prdctsInCmp.length) {
				validCmpns.add(cmp);
				for(Product prdct: prdctsInCmp) {
					if(prdct.getCostOfRelocation() <= plyr.getBalance() && 
							prdct.getCountriesOwned().length < allCntrys.length) {
						validPrdcts.add(prdct);
					}
				}
			}
		}
		
		return validPrdcts;
	}

	/**
	 * This method will return all the companies which the player owns all
	 * the products for
	 * @author AdamLogan & XXXXX
	 * @param plyr - the current player
	 * @param allCmps - the companies to be checked
	 * @return - An array list of the companies owned by the player
	 */
	public static ArrayList<Company> companiesOwned(Player plyr, Company[] allCmps) {
		Product[] ownedProducts = plyr.getOwnedProducts();
		ArrayList<Company> cmpnsOwned = new ArrayList<Company>();
		
		if(ownedProducts.length != 0) {
			for (Company cmp : allCmps) {
				Product[] cmpPrdcts = cmp.getPrdcts();
				if (Arrays.asList(ownedProducts).containsAll(Arrays.asList(cmpPrdcts))) {
					cmpnsOwned.add(cmp);
				}
			}
		}
		return cmpnsOwned;
	}

	/**
	 * This method displays the relevant options to the player depending on the
	 * state of the game. If another player owns the product this will remove and
	 * add the correct amount of funds from the players involved. If no player owns
	 * the product certain options will be displayed to the user.
	 * @author AdamLogan
	 * @param plyr - the current player
	 * @param allPlayers - an array of all the players within the game
	 * @return - true if the player is still in the game and false otherwise
	 */
	public static boolean landedOnProduct(Player plyr, Player allPlayers[]) {
		Product currentPrdct = (Product) plyr.getPresentSquare();

		for (Player currentPlyr : allPlayers) {
			Product[] ownedProducts = currentPlyr.getOwnedProducts();
			for (Product prdct : ownedProducts) {
				if (prdct != null) {
					if (currentPlyr.equals(plyr) && prdct.equals(currentPrdct)) {
						System.out.println("You already own this product!");
						return true;
					} else if (prdct.equals(currentPrdct)) {
						// the player has landed on a product someone else owns and therefore needs to be charged
						System.out.println("You have landed on " + currentPlyr.getName() + "'s product!");
						System.out.println(
								"You have been charged �" + prdct.getAmountToCharge() + " to use " + currentPrdct.getName() + ".");

						// the player who owns the product needs to be given the money owed to them
						currentPlyr.moneyIn(prdct.getAmountToCharge());
						return plyr.moneyOut(prdct.getAmountToCharge());
					}
				}
			}
		}

		// if no one owns the product then the player will be allowed to buy the product
		if(plyr.getBalance() > currentPrdct.getPrice()) {
			System.out.println("This product costs �" + currentPrdct.getPrice());
			System.out.println();

			String[] options = { "Buy!", "Skip" };

			Menu productMenu = new Menu("Buy " + currentPrdct.getName() + "?", options);

			int choice = productMenu.getUserChoice();

			System.out.println();

			if (choice == 1) {
				plyr.moneyOut(currentPrdct.getPrice());
				plyr.addProduct(currentPrdct);
				System.out.println("You have successfully bought " + currentPrdct.getName() + "!");
				

			} else if (choice == 2) {
				System.out.println("You have chosen not to buy " + currentPrdct.getName() + " :(");
			}
		} else {
			System.out.println("You do not have the funds to buy "+ currentPrdct.getName() + "!");
		}

		return true;
	}

	/**
	 * This method will give the user the choice to play the stock exchange game
	 * @author AdamLogan
	 * @param plyr - the player who landed on the stock exchange
	 * @return - true if the player is still in the game and false otherwise
	 */
	public static boolean landedOnStockExchange(Player plyr) {
		// for demo purposes if the player invests in the stock exchange they will
		// always win and gain 100
		String[] options = { "Invest", "Skip" };

		Menu stockExchangeMenu = new Menu("Stock Exchange", options);

		int choice = stockExchangeMenu.getUserChoice();

		if (choice == 1)
			return playingStockExchange(plyr);
		else if (choice == 2) {
			System.out.println("You have chosen not to play the stock exchange game :(");
		}

		return true;
	}

	/**
	 * This will ask the user for the input required to play the stock exchange game
	 * and displays the result
	 * @author AdamLogan
	 * @param plyr - the current player
	 * @return - true if the player is still in the game and false otherwise
	 */
	public static boolean playingStockExchange(Player plyr) {
		StockExchange currentSE = (StockExchange) plyr.getPresentSquare();
		System.out.println();
		System.out.println("You have chosen to play the stock exchange game :)");
		System.out.println();
	
		int invtAmnt = validatePlayerInptAmnt("investment", plyr);
	
		int highNum = currentSE.getHighNum();
		int lowNum = currentSE.getLowNum();
		String messg = "\nChose a number between " + lowNum + " and " + highNum + ": ";
		int guessedNum = validateRange(lowNum, highNum, messg);
	
		int result = currentSE.playGame(guessedNum, invtAmnt);
	
		// checks if the player needs to gain or lose money
		if (result > 0) {
			System.out.println("\nCongratulations you have won " + result + " :)");
			plyr.moneyIn(result);
		} else if (result < 0) {
			System.out.println("\nSadly you have lost �" + Math.abs(result) + " :(");
			return plyr.moneyOut(Math.abs(result));
		} else {
			System.out.println("\nYou didn't invest enough to lose any money!");
		}
	
		return true;
	}

	/**
	 * This method allows users to bid on a product of another player
	 * @author AdamLogan & XXXXX
	 * @param plyr - the player who landed on the square
	 * @param allPlayers - an array of all the players within the game
	 */
	public static void landedOnProductBidding(Player plyr, Player allPlayers[]) {
		Product[] ownedProducts = plyr.getOwnedProducts();
		ProductBidding crrntBiddingSquare = (ProductBidding) plyr.getPresentSquare();
		
		// exits function if the player owns no products
		if (ownedProducts.length == 0) {
			System.out.println("You do not own any products and therefore no bid can take place");
			return;
		} 
		
		boolean bidOnGoing = true;
		
		Product chosenProduct = crrntBiddingSquare.prdctToBid(ownedProducts);
		System.out.println("Your product " + chosenProduct.getName() + " is now up for auction!");
	
		// creates an array of the Players that can bid on the Product
		Player[] biddingPlayers = new Player[allPlayers.length - 1];
		int j = 0;
		for (int i = 0; i < allPlayers.length; i++) {
			if (!allPlayers[i].equals(plyr)) {
				biddingPlayers[j++] = allPlayers[i];
			}
		}
		
		Player winningPlayer = null;
		int lastBid = crrntBiddingSquare.getLowestBid();
		
		System.out.println();
		
		if(biddingPlayers.length == 1) {
			int newPrdctPrc = (int) Math.ceil(chosenProduct.getPrice() * 1.5);
			String takePrdct = "Would you like to buy " + chosenProduct.getName() + " for �" + newPrdctPrc + "?";
			String[] options = { "Yes", "No" };
			Menu takePrdctMenu = new Menu(takePrdct, options);
			int takePrdctChoice = takePrdctMenu.getUserChoice();
			
			if(takePrdctChoice == 1) {
				lastBid = newPrdctPrc;
				winningPlayer = biddingPlayers[0];
			} else {
				System.out.println();
				System.out.println("You have decided not to buy " + chosenProduct.getName() + ".");
				return;
			}
			
			bidOnGoing = false;
		} else {
			System.out.println("The minimum bid for this Auction is �" + lastBid);
			System.out.println();
		}
	
		// each iteration of this loop is a 'round' of testing
		while (bidOnGoing) {
			// each iteration of this loop is a single bid
			for (int i = 0; i < biddingPlayers.length; i++) {
				// checks if the player about to be asked to bid is the last player
				if (numOfNonNullPlyrs(biddingPlayers) <= 1) {
					System.out.println();
					System.out.println("The bidding is now closed.");
					bidOnGoing = false;
					break;
				}
				
				if (biddingPlayers[i] != null && biddingPlayers[i].getBalance() > lastBid) {
					System.out.println();
					System.out.println("It is player's " + biddingPlayers[i].getName() + " time to bid!");
					int currentBid = bid(biddingPlayers[i], lastBid);
					if(currentBid != -1) {
						winningPlayer = biddingPlayers[i];
						lastBid = currentBid;
					} else {
						biddingPlayers[i] = null;
					}
				} else {
					biddingPlayers[i] = null;
				}
			}
		}
		
		// if there is a winning player the product gets transferred
		if (winningPlayer != null) {
			winningPlayer.moneyOut(lastBid);
			System.out.println();
			System.out.print("Congratulations to " + winningPlayer.getName() + ", " + chosenProduct.getName() + " is now yours!");
			System.out.println();
			crrntBiddingSquare.transferPrdct(plyr,winningPlayer,chosenProduct);		
		} else {
			System.out.println("No one decided to bid so " + plyr.getName() + " can keep " + chosenProduct.getName() + "!");
		}
	}

	/**
	 * This is the validation for the player's bid
	 * @author AdamLogan
	 * @param plyr - the Player that is currently bidding
	 * @param lastBid - the previous bid, 0 if this is the first bid
	 * @return - the valid bid the player has put forward, will return a '-1' if player has chosen not to bid
	 */
	public static int bid(Player plyr, int lastBid) {
		String bidQuestion = "Would you like to bid?";
		String[] options = { "Yes", "No" };
		Menu bidMenu = new Menu(bidQuestion, options);
		int bidChoice = bidMenu.getUserChoice();
		
		if (bidChoice == 1) {
			int playerBid = validatePlayerInptAmnt("bid", plyr);
			
			if (playerBid > lastBid) {
				lastBid = playerBid;
				return playerBid;
			} else {
				System.out.println("You have to bid more than �" + lastBid + "!");
				System.out.println();
				return bid(plyr, lastBid);
			}
		} else {
			System.out.println("You have chosen to leave the bid.");
			return -1;
		}
	}
	
	/**
	 * This will return the number of non null Player
	 * objects in an array of Players
	 * @author AdamLogan
	 * @param plyrs - the array of Players to check
	 * @return - number of non null Player objects
	 */
	public static int numOfNonNullPlyrs(Player plyrs[]) {
		int count = 0;
		for (Player plyr : plyrs) {
			if (plyr != null) {
				count++;
			}
		}
		return count;
	}

	/**
	 * This method removes a player from the game
	 * @author AdamLogan
	 * @param eliminatedPlayer - the player to be eliminated
	 * @param allPlayers - an array of all the players within the game
	 * @return - an updated array of the the current players without the eliminatedPlayer
	 */
	public static Player[] eliminatePlayer(Player eliminatedPlayer, Player allPlayers[]) {
		// Makes the products owned by the eliminated Player to their original state
		Product[] prdctsToClear = eliminatedPlayer.getOwnedProducts();
		for(Product prdct: prdctsToClear) {
			prdct.clearPrdct();
		}
		
		Player temp[] = new Player[allPlayers.length - 1];

		int countForTemp = 0;
		for (int i = 0; allPlayers.length > i; i++) {
			if (!(allPlayers[i] == eliminatedPlayer)) {
				temp[countForTemp] = allPlayers[i];
				countForTemp++;
			}
		}
		allPlayers = temp;
		return allPlayers;
	}

	/**
	 * Prompts the user with error messages when they enter a value 
	 * that is not an integer and one which is more than the amount
	 * in their balance
	 * @author AdamLogan
	 * @param userDisplayName - the name of the input, to be shown to the user
	 * @param plyr - the Player that is being asked to enter an amount
	 * @return - the validated integer
	 */
	public static int validatePlayerInptAmnt(String userDisplayName, Player plyr) {
		int inputVar = 0;
		boolean valid = false;
		String messg = "Please enter the amount you would like to " + userDisplayName + ": ";
		do {
			inputVar = 0;
			// checks if the player has entered a whole number value
			try {
				System.out.print(messg);
				String inputAsStr = input.nextLine();
				inputAsStr = inputAsStr.strip();
				double dblInputVar = Double.parseDouble(inputAsStr);
				if(dblInputVar % 1 == 0) {
					inputVar = (int) dblInputVar;
					valid = true;
				} else {
					System.out.println("Please enter a whole number.");
				}
				
			} catch (Exception e) {
				System.out.println("Please enter a whole number.");
			}
	
			// checks if the player has the funds to make the investment
			if (plyr.getBalance() < inputVar) {
				System.out.println("You cannot " + userDisplayName + " this much as you do not have the funds.");
				System.out.println("Please try again.");
				valid = false;
			}
			// checks if the player has entered in a positive value
			else if (inputVar < 0) {
				System.out.println("You cannot " + userDisplayName + " a negative amount.");
				System.out.println("Please try again.");
				valid = false;
			}
		} while (!valid);
		
		return inputVar;
	}

	/**
	 * Prompts the user with error messages when they enter a value 
	 * that is not an integer, one which is out of the range
	 * @author AdamLogan
	 * @param lowNum - the lowest number that can be entered
	 * @param highNum - the highest number that can be entered
	 * @param messg - the message to be displayed to the user when asking for input
	 * @return - the validated integer
	 */
	public static int validateRange(int lowNum, int highNum, String messg) {
		int inputNum = 0;
		boolean valid = false;
		do {
			// checks if the player has entered a whole number
			inputNum = lowNum+1; // this needs to be within the range to prevent the out of range error message to be triggered
			try {
				System.out.print(messg);
				String inputAsStr = input.nextLine();
				inputAsStr = inputAsStr.strip();
				double dblInputNum = Double.parseDouble(inputAsStr);
				if(dblInputNum % 1 == 0) {
					inputNum = (int) dblInputNum;
					valid = true;
				} else {
					System.out.println("Please enter a whole number.");
				}
			} catch (Exception e) {
				System.out.println("Please enter a whole number.");
			}
	
			// checks if the guessed number is within the range provided
			if (highNum < inputNum || lowNum > inputNum) {
				System.out.println("Please enter a number between " + lowNum + " and " + highNum + ".");
				valid = false;
			}
		} while (!valid);
		
		return inputNum;
	}
}