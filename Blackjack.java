import java.util.Random;

public class Blackjack {
	/* Initializing int arrays for dealer and player cards. I am choosing to allow
	10 cards since plausibly there will not be more than this many
	cards in a given hand
	*/
	int[] dealerCards = new int[10];
	String[] dealerCardsNames = new String[10];
	static int[] playerCards = new int[10];
	String[] playerCardsNames = new String[10];

	private Random randomNumbers = new Random();

	private int sumOfValues;

	private int dealerSum, playerSum;
	private boolean dealerOver, playerOver;
	private boolean dealerWon = false;
	private boolean playerWon = false;

	/* I am ommitting a constructor method.
	*/
	public static void main(String[] args) {
		Blackjack demo = new Blackjack();
		for(int i=0;i<playerCards.length;i++) {
			System.out.println(playerCards[i]);
		}
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
	}

	/* Meant to be used only on a 'New Game' or 'Deal' Action in the interface class.
	Simply empties the array, then deals two new values to the first cards.
	*/
	public int[] dealFirstCards(int[] cardArray) {
		//Array Clearing Portion
		for(int i=0;i<cardArray.length;i++) {
			cardArray[i] = 0;
		}

		//Card Dealing Portion
		for(int i=0;i<2;i++){
			cardArray[i] = randomNumbers.nextInt(14);
		}
		return cardArray;
	}

	/* Give the user a new card. Need to think about design, but probably
	will loop through playerArray until [i]=0. Hit there. Loop breaks if
	once it replaces one value of zero with a randValue.
	*/
	public int[] hitMe(int[] cardArray) {
		for(int i=0; i<cardArray.length; i++) {
			if(cardArray[i]==0) {
				cardArray[i] = randomNumbers.nextInt(14);
				break;
			}
		}
		return cardArray;
	}

	/* This simple method adds array values and stores them in an int. If
	the sum exceeds 21, the user is Over 21 and therefor loses the hand. Should
	be checked by some area of the interface class.
	*/
	public boolean checkIfOver(int[] cardArray) {
		sumOfValues = 0;
		for(int i=0;i<cardArray.length;i++) {
			sumOfValues += cardArray[i];
			if (sumOfValues>21) {
				return true;
			}
		}
		return false;
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