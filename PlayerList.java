/**
 * PlayerList is a doubly linked list class containing
 * each Player object in the game.
 *
 */
public class PlayerList<Player> {
    private PlayerNode<Player> header;
    private PlayerNode<Player> trailer;
    private int size;

    /**
     * The PlayerList constructor initializes a new EMPTY doubly linked player list
     */
    public PlayerList() {
        this.header = new PlayerNode<Player>(null, null, null);
        this.trailer = new PlayerNode<Player>(null, header, null);
        this.header.setNext(trailer);
        this.size = 0;
    }

    /**
     * A standard addBetween method for a doubly linked list, that
     * creates a new node with a Player element and adds it in between
     * two other nodes.
     * @param e -- the Player element for the new node
     * @param pre -- the Node that that comes before the added node
     * @param post -- the Node that comes after the added node
     */
    public void addBetween(Player e, PlayerNode<Player> pre, PlayerNode<Player> post) {
        PlayerNode<Player> newest = new PlayerNode<Player>(e, pre, post);
        pre.setNext(newest);
        post.setPrevious(newest);
        size++;
    }

    /**
     * the add method adds a Node (with an inputted Player) to the beginning of the list
     * @param player -- the player object from which the new node will be created
     */
    public void add(Player player) {  // adds a new player to the beginning of the list
        addBetween(player, trailer.getPrevious(), trailer);
    }

    // Getter and Setter Methods:

    /**
     * the getNextPlayer method is a modified getNext function that takes the current Node
     * as well as a boolean input for the game's direction in order to move to the next
     * node in the doubly linked list.
     * @param current -- the current Node in the list.
     * @param gameDirection -- boolean that represents the direction where "next" is in the list
     *                      (whether it is previous or next.)
     * @return the PlayerNode for the next player in the list
     */
    public PlayerNode<Player> getNextPlayer(PlayerNode<Player> current, boolean gameDirection) {
        // cycles through continuously as if the list were a circularly linked list (no null values)
        if (!gameDirection) {  // if game direction is reversed:
            if (current.getPrevious() == header) {
                return last();  // cycle to the end of the list
            } return current.getPrevious();  // otherwise, just return the previous node
        } else {  // if game direction is forward
            if (current.getNext() == trailer) {
                return first();  // cycle to the beginning of the list
            } return current.getNext();  // otherwise, just return the next node
        }
    }
/// basic isEmpty, size, first and last methods for the doubly linked list
    public boolean isEmpty() {return this.size == 0;}  // Checks whether the list is empty
    public int size() {return this.size;}

    public PlayerNode<Player> first() {return this.header.getNext();}
    public PlayerNode<Player> last() {return this.trailer.getPrevious();}

}

