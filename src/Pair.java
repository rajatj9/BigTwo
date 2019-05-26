
/**
 * This class is used to model a hand of Pair and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */

public class Pair extends Hand implements Hand_Interface {

	/**
	 * This constructor is used to make a hand of pair of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 		Player who plays the pair
	 * 
	 * @param cards
	 * 		Cards to form/check a pair
	 */
	public Pair(CardGamePlayer player, CardList card)	{
		super(player, card);
	}
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		
		if (this.getCard(0).suit > this.getCard(1).suit) {
			return this.getCard(0);
		}
		
		else {
			return this.getCard(1);
		}
	}

	
	/**
	  * Method to check if this is a valid pair.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is pair or not
	 */
	public boolean isValid() {
		if(this.size() != 2) {
			return false;
		}
		if (this.getCard(0).rank == this.getCard(1).rank) {
			return true;
		}
		
		return false;
	}
	
	/**
	  * Method to return a string specifying hand is pair
	 * @return
	 * 		String containing "Pair"
	 */
	public String getType() {
		String handType =  "Pair";
		return handType;
	}
 }

