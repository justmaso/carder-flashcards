package views;
import data_access.InMemoryDataAccessObject;
import entities.Card;
import entities.CardSet;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudyView extends ParentView implements PropertyChangeListener {

    private final Container container = new Container();
    private int currentCard = 1;
    private CardLayout cardLayout;
    private final StudyViewModel studyViewModel;
    private InMemoryDataAccessObject IMDao = new InMemoryDataAccessObject();
    // private final StudyController controller;

    public StudyView(StudyViewModel svm, int index) {
        studyViewModel = svm;
        svm.addPropertyChangeListener(this);

    }
    public JPanel getStudyPanel(int index) //StudyView(StudyViewModel studyViewModel, StudyController controller, DAO)
    {
        CardSet cardSet = IMDao.getCardSets().get(index);

        // JPanel where the cards will go
        JPanel panel = new JPanel();

        this.cardLayout = new CardLayout();
        // card = new CardLayout(40, 30);
        panel.setLayout(cardLayout);
        int size = cardSet.getBacks().size();
        for (int i=0; i<size; i++) {
            JPanel cl = new CardPanel(cardSet.getFronts().get(i), cardSet.getBacks().get(i));
            panel.add(cl, String.valueOf(i+1));
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

        buttonPanel.add(nextBtn);

        buttonPanel.add(previousBtn);

        buttonPanel.add(lastBtn);

        buttonPanel.add(shuffleBtn);

        // add 'first' button in ActionListener
        firstBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                // first card in CardLayout
                // value of the card is 1
                currentCard = 1;
                cardLayout.show(panel, "" + (currentCard));
            }
        });

        // add "last" button in ActionListener
        lastBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                // last card in CardLayout

                // value of the last card
                currentCard = size;
                cardLayout.show(panel, "" + (currentCard));
            }
        });

        // add 'next' button in ActionListener
        nextBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                if (currentCard < size) // studyset size
                {

                    // move to the next card
                    currentCard += 1;

                    // show the value of the current card
                    cardLayout.show(panel, "" + (currentCard));
                }
            }
        });

        // add 'previous' button in ActionListener
        previousBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if (currentCard > 1) {

                    // move to the previous card
                    currentCard -= 1;

                    // show the value of current card
                    cardLayout.show(panel, "" + (currentCard));
                }
            }
        });

        shuffleBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                List<Card> shuffled = cardSet.shuffleCards();
                for (int i=0; i<size; i++) {
                    JPanel cl = new CardPanel(shuffled.get(i).getFront(), shuffled.get(i).getBack());
                    panel.add(cl, String.valueOf(i+1));
                }
                currentCard = 1;

                // value of the card is 1
                cardLayout.show(panel, "" + (currentCard));
            }
        });

        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(new Navigation(), BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    // Main Method (for testing right now)
    public static void main(String[] args)
    {
        int index=1;
        JFrame j = new JFrame();
        StudyView studyView = new StudyView(new StudyViewModel(), index);
        j.add(studyView.getStudyPanel(index));
        j.setSize(500,350);
        j.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        cardLayout.next(container);
    }
}
