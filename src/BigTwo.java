
import java.util.*;

/**
 * This class represents a BigTwoObject and the overall gameplay of the game.
 * 
 * @author Rajat Jain
 *
 */
public class BigTwo implements CardGame {

	private BigTwoTable bigTwoTable;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList; // list of players in the game
	private ArrayList<Hand> handsOnTable; // list of the hands on the table
	private int currentIdx; // specifies index of the active player



	/**
	 * The constructor for the BigTwoObject which initializes the players in the game, the hands on the table and the bigtwo console.
	 */
	public BigTwo() {
		playerList = new ArrayList<CardGamePlayer>();

		for (int i=0; i<4; i++) {
			CardGamePlayer newPlayer = new CardGamePlayer();
			playerList.add(newPlayer);

		}
		
		handsOnTable = new ArrayList<Hand>();
		bigTwoTable = new BigTwoTable(this);
	}

	/**
	 * a method for retrieving the deck of cards being used
	 * 
	 * @return deck object containing the deck of cards in the game
	 */
	public Deck getDeck() {
		return this.deck;
	}

	/**
	 * a method for retrieving the list of
	 * players.
	 * 
	 * @return ArrayList containing the players in the game.
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return this.playerList;
	}
	
	/**
	 * – a method for retrieving the list of hands played
	 * on the table
	 * 
	 * @return ArrayList containing the hands on the table.
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return this.handsOnTable;
	}

	/**
	 * a method for retrieving the index of the current player
	 * 
	 * @return id of current player.
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}

	/**
	 * a method for starting the game with a (shuffled) deck
	 * of cards supplied as the argument. It implements the Big Two game logics.
	 * 
	 * @param deck contains the deck with which the game should start.
	 */
	public void start(Deck deck) {

		handsOnTable.clear();
		
		for(int i = 0; i < this.getNumOfPlayers(); i++) {
			this.getPlayerList().get(i).removeAllCards();
		}
		
		
		this.getHandsOnTable().clear();
		
		//Distributing the cards to the players
		for (int i = 0; i < deck.size(); i++) {
			this.getPlayerList().get(i%getNumOfPlayers()).addCard(deck.getCard(i));
		}
		
		  // Sorting the cards in the hands of the players
		  for (int i = 0; i < 4; i++ ) {
			  playerList.get(i).getCardsInHand().sort();
		  }

		  currentIdx = -1;
		  // Player with 3 of Diamonds must start
		  for(int i=0; i<4;i++) {
			  Card ThreeOfDiamonds = new Card(0,2);
			  if (playerList.get(i).getCardsInHand().contains(ThreeOfDiamonds))
			  {
				  bigTwoTable.setActivePlayer(i);
				  currentIdx = i;
				  break;
			  }
		  }

		bigTwoTable.repaint();
	}
	
	/**
	 * method for making a move by a player with the specified playerID
	 * using the cards specified by the list of indices
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 */
	public void makeMove(int playerID, int [] cardIdx)
	{
		checkMove(playerID,cardIdx);
	}
	
	// Defining Game Logic

	/**
	 * Method for checking a move made by a player.
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 */
	public void checkMove(int playerID, int[] cardIdx)
	{
			 int prevPlayer;
			 boolean isValidMove = true;
			 CardList cardlist = new CardList();
			 
			 if(handsOnTable.isEmpty()) {
				 
				 prevPlayer = -1;
				 
			 }
			 
			 else {
					
				 	Hand lastHandPlayed = handsOnTable.get(handsOnTable.size() - 1);
					String prevPlayerName = lastHandPlayed.getPlayer().getName();

					if(prevPlayerName.equals("Player 0")){prevPlayer = 0;}
					else if(prevPlayerName.equals("Player 1")){prevPlayer = 1;}
					else if(prevPlayerName.equals("Player 2")){prevPlayer = 2;}
					else {prevPlayer = 3;}
					

			 }
					if (cardIdx != null) {

					  
					  cardlist = playerList.get(playerID).play(cardIdx);
					  

					  Hand hand = composeHand(playerList.get(playerID), cardlist);

					  //Checking for new game
					  if(handsOnTable.isEmpty() == true) {
						  
						  Card ThreeOfDiamonds = new Card(0,2);
						  
						  if(hand.contains(ThreeOfDiamonds) && !hand.isEmpty() && hand.isValid()) {

							  	
							  	isValidMove = true;

						  }
						  else {
							  
							  isValidMove = false;
						  }
					  
					  }
				  
					  else {
						  
						  if(playerID != prevPlayer) {

								if(handsOnTable.size() != 0) {
										
								  Hand lastHandOnTable = handsOnTable.get(handsOnTable.size() - 1);
								  isValidMove = hand.beats(lastHandOnTable);
								}
								else {
									isValidMove = false;
								}
							  
						  }
						  else {
							  isValidMove = true;
						  }
					  }

					  if(isValidMove == true && hand.isValid() == true) {
						  prevPlayer = playerID;

						  for(int i=0; i<cardlist.size(); i++) {

							  playerList.get(playerID).getCardsInHand().removeCard(cardlist.getCard(i));
						  }

							bigTwoTable.printMsg("Player " + playerID  + "'s turn: ");
							bigTwoTable.printMsg("{" + hand.getType() + "}" + " " + hand);
							bigTwoTable.printMsg("");
							
							handsOnTable.add(hand);

							playerID = (playerID + 1) % 4;
							bigTwoTable.setActivePlayer(playerID);
							


					  }


					  else {
							
							bigTwoTable.printMsg("Player " + playerID  + "'s turn");
							bigTwoTable.printMsg("Not a legal move!"); 
					  }

				  }

				  else  {

					  // Passing Mechanism

					  if(!handsOnTable.isEmpty() && prevPlayer != playerID) {
						  
						  playerID = (playerID + 1)%4;

						  bigTwoTable.setActivePlayer(playerID);

							bigTwoTable.printMsg("Player " + playerID  + "'s turn");
							bigTwoTable.printMsg("{Pass}");

						  isValidMove = true;

					  }
					  else {
							bigTwoTable.printMsg("Player " + playerID  + "'s turn");
							bigTwoTable.printMsg("Not a legal move");
					  }

				  }

			  

			  // Final Results of the Game when gameEnd becomes true and resetting State

			  if(endOfGame()) {


				  bigTwoTable.setActivePlayer(-1);
					
				  bigTwoTable.repaint();

				  bigTwoTable.printMsg("");
				  bigTwoTable.printMsg("End of Game!"); // Game ends

				  for (int i=0; i< playerList.size(); i++) {

					  if(playerList.get(i).getCardsInHand().size() == 0) {
						  bigTwoTable.printMsg("Player " + i + " wins the game");
					  }
					  else {
						  bigTwoTable.printMsg("Player "+ i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand");
					  }
				  }

					bigTwoTable.disable();
			  }
	}

	
	/**
	 * Checks if the game has ended.
	 * 
	 * @return true if the game ends; false otherwise
	 */
	public boolean endOfGame() {
		
		for(int i = 0; i < playerList.size();i++)
		{
			
			if(playerList.get(i).getCardsInHand().size() == 0)
				return true;
			
		}

		return false;
	}

	
	/**
	 * a method for starting a Big Two card game. It should
	 * create a Big Two card game, create and shuffle a deck of cards, and start the game with
	 * the deck of cards.
	 * @param args
	 */
	public static void main(String args[]) {

		BigTwoDeck gameDeck = new BigTwoDeck();
		// Shuffling the deck
		gameDeck.shuffle();

		BigTwo game = new BigTwo();

		// Starting the Game

		game.start(gameDeck);


	}

	/**
	 * a method for
	 * returning a valid hand from the specified list of cards of the player. Returns null is no
	 * valid hand can be composed from the specified list of cards.
	 * @param player represents the player who played the hand
	 * @param cards represents the cards in the hand 
	 * @return an object of the type of the hand
	 */
	public Hand composeHand(CardGamePlayer player, CardList cards) {


		Hand[] type = new Hand[8];
		type[0] = new Single(player, cards);
		type[1] = new Pair(player, cards);
		type[2] = new Triple(player, cards);
		type[3] = new Straight(player, cards);
		type[4] = new Flush(player, cards);
		type[5] = new FullHouse(player, cards);
		type[6] = new Quad(player, cards);
		type[7] = new StraightFlush(player, cards);

		for(int i=7; i>=0;i--) {
			if (type[i].isValid())
				return type[i];
		}

		return new Hand(player, cards);
	}

	/**
	 * method for getting the number of players
	 * 
	 * @return 
	 * 			number of players in the game
	 */
	public int getNumOfPlayers() {
		return playerList.size();
	}
}
