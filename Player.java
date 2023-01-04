import java.util.ArrayList;

/**
 * the Player class represents a player of the Uno game, which has a name and a hand
 * (which is an ArrayList of Card objects.)
 */
public class Player {
    String name;
    ArrayList<Card> hand;

    /**
     * the Player constructor creates a Player object.
     * @param playerName -- the playerName is a String that is inputted
     *                   to the Player constructor in order to initialize the
     *                   name field.
     */
    public Player(String playerName) {
        this.name = playerName;
        this.hand = new ArrayList<Card>();  // Players start with an empty hand
    }

    /**
     * the toString method returns a string representation of a player object
     * @return -- the String representation of a player.
     */
    public String toString(){
        if (hand.size() == 0) {
            return "Player: " + name + "  (no cards)";
        }
        return "Player: " + name + "  (" + getHandSize() + " cards)";
    }

    /**
     * Adds an inputted Card object to the player's hand.
     * @param card the Card object to add to the player's hand.
     * @return Nothing.
     */
    public void addToHand(Card card) {
        hand.add(card);
    }

    //the following are "getter" methods, as well as an isEmpty method
    public ArrayList<Card> getHand() {return hand;}  // returns an arrayList of cards containing the player's hand
    public boolean isHandEmpty() {return hand.size()==0;} // checks if the player's hand is empty

    public int getHandSize() {return hand.size();} // returns the number of cards in a player's hand
    public String getName() {return this.name;}  // returns the name of the player

}
