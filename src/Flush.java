import java.util.Arrays;


/**
 * This class is used to model a hand of flush and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */

public class Flush extends Hand implements Hand_Interface {
	
	/**
	 * This constructor is used to make a hand of flush of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 			Player who plays the flush
	 * @param cards
	 * 			Cards to form the flush
	 */
	
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	
	/**
	 * The method to retrieve the top card of the Flush.
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		
		int [] cardRanks = new int[5];
		for (int i=0; i<5; i++) {
			 cardRanks[i] = this.getCard(i).rank;
		 }
			
		
		for (int i = 0; i < 5; i++) {
			if (cardRanks[i] == 0) {
				cardRanks[i] = 13;
 			}
			else if (cardRanks[i] == 1) {
				cardRanks[i] = 14;
			}
		}
		
		Arrays.sort(cardRanks);
		
		// Finding the highest Card
		

		for (int i=0; i< cardRanks.length; i++) {
			if (this.getCard(i).rank == 0 && cardRanks[4] == 13) {
				return this.getCard(i);
			}
			else if (this.getCard(i).rank == 0 && cardRanks[4] == 14 ) {
				return this.getCard(i);
			}
		}
		
		int highest_index = 0;
		for (int i=0; i < cardRanks.length; i++) {
			if (this.getCard(i).rank  == cardRanks[4]) {
				highest_index  = i;
			}
		}
		return this.getCard(highest_index);
	}
	
	/**
	  * Method to check if this is a valid flush.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is flush or not.
	 */
	public boolean isValid() {
		if (this.size() != 5 ) {
			return false;
		}
		if(this.getCard(0).suit == this.getCard(1).suit && this.getCard(1).suit == this.getCard(2).suit && this.getCard(2).suit == this.getCard(3).suit && this.getCard(3).suit == this.getCard(4).suit) {
			return true;
		}
		return false;
	}
	
	
	/**
	  * Method to return a string specifying hand is flush
	 * @return
	 * 		String containing "Flush"
	 */
	public String getType() {
		
		String handType = "Flush";
		return handType;
	}
}
