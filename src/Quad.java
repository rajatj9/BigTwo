import java.util.Arrays;


/**
 * This class is used to model a hand of Quad and is a sub-class of Hand. This class would define the abstract methods of Hand ( isValid() and getType()).
 * @author Rajat Jain
 *
 */

public class Quad extends Hand implements Hand_Interface {
	
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		
		this.sort();
		
		if(this.getCard(0) == this.getCard(3)) {
			return this.getCard(3);
		}
		else {
			return this.getCard(4);
		}
	}
	
	/**
	  * Method to check if this is a valid quad.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is quad or not
	 */	
	public boolean isValid() {
		
		if (this.size() != 5 ) {
			return false;
		}
		
		this.sort();
		
		if(this.getCard(0).rank == this.getCard(1).rank)
		{
			if(this.getCard(1).rank == this.getCard(2).rank && this.getCard(2).rank == this.getCard(3).rank)
			{
				return true;
			}
		}
		
		else if(this.getCard(1).rank == this.getCard(2).rank)
		{
			if(this.getCard(2).rank == this.getCard(3).rank && this.getCard(2).rank == this.getCard(4).rank)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	  * Method to return a string specifying hand is quad
	 * @return
	 * 		String containing "Quad"
	 */
	public String getType() {
		String handType = "Quad";
		return handType;
	}
	
}
