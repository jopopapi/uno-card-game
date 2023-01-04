import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * the StartFrame class displays the UI for the "start screen" of the Uno game.
 */
public class StartFrame extends JFrame {

    public static void main(String[] args) throws IOException {new StartFrame();}

    JFrame frame = new JFrame();
    JButton startButton = new JButton("START"); // start button
    JButton exitButton = new JButton("EXIT"); // exit button

    /**
     * The StartFrame constructor constructs the new start frame.
     * @throws IOException
     */
    public StartFrame() throws IOException {
        //display the start window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new JLabel(rescale("src/CardIcons/Uno.jpg", 1440, 900)));
        frame.setVisible(true);

        //add the start and exit button
        frame.add(startButton);
        frame.add(exitButton);

        //style the start and exit buttons
        startButton.setBounds(600, 600, 200, 60);
        exitButton.setBounds(600, 700, 200, 60);
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        exitButton.setFont(new Font("Arial", Font.BOLD, 32));

        startButton.addActionListener(new ActionListener() {
            @Override
            /**
             * the actionPerformed method is called when a user clicks
             * on the start button. It creates a new PlayerFrame (so
             * the user can start the game by inputting the names of
             * the players.
             */
            public void actionPerformed(ActionEvent actionEvent) {
                new PlayerFrame().setVisible(true);
                frame.dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            /**
             * this actionPerformed method exits the game if the player
             * clicks the exitButton.
             */
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
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