package views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A panel representing a flashcard.
 */
public class CardPanel extends JPanel implements ActionListener {

    private final CardLayout card;
    private final JButton back;
    private final JButton front;
    private final Container container;

    CardPanel(String question, String answer) {
        // q is the text on the front, a is the text on the back.
        // Initialize cards
        card = new CardLayout(40, 30);
        container = this;
        container.setLayout(card);
        // Initialize the button to flip to the card's back
        back = new JButton(question);

        // Initialize the button to flip to the card's front
        front = new JButton(answer);

        // Add JButton using ActionListener.
        back.addActionListener(this);

        front.addActionListener(this);

        // Adding the JButtons
        container.add("a", back);

        container.add("b", front);

        container.setPreferredSize(new Dimension(100, 250));
        container.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        card.next(container);
    }
}

