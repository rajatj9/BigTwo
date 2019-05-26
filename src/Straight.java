
import java.util.Arrays;


/**
 * This class is used to model a hand of Straight and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */
public class Straight extends Hand implements Hand_Interface {
	
	/**
	 * This constructor is used to make a hand of straight of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 		Player who plays the straight
	 * 
	 * @param cards
	 * 		Cards to form a straight
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		
		int [] cardRanks = new int[5];
		for (int i=0; i<5; i++) {
			 cardRanks[i] = this.getCard(i).getRank();
				if (cardRanks[i] == 0) {
					cardRanks[i] = 13;
	 			}
				else if (cardRanks[i] == 1) {
					cardRanks[i] = 14;
				}
		 }
			
		
		Arrays.sort(cardRanks);
		
		// Finding the highest Card
		
		int highest_index = 0;
		
		for (int i=0; i< cardRanks.length; i++) {
			if (this.getCard(i).getRank() == 1 && cardRanks[4] == 14 ) {
				return this.getCard(i);
			}
			else if (this.getCard(i).getRank() == 0 && cardRanks[4] == 13) {
				return this.getCard(i);
			}
			
			if (this.getCard(i).rank  == cardRanks[4]) {
				highest_index  = i;
			}	
		}

		return this.getCard(highest_index);
	}
	
	
	/**
	  * Method to check if this is a valid straight.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is straight or not
	 */
	public boolean isValid() {
		
		
		if (this.size() != 5) {
			return false;
		}
		
		int [] cardRanks = new int[5];
		for (int i=0; i<5; i++) {
			 cardRanks[i] = this.getCard(i).getRank();
				if (cardRanks[i] == 0) {
					cardRanks[i] = 13;
	 			}
				else if (cardRanks[i] == 1) {
					cardRanks[i] = 14;
				}
		 }

		
		Arrays.sort(cardRanks);
		
		for (int i=1; i < cardRanks.length; i++) {
			if (cardRanks[i] != cardRanks[i-1] + 1) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	  * Method to return a string specifying hand is straight
	 * @return
	 * 		String containing "Straight"
	 */
	public String getType() {
		String handType  = "Straight";
		return handType;
	}
	
}
