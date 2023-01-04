import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * GameFrame is the java class that creates the UI for the current player's hand, the discard
 * pile, the deck, etc. (The frame that allows one to choose a wild card is also a
 * part of this class.
 */
public class GameFrame extends JFrame{

    //creating different components from JavaSwing (frames, labels, buttons, etc) that
    //will be needed for the game.
    JFrame frame = new JFrame();
    JLabel directionLabel = new JLabel("DIRECTION");
    JLabel directionIconLabel = new JLabel();
    JLabel playerLabel = new JLabel("Test");
    JLabel dialogLabel = new JLabel();
    JTextArea playerInfoText = new JTextArea();
    JLabel deckLabel = new JLabel("DECK");
    JLabel discardLabel = new JLabel("DISCARD PILE");
    JButton deckButton = new JButton();
    JButton discardButton = new JButton();
    JPanel handPanel;
    JButton handToNextPlayerButton = new JButton();


    /**
     * The GameFrame is constructed after the user inputs the names
     * for the players.
     * @param input -- the list of strings with the names of the players
     * @throws IOException
     */
    public GameFrame(String[] input) throws IOException {

        // sets the size and visibility of the game frame
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null); // Not using a layout manager

        // Creating a new game with the inputted player names:
        Game game = new Game(input);
        game.start();

        // Setting up the layout for the top of the game screen:
        frame.add(dialogLabel);
        dialogLabel.setBounds(500, 335, 820, 40);

        // Setting up the layout for the deck and the discard pile:
        deckButton.setIcon(rescale("src/CardIcons/face-down.png", 200, 280));
        discardButton.setIcon(rescale("src/CardIcons/" + game.getTopColor() + "-" + game.getTopValue() + ".png", 200, 280));
        frame.add(deckLabel);
        frame.add(discardLabel);
        frame.add(deckButton);
        frame.add(discardButton);
        deckButton.setBounds(20, 40, 200, 280);
        deckLabel.setBounds(20, 20, 100, 10);
        discardButton.setBounds(250, 40, 200, 280);
        discardLabel.setBounds(250, 20, 100, 10);

        frame.add(playerLabel);
        playerLabel.setBounds(20, 335, 200, 40);
        frame.add(playerInfoText);
        playerInfoText.setBounds(500, 20, 700, 300);
        updatePlayerInfo(game);

        directionIconLabel.setIcon(rescale("src/CardIcons/Downward_Arrow.png", 150, 150));
        frame.add(directionLabel);
        frame.add(directionIconLabel);
        directionLabel.setBounds(1230, 20, 100, 10);
        directionIconLabel.setBounds(1230, 40, 150, 150);



        // Setting up functionality for the draw pile (deck):
        deckButton.addActionListener(new ActionListener() {
            @Override
            /**
             * ActionListener for when the user clicks the deck pile to draw a card.
             */
            public void actionPerformed(ActionEvent e) {
                String name = game.getCurrentPlayerString();
                game.submitDraw(game.getCurrentPlayer().getPlayer());  // draws one card from the deck and adds it to the player's hand.
                dialogLabel.setText(name + " drew a card.");
                // Updating the card display:
                handPanel.setVisible(false);
                frame.remove(handPanel);
                displayCards(game);
                updatePlayerInfo(game);
            }
        });

        // Setting up the layout for the current player's hand:
        dialogLabel.setText("Click on a card to play it");
        int i = 0;
        displayCards(game);

        // Setting up functionality for the "Hand to Next Player" JButton:
        handToNextPlayerButton.addActionListener(new ActionListener() {
            @Override
            /**
             * actionPerformed runs when the handToNextPlayerButton is clicked,
             * leads to the next user's cards being displayed.
             */
            public void actionPerformed(ActionEvent e) {
                handToNextPlayerButton.setVisible(false);
                frame.remove(handToNextPlayerButton);
                displayCards(game);

            }
        });

    }


    /**
     * displayCards displays the current player's hand onto the game screen.
     * Each card in the player's hand is a JButton that, when clicked, causes
     * the corresponding card to be played.
     * @return Nothing.
     */
    public void displayCards(Game game) {

        // JPanel component to hold the player's hand of cards

        handPanel = new JPanel();
        frame.add(handPanel);
        handPanel.setBounds(20, 390, 1360, 475);
        handPanel.setBackground(Color.lightGray);
        int i = 0;
        for (Card card : game.getCurrentPlayer().getPlayer().getHand()) {
            // for each card in the player's hand, create a new JButton and put it on the screen
            JButton cardButton = new JButton(rescale("src/CardIcons/" + card.getColor() + "-" + card.getValue() + ".png", 100, 140));
            handPanel.add(cardButton);
            // positioning the cards in rows
            if (i < 10) {
                cardButton.setBounds((15 + 15 * i + 100 * i), 400, 100, 140);
            } else if (i < 20){  // wrapping the cards in an additional row if there are too many cards
                cardButton.setBounds((15 + 15 * (i - 8) + 100 * (i - 8)), 560, 100, 140);
            } else {  // wrapping again
                cardButton.setBounds((15 + 15 * (i - 16) + 100 * (i - 16)), 720, 100, 140);
            }

            i++;
            // Add a new action listener for every card button in the player's hand displayed on the screen:
            cardButton.addActionListener(new ActionListener() {
                @Override
                /**
                 * actionPerformed is an action listener method that is called
                 * when one of the cardButtons is clicked (in order to play a card.)
                 */
                public void actionPerformed(ActionEvent e) {

                    // Function for when a user plays a card:
                    if (!game.validPlay(card)) {  // checks whether the card play is invalid
                        if (game.getTopValue() == Card.Value.Wild || game.getTopValue() == Card.Value.WildDrawFour) {
                            dialogLabel.setText("Invalid card. Make sure to play a card that matches the color " +
                                    game.getTopColor() + ".");
                        } else {
                            dialogLabel.setText("Invalid card. Make sure to play a card that is either " +
                                    game.getTopColor() + " or has a value of " + game.getTopValue() + ".");
                        }
                    } else {
                        Card.Color[] colorPick = new Card.Color[1]; // initiating a variable to hold a color choice
                        if (card.getColor() == Card.Color.Wild) { // if the card is a wild card
                            //create a frame with four butons representing the different colors
                            //that can be chosen by the player.
                            JFrame wildFrame = new JFrame();
                            JButton redButton = new JButton("Red");
                            JButton yellowButton = new JButton("Yellow");
                            JButton greenButton = new JButton("Green");
                            JButton blueButton = new JButton("Blue");

                            ArrayList<JButton> colorButtons = new ArrayList<JButton>();

                            //set the size and visibility for the wild card color picker frame.
                            wildFrame.setSize(120,500);
                            wildFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            wildFrame.setVisible(true);
                            wildFrame.setLayout(null); // Not using a layout manager

                            //add the buttons to the frame
                            colorButtons.add(redButton);
                            colorButtons.add(yellowButton);
                            colorButtons.add(greenButton);
                            colorButtons.add(blueButton);

                            //set the positioning of the colorButtons
                            redButton.setBounds(10, 10 + 40, 100, 20);
                            yellowButton.setBounds(10, 10 + 80, 100, 20);
                            greenButton.setBounds(10, 10 + 120, 100, 20);
                            blueButton.setBounds(10, 10 + 160, 100, 20);

                            //add action listeners to all of the color buttons...
                            for(JButton colorButton: colorButtons){
                                wildFrame.add(colorButton);
                                //colorButton.setBounds(10, 10 + 40*i, 100, 20);

                                colorButton.setFont(new Font("Arial", Font.BOLD, 16));

                                colorButton.addActionListener(new ActionListener() {
                                    @Override
                                    /**
                                     * if one of the colorButtons is clicked, set the "colorPick"
                                     * variable to be equal to the string value of the clicked button,
                                     * and then set the top color of the deck to be equal to the
                                     * chosen color.
                                     */
                                    public void actionPerformed(ActionEvent e) {
                                        String stringColor = colorButton.getText(); //get the color of the chosen button
                                        colorPick[0] = Card.Color.valueOf(stringColor);
                                        wildFrame.dispose();
                                        String playCardString = game.playCard(card, colorPick[0]); //set the color for the game to be equal to the chosen color
                                        dialogLabel.setText(playCardString); //update the screen with what color was chosen
                                        updateDiscardPileIcon(game); //update the discard pile with the wild card
                                    }
                                });
                            }
                        } else { // if the chosen card is not wild...
                            colorPick[0] = card.getColor(); //get the color of the chosen card
                            String playCardString = game.playCard(card, colorPick[0]); // playing the card
                            if (playCardString.equals("Game Over!")) {
                                // Checking if the game is over...
                                dialogLabel.setText("[GAME OVER] " + game.getCurrentPlayer().getPlayer().getName() + " has won! Thanks for playing!");
                                //exit the game if the game is over
                                try { Thread.sleep(5000); } catch (Exception eeee){};
                                System.exit(0);
                            } else { // if the game is not over, display information about what the player played
                                dialogLabel.setText(playCardString);
                            }
                            updateDiscardPileIcon(game);
                            //display the arrow showing that the game's direction has been reversed (if a reverse card has been played)
                            if (card.getValue() == Card.Value.Reverse) {
                                if (game.getDirection()) {
                                    directionIconLabel.setIcon(rescale("src/CardIcons/Downward_Arrow.png", 150, 150));
                                } else {
                                    directionIconLabel.setIcon(rescale("src/CardIcons/Upward_Arrow.png", 150, 150));

                                }
                            }
                        }
                        // Removing the current player's hand from view:
                        handPanel.setVisible(false);
                        frame.remove(handPanel);

                        // Setting up the next player's turn:
                        updatePlayerInfo(game);
                        displayNextPlayerButton(game);
                    }

                }
            });
        }
    }

    /**
     * Removes the previous player's hand of cards from the view, and
     *          * displays a button between players' turns.
     * @param game -- takes the game object that is currently being played as its input
     */
    public void displayNextPlayerButton(Game game) {
        handPanel.setVisible(false);
        // Displaying the button that gives the name of the next player
        frame.add(handToNextPlayerButton);
        handToNextPlayerButton.setVisible(true);
        handToNextPlayerButton.setText("Click to Start " + game.getCurrentPlayerString() + "'s Turn");
        handToNextPlayerButton.setBounds(75, 400, 300, 40);

    }

    /**
     * rescales an image to be a new size
     * @param fh -- String with the address of the image
     * @param newWidth -- int value for the new width of the image
     * @param newHeight -- int value for the new height of the image
     * @return -- returns an ImageIcon icon with the resized image
     */
    public ImageIcon rescale(String fh, int newWidth, int newHeight) {
        //resizes an image and returns it
        ImageIcon image = new ImageIcon(fh);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH );
        return new ImageIcon(newImg);
    }

    /**
     * updatePlayerInfo updates the information displayed about who is currently playing,
     * the number of cards in each player's deck, etc.
     * @param game -- the Game object for the current game that is being played.
     */
    public void updatePlayerInfo(Game game) {
        String info = " ";
        PlayerNode<Player> current = game.getPlayers().first();//gets the current player (at the beginnning of the linked list)
        for (int i = 0; i < game.getPlayers().size(); i++) { // for each player, add the current info
            info += current.getPlayer().getName() + ": " + current.getPlayer().getHandSize() + " cards";
            if(current.equals(game.getCurrentPlayer())) { // mark who is currently playing
                info += " (Playing)";
            }
            info += "\n\n";
            current = current.getNext(); //go to the next player
        }
        info += "Number of Cards: " + game.getDeck().getSize(); // display deck size
        info += "\nNumber in Discard Pile: " + game.getDiscardPile().size(); // display discard pile size
        playerInfoText.setText(info); // display all of the accumulated information at the top of the frame.
        playerLabel.setText(game.getCurrentPlayer().getPlayer().getName());
        playerLabel.setFont(new Font("Arial", Font.BOLD, 32));
    }

    /**
     * updateDiscardPileIcon updates the card displayed for the discard pile to be
     * the image associated with the most recently played card.
     * @param game -- the game object for the current game
     */
    public void updateDiscardPileIcon(Game game) {
        //display the image for the most recently played card
        if (game.getTopValue () == Card.Value.Wild || game.getTopValue () == Card.Value.WildDrawFour) {
            discardButton.setIcon(rescale("src/CardIcons/Wild-" + game.getTopValue() + ".png", 200, 280));
        } else {
            discardButton.setIcon(rescale("src/CardIcons/" + game.getTopColor() + "-" + game.getTopValue() + ".png", 200, 280));
        }
    }
}
