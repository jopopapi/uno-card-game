/**
 * This is the Deck class. The deck of cards is represented by an ArrayList of Card objects.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Deck class is used to initialize a Deck object, which contains an ArrayList
 * of Cards that make up the overall deck
 */
public class Deck {
    // Fields
    private ArrayList<Card> cards;  // arrayList of Card objects
    private int size;  // number of cards in the deck

    public Deck() {
        /**
         * This is the constructor method for the Deck() class.
         * It creates a new deck of cards containing 108 cards in total.
         */
        cards = new ArrayList<Card>();
        size = 0;
        for (int i = 0; i < 4; i++) {
            // Creating cards for each regular color (excluding wild cards for now)...
            Card.Color color = Card.getColor(i);
            cards.add(new Card(color, Card.Value.Zero));  // each deck has one Zero card in each color
            size++;

            for (int j = 1; j < 10; j++) {  // each deck has two of each of the following cards in each color: 1s, 2s, 3s, 4s, 5s, 6s, 7s, 8s, and 9s.
                cards.add(new Card(color, Card.getValue(j))); size++;
                cards.add(new Card(color, Card.getValue(j))); size++;
            }

            cards.add(new Card(color, Card.Value.DrawTwo)); size++;  // each deck has two draw-two cards in each color
            cards.add(new Card(color, Card.Value.DrawTwo)); size++;

            cards.add(new Card(color, Card.Value.Skip)); size++;  // each deck has two skip cards in each color
            cards.add(new Card(color, Card.Value.Skip)); size++;

            cards.add(new Card(color, Card.Value.Reverse)); size++;  // each deck has two reverse cards in each color
            cards.add(new Card(color, Card.Value.Reverse)); size++;
        }
        // Creating the wild cards...
        for (int w = 0; w < 4; w++) {  // each deck has four wild cards and wild-draw-four cards
            cards.add(new Card(Card.Color.Wild, Card.Value.Wild)); size++;
            cards.add(new Card(Card.Color.Wild, Card.Value.WildDrawFour)); size++;
        }
    }

    // toString Method

    /**
     * The toString method returns a string that represents the Deck.
     * @return
     */
    public String toString() {
        String msg = "";
        for (Card card : cards) {
            msg += card.toString() + "\n";
        }
        return msg;
    }

    /**
     * This method fills the deck with an array list of card and then shuffles the deck
     * @param pile -- the ArrayList of card objects that are to be shuffled.
     */
    public void replaceDeck(ArrayList<Card> pile) {
        for (int i = 0; i < pile.size() -1; i++) {
            Card card = pile.remove(0);
            cards.add(card);
        }
        shuffle();
    }

    /**
     * The shuffle() method shuffles the deck of cards.
     * @return Nothing.
     */
    public void shuffle() {
        // Instantiating a new Random object:
        Random rand = new Random();
        // Iterate through the deck and swapping cards at random indexes:
        for (int i = 0; i < size * 2; i++) {
            int firstRandomIndex = rand.nextInt(size-1);
            int secondRandomIndex = rand.nextInt(size-1);
            swapCards(firstRandomIndex, secondRandomIndex);
        }
    }

    /**
     * Swaps two cards at give indices.
     * @param i index of the first card to swap
     * @param j index of the second card to swap
     * @return Nothing.
     */
    public void swapCards(int i, int j) {

        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
    }

    /**
     * Checks whether the deck of cards is empty.
     * @return True if the deck is empty, False if the deck is not empty.
     */
    public boolean isEmpty(){
        return this.size==0;
    }

    /**
     * Draws a given number of cards from the deck of cards.
     * @param i the number of cards to draw from the deck.
     * @return an ArrayList of Cards containing the cards that were drawn. Throws
     *         exceptions if the input is not positive, or if trying to draw more
     *         cards than are contained in the deck.
     */
    public ArrayList<Card> draw(int i) throws IllegalArgumentException {
        if (i <= 0) {
            throw new IllegalArgumentException("Input a positive number of cards to draw.");
        }
        if (i > size) {
            throw new IllegalArgumentException("Can't draw " + i + " cards from a deck of " + size + " cards.");
        }

        ArrayList<Card> drawn = new ArrayList<Card>(); // instantiating a new arrayList to hold the drawn cards
        for (int n = 0; n < i; n++) {
            Card card = cards.get(size-1);
            cards.remove(size-1);
            size--;
            drawn.add(card);  // returning the top i cards in the deck
        } return drawn;
    }

    /**
     * Draws a single card from the deck.
     * @return the Card object that was drawn from the deck. Throws an exception if the deck is empty.
     */
    public Card draw() throws IllegalArgumentException {
        if (isEmpty()) { throw new IllegalArgumentException("Can't draw cards from an empty deck!");}
        Card ret = cards.get(size-1); // storing the top card in the deck in a return statement
        cards.remove(size-1);  // removing the top card from the deck
        size--;
        return ret;
    }

    /**
     * the getSize() method returns the size of the deck.
     * @return -- int value that is the size of the deck
     */
    public int getSize() {return cards.size();}

}
