import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The PlayerFrame class is used to construct the frame of the UI
 * which displays the rules of the Uno game as well as an input field
 * for the user to input the names of the game players.
 */
public class PlayerFrame extends JFrame {
    // Main Method

    /**
     * the main method creates a new player frame
     * @param args
     */
    public static void main(String[] args) {
        new PlayerFrame();
    }

    // Start Components

    //The following Java Swing components (with text, fields, etc) are initialized here,
    //and will be added to the PlayerFrame once the PlayerFrame is constructed.
    JFrame frame = new JFrame();
    JTextField nameField = new JTextField();
    JLabel disclaimerLabel = new JLabel("Please enter number of players between 2 and 10, separated by commas");

    JButton startButton = new JButton("SUBMIT");

    JTextArea introText = new JTextArea("Welcome to UNO.\n\n\nThe rules are as follows:\n\n" +
            "Each player is dealt 7 cards. The objective is to be the first to get rid of all 7 cards.\nDuring your turn, " +
            "you can play a single card at a time, but it must match either the color or\nvalue of the card at the top of the discard pile. " +
            "Or, you may play any Wild or Wild Draw 4 card.\nIf you can't play anything, you must draw a card from the deck.\n" +
            "\nSPECIAL CARDS:\n" +
            "Draw 2 Card:  When you play this card, the next person must draw 2 cards and forfeit their turn.\n" +
            "Reverse Card:  This card reverses the direction of play.\n" +
            "Skip Card:  Skips the next person in line to play.\n" +
            "Wild Card:  When you play this card, you may change the color being played to any color you want\n(can be played after any card).\n" +
            "Wild Draw 4 Card:  Also can be played on any card. Requires the next player to draw 4 cards and\nforfeit their turn.\n" +
            "\n\n" +
            "Enter player names in the field below (each name should be separated by a comma only, without\nspaces)");

    /**
     * PlayerFrame() initializes the start screen by adding the appropriate JComponents,
     * prompting the user to input a list of player names. It then accesses those names
     * and starts a new game accordingly.
     *
     * @return Nothing.
     */
    public PlayerFrame() {
        // JFrame Properties
        frame.setSize(900, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null); // Not using a layout manager

        // Adding JComponents to the JFrame
        frame.add(introText);
        frame.add(nameField);
        frame.add(startButton);

        introText.setBounds(20,20,660,500);
        nameField.setBounds(20, 530, 450, 30);
        startButton.setBounds(20, 570, 100, 40);
            startButton.addActionListener(new ActionListener() {
                @Override
                /**
                 * when the startButton is pressed, a new game is initialized
                 * using the inputted values added to the text field.
                 */
                public void actionPerformed(ActionEvent e) {
                    // When the user clicks "Start", a new game will be initialized and the user interface will be set up
                        String[] input = nameField.getText().split(","); // getting the text field input as a string array
                        if (input.length < 2 || input.length > 10) { // display an error if there are too few or too many inputted players
                            frame.add(disclaimerLabel);
                            disclaimerLabel.setBounds(20, 620, 660, 30);
                        } else { // if there is a proper number of inputted players
                            try {
                                new GameFrame(input).setVisible(true); // create a GameFrame with the inputted player names
                                frame.dispose();
                            } catch (IOException ex) { // catch any exceptions
                                ex.printStackTrace();
                            }
                        }
                    }
            });
    }
    /**
     * rescales an image to be a new size
     * @param fh -- String with the address of the image
     * @param newWidth -- int value for the new width of the image
     * @param newHeight -- int value for the new height of the image
     * @return -- returns an ImageIcon icon with the resized image
     */
    public ImageIcon rescale(String fh, int newWidth, int newHeight) {
        ImageIcon image = new ImageIcon(fh);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}

