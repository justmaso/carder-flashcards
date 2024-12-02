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
import interface_adapters.TextToSpeech;

/**
 * A class for the study view.
 */
public class StudyView extends ParentView implements PropertyChangeListener, ActionListener {

    private final Container container = new Container();
    private int currentCard = 1;
    private CardLayout cardLayoutCards;
    private CardLayout cardLayoutNumbers;
    private JPanel studyPanel = new JPanel();
    private StudyController studyController;
    private List<List<String>> shuffledCards = new ArrayList<>();

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
        JPanel cardsPanel = new JPanel();
        // JPanel to track the card numbers
        final JPanel numberPanel = new JPanel();
        // JPanel to track favorites
        final JPanel favoritesPanel = new JPanel();

        cardLayoutCards = new CardLayout();
        cardLayoutNumbers = new CardLayout();
        cardsPanel.setLayout(cardLayoutCards);
        numberPanel.setLayout(cardLayoutNumbers);

        List<String> fronts = state.getFronts();
        List<String> backs = state.getBacks();
        int size = fronts.size();

        loadDefaultCards(size, fronts, backs, cardsPanel, numberPanel);
        // Create a JPanel for the navigation buttons
        JPanel buttonPanel = new JPanel();

        // Initialize the buttons to move through flashcard set
        JButton nextBtn = new JButton("Next");
        JButton previousBtn = new JButton("Previous");
        JButton shuffleBtn = new JButton("Shuffle");
        JButton unshuffleBtn = new JButton("Unshuffle");

        // Add buttons to move through the flashcard set.
        buttonPanel.add(previousBtn);
        buttonPanel.add(nextBtn);
        buttonPanel.add(shuffleBtn);
        buttonPanel.add(unshuffleBtn);

        unshuffleBtn.addActionListener(arg0 -> {
            loadDefaultCards(size, fronts, backs, cardsPanel, numberPanel);
            currentCard = 1;
            cardLayoutCards.show(cardsPanel, "" + currentCard);
            cardLayoutNumbers.show(numberPanel, "" + currentCard);
        });

        // add 'next' button in ActionListener
        nextBtn.addActionListener(arg0 -> {
            if (currentCard < size) {
                // move to the next card
                currentCard += 1;
                cardLayoutCards.show(cardsPanel, "" + currentCard);
                cardLayoutNumbers.show(numberPanel, "" + currentCard);
            }
        });

        // add 'previous' button in ActionListener
        previousBtn.addActionListener(arg0 -> {
            if (currentCard > 1) {
                // move to the previous card
                currentCard -= 1;
                // show the value of current card
                cardLayoutCards.show(cardsPanel, "" + currentCard);
                cardLayoutNumbers.show(numberPanel, "" + currentCard);
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
            do {
                Collections.shuffle(frontToBack);
            } while (frontToBack.getFirst().getFirst().equals(shuffledCards.get(currentCard - 1).getFirst()));
            shuffledCards = frontToBack;
            for (int i = 0; i < size; i++) {
                JPanel cl = new CardPanel(frontToBack.get(i).getFirst(), frontToBack.get(i).getLast());
                cardsPanel.add(cl, String.valueOf(i + 1));
            }
            currentCard = 1;

            // value of the card is 1
            cardLayoutCards.show(cardsPanel, "" + currentCard);
            cardLayoutNumbers.show(numberPanel, "" + currentCard);
        });

        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(cardsPanel, BorderLayout.CENTER);
        mainPanel.add(numberPanel, BorderLayout.EAST);
        mainPanel.add(favoritesPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setPreferredSize(new Dimension(650,350));
        return mainPanel;
    }

    private void loadDefaultCards(int size, List<String> fronts, List<String> backs,
                                  JPanel cardsPanel, JPanel numberPanel) {
        for (int i = 0; i < size; i++) {
            String curr = String.valueOf(i + 1);
            JPanel cl = new CardPanel(fronts.get(i), backs.get(i));
            cardsPanel.add(cl, curr);
            JLabel number = new JLabel(curr + " / " + size);
            numberPanel.add(number, curr);
            List<String> currentCardsText = new ArrayList<>();
            currentCardsText.add(fronts.get(i));
            currentCardsText.add(backs.get(i));
            shuffledCards.add(currentCardsText);
        }
    }

    public void actionPerformed(ActionEvent evt) {
        cardLayoutCards.next(container);
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
    }
}
