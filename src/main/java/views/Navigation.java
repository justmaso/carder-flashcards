package views;

import javax.swing.*;
import java.awt.*;

/**
 * The navigation panel common to all views.
 */
public class Navigation extends JPanel {
    public static final String HOME = "home";
    public static final String ABOUT = "about";
    private final JButton homeButton;
    private final JButton aboutButton;

    public Navigation() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        homeButton = new JButton(HOME);
        aboutButton = new JButton(ABOUT);

        // buttons have no functionality right now
        // the functionality is added in each separate view
        add(homeButton);
        add(aboutButton);
    }

    /**
     * Gets the home button.
     * @return the home JButton.
     */
    public JButton getHomeButton() {
        return homeButton;
    }

    /**
     * Gets the about button.
     * @return the about JButton.
     */
    public JButton getAboutButton() {
        return aboutButton;
    }

}
