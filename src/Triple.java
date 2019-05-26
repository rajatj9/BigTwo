/**
 * This class is used to model a hand of Triple and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */
public class Triple extends Hand implements Hand_Interface {
	
	/**
	 * This constructor is used to make a hand of Triple of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 		Player who plays the Triple
	 * 
	 * @param cards
	 * 		Cards to form a Triple
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		if (this.getCard(0).suit > this.getCard(1).suit )
		{
			if (this.getCard(0).suit > this.getCard(2).suit) {
				return this.getCard(0);
			}
			else {
				return this.getCard(2);
			}
		}
		else {
			if (this.getCard(1).suit > this.getCard(2).suit) {
				return this.getCard(1);
			}
			else {
				return this.getCard(2);
			}
		}
	}
	
	/**
	  * Method to check if this is a valid Triple.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is Triple or not
	 */
	public boolean isValid() {
		if (this.size() != 3) {
			return false;
		}
		if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(0).getRank() == this.getCard(2).getRank() ) {
			return true;
		}
		return false;
	}
	
	/**
	  * Method to return a string specifying hand is Triple
	 * @return
	 * 		String containing "Triple"
	 */
	public String getType() {
		String handType = "Triple";
		return handType;
	}
}
