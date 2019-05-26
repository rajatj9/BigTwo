/**
 * This class is a sub-class of the Card Class and is used to model a card used in Big Two card game. 
 * @author Rajat Jain
 *
 */
public class BigTwoCard extends Card{
	
		
		/**
		 * The constructor to build a card with a specified suit and rank.
		 * @param suit
		 * 			Integer value between 0 and 3 representing the suit of the card
		 * @param rank
		 * 			Integer value between 0 and 12 representing the rank of the card
		 */
		public BigTwoCard(int suit, int rank) {
			super (suit, rank);
		}
		
		
		/**
		 * Compares this card with the specified card for order for Big Two Game
		 * 
		 * @param card
		 *            the card to be compared
		 * @return a negative integer, zero, or a positive integer if this card is
		 *         less than, equal to, or greater than the specified card respectively
		 */
		public int compareTo(Card card) {
			
			
			if(this.getRank()==card.getRank())
			{
				if(this.getSuit()==card.getSuit())
					return 0;
				else if(this.getSuit()>card.getSuit()) 
					return 1;
				else 
					return -1;
			}
			else if(this.getRank()==1 || card.getRank()==1)
			{
				if(this.getRank()==1)
					return 1;
				else
					return -1;
			}
			else if(this.getRank()==0 || card.getRank()==0) 
			{
				if(this.getRank()==0)
					return 1;
				else
					return -1;
			}
			else if(this.getRank()>card.getRank())
				return 1;
			else
				return -1;
		}
		
	
}
