package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import interface_adapters.study.StudyController;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;

/**
 * A class for the study view.
 */
public class StudyView extends ParentView implements PropertyChangeListener, ActionListener {

    private final Container container = new Container();
    private int currentCard = 1;
    private CardLayout cardLayout;
    private JPanel studyPanel = new JPanel();
    private StudyController studyController;

    public StudyView(StudyViewModel svm) {

        svm.addPropertyChangeListener(this);

        add(studyPanel, BorderLayout.CENTER);

        addAboutListener(evt -> {
            JOptionPane.showMessageDialog(null,
                    "You are currently studying flashcards");
        });

        addHomeListener(evt -> {
            studyController.switchToHomeView();

        });

    }

    private JPanel getStudyPanel(StudyState state) {
        // JPanel where the cards will go
        JPanel panel = new JPanel();

        this.cardLayout = new CardLayout();
        panel.setLayout(cardLayout);

        List<String> fronts = state.getFronts();
        List<String> backs = state.getBacks();
        int size = fronts.size();

        for (int i = 0; i < size; i++) {
            JPanel cl = new CardPanel(fronts.get(i), backs.get(i));
            panel.add(cl, String.valueOf(i + 1));
        }
        // Create a JPanel for the navigation buttons
        JPanel buttonPanel = new JPanel();

        // Initialize the buttons to move through flashcard set
        JButton firstBtn = new JButton("First");

        JButton nextBtn = new JButton("Next");

        JButton previousBtn = new JButton("Previous");

        JButton lastBtn = new JButton("Last");

        JButton shuffleBtn = new JButton("Shuffle");

        // Add buttons to move through the flashcard set.
        buttonPanel.add(firstBtn);
        buttonPanel.add(previousBtn);
        buttonPanel.add(nextBtn);
        buttonPanel.add(lastBtn);
        buttonPanel.add(shuffleBtn);

        // add 'first' button in ActionListener
        firstBtn.addActionListener(arg0 -> {

            // first card in CardLayout
            // value of the card is 1
            currentCard = 1;
            cardLayout.show(panel, "" + currentCard);
        });

        // add "last" button in ActionListener
        lastBtn.addActionListener(arg0 -> {

            // last card in CardLayout

            // value of the last card
            currentCard = size;
            cardLayout.show(panel, "" + currentCard);
        });

        // add 'next' button in ActionListener
        nextBtn.addActionListener(arg0 -> {

            if (currentCard < size) {
                // move to the next card
                currentCard += 1;
                cardLayout.show(panel, "" + currentCard);
            }
        });

        // add 'previous' button in ActionListener
        previousBtn.addActionListener(arg0 -> {
            if (currentCard > 1) {

                // move to the previous card
                currentCard -= 1;

                // show the value of current card
                cardLayout.show(panel, "" + currentCard);
            }
        });

        shuffleBtn.addActionListener(arg0 -> {
            List<List<String>> frontToBack = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                List<String> card = new ArrayList<>();
                card.add(fronts.get(i));
                card.add(backs.get(i));
                frontToBack.add(card);
            }

            Collections.shuffle(frontToBack);
            for (int i = 0; i < size; i++) {
                JPanel cl = new CardPanel(frontToBack.get(i).getFirst(), frontToBack.get(i).getLast());
                panel.add(cl, String.valueOf(i + 1));
            }
            currentCard = 1;

            // value of the card is 1
            cardLayout.show(panel, "" + currentCard);
        });

        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        return mainPanel;

    }

    public void actionPerformed(ActionEvent evt) {
        cardLayout.next(container);
    }

    public void setStudyController(StudyController studyController) {
        this.studyController = studyController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final StudyState state = (StudyState) evt.getNewValue();
        studyPanel.removeAll();
        // reset current card to the first card
        currentCard = 1;
        studyPanel.add(getStudyPanel(state));

        // addAboutListener(e -> {
            // JOptionPane.showMessageDialog(studyPanel,
                    // String.format("You are currently studying %s", state.getDescription()));
        // });

    }
}
