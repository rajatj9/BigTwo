/**
 * This abstract class is a sub-class of CardList class and is used to model a hand of cards. It has private instance variables for storing the player who plays this hand.
 * It also has methods for getting the player of this hand checking if it is a valid hand, getting the type of hand and checking if it beats a specified hand.
 * 
 * 
 * @author Rajat Jain
 *
 */


public class Hand extends CardList {
	
	private CardGamePlayer player;
	
	/**
	 * The constructor for building a hand with specified players and list of  cards
	 * @param player
	 * 		The Player who plays the hand
	 * @param cards
	 * 		The cards to form/check the hand
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		
		this.player = player;
		

		for (int i=0; i< cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * The method to retrieve the player of the hand
	 * @return
	 * 		Player who plays the hand
	 */
	public CardGamePlayer getPlayer() {
		
		return this.player;
	}
	
	/**
	 * The method to retrieve the top card of the hand
	 * @return
	 * 		Top card of the hand
	 */
	public Card getTopCard() {
		return null;
	}
	
	/**
	 * The method to check if this hand beats the specified hand
	 * @param hand
	 * 		The specified hand to be checked
	 * @return
	 * 		Boolean value of true/false if this hand beats specified hand
	 */
	public boolean beats(Hand hand) {
		
		if( hand.size() == 1 || hand.size() == 2 || hand.size() == 3)
		{
			if(this.size() == hand.size() && this.isValid() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
			{ 
				return true;
			}
			else if (this.size() == hand.size() && this.isValid() && this.getTopCard().compareTo(hand.getTopCard()) == -1) {
				return false;
			}
		}
		
		if (hand.size() == 5 && this.size() == 5) {
			
			// Dealing with same types of Hands
			if (this.getType() == hand.getType()) {
				
				if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
					
					return true;
				
				}
				else {
					return false;
				}
			}
			
			else if (this.getType() == "Straight Flush") {
				
				return true;
			
			}
			
			else if (this.getType() == "Quad") {
				
				if (hand.getType() == "Straight Flush") {
					return false;
				}
				
				return true;
			}
			
			else if (this.getType() == "Full House") {
					
				if (hand.getType() == "Straight Flush" || hand.getType() == "Quad") {
					
					return false;
					
				}
					
					return true;
				}
			
			else if (this.getType() == "Flush") {
				
				if (hand.getType() == "Straight Flush" || hand.getType() == "Quad" || hand.getType() == "Full House") {
					return false;
				}
				return true;
			
			}
			
			else if (this.getType() == "Straight") {
				
				return false;
				
			}
		}
		
		return false;
		
	}
	
	 /**
	  * Method to check if this is a valid hand. Will be defined in the sub-classes of Hand.
	 * @return
	 * 		Boolean value of true/false depending on whether hand is valid or not. This function returns false as default.
	 */
	public boolean isValid() {
		return false;
		}
	
	 /**
	  * Method to return a string specifying the type of the hand. Will be defined in the sub-classes of Hand.
	 * @return
	 * 		String containing the type of hand, this functions returns null as default.
	 */
	public String getType() {
		return null;
	}
}
