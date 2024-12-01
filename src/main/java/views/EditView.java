package views;


import interface_adapters.edit.EditController;
import interface_adapters.edit.EditState;
import interface_adapters.edit.EditViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditView extends ParentView implements PropertyChangeListener {
    private final EditViewModel editViewModel;
    private EditController editController;
    final JPanel headerRow = new JPanel();
    private final JTextField titleField = new JTextField(20);
    private final JTextField descriptionField = new JTextField(20);
    private final JPanel oldCardSetsPanel = new JPanel();
    private final JScrollPane editScrollPane = new JScrollPane(oldCardSetsPanel);
    private int numCreationRows;

    public EditView(EditViewModel evm) {
        editViewModel = evm;
        evm.addPropertyChangeListener(this);

        configureHeaderPanel();
        configureOldCardSetsPanel();

        final JPanel actionPanel = getActionPanel();
        add(editScrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        addHomeListener(e -> editController.switchToHomeView());
        addAboutListener(e -> JOptionPane.showMessageDialog(this,"you are now in editing mode"));
    }

    private void clearUI() {
        headerRow.removeAll();
        oldCardSetsPanel.removeAll();
    }

    private void configureHeaderPanel() {
        headerRow.add(new JLabel("title:"));
        headerRow.add(titleField);
        headerRow.add(new JLabel("description:"));
        headerRow.add(descriptionField);

        configureDescriptionFocusing();
    }

    private void configureDescriptionFocusing() {
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
                final JPanel topRow = (JPanel) oldCardSetsPanel.getComponent(2);
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
    }

    private void configureOldCardSetsPanel() {
        oldCardSetsPanel.setLayout(new BoxLayout(oldCardSetsPanel, BoxLayout.Y_AXIS));
        oldCardSetsPanel.add(headerRow);
        oldCardSetsPanel.add(new JSeparator());
    }

    private void updateHeaderPanel() {
        titleField.setText(editViewModel.getState().getTitle());
        descriptionField.setText(editViewModel.getState().getDescription());
    }

    private void updateOldCardSetsPanel() {
        final EditState editState = editViewModel.getState();
        final List<List<String>> oldCards = editState.getCards();

        for (List<String> oldCard : oldCards) {
            final JPanel cardDataPanel = new JPanel();

            final JButton deleteButton = getDeleteButton(cardDataPanel);
            final FlaggedJTextArea frontTextArea = new FlaggedJTextArea(oldCard.getFirst(), true);
            final FlaggedJTextArea backTextArea = new FlaggedJTextArea(oldCard.getLast(), false);

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

            cardDataPanel.add(new JLabel("front:"));
            cardDataPanel.add(frontTextArea);
            cardDataPanel.add(new JLabel("back:"));
            cardDataPanel.add(backTextArea);
            cardDataPanel.add(deleteButton);
            cardDataPanel.setMaximumSize(cardDataPanel.getPreferredSize());
            oldCardSetsPanel.add(cardDataPanel);
        }
    }

    private JButton getDeleteButton(JPanel cardDataRow) {
        final JButton deleteButton = new JButton("x");
        deleteButton.addActionListener(e -> {
            if (numCreationRows == 1) {
                JOptionPane.showMessageDialog(this,
                        "sets must have at least 1 card\n" +
                                "you can't study from nothing");
            } else {
                oldCardSetsPanel.remove(cardDataRow);
                oldCardSetsPanel.revalidate();
                oldCardSetsPanel.repaint();
                numCreationRows--;
            }
//            if (editViewModel.getState().getNewCards().size() == 1) {
//                JOptionPane.showMessageDialog(this,
//                        "sets must have at least 1 card\n" +
//                                "you can't study from nothing");
//            } else {
//                oldCardSetsPanel.remove(cardDataRow);
//            }
        });
        return deleteButton;
    }

    private JPanel getActionPanel() {
        final JPanel actionPanel = new JPanel();
        final JButton addCardButton = new JButton("+ card");
        final JButton doneButton = new JButton("done");

        addCardButton.addActionListener(e -> {
            addCreationRow();
            revalidate();
            repaint();
        });
        doneButton.addActionListener(e -> {
            final EditState editState = editViewModel.getState();
            final String newTitle = titleField.getText();
            final String newDescription = descriptionField.getText();
            editState.setTitle(newTitle);
            editState.setDescription(newDescription);

            editController.publishEdits(
                    editViewModel.getState().getID(),
                    newTitle,
                    newDescription,
                    getUserCards()
            );
        });

        actionPanel.add(addCardButton);
        actionPanel.add(doneButton);
        return actionPanel;
    }

    private List<List<String>> getUserCards() {
        List<List<String>> cards = new ArrayList<>();

        Component[] components = oldCardSetsPanel.getComponents();
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

    private void addCreationRow() {
        numCreationRows++;

        final JPanel creationRow = new JPanel();
        final JButton deleteButton = getDeleteButton(creationRow);
        final FlaggedJTextArea frontTextArea = new FlaggedJTextArea(true);
        final FlaggedJTextArea backTextArea = new FlaggedJTextArea(false);

        final InputMap frontTextInputMap = frontTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        frontTextInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");
        frontTextInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");

        final ActionMap frontTextActionMap = frontTextArea.getActionMap();
        frontTextActionMap.put("focusPrev", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
            }
        });
        frontTextActionMap.put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backTextArea.requestFocusInWindow();
            }
        });

        final InputMap backTextInputMap = backTextArea.getInputMap(JComponent.WHEN_FOCUSED);
        backTextInputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");
        backTextInputMap.put(KeyStroke.getKeyStroke("shift TAB"), "focusPrev");

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

        creationRow.add(new JLabel("front:"));
        creationRow.add(frontTextArea);
        creationRow.add(new JLabel("back:"));
        creationRow.add(backTextArea);
        creationRow.add(deleteButton);

        creationRow.setMaximumSize(creationRow.getPreferredSize());
        oldCardSetsPanel.add(creationRow);
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("init".equals(event.getPropertyName())) {
            numCreationRows = editViewModel.getState().getCards().size();
            updateHeaderPanel();
            updateOldCardSetsPanel();
        } else if ("clear".equals(event.getPropertyName())) {
            clearUI();
            configureHeaderPanel();
            configureOldCardSetsPanel();
        } else if ("edit".equals(event.getPropertyName())) {
            final EditState editState = editViewModel.getState();
            JOptionPane.showMessageDialog(this, Objects.requireNonNullElseGet(editState.getEditError(),
                    () -> String.format("%s: %s\nhas been saved", editState.getTitle(), editState.getDescription())));
        }
    }
}