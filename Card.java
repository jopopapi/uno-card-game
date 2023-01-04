import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * The Card class stores the information associated with an Uno card, including its color and value.
 */
public class Card {
    // Fields
    public enum Color{Red, Yellow, Green, Blue, Wild};
    public static final Color[] colors = Color.values();  // creating an array containing all possible colors
    public enum Value{Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip, Reverse, Wild, WildDrawFour};
    public static final Value[] values = Value.values();  // creating an array containing all possible values

    private Color color;
    private Value value;

    // Constructor Method

    /**
     * The Card constructor initializes a new object of type Card
     * @param color -- the color of the card (of enum Color)
     * @param value -- the value of the card (of enum Value)
     */
    public Card(final Color color, final Value value) {
        this.color = color;
        this.value = value;

    }

    // ToString Method

    /**
     * The toString method returns a string representing the Card
     * @return -- the String representation of the Card
     */
    public String toString(){
        return color + " / " + value;
    }


    // Getter Methods (there are no setter methods because a card's value/color are final)

    /**
     * getColor returns the Color of the card associated with an int that is inputted
     * @param i -- int that is inputted that matches to a color
     * @return the enum Color that corresponds to the int.
     */
    public static Color getColor(int i) {
        // This method returns the color corresponding to an inputted integer.
        // 0: Red    1: Yellow    2: Green    3: Blue    4: Wild
        return colors[i];
    }

    /**
     * getColor() returns the Color associated with the current Card.
     * @return -- the Color of the current Card object's color field.
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * getValue returns the Value associated with an inputted integer, i.
     * @param i -- int that is inputted
     * @return enum of type Value that is associated with the integer
     */
    public static Value getValue(int i) {
        // This method returns the value corresponding to an inputted integer.
        // 0-9: Numbers Zero through Nine     10: DrawTwo    11: Skip
        // 12: Reverse     13: Wild     14: WildDrawFour
        return values[i];
    }

    /**
     * getValue (if nothing is inputted) returns the value associated with the current
     * Card object.
     * @return -- the Value enum that is associated with the current Card object.
     */
    public Value getValue(){
        return this.value;
    }


}
