package views;

import interface_adapters.TextToSpeech;
import interface_adapters.study.StudyController;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The study view for the app.
 */
public class StudyView extends ParentView implements PropertyChangeListener {
    // view model and controller
    private final StudyViewModel studyViewModel;
    private StudyController studyController;

    // main structural components
    private final JPanel studyPanel = new JPanel();
    private final JPanel infoPanel = new JPanel();
    private final JLabel infoLabel = new JLabel();
    private final JPanel cardPanel = new JPanel();
    private final JTextArea visibleText = new JTextArea();
    private final JScrollPane visibleScrollPane = new JScrollPane(visibleText);
    private final JPanel progressPanel = new JPanel();
    private final JLabel progressLabel = new JLabel();
    private final JPanel buttonPanel = new JPanel();

    // buttons
    private final JButton shuffleButton = new JButton();
    private final JButton trackButton = new JButton();
    private final JButton undoButton = new JButton("undo");
    private final JButton leftButton = new JButton();
    private final JButton rightButton = new JButton();
    private final JButton flipButton = new JButton("flip");
    private final JButton speakButton = new JButton("speak");

    // keyboard shortcuts for the buttons
    private final String shuffleKey = "s";
    private final String trackKey = "h";
    private final String undoKey = "slash";
    private final String leftKey = "left";
    private final String rightKey = "right";
    private final String flipKey = "up/down";
    private final String speakKey = "a";

    public StudyView(StudyViewModel svm) {
        studyViewModel = svm;
        studyViewModel.addPropertyChangeListener(this);
        configureUI();
    }

    private void configureUI() {
        configureParentPanel();
        configureInfoPanel();
        configureStudyPanel();
        configureCardPanel();
        configureVisibleTextArea();
        configureProgressPanel();
        configureButtonPanel();
    }

    private void configureParentPanel() {
        add(studyPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        addParentListeners();
    }

    private void configureInfoPanel() {
        infoPanel.add(infoLabel);
    }

    private void configureStudyPanel() {
        studyPanel.setLayout(new BorderLayout());
        studyPanel.add(infoPanel, BorderLayout.NORTH);
        studyPanel.add(cardPanel, BorderLayout.CENTER);
        studyPanel.add(progressPanel, BorderLayout.SOUTH);
    }

    private void configureCardPanel() {
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        cardPanel.add(visibleScrollPane, gbc);
    }

    private void configureVisibleTextArea() {
        visibleText.setFont(new Font("D-DIN", Font.PLAIN, 30));
        visibleText.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                studyController.flip();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        visibleText.setLineWrap(true);
        visibleText.setWrapStyleWord(true);
        visibleText.setEditable(false);
        visibleText.setFocusable(false);
        visibleText.setBackground(cardPanel.getBackground());
    }

    private void configureProgressPanel() {
        progressPanel.add(progressLabel);
    }

    private void configureButtonPanel() {
        buttonPanel.add(shuffleButton);
        buttonPanel.add(trackButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(flipButton);
        buttonPanel.add(speakButton);

        configureStaticButtonActions();
        configureKeyboardButtonActions();
    }

    private void addParentListeners() {
        addHomeListener(e -> studyController.switchToHomeView());
        addAboutListener(e -> JOptionPane.showMessageDialog(this,"""
                            you are now in study mode:
                            - press left to go to the previous card
                            - press right to go to the next card
                            - press up/down to flip the card
                            - press 's' to shuffle the set
                            - press 't' to speak the visible text"""));
    }

    private void configureStaticButtonActions() {
        shuffleButton.addActionListener(e -> studyController.toggleShuffling());
        trackButton.addActionListener(e -> studyController.toggleSorting());
        undoButton.addActionListener(e -> undo());
        leftButton.addActionListener(e -> fuckingMoveToPrev());
        rightButton.addActionListener(e -> fuckingMoveToNext());
        flipButton.addActionListener(e -> studyController.flip());
        speakButton.addActionListener(e -> TextToSpeech.speak(visibleText.getText()));

        undoButton.setToolTipText(String.format("[%s] undo", undoKey));
        flipButton.setToolTipText(String.format("[%s] flip the current card", flipKey));
        speakButton.setToolTipText(String.format("[%s] speak the visible text", speakKey));
    }

    private void configureKeyboardButtonActions() {
        final InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = buttonPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(shuffleKey.toUpperCase()), "shuffle");
        inputMap.put(KeyStroke.getKeyStroke(undoKey.toUpperCase()), "undo");
        inputMap.put(KeyStroke.getKeyStroke(leftKey.toUpperCase()), "prev");
        inputMap.put(KeyStroke.getKeyStroke(rightKey.toUpperCase()), "next");
        inputMap.put(KeyStroke.getKeyStroke("UP"), "flip");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "flip");
        inputMap.put(KeyStroke.getKeyStroke(trackKey.toUpperCase()), "enableSorting");
        inputMap.put(KeyStroke.getKeyStroke(speakKey.toUpperCase()), "talk");

        actionMap.put("prev", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fuckingMoveToPrev();
            }
        });

        actionMap.put("shuffle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studyController.toggleShuffling();
            }
        });
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studyViewModel.getState().getSortingOn()) undo();
            }
        });
        actionMap.put("next", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fuckingMoveToNext();
            }
        });
        actionMap.put("flip", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studyController.flip();
            }
        });
        actionMap.put("enableSorting", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studyController.toggleSorting();
            }
        });
        actionMap.put("talk", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextToSpeech.speak(visibleText.getText());
            }
        });
    }

    private void refreshInfoPanel() {
        StudyState studyState = studyViewModel.getState();
        infoLabel.setText(String.format("%s: %s", studyState.getTitle(), studyState.getDescription()));
    }

    private void refreshVisibleText() {
        final StudyState studyState = studyViewModel.getState();
        // dynamically updates which text is being shown
        if (studyState.getFrontVisible()) {
            visibleText.setText(studyState.getActiveFront());
        } else {
            visibleText.setText(studyState.getActiveBack());
        }
        visibleText.setCaretPosition(0);
    }

    private void refreshProgressPanel() {
        final StudyState studyState = studyViewModel.getState();
        // updates to the user's current card
        progressLabel.setText(String.format("card %d of %d",
                studyState.getCurrentIndex() + 1,
                studyState.getCurrentSize()));
    }

    private void refreshButtonPanel() {
        final StudyState studyState = studyViewModel.getState();
        final int currentIndex = studyState.getCurrentIndex();
        final boolean shufflingOn = studyState.getShufflingOn();
        final boolean sortingOn = studyState.getSortingOn();

        // updates the dynamic buttons
        if (sortingOn) {
            undoButton.setVisible(true);
            leftButton.setEnabled(true);
            rightButton.setEnabled(true);
            leftButton.setText("unknown");
            rightButton.setText("known");
            leftButton.setToolTipText(String.format("[%s] still learning", leftKey));
            rightButton.setToolTipText(String.format("[%s] know", rightKey));
        } else {
            undoButton.setVisible(false);
            leftButton.setEnabled(currentIndex != 0);
            rightButton.setEnabled(currentIndex != studyState.getCurrentSize());
            leftButton.setText("prev");
            rightButton.setText("next");
            leftButton.setToolTipText(String.format("[%s] move to previous card", leftKey));
            rightButton.setToolTipText(String.format("[%s] move to next card", rightKey));
        }
        // updates the static buttons
        shuffleButton.setText(shufflingOn ? "unshuffle" : "shuffle");
        shuffleButton.setToolTipText(String.format("[%s] %s", shuffleKey, shufflingOn ? "unshuffle" : "shuffle"));
        trackButton.setText(sortingOn ? "untrack" : "track");
        trackButton.setToolTipText(String.format("[%s] %s", trackKey, sortingOn ? "disable card tracking" : "enable card tracking"));
        undoButton.setEnabled(currentIndex != 0);
    }

    private void undo() {
        if (studyViewModel.getState().getCurrentIndex() != 0) {
            studyController.undo();
            studyController.moveToPrevCard();
        }
    }

    private void fuckingMoveToPrev() {
        // if we're sorting, we'll need to push false to the sorting stack
        final StudyState studyState = studyViewModel.getState();
        if (studyState.getSortingOn()) {
            studyController.addToUnknown(
                    studyState.getActiveFront(),
                    studyState.getActiveBack()
            );
            actuallyMoveToNext(studyState);
        } else if (studyState.getCurrentIndex() != 0) {
            studyController.moveToPrevCard();
        }
    }

    private void fuckingMoveToNext() {
        // if we're sorting, we'll need to push true to the sorting stack
        final StudyState studyState = studyViewModel.getState();
        if (studyState.getSortingOn()) {
            studyController.addToKnown();
        }
        actuallyMoveToNext(studyState);
    }

    private void actuallyMoveToNext(StudyState studyState) {
        final int currentIndex = studyState.getCurrentIndex();
        final boolean sorting = studyState.getSortingOn();
        final Object[] options;
        final int choice;

        if (sorting) {
            if (currentIndex + 1 == studyState.getCurrentSize()) {
                final int unknownCardsSize = studyState.getUnknownCards().size();
                if (unknownCardsSize == 0) {
                    options = new Object[]{"fully restart", "go back"};
                    choice = JOptionPane.showOptionDialog(this,
                            "you've sorted all the cards!\nwhat would you like to do?",
                            "studying done",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if (choice == JOptionPane.YES_OPTION) fullyRestart();
                    if (choice == JOptionPane.NO_OPTION) studyController.undo();
                } else {
                    options = new Object[]{"study unknown", "fully restart", "go back"};
                    choice = JOptionPane.showOptionDialog(this,
                            "you're still learning some cards.\nwhat would you like to do?",
                            "studying almost done",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (choice == JOptionPane.YES_OPTION) studyController.studyUnknown();
                    else if (choice == JOptionPane.NO_OPTION) fullyRestart();
                    else if (choice == JOptionPane.CANCEL_OPTION) studyController.undo();
                }
            } else {
                studyController.moveToNextCard();
            }
        } else {
            if (currentIndex + 1 == studyState.getCurrentSize()) {
                options = new Object[]{"fully restart", "go back"};
                choice = JOptionPane.showOptionDialog(this,
                        "you’ve reviewed all the cards.\nwhat would you like to do?",
                        "studying done",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (choice == JOptionPane.YES_OPTION) fullyRestart();
                else if (choice == JOptionPane.NO_OPTION) studyController.undo();
            } else {
                studyController.moveToNextCard();
            }
        }
    }

    private void fullyRestart() {
        studyController.fullyRestart();
    }

    public void setStudyController(StudyController studyController) {
        this.studyController = studyController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("init".equals(event.getPropertyName())) {
            refreshInfoPanel();
        }
        // always updates to state changes
        refreshVisibleText();
        refreshProgressPanel();
        refreshButtonPanel();
        revalidate();
        repaint();
    }
}