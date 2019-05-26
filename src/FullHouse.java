import java.util.Arrays;

/**
 * This class is used to model a hand of FullHouse and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */

public class FullHouse extends Hand implements Hand_Interface {
	
	
	/**
	 * This constructor is used to make a hand of FullHouse of the specified cards of the player by calling constructor of the superclass Hand.
	 * @param player
	 * 			Player who plays the FullHouse
	 * @param cards
	 * 			Cards to form the FullHouse
	 */
	
	public FullHouse(CardGamePlayer player, CardList cards) {
		
		super (player, cards);
	
	}
	
	
	/**
	 * Over-ridden method of Hand to retrieve the top card of the FullHouse
	 * @return
	 * 		Top card of the FullHouse
	 */
	public Card getTopCard() {
		this.sort();
		
		if (this.getCard(0).getRank() == this.getCard(2).getRank()) {
			return this.getCard(2);
		}
		else {
			return this.getCard(4);
		}
	}
	
	/**
	  * Method to check if this is a valid FullHouse.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is FullHouse or not
	 */
	public boolean isValid() {
		
		if (this.size() != 5 ) {
			return false;
		}
		
		this.sort();
		
		if(this.getCard(0).rank == this.getCard(2).rank)
		{
			if(this.getCard(0).rank == this.getCard(1).rank && this.getCard(0).rank == this.getCard(2).rank && this.getCard(3).rank == this.getCard(4).rank)
			{
				return true; // condition for isValid is satisfied 
			}
		}
		
		else if(this.getCard(2).rank == this.getCard(4).rank)
		{
			if(this.getCard(2).rank == this.getCard(3).rank && this.getCard(2).rank == this.getCard(4).rank && this.getCard(0).rank == this.getCard(1).rank)
			{
				return true;
			}
		}
		
		return false;
	
	}
	
	/**
	  * Method to return a string specifying hand is FullHouse
	 * @return
	 * 		String containing "FullHouse"
	 */
	public String getType() {
		String handType = "Full House";
		return handType;
	}
}
