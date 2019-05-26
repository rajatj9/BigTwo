import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BigTwoClient implements CardGame, NetworkGame {

	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int currentIdx;
	private BigTwoTable table;
	
	
	public BigTwoClient(){
		
		this.playerList = new ArrayList<CardGamePlayer>();
		
		for (int i = 0; i < 4; i++) {
			
			this.playerList.add(new CardGamePlayer());
			
		}
		
		this.numOfPlayers = this.playerList.size();
		this.handsOnTable = new ArrayList<Hand>();
		this.table = new BigTwoTable(this);
		
		String name = JOptionPane.showInputDialog("Please enter your name: ");
		if (name != null) {
			
			this.setPlayerName(name);
			this.setServerIP("127.0.0.1");
			this.setServerPort(2396);
			this.makeConnection();
			table.disable();
			
		} else {
			
			this.table.printMsg("Please enter your name.\n");
			this.table.disable();
		
		}
	}
	
	@Override
	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	@Override
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}

	@Override
	public int getCurrentIdx() {
		return currentIdx;
	}
	
	
	
	@Override
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
				  table.setActivePlayer(i);
				  currentIdx = i;
				  break;
			  }
		  }
		  
		table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn");
		table.repaint();
		
	}
	
	@Override
	public void makeMove(int playerID, int[] cardIdx) {
	
		sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
		
	}
	
	@Override
	public void checkMove(int playerID, int [] cardIdx)
	{
		CardList cardlist = new CardList(); 
		boolean isValidMove = true; 
		
		Card ThreeOfDiamonds = new Card(0,2);
		
		if(cardIdx!= null) {
			cardlist = playerList.get(playerID).play(cardIdx);
			Hand hand = composeHand(playerList.get(playerID), cardlist);
			
			if(handsOnTable.isEmpty())
			{
			
				if(hand.contains(ThreeOfDiamonds) && !hand.isEmpty() && hand.isValid()) {isValidMove = true; }
				else {isValidMove = false;} 
			
			}
			else {
				if(handsOnTable.get(handsOnTable.size() - 1).getPlayer() != playerList.get(playerID))
				{
					if(!hand.isEmpty() == true) {
						
						Hand lastHand = handsOnTable.get(handsOnTable.size() - 1);
						isValidMove = hand.beats(lastHand);
						
					}
					else {
						isValidMove = false;
					}
				}
				else {
					if(!hand.isEmpty()) { isValidMove = true; }
					
					else { isValidMove= false; }
				}	
			}
			if(isValidMove && hand.isValid()) {
				
				
				for(int i=0;i<cardlist.size();i++)
				{
					playerList.get(playerID).getCardsInHand().removeCard(cardlist.getCard(i)); 
				}
				
				table.printMsg("{" + hand.getType() + "} " + hand);
				
				currentIdx = (currentIdx + 1) % 4;
				table.setActivePlayer(currentIdx);
				
				handsOnTable.add(hand);
				
				table.printMsg(getPlayerList().get(currentIdx).getName()+ "'s turn: ");
			}
			else
			{
				table.printMsg(cardlist +" -> Not a legal move!!!");
			}
		}
		else {
			
				if(!handsOnTable.isEmpty() && handsOnTable.get(handsOnTable.size()-1).getPlayer() != playerList.get(playerID)) {
				
					currentIdx = (currentIdx+1) % 4;
					table.setActivePlayer(currentIdx);
					
					isValidMove = true;
					
					table.printMsg("{ Pass }");
					table.printMsg(getPlayerList().get(currentIdx).getName() + "'s turn: ");
					
				
			}
			else {
				
				isValidMove= false;
				table.printMsg("Not a legal move!");
				
			}
		}
		
		table.repaint();
		
		
		// checking for end of game
	      if(endOfGame()) {
		        
		        String message = "";
		        message += "End of Game! \n"; // Game ends

		        for (int i=0; i< playerList.size(); i++) {

		          if(playerList.get(i).getCardsInHand().size() == 0) {
		            message += "Player " + i + " wins the game \n";
		          }
		          else {
		            message += "Player "+ i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand \n";

		          }
		        }
		        for (int i=0; i<4; ++i)
		        {
		          playerList.get(i).removeAllCards();
		        }
		        
		        table.disable();
		        table.printMsg(message);
		        JOptionPane.showMessageDialog(null, message);
		        CardGameMessage ready = new CardGameMessage(CardGameMessage.READY,-1,null);
		        
		        sendMessage(ready);
		      }
	}
	
	@Override
	public boolean endOfGame() {
		
		for(int i = 0; i < playerList.size();i++)
		{
			
			if(playerList.get(i).getCardsInHand().size() == 0)
				return true;
			
		}

		return false;
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
	
	@Override
	public int getPlayerID() {
		return this.playerID;
	}
	
	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	@Override
	public String getPlayerName() {
		return this.playerName;
	}
	
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public String getServerIP() {
		return this.serverIP;
	}
	
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	@Override
	public int getServerPort() {
		return serverPort;
	}
	
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/*
	 * The 
	 * @return 
	 * 		 Whether client is connected or not
	 *
	 */
	public boolean isConnected() {
		if(!sock.isClosed()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void makeConnection() {
		if (sock == null || !sock.isConnected() ) {
			try {
				sock = new Socket(this.getServerIP(),this.getServerPort());
				oos = new ObjectOutputStream(sock.getOutputStream());
				
				Runnable job = new ServerHandler();
				Thread thread = new Thread(job);
				thread.start();
				
				sendMessage(new CardGameMessage(CardGameMessage.JOIN,-1,this.getPlayerName()));
				
				sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


	@Override
	public void parseMessage(GameMessage message) {
		if (message.getType() == CardGameMessage.MOVE) {
			
			checkMove(message.getPlayerID(),(int[]) message.getData());
			
		} else if (message.getType() == CardGameMessage.PLAYER_LIST) {
			
			this.setPlayerID(message.getPlayerID());
			for (int i = 0; i < this.getNumOfPlayers(); i++) {
				
				if (((String[]) message.getData())[i] != null){
					
					this.getPlayerList().get(i).setName(((String[]) message.getData())[i]);
					
				}
			}
			table.repaint();
			
		}  else if (message.getType() == CardGameMessage.QUIT) {
			
			table.printMsg("Player " + message.getPlayerID() + " " + playerList.get(message.getPlayerID()).getName() + " left the game.\n");
			playerList.get(message.getPlayerID()).setName("");
			
			if (this.endOfGame() == false)
			{
				table.disable(); 
				this.sendMessage(new CardGameMessage(4, -1, null));
				for (int i = 0; i < 4; i++)
				{
					playerList.get(i).removeAllCards();
				}
					
				table.repaint();
			}
			
			table.repaint();
			
		} else if (message.getType() == CardGameMessage.FULL) {
			
			this.table.printMsg("The server is full and the game cannot be joined.");
			try {
				
				this.sock.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
		} else if (message.getType() == CardGameMessage.JOIN) {
			
			this.getPlayerList().get(message.getPlayerID()).setName((String)message.getData());
			this.table.repaint();
			table.printMsg("Player " + playerList.get(message.getPlayerID()).getName() + " joined the game!");
			
		} else if (message.getType() == CardGameMessage.READY) {
			
			this.table.printMsg("Player " + message.getPlayerID() + " is ready.");
			
		} else if (message.getType() == CardGameMessage.START) {
			
			deck = (Deck) message.getData();
			this.start(deck);
			table.enable();
			table.repaint();
			
			
			
		
		} else if (message.getType() == CardGameMessage.MSG) {
			
			this.table.printChatMsg((String) message.getData());
			
		}
	}
	
	
	
	
	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class ServerHandler implements Runnable {
		@Override
		public void run() {	
			try {
				ois = new ObjectInputStream(sock.getInputStream());
				CardGameMessage message;
	
				while (!sock.isClosed()) {
					
					message = (CardGameMessage) ois.readObject();
							
					if (message  != null) {
						
						parseMessage(message);
						
					}
					
				} 
				ois.close();
				
			} catch (Exception ex) {
				
				ex.printStackTrace();
				
			}
			
		}
		
	}


	
	public static void main(String[] args) {
		BigTwoClient client = new BigTwoClient();
	}
	
	
}
