/**
 * PlayerNode class for the PlayerList (resembles a node for a doubly linked list)
 */
public class PlayerNode<Player> {
    // Instance Fields:
    private Player player;
    private PlayerNode<Player> previous;
    private PlayerNode<Player> next;

    // Constructor Method:

    /**
     * the PlayerNode constructor creates a PlayerNode object.
     * @param player -- the Player object that the PlayerNode will contain
     * @param previous -- a reference to the previous player node in the PlayerList
     * @param next -- a reference  to the next player node in the PlayerList
     */
    public PlayerNode(Player player, PlayerNode<Player> previous, PlayerNode<Player> next) {
        this.player = player;
        this.previous = previous;
        this.next = next;
    }

    // ToString Method

    /**
     * toString method returns a string representation of the PlayerNode.
     * @return
     */
    public String toString() {
        if (player == null) {
            return null;
        }
        return player.toString();
    }

    // Getter and Setter Methods:
    public Player getPlayer() {return this.player;}
    public PlayerNode<Player> getPrevious() {return this.previous;}
    public PlayerNode<Player> getNext() {return this.next;}
    public void setPrevious(PlayerNode<Player> p) {previous = p;}
    public void setNext(PlayerNode<Player> n) {next = n;}

}
