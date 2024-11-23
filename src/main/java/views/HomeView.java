package views;

import interface_adapters.home.HomeController;
import interface_adapters.home.HomeState;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyState;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;

/**
 * The home view for our app.
 */
public class HomeView extends ParentView implements PropertyChangeListener {
    private final HomeViewModel homeViewModel;
    private HomeController homeController;
    private JPanel cardSetsPanel = new JPanel();
    private final JScrollPane cardSetsScrollPane = new JScrollPane(cardSetsPanel);

    public HomeView(HomeViewModel hvm) {
        homeViewModel = hvm;
        hvm.addPropertyChangeListener(this);

        final JPanel createPanel = getCreatePanel();

        add(cardSetsScrollPane, BorderLayout.CENTER);
        add(createPanel, BorderLayout.SOUTH);

        // refreshes the home view
        addHomeListener(e -> {
            homeController.execute();
        });

        addAboutListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "carder flashcards is a simple studying app built in java\n");
        });
    }

    private JPanel getCardSetsPanel() {
        final JPanel flashCardsPanel = new JPanel();
        flashCardsPanel.setLayout(new BoxLayout(flashCardsPanel, BoxLayout.Y_AXIS));

        final HomeState homeState = homeViewModel.getState();
        if (homeState.getIDs().isEmpty()) {
            final JPanel noCardsPanel = new JPanel(new BorderLayout());
            final JLabel noCardsLabel = new JLabel("no sets exist yet", JLabel.CENTER);
            noCardsPanel.add(noCardsLabel, BorderLayout.CENTER);
            return noCardsPanel;
        }

        // parallel lists, so we can use the same index variable
        final List<Integer> IDs = homeState.getIDs();
        final List<String> titles = homeState.getTitles();
        final List<String> descriptions = homeState.getDescriptions();
        final List<List<String>> fronts = homeState.getFronts();
        final List<List<String>> backs = homeState.getBacks();

        for (int k = 0; k < IDs.size(); k++) {
            final JPanel row = new JPanel(new BorderLayout());

            // add the labels
            final JPanel labelsPanel = new JPanel();
            labelsPanel.add(new JLabel(String.format("[id=%s]", IDs.get(k))));
            labelsPanel.add(new JLabel(String.format("%s:", titles.get(k))));
            labelsPanel.add(new JLabel(String.format("%s", descriptions.get(k))));

            // create the buttons
            final JPanel buttonsPanel = new JPanel();
            final JButton studyButton = new JButton("study");
            final JButton editButton = new JButton("edit");
            final JButton deleteButton = new JButton("delete");

            // add the buttons
            buttonsPanel.add(studyButton);
            buttonsPanel.add(editButton);
            buttonsPanel.add(deleteButton);

            row.add(labelsPanel, BorderLayout.WEST);
            row.add(buttonsPanel, BorderLayout.EAST);

            // add functionality to the buttons
            int finalK = k;
            studyButton.addActionListener(e -> {
                if (e.getSource().equals(studyButton)) {
                    StudyState studyState = new StudyState();
                    studyState.setTitle(titles.get(finalK));
                    studyState.setDescription(descriptions.get(finalK));
                    studyState.setFronts(fronts.get(finalK));
                    studyState.setBacks(backs.get(finalK));
                    homeController.switchToStudyView(studyState);
                }
                JOptionPane.showMessageDialog(this,
                        String.format("studying [%d], described as [%s]", IDs.get(finalK), descriptions.get(finalK)));

            });
            editButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this,
                        String.format("editing [%d], described as [%s]", IDs.get(finalK), descriptions.get(finalK)));
            });
            deleteButton.addActionListener(e -> {
                int choice = JOptionPane.showConfirmDialog(this,
                        String.format("are you sure you want to delete\n%s: %s?", titles.get(finalK), descriptions.get(finalK)));
                if (choice == JOptionPane.YES_OPTION) {
                    homeController.removeCardSet(titles.get(finalK));

                    // updates the cards available after deletion
                    // automatically refreshes our home view
                    homeController.execute();
                }
            });

            flashCardsPanel.add(row);
        }
        return flashCardsPanel;
    }

    private JPanel getCreatePanel() {
        final JPanel createPanel = new JPanel();
        final JButton createNewSetButton = new JButton("+ new set");
        createNewSetButton.addActionListener(e -> {
            if (e.getSource().equals(createNewSetButton)) {
                homeController.switchToCreateView();
            }
        });
        createPanel.add(createNewSetButton);
        return createPanel;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        cardSetsPanel = getCardSetsPanel();
        cardSetsScrollPane.setViewportView(cardSetsPanel);
    }
}
