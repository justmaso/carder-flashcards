package interface_adapters;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;

/**
 * Manages the theme of the app.
 */
public class ThemeManager {
    private static boolean isDarkTheme = true;
    private static final FlatMacDarkLaf DARK_LAF = new FlatMacDarkLaf();
    private static final FlatMacLightLaf LIGHT_LAF = new FlatMacLightLaf();

    /**
     * Applies the initial theme to the app.
     */
    public static void applyInitialTheme() {
        setTheme(isDarkTheme);
    }

    /**
     * Toggles the app theme between light and dark.
     */
    public static void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        setTheme(isDarkTheme);
    }

    /**
     * Sets the theme accordingly.
     * @param useDarkTheme whether the dark theme should be set.
     */
    private static void setTheme(boolean useDarkTheme) {
        try {
            UIManager.setLookAndFeel(useDarkTheme ? DARK_LAF : LIGHT_LAF);
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the theme that isn't active.
     * @return the other theme's name.
     */
    public static String getOtherThemeName() {
        return !isDarkTheme ? "dark theme" : "light theme";
    }
}