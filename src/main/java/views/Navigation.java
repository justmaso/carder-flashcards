package views;

import interface_adapters.ThemeManager;

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
    private final JButton themeButton;

    public Navigation() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        homeButton = new JButton(HOME);
        aboutButton = new JButton(ABOUT);
        themeButton = new JButton(ThemeManager.getOtherThemeName());

        // buttons have no functionality right now
        add(homeButton);
        add(aboutButton);
        add(themeButton);

        themeButton.setToolTipText("toggle theme");

        themeButton.addActionListener(e -> {
            ThemeManager.toggleTheme();
            themeButton.setText(ThemeManager.getOtherThemeName());
        });
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
