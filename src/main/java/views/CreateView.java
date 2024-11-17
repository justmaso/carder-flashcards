package views;

import interface_adapters.create.CreateController;
import interface_adapters.create.CreateState;
import interface_adapters.create.CreateViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class CreateView extends ParentView implements PropertyChangeListener {
    private final CreateViewModel createViewModel;
    private CreateController createController;
    private final JPanel creationPanel = new JPanel();
    private final JScrollPane creationScrollPane = new JScrollPane(creationPanel);
    private final JTextField titleField = new JTextField(27);
    private final JTextField descriptionField = new JTextField(27);
    private int numCreationRows;

    public CreateView(CreateViewModel cvm) {
        createViewModel = cvm;
        createViewModel.addPropertyChangeListener(this);

        setUpCreationPanel();
        final JPanel actionPanel = getActionPanel();

        add(creationScrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

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
     * adds a creation row to the create view panel.
     */
    private void addCreationRow() {
        numCreationRows++;

        final JPanel creationRow = new JPanel();
        final JButton deleteButton = getDeleteButton(creationRow);

        creationRow.add(new JLabel("front:"));
        creationRow.add(new JTextField(26));
        creationRow.add(new JLabel("back:"));
        creationRow.add(new JTextField(26));
        creationRow.add(deleteButton);

        creationRow.setMaximumSize(creationRow.getPreferredSize());

        creationPanel.add(creationRow);
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
        });
        createButton.addActionListener(e -> {
            List<String> fronts = new ArrayList<>();
            List<String> backs = new ArrayList<>();

            Component[] components = creationPanel.getComponents();
            // loop over the creation panels
            for (int k = 2; k < components.length; k++) {
                JPanel row = (JPanel) components[k];
                Component[] rowComponents = row.getComponents();

                fronts.add(((JTextField) rowComponents[1]).getText());
                backs.add(((JTextField) rowComponents[3]).getText());
            }

            createController.execute(
                    titleField.getText(),
                    descriptionField.getText(),
                    fronts,
                    backs
            );
        });
        // createAndStudyButton.addActionListener(e -> {
        //     JOptionPane.showMessageDialog(this, "you just pressed create + study in create view");
        // });

        actionPanel.add(addCardButton);
        actionPanel.add(createButton);
        // actionPanel.add(createAndStudyButton);

        return actionPanel;
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