package views;

import interface_adapters.create.CreateController;
import interface_adapters.create.CreateState;
import interface_adapters.create.CreateViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class CreateView extends ParentView implements PropertyChangeListener {
    private final CreateViewModel createViewModel;
    private CreateController createController;
    private final JPanel creationPanel = new JPanel();
    private final JScrollPane creationScrollPane = new JScrollPane(creationPanel);
    private final JTextField titleField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private int numCreationRows;

    public CreateView(CreateViewModel cvm) {
        createViewModel = cvm;
        createViewModel.addPropertyChangeListener(this);

        setUpCreationPanel();
        final JPanel actionPanel = getActionPanel();

        add(creationScrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        descriptionField.setFocusTraversalKeysEnabled(false);
        final InputMap descriptionInputMap = descriptionField.getInputMap(JComponent.WHEN_FOCUSED);
        descriptionInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");
        descriptionInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");

        final ActionMap descriptionActionMap = descriptionField.getActionMap();
        descriptionActionMap.put("focusPrev", new AbstractAction() {
           @Override
           public void actionPerformed(ActionEvent e) {
               KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
           }
        });
        descriptionActionMap.put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JPanel topRow = (JPanel) creationPanel.getComponent(2);
                for (Component component : topRow.getComponents()) {
                    if (component instanceof FlaggedJTextArea tempTextArea) {
                        // check if this is the frontTextArea
                        if (tempTextArea.getFlag()) {
                            tempTextArea.requestFocusInWindow();
                            return;
                        }
                    }
                }
            }
        });


        addHomeListener(e -> {
            createController.switchToHomeView();
        });

        addAboutListener(e -> {
            JOptionPane.showMessageDialog(this,
                    """
                            you can now create flashcards!
                            - fill in the text boxes below
                            - add more cards with the '+ card' button
                            - delete cards using the 'x' button
                            - when you're done, press the 'create' button""");
        });
    }

    private void setUpCreationPanel() {
        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.Y_AXIS));
        final JPanel dataRow = new JPanel();

        dataRow.add(new JLabel("title:"));
        dataRow.add(titleField);
        dataRow.add(new JLabel("description:"));
        dataRow.add(descriptionField);

        creationPanel.add(dataRow);
        creationPanel.add(new JSeparator());
        addCreationRow();
    }

    /**
     * Adds a creation row to the create view panel.
     */
    private void addCreationRow() {
        numCreationRows++;

        final JPanel creationRow = new JPanel();
        final JButton deleteButton = getDeleteButton(creationRow);

        final FlaggedJTextArea frontTextArea = new FlaggedJTextArea(true);
        final FlaggedJTextArea backTextArea = new FlaggedJTextArea(false);


        final InputMap frontTextInputMap = frontTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        frontTextInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");
        frontTextInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");

        final ActionMap frontTextActionMap = frontTextArea.getActionMap();
        frontTextActionMap.put("focusPrev", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backTextArea.setFocusable(false);
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
                backTextArea.setFocusable(true);
            }
        });
        frontTextActionMap.put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backTextArea.requestFocusInWindow();
            }
        });

        final InputMap backTextInputMap = backTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        backTextInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");
        backTextInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");

        final ActionMap backTextActionMap = backTextArea.getActionMap();
        backTextActionMap.put("focusPrev", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frontTextArea.requestFocusInWindow();
            }
        });
        backTextActionMap.put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton.requestFocusInWindow();
            }
        });

        deleteButton.setFocusTraversalKeysEnabled(false);
        final InputMap deleteInputMap = deleteButton.getInputMap(JComponent.WHEN_FOCUSED);
        deleteInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");
        deleteInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");

        final ActionMap deleteActionMap = deleteButton.getActionMap();
        deleteActionMap.put("focusPrev", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backTextArea.requestFocusInWindow();
            }
        });
        deleteActionMap.put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
            }
        });

        creationRow.add(new JLabel("front:"));
        creationRow.add(frontTextArea);
        creationRow.add(new JLabel("back:"));
        creationRow.add(backTextArea);
        creationRow.add(deleteButton);

        creationRow.setMaximumSize(creationRow.getPreferredSize());
        creationPanel.add(creationRow);
    }

    private JTextArea getWrapTextArea() {
        final JTextArea textArea = new JTextArea(1, 23);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JButton getDeleteButton(JPanel creationRow) {
        final JButton deleteButton = new JButton("x");
        deleteButton.addActionListener(e -> {
            if (numCreationRows == 1) {
                JOptionPane.showMessageDialog(this,
                        "sets must have at least 1 card\n" +
                                "you can't study from nothing");
                return;
            }
            creationPanel.remove(creationRow);
            creationPanel.revalidate();
            creationPanel.repaint();
            numCreationRows--;
        });
        return deleteButton;
    }

    private JPanel getActionPanel() {
        final JPanel actionPanel = new JPanel();

        final JButton addCardButton = new JButton("+ card");
        final JButton createButton = new JButton("create");
        final JButton createAndStudyButton = new JButton("create + study");

        addCardButton.addActionListener(e -> {
            addCreationRow();
            revalidate();
            repaint();
            SwingUtilities.invokeLater(() -> {
                final JScrollBar scrollBar = creationScrollPane.getVerticalScrollBar();
                scrollBar.setValue(scrollBar.getMaximum());
            });
        });
        createButton.addActionListener(e -> {
            createController.create(
                    titleField.getText(),
                    descriptionField.getText(),
                    getUserCards()
            );
        });
        createAndStudyButton.addActionListener(e -> {
            createController.createAndStudy(
                    titleField.getText(),
                    descriptionField.getText(),
                    getUserCards()
            );
        });

        actionPanel.add(addCardButton);
        actionPanel.add(createButton);
        actionPanel.add(createAndStudyButton);

        return actionPanel;
    }

    private List<List<String>> getUserCards() {
        List<List<String>> cards = new ArrayList<>();

        Component[] components = creationPanel.getComponents();
        // loop over the creation panels
        for (int k = 2; k < components.length; k++) {
            String frontText = null;
            String backText = null;

            for (Component component : ((JPanel) components[k]).getComponents()) {
                if (component instanceof FlaggedJTextArea textArea) {
                    if (textArea.getFlag()) {
                        frontText = textArea.getText();
                    } else {
                        backText = textArea.getText();
                    }
                }
            }

            cards.add(List.of(frontText, backText));
        }
        return cards;
    }

    public void setCreateController(CreateController createController) {
        this.createController = createController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        CreateState createState = createViewModel.getState();
        String createStateError = createState.getCreateError();

        if (createStateError == null) {
            JOptionPane.showMessageDialog(this,
                    String.format("%s: %s\nhas been saved", createState.getCreateTitle(), createState.getCreateDescription()));

            // successful creation, clear all fields
            creationPanel.removeAll();
            setUpCreationPanel();
            creationScrollPane.setViewportView(creationPanel);
            titleField.setText("");
            descriptionField.setText("");

            // update the number of creation rows
            numCreationRows = 1;
        } else {
            JOptionPane.showMessageDialog(this, createStateError);
        }

        // in either case, clear the 'create' state
        createState.clear();
    }
}