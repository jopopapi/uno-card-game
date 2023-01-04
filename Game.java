/**
 * This is the Game class. Each instance of Game also instantiates a deck and a list of players.
 * This class contains methods for the game itself, including initiating the start of a game,
 * checking whether a play is valid, and checking whether a game is over.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Game {
    // Fields
    private PlayerList<Player> players;  // stores a linked list of players
    private PlayerNode<Player> currentPlayer;  // stores the current player as a node
    private Deck deck;  // the deck of cards
    private ArrayList<Card> discardPile;  // an arrayList containing the discarded cards
    private Card.Color topColor;  // storing the data about the top card in order to evaluate whether a played card is valid
    private Card.Value topValue;  // same as above
    private boolean direction;  // true if forward, false if backward

    /**
     * Constructor method for Game class. It creates a new shuffled deck, sets the direction, give each player 7 cards
     * @param args array contains all the player names
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public Game(String[] args) throws IOException, IllegalArgumentException {
        // Creating Game (creates a deck, shuffles the cards, and a discard pile)
        deck = new Deck();
        deck.shuffle();
        discardPile = new ArrayList<Card>();
        direction = true;

        // Creating Players:
        if (args.length == 0) {throw new IllegalArgumentException("Can't access player names.");}
        players = new PlayerList<Player>();
        for (String playerName : args) { // adds players to the PlayerList (with the inputted names)
            players.add(new Player(playerName));
        }

        // Dealing 7 Cards to Each Player
        PlayerNode<Player> curr = players.first();
        while (curr.getNext() != null) {  // Iterating through each player
            ArrayList<Card> hand = new ArrayList<Card>(deck.draw(7)); // creates a hand for each player
            for (Card card : hand) {
                curr.getPlayer().addToHand(card);
            }
            curr = curr.getNext(); // goes to the next player
        }
    }

    /**
     * Method that starts the game. If the card drawn is a special card, another card is drawn.
     */
    public void start() {
        Card card = deck.draw(); //draws a card from the deck
        setTopCard(card); //sets the top card of discard pile to be the drawn card
        currentPlayer = players.first();

        //if the drawn card is a special card, draw again
        if (card.getValue() == Card.Value.DrawTwo || card.getValue() == Card.Value.WildDrawFour || card.getColor() == Card.Color.Wild || card.getValue() == Card.Value.Skip || card.getValue() == Card.Value.Reverse) {start();}  // restart if starting with a draw or wild card
        discardPile.add(card);
    }

    /**
     * Method for when a player submits a card to play it.
     *
     * @param card the card that the player has chosen to play.
     * @param colorPick the player's color choice if they have played a Wild Card
     * @return A String containing the message to put in the dialog box after the card is played.
     */
    public String playCard(Card card, Card.Color colorPick){
        Player player = currentPlayer.getPlayer();
        checkDeck();    // checks if deck has enough cards
        player.getHand().remove(card);
        discardPile.add(card);
        if (player.isHandEmpty()) {     // checks if the game is over
            return "Game Over!";
        }

        String ret = "";    // String that tells the user what happened

        // if the card played was a special card, then the next player has a different set of actions:
        if (card.getValue() == Card.Value.DrawTwo) { // if it was a wild-draw-two card
            setTopCard(card);
            nextPlayer();
            currentPlayer.getPlayer().getHand().add(deck.draw());
            currentPlayer.getPlayer().getHand().add(deck.draw());
            ret += currentPlayer.getPlayer().getName() + " drew 2 cards.";
        } else if (card.getValue() == Card.Value.WildDrawFour) { // if it was a wild-draw-four card
            setTopColor(colorPick);
            setTopValue(Card.Value.WildDrawFour);
            nextPlayer();
            currentPlayer.getPlayer().getHand().add(deck.draw());
            currentPlayer.getPlayer().getHand().add(deck.draw());
            currentPlayer.getPlayer().getHand().add(deck.draw());
            currentPlayer.getPlayer().getHand().add(deck.draw());
            ret += player.getName() + " played a wild card and set the color to " + colorPick + ". " + currentPlayer.getPlayer().getName() + " drew 4 cards.";
        }  else if (card.getValue() == Card.Value.Wild) {
            setTopColor(colorPick);
            setTopValue(Card.Value.Wild);
            nextPlayer();
            ret += player.getName() + " played a wild card and set the color to " + colorPick + ".";
        } else if (card.getValue() == Card.Value.Skip) { // if it was a skip card
            setTopCard(card);
            nextPlayer();
            String skippedPlayer = currentPlayer.getPlayer().getName();
            nextPlayer();
            ret += player.getName() + " skipped " + skippedPlayer + "'s turn.";
        } else if (card.getValue() == Card.Value.Reverse) { // if it was a reverse card
            // reversing the game direction (if true, set to false; if false, set to true)
            setTopCard(card);
            direction = !direction;
            nextPlayer();
            ret += player.getName() + " reversed the direction of play.";
        } else {
            setTopCard(card);
            nextPlayer();
            ret += player.getName() + " played a card";
        } return ret;
    }

    /**
     * Method for when a player draws a card.
     * @param player the player who is requesting to draw a card
     * @return Nothing
     */
    public void submitDraw(Player player){
        checkDeck();    //check if deck has enough cards
        player.getHand().add(deck.draw());  // Draw a card and add it to the player's hand
    }


    // Helper Methods:
    /**
     * Makes the game go on to the next player
     */
    public void nextPlayer() {
        currentPlayer = players.getNextPlayer(currentPlayer, direction);
    }

    /**
     * Checks deck size and if it is less than 4, it is filled with the discard pile
     */
    public void checkDeck() {
        if (deck.getSize() < 4) {
            deck.replaceDeck(discardPile);
        }
    }

    public boolean validPlay(Card played) {
        /**
         * validPlay(Card played) checks whether a card play follows the rules.
         * @param played the card that the function will check.
         * @return True if the play is valid, False if the play is invalid.
         */
        // The rules of Uno are that each card played must either match the top card's
        // value OR color, unless the user plays a wild card. If a wild card was played
        // previously, the next player only needs to match the selected color.
        if (played.getColor() == Card.Color.Wild) {
            return true;
        }
        else if (topValue == Card.Value.Wild || topValue == Card.Value.WildDrawFour) {
            return played.getColor() == topColor;
        }
        else {return played.getColor() == topColor || played.getValue() == topValue;}
    }


    // Getter Methods (too many methods to add javadocs to):
    public PlayerList<Player> getPlayers() {return players;}
    public Deck getDeck() {return deck;}
    public ArrayList<Card> getDiscardPile() {return discardPile;}
    public boolean getDirection() {return direction;}
    public Card.Color getTopColor() {return topColor;}
    public Card.Value getTopValue() {return topValue;}
    public String getCurrentPlayerString() {return currentPlayer.getPlayer().getName();}
    public PlayerNode<Player> getCurrentPlayer() {return currentPlayer;}
    public PlayerNode<Player> getNextPlayer() {return players.getNextPlayer(currentPlayer, direction);}

    // Setter Methods:
    public void setTopCard(Card card) {  // Resets the top card value depending on what card was just played
        topColor = card.getColor();
        topValue = card.getValue();
    }
    public void setTopColor(Card.Color color) {topColor = color;}
    public void setTopValue(Card.Value value) {topValue = value;}

}

