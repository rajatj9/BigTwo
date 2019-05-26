import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to build a GUI for the Big Two card game and handle all user actions.
 * @author Rajat Jain
 */

public class BigTwoTable implements CardGameTable {

	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField chatTypeArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	
	/**
	 * a constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 * @param game A Card Game of BigTwo type to play through this GUI
	 */
	
	public BigTwoTable(CardGame bigTwo) {
		
		this.game = (BigTwoClient) bigTwo;
		selected = new boolean[13];

		// Loading Images
		
		avatars = new Image[4];
		cardImages = new Image[4][13];
		
		avatars[0] = new ImageIcon("assets/avatars/player1.png").getImage();
		avatars[1] = new ImageIcon("assets/avatars/player2.png").getImage();
		avatars[2] = new ImageIcon("assets/avatars/player3.png").getImage();
		avatars[3] = new ImageIcon("assets/avatars/player4.png").getImage();
		
		cardBackImage = new ImageIcon("assets/cards/b.gif").getImage();
		
		char[] suit = {'d','c','h','s'};
		char[] rank = {'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j', 'q', 'k'};
		
		for (int i = 0; i < 4; i++)
		    {
				for(int j = 0 ; j < 13;j++)
				{
			        cardImages[i][j] = new ImageIcon("assets/cards/" + rank[j] + suit[i] + ".gif").getImage();
				}
		        
		    }	
		
		this.buildGUI();
	}
	
	private void buildGUI() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500,950);
		
		frame.setLayout(new BorderLayout());
		
		setActivePlayer(game.getCurrentIdx());
		
		JMenuItem item, item1;
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Game");
		
	    item = new JMenuItem("Connect");
		item1 = new JMenuItem("Quit");
		
		item.addActionListener(new ConnectMenuItemListener());
		item1.addActionListener(new QuitMenuItemListener());
		
	    menu.add(item);
		menu.add(item1);
	
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setLayout(new BoxLayout(bigTwoPanel, BoxLayout.Y_AXIS));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		
		bottomPanel.add(playButton);
		bottomPanel.add(new JSeparator());
		bottomPanel.add(passButton);		
		

		JPanel messages = new JPanel();
		messages.setLayout(new BoxLayout(messages, BoxLayout.PAGE_AXIS));
		
		msgArea = new JTextArea(20,25);
		msgArea.setBackground(new Color(255,246,237)); 
		msgArea.setFont(new Font("SansSerif", Font.BOLD, 18));
		msgArea.setEditable(false);
		msgArea.setEnabled(false);
		
		
	    DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(msgArea);
	    messages.add(scrollPane);
		
		chatArea = new JTextArea(21,25);
		chatArea.setBackground(new Color(255,246,237)); 
		chatArea.setFont(new Font("SansSerif", Font.BOLD, 18));
		chatArea.setEnabled(false);
		
	    DefaultCaret caretChat = (DefaultCaret) chatArea.getCaret();
	    caretChat.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scrollPaneChat = new JScrollPane();
	    
	    scrollPaneChat.setViewportView(chatArea);
	    
	    messages.add(scrollPaneChat);

	    JPanel chat = new JPanel();
	    chat.setLayout(new FlowLayout());
	    chat.add(new JLabel("Message:"));
	    
	    chatTypeArea = new JTextField();
	    chatTypeArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
	    chatTypeArea.addActionListener(new EnterListener());
	    chatTypeArea.setPreferredSize( new Dimension( 200, 25 ) );
	    chat.add(chatTypeArea);
	    messages.add(chat);
	    
		
	    frame.add(messages, BorderLayout.EAST);;
		
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
	
	
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer
	 *            an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
		
	/**
	 * method for getting an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected
	 */
	public int[] getSelected() {
		ArrayList<Integer> selectedCards = new ArrayList<Integer>();
		
		for(int i=0; i< selected.length; i++) {
			if(selected[i] == true) {
				selectedCards.add(i);
			}
		}
		
		if(selectedCards.size() <= 0) {
			return null;
		}
		else {
			int[] selectedIndices = new int [selectedCards.size()];  
	        for(int i=0; i<selectedCards.size(); i++){  
	            selectedIndices[i] = selectedCards.get(i);  
	        }  
	        return selectedIndices;
		}
	}
	
	/**
	 * method for resetting the list of selected cards
	 */
	public void resetSelected() {
		this.selected = new boolean[13];
		this.repaint();
	}
	
	/**
	 * method for printing the specified string to the message area of the GUI.
	 * 
	 * @param msg
	 *            the string to be printed to the message area of the card game
	 *            table
	 */
	public void printMsg(String msg) {
		msgArea.append(msg + "\n");
	}
	
	/**
	 * method for clearing the message area of the GUI
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	/**
	 * Prints the specified string to the chat message area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the chat message area of the card game
	 *            table
	 */
	public void printChatMsg(String msg) {
		chatArea.append(msg+"\n");
	}
	
	/**
	 * Clears the message area of the card game table.
	 */
	public void clearChatMsgArea() {
		chatArea.setText("");
	}
	
	/**
	 * method for resetting the GUI
	 */
	public void reset() {
		resetSelected();
		clearMsgArea();
		enable();
	}
	
	/**
	 * method for enabling user interactions with the GUI
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	
	/**
	 * method for disabling user interactions with the GUI
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	
	/**
	 * method for resetting the GUI
	 */
	public void repaint() {
		for(int i=0; i<13; i++) {
			selected[i] = false;
		}
		
		bigTwoPanel.repaint();
		bigTwoPanel.revalidate();
	}
	
	/**
	 * Prints the cards of CardList in a pretty way into the message area
	 * @param cards cards list of type CardList to be printed
	 */
		public void printCards(CardList cards) {
			boolean printFront = true;
			boolean printIndex = false;
			String string = "";
			if (cards.size() > 0) {
				for (int i = 0; i < cards.size(); i++) {
					
					if (printIndex) {
						string = i + " ";
					}
					
					if (printFront) {
						string = string + "[" + cards.getCard(i) + "]";
					} else {
						string = string + "[  ]";
					}
					
					if (i % 13 != 0) {
						string = " " + string;
					}
					
				}
				this.printMsg(string);
				
			} else {
				this.printMsg("[Empty]");
			}
		}
	
	/**
	 * an inner class that extends the JPanel class and implements the
	 * MouseListener interface. Overrides the paintComponent() method inherited from the
	 * JPanel class to draw the card game table. Implements the mouseClicked() method from
	 * the MouseListener interface to handle mouse click events. 
	 * 
	 * @author Rajat Jain
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener {
		
		/**
		 * BigTwoPanel default constructor which adds the Mouse Listener and sets background of the card table.
		 */
		BigTwoPanel(){
			this.addMouseListener(this);
	        setBackground(new Color(178,30,59)); 
		}
		
		/**
		 * 		
		 * Draws the avatars, text and cards on card table
		 * @param g Provided by system to allow drawing
		 */
		 
		public void paintComponent(Graphics g) {
		 
			
			super.paintComponent(g);
			g.setColor(Color.YELLOW);
			
			int[] coordinate_indices = {43,203,363,523};
			
			Graphics2D g2 = (Graphics2D) g;
			
			for(int i=0; i<game.getNumOfPlayers(); i++) {
				
				Font font = new Font("SansSerif", Font.BOLD, 17);
				g.setFont(font);
				

				if (i == game.getPlayerID()) {
					if (i == game.getCurrentIdx()) {
						g.setColor(Color.BLUE);
					} else {
						g.setColor(Color.YELLOW);
					}
					g.drawString(game.getPlayerList().get(i).getName()+" (You)",5,20+(i*160));
					g.drawImage(avatars[i], 5, 30+(i*160), this);
					g.setColor(Color.WHITE);
					g2.drawLine(0,160*(i+1),1600, 160*(i+1));
					g.setColor(Color.YELLOW);
				} 

				else if (i == game.getCurrentIdx()) {
					g.setColor(Color.BLUE);
					g.drawString(game.getPlayerList().get(i).getName(),5, 20+(i*160));
					g.drawImage(avatars[i], 5, 30+(i*160), this);
					g.setColor(Color.WHITE);
					g2.drawLine(0,160*(i+1),1600, 160*(i+1));
					g.setColor(Color.YELLOW);
				
				}
				else {
					g.setColor(Color.YELLOW);
	 				g.drawString(game.getPlayerList().get(i).getName(), 5, 20+(i*160));
					g.drawImage(avatars[i], 5, 30+(i*160), this);
					g.setColor(Color.WHITE);
					g2.drawLine(0,160*(i+1),1600, 160*(i+1));
					g.setColor(Color.YELLOW);
				
				
				}
					
					for (int j=0; j < game.getPlayerList().get(i).getNumOfCards() && j < 13; j++) {
						
						if (i == game.getPlayerID()) {
						int selectedSuit = game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
						int selectedRank = game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
						
						if (selected[j] == true) {

							g.drawImage(cardImages[selectedSuit][selectedRank], 155+40*j, coordinate_indices[i]-20, this);
						}
						
						else
			    		{
			    			g.drawImage(cardImages[selectedSuit][selectedRank], 155+40*j, coordinate_indices[i], this);
			    		}
						
					}
					    else
					    {
					    		g.drawImage(cardBackImage, 155 + 40*j, coordinate_indices[i], this);
					    }
						
					
					}
				

			}
			
			g.drawString("Hands on Table", 400, 660);
			
			if(game.getHandsOnTable().isEmpty() == false) {
				
				
				g.setFont(new Font("SansSerif", Font.PLAIN, 15));
				Hand handOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
		    	g.drawString("Hand Type:\n ", 10, 700);
		    	
		    	g.drawString(game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getType() , 90, 700);
		    	for (int i = 0; i < handOnTable.size(); i++)
		    		{
		    			int tableCardSuit = handOnTable.getCard(i).getSuit();
		    			int tableCardRank = handOnTable.getCard(i).getRank();
		    			g.drawImage(cardImages[tableCardSuit][tableCardRank], 160 + 40*i, 690, this);
		    		}
		    		
		    	g.drawString("Played by: " + game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName(), 10, 725);
				
		    	g.setFont(new Font("SansSerif", Font.BOLD, 17));
			}
			
			this.repaint();
			
		}

		/**
		 * 	
		 * Defines what happens when mouse is clicked on the card table. Only allows clicks on cards of active player. Once cards are selected, the JPanel is repainted to reflect changes.
		 * @param e Mouse event created when Mouse Clicked
		 */
		

		public void mouseClicked(MouseEvent e) 
		{
		
			if(activePlayer == ((BigTwoClient) game).getPlayerID()) {
			int beginningPoint = game.getPlayerList().get(activePlayer).getNumOfCards()-1;
			boolean marker = false; 
			
			int xCoord = 155;
			int yLow = 43 + 160 * activePlayer;
			
			
			if (e.getX() >= (xCoord+beginningPoint*40) && e.getX() <= (xCoord+beginningPoint*40+73)) 
			{
				
				if(selected[beginningPoint] == false && e.getY() >= yLow && e.getY() <= yLow+97) 
				{
					
					selected[beginningPoint] = true;
					marker = true;
			
				} 
				
				else if (selected[beginningPoint] == true && e.getY() >= yLow-20 && e.getY() <= yLow+77)
				{
					
					selected[beginningPoint] = false;
					marker = true;
					
				}
				
			}
			
			for (beginningPoint = game.getPlayerList().get(activePlayer).getNumOfCards()-2; beginningPoint >= 0 && !marker; beginningPoint--) 
			{
				if (e.getX() >= (xCoord+beginningPoint*40) && e.getX() <= (xCoord+beginningPoint*40+40)) 
				{
					
					if(selected[beginningPoint] == false && e.getY() >= yLow && e.getY() <= yLow+97) 
					{
						
						selected[beginningPoint] = true;
						marker = true;
						
					} 
					
					else if (selected[beginningPoint] == true && e.getY() >= yLow-20 && e.getY() <= yLow+97-20)
					{
						
						selected[beginningPoint] = false;
						marker = true;
						
					}
					
				}
				
				else if (e.getX() >= (xCoord+beginningPoint*40+40) && e.getX() <= (xCoord+beginningPoint*40+73) && e.getY() >= yLow && e.getY() <= yLow+97) 
				{
					
					if (selected[beginningPoint+1] == true && selected[beginningPoint] == false) 
					{
						
						selected[beginningPoint] = true;
						marker = true;
						
					}
					
				}
				
				else if (e.getX() >= (xCoord+beginningPoint*40+40) && e.getX() <= (xCoord+beginningPoint*40+73) && e.getY() >= yLow-20 && e.getY() <= yLow+97-20)
				{
					
					if (selected[beginningPoint+1] == false && selected[beginningPoint] == true)
					{
						
						selected[beginningPoint] = false;
						marker = true;
						
					}
					
				}
			}
			
			this.repaint();		
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {	
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		
		@Override
		public void mouseExited(MouseEvent e) {	
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button. When the “Play” button is clicked, it calls the makeMove() method of CardGame object to make a move.
	 * @author Rajat Jain
	 */
	class PlayButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
			if (game.getPlayerID() == game.getCurrentIdx()) {
				
				if(getSelected() ==  null) {
					printMsg("Inavalid Move! Please Select a Card. \n");
				}
				else {
				game.makeMove(activePlayer, getSelected());
				repaint();
				}
			}
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button. When the “Pass” button is clicked, it calls the makeMove() method of CardGame object to make a move.
	 * @author Rajat Jain
	 */
	class PassButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (game.getPlayerID() == game.getCurrentIdx()) {
				game.makeMove(activePlayer, null);
				repaint();
			}
		}
		
	}
	

	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item. When the “Quit” menu item is selected, it terminates application.
	 * @author Rajat Jain
	 */
	class QuitMenuItemListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) { 
			
			System.exit(activePlayer);
			
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Send” button.
	 * @author Rajat Jain
	 */
	class EnterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG, -1, chatTypeArea.getText());
			chatTypeArea.setText("");
			game.sendMessage(message);
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Connect” menu item.
	 * @author Rajat Jain
	 */
	class ConnectMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!game.isConnected()) {
				reset();
				game.makeConnection();
			}
		}
	}

	
}

