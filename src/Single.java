
    
/**
 * This class is used to model a hand of Single and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */
public class Single extends Hand implements Hand_Interface {
	
	/**
	 * This constructor is used to make a hand of single of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 		Player who plays the single
	 * 
	 * @param cards
	 * 		Cards to form a single
	 */
	public Single(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard()
	{
		return this.getCard(0);
	}
	
	/**
	  * Method to return a string specifying hand is single
	 * @return
	 * 		String containing "Single"
	 */
	public String getType() {
		String handType = "Single";
		return handType;
	}

	/**
	  * Method to check if this is a valid single.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is single or not
	 */
	public boolean isValid() {
		if (this.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
