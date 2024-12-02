package views;

import interface_adapters.home.HomeController;
import interface_adapters.home.HomeState;
import interface_adapters.home.HomeViewModel;

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
            homeController.refresh();
        });

        addAboutListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "carder flashcards is a simple studying app built in java");
        });
    }

    private JPanel getCardSetsPanel() {
        final JPanel flashCardsPanel = new JPanel();
        flashCardsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

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
        final List<List<List<String>>> cards = homeState.getCards();

        for (int k = 0; k < IDs.size(); k++) {
            final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEADING));

            // add the labels
            final JLabel infoLabel = new JLabel(String.format(
                    "[id=%s] %s: %s",
                    IDs.get(k), titles.get(k), descriptions.get(k)
            ));

            // create the buttons
            final JPanel buttonsPanel = new JPanel();
            final JButton studyButton = new JButton("study");
            final JButton editButton = new JButton("edit");
            final JButton deleteButton = new JButton("delete");

            // add tool tips to the buttons
            final String commonText = String.format("%s: %s", titles.get(k), descriptions.get(k));
            studyButton.setToolTipText(String.format("study %s", commonText));
            editButton.setToolTipText(String.format("edit %s", commonText));
            deleteButton.setToolTipText(String.format("delete %s", commonText));

            // add the buttons
            buttonsPanel.add(studyButton);
            buttonsPanel.add(editButton);
            buttonsPanel.add(deleteButton);

            row.add(buttonsPanel);
            row.add(infoLabel);
            // add functionality to the buttons
            int finalK = k;
            studyButton.addActionListener(e -> {
                homeController.switchToStudyView(
                        titles.get(finalK),
                        descriptions.get(finalK),
                        cards.get(finalK)
                );
            });
            editButton.addActionListener(e -> {
                homeController.switchToEditView(
                        IDs.get(finalK),
                        titles.get(finalK),
                        descriptions.get(finalK),
                        cards.get(finalK)
                );
            });
            deleteButton.addActionListener(e -> {
                int choice = JOptionPane.showConfirmDialog(this,
                        String.format("are you sure you want to delete\n%s: %s?", titles.get(finalK), descriptions.get(finalK)));
                if (choice == JOptionPane.YES_OPTION) {
                    homeController.removeCardSet(titles.get(finalK));
                    homeController.refresh();
                }
            });
            row.setMinimumSize(flashCardsPanel.getPreferredSize());
            flashCardsPanel.add(row, gbc);
            gbc.gridy++;
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
        createNewSetButton.setToolTipText("create new set");

        createPanel.add(createNewSetButton);
        return createPanel;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("edited".equals(event.getPropertyName()) || "init".equals(event.getPropertyName())) {
            homeController.refresh();
        } else {
            cardSetsPanel = getCardSetsPanel();
            cardSetsScrollPane.setViewportView(cardSetsPanel);
        }
    }
}
