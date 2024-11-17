package views;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

/**
 * An abstract class to represent the common navigation.
 */
public abstract class ParentView extends JPanel {
    private final Navigation navigationPanel;

    public ParentView() {
        setLayout(new BorderLayout());
        navigationPanel = new Navigation();
        add(navigationPanel, BorderLayout.NORTH);
    }

    /**
     * Adds an ActionListener to the 'home' button.
     * @param listener the listener to add.
     */
    public void addHomeListener(ActionListener listener) {
        navigationPanel.getHomeButton().addActionListener(listener);
    }

    /**
     * Adds an ActionListener to the 'about' button.
     * @param listener the listener to add.
     */
    public void addAboutListener(ActionListener listener) {
        navigationPanel.getAboutButton().addActionListener(listener);
    }
}
