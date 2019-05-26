
/**
 * This class is a sub-class of the Deck class and is used to model a deck of cards used in Big Two card game
 * @author Rajat Jain
 *
 */
public class BigTwoDeck extends Deck {

	/**
	 * Initialize the deck of cards of Big Two Game.
	 */
	
	public void initialize()
	{
		removeAllCards();
		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j < 13; j++) 
			{
				BigTwoCard bigtwocard = new BigTwoCard(i, j); // Added Card must be a BigTwoCard
				this.addCard(bigtwocard);
			}
		}
		
	}

}
