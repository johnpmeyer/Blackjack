import java.util.Random;

public class Blackjack {
	/* Initializing int arrays for dealer and player cards. I am choosing to allow
	10 cards since plausibly there will not be more than this many
	cards in a given hand
	*/
	int[] dealerCards = new int[10];
	String[] dealerCardsNames = new String[10];
	int[] playerCards = new int[10];
	String[] playerCardsNames = new String[10];

	// This Array is mostly used for the values of the cards
	int[] actualPlayerValues = new int[10];
	int[] actualDealerValues = new int[10];

	private Random randomNumbers = new Random();

	private int sumOfValues;

	// Variables related to gameplay itself
	private int dealerSum, playerSum;
	private boolean dealerOver, playerOver;
	private boolean dealerWon = false;
	private boolean playerWon = false;
	private boolean playerDone = false;

	/* I am ommitting a constructor method.
	*/

	//Currently being used for debugging/testing
	public static void main(String[] args) {
		/*
		Blackjack demo = new Blackjack();
		demo.dealFirstCards(playerCards);
		System.out.println("\n");
		for(int i=0;i<playerCards.length;i++) {
			System.out.println(playerCards[i]);
		}
		demo.hitMe(playerCards);
		System.out.println("\n");
		for(int i=0;i<playerCards.length;i++) {
			System.out.println(playerCards[i]);
		}
		System.out.println(demo.checkIfOver(playerCards));
		playerCards[2]=14;
		demo.setCardNames(playerCards, playerCardsNames);
		System.out.println("\n");
		for(int i=0;i<playerCards.length;i++) {
			System.out.println(playerCardsNames[i]);
		}
		System.out.println("\n");
		for(int i=0;i<playerCards.length;i++) {
			System.out.println(playerCards[i]);
		}
		*/
	}

	/* Meant to be used only on a 'New Game' or 'Deal' Action in the interface class.
	Simply empties the array, then deals two new values to the first cards.
	*/
	public int[] dealFirstCards(int[] cardArray, int[] actualValueArray) {
		int randomNumber = 0;

		//Array Clearing Portion
		for(int i=0;i<cardArray.length;i++) {
			cardArray[i] = 0;
			actualValueArray[i] = 0;
		}

		//Card Dealing Portion
		for(int i=0;i<2;i++){
			 randomNumber = randomNumbers.nextInt(13)+2;
			 cardArray[i] = randomNumber;
			 actualValueArray[i] = randomNumber;
		}
		return cardArray;
	}

	/* This method sets the names of different sets of cards based on numbers 0 to 13.
	11 is Jack, 12 is Queen, and 13 is King. 0 is Ace.

	In addition, I am going to use this method to also set any values greater than
	10 but less than 14 to 10
	*/
	public String[] setCardNames(int[] cardNumbersArray, String[] cardNamesArray,
	int[] actualValueArray) {
		for(int i=0;i<cardNumbersArray.length;i++) {
			int compareNumber = cardNumbersArray[i];

			/* This line is a little out of place, but it is best for this
			to happen after I can assign the array value to a different variable
			to complete the test
			*/

			//This part makes anything above 10 a 10 in value (kings, queens, jacks
			if(compareNumber>10 && compareNumber < 14) {
				actualValueArray[i] = 10;

			/* This loop tallies whether or not an 11 will put the user
			over in blackjack. If so, the ace is simply a one. Otherwise,
			it is 11.
			*/
			} else if (compareNumber==14) {
				int currentSum=0;
				for(int k=0;k<cardNumbersArray.length;k++) {
					currentSum += cardNumbersArray[k];
				}
				currentSum = currentSum-14;

				if(currentSum+11>21) {
					actualValueArray[i] = 1;
				} else {
					actualValueArray[i] = 11;
				}
			}

			/* This loop compares the current card number to the case
			statement in order to change the name to Jack, Queen, King, or Ace
			*/
			for(int j=0;j<cardNamesArray.length;j++) {
				if(compareNumber>10) {
					switch(compareNumber) {

						case 11:
							cardNamesArray[i] = "Jack";
							break;
						case 12:
							cardNamesArray[i] = "Queen";
							break;

						case 13:
							cardNamesArray[i] = "King";
							break;

						case 14:
							cardNamesArray[i] = "Ace";
							break;
					}
				} else {
					cardNamesArray[i] = Integer.toString(compareNumber);
				}
			}
		}
		return cardNamesArray;
	}

	/* Give the user a new card. Need to think about design, but probably
	will loop through playerArray until [i]=0. Hit there. Loop breaks if
	once it replaces one value of zero with a randValue.
	*/
	public int[] hitMe(int[] cardArray, int[] actualValueArray) {
		for(int i=0; i<cardArray.length; i++) {
			if(cardArray[i]==0) {
				int randomNumber = randomNumbers.nextInt(13)+2;
				cardArray[i] = randomNumber;
				actualValueArray[i] = randomNumber;
				break;
			}
		}
		return cardArray;
	}

	/* This simple method adds array values and stores them in an int. If
	the sum exceeds 21, the user is Over 21 and therefor loses the hand. Should
	be checked by some area of the interface class.
	*/
	public boolean checkIfOver(int[] actualValueArray) {
		sumOfValues = 0;
		for(int i=0;i<actualValueArray.length;i++) {
			sumOfValues += actualValueArray[i];
			if (sumOfValues>21) {
				return true;
			}
		}
		return false;
	}

	/* This method will take place once playerDone = true. As in real
	table blackjack, the dealer only plays after the player will select a button
	that indicates they are done playing.
	*/
	public void dealerPlays() {
		while(dealerSum<16) {
			hitMe(dealerCards, actualDealerValues);
		}
	}

	/* This method simply checks the user versus the dealer. Whoever has the greater
	number wins, provided he/she is not over 21. In the event of a tie, the dealer
	wins.
	*/
	public void checkWinner() {
		dealerSum = 0;
		playerSum = 0;
		//Sum up values here. I opt for one for loop since both arrays are the same length
		for(int i=0;i<dealerCards.length;i++) {
			dealerSum += dealerCards[i];
			playerSum += playerCards[i];
		}
		// Check to see if either player is over. Info used in if statement below.
		dealerOver = checkIfOver(dealerCards);
		playerOver = checkIfOver(playerCards);

		if(playerOver == true) {
			dealerWon = true;
		} else if (dealerOver == true) {
			playerWon = true;
		} else if (dealerSum>=playerSum && dealerOver == false) {
			dealerWon = true;
		} else if (dealerSum<playerSum) {
			playerWon = true;
		}
	}
}