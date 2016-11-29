import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.*;

public class BlackjackInterface extends JFrame{
	Blackjack blackjack;

	private JButton dealButton, hitMeButton, stayButton;
	private JTextArea playerCards, dealerCards;
	private JLabel playerLabel, dealerLabel;
	private JTextField playerSumField, dealerSumField;

	private int playerSum, dealerSum;

	public static void main(String[] args) {
		BlackjackInterface demo = new BlackjackInterface();
		demo.setSize(400, 400);
		demo.setLocationRelativeTo(null);
		demo.createGUI();
		demo.setTitle("Blackjack");
		demo.setVisible(true);
	}

	public void createGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container window = getContentPane();
		window.setLayout(null);

		playerLabel = new JLabel("Player Cards");
		playerLabel.setBounds(40, 5, 150, 25);
		playerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(playerLabel);

		playerCards = new JTextArea();
		playerCards.setBounds(25, 35, 150, 250);
		playerCards.setFont(new Font("Arial", Font.PLAIN, 26));
		playerCards.setEditable(false);
		window.add(playerCards);

		playerSumField = new JTextField();
		playerSumField.setBounds(25, 285, 150, 25);
		playerSumField.setEditable(false);
		window.add(playerSumField);

		dealerLabel = new JLabel("Dealer Cards");
		dealerLabel.setBounds(225, 5, 150, 25);
		dealerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		window.add(dealerLabel);

		dealerCards = new JTextArea();
		dealerCards.setBounds(210, 35, 150, 250);
		dealerCards.setFont(new Font("Arial", Font.PLAIN, 26));
		dealerCards.setEditable(false);
		window.add(dealerCards);

		dealerSumField = new JTextField();
		dealerSumField.setBounds(210, 285, 150, 25);
		dealerSumField.setEditable(false);
		window.add(dealerSumField);

		dealButton = new JButton("Deal");
		dealButton.setBounds(35, 320, 100, 25);
		window.add(dealButton);
		dealButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dealAction(e);
				}
			}
		);

		hitMeButton = new JButton("Hit Me");
		hitMeButton.setBounds(145, 320, 100, 25);
		window.add(hitMeButton);
		hitMeButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hitMeAction(e);
				}
			}
		);

		stayButton = new JButton("Stay");
		stayButton.setBounds(255, 320, 100, 25);
		window.add(stayButton);
		stayButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stayAction(e);
				}
			}
		);

		//Debugging, Testing
		blackjack = new Blackjack();

	}

	//BEGINNING OF BUTTON PRESS ACTIONS

	/*  Deal Cards Action initialized by button press.
		The cards are dealt, the card names are generated, and the values
		are dispalyed to the user.

		The array is cleared to begin the program
	*/
	public void dealAction(ActionEvent e) {
		dealerSumField.setText("" + dealerSum);
		playerSumField.setText("" + playerSum);

		blackjack.dealFirstCards(blackjack.playerCards, blackjack.actualPlayerValues);
		blackjack.dealFirstCards(blackjack.dealerCards, blackjack.actualDealerValues);

		blackjack.setCardNames(blackjack.playerCards, blackjack.playerCardsNames,
		blackjack.actualPlayerValues);
		blackjack.setCardNames(blackjack.dealerCards, blackjack.dealerCardsNames,
		blackjack.actualDealerValues);

		displayCards();
	}

	// Hit Me Action initialized when user clicks the button
	public void hitMeAction(ActionEvent e) {
		blackjack.hitMe(blackjack.playerCards, blackjack.actualPlayerValues);
		blackjack.setCardNames(blackjack.playerCards, blackjack.playerCardsNames,
		blackjack.actualPlayerValues);
		displayCards();
		boolean over = blackjack.checkIfOver(blackjack.actualPlayerValues);
		if(over==true) {
			JOptionPane.showMessageDialog(this, "Bust! You lose ma'fucker" + "\n" +
			"New game starting");
		}
	}

	// Stay Action if user hasn't yet busted
	public void stayAction(ActionEvent e) {
		dealerCards.setText("");

		for(int i=0; i<blackjack.dealerCardsNames.length; i++) {
			String dealerCardName = blackjack.dealerCardsNames[i];
				if(dealerCardName.equals("0") == false) {
					dealerCards.append("" + dealerCardName + "\n");
				}
		}

		dealerSum = sumCards(blackjack.actualDealerValues, dealerSum);
		dealerSumField.setText("" + dealerSum);

		while(dealerSum<16) {

			blackjack.hitMe(blackjack.dealerCards, blackjack.actualDealerValues);
			blackjack.setCardNames(blackjack.dealerCards, blackjack.dealerCardsNames,
			blackjack.actualDealerValues);

			dealerSum = sumCards(blackjack.actualDealerValues, dealerSum);
			dealerSumField.setText("" + dealerSum);
			dealerCards.setText("");

			for(int i=0; i<blackjack.dealerCardsNames.length; i++) {
				String dealerCardName = blackjack.dealerCardsNames[i];
					if(dealerCardName.equals("0") == false) {
						dealerCards.append("" + dealerCardName + "\n");
					}
			}
		}
	}

	//END OF BUTTON PRESS ACTIONS

	/* This method displays the NAMES of the first cards dealt.
	As in regular blackjack, the first two cards are dealt for the user.
	The dealer, however, only shows one card
	*/
	public void displayCards() {

		playerCards.setText("");
		dealerCards.setText("");

		for(int i=0; i<blackjack.playerCardsNames.length; i++) {

			String playerCardName = blackjack.playerCardsNames[i];
			if(playerCardName.equals("0") == false) {
				playerCards.append("" + playerCardName + "\n");
			}
		}


		for(int i=0; i<2; i++) {
			String dealerCardName = blackjack.dealerCardsNames[i];
			if(i<1) {
				if(dealerCardName.equals("0") == false) {
					dealerCards.append("" + dealerCardName + "\n");
				}
			} else {
				dealerCards.append("-" + "\n");
			}
		}

		playerSum = sumCards(blackjack.actualPlayerValues, playerSum);
		playerSumField.setText("" + playerSum);
		dealerSumField.setText("" + blackjack.actualDealerValues[0]);
	}

	private int sumCards(int[] actualValues, int sum) {
		sum = 0;

		for(int i=0; i<actualValues.length; i++) {
			sum += actualValues[i];
		}

		return sum;
	}
}