package interface_adapters.edit;

import use_cases.edit.EditInputBoundary;
import use_cases.edit.EditInputData;

import java.util.List;


public class EditController {
    private final EditInputBoundary editInteractor;

    public EditController(EditInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    /**
     * Attempts to publish the edits to the card set.
     * @param ID the set's identifier.
     * @param newTitle the new title.
     * @param newDescription the new description.
     * @param newCards the new cards.
     */
    // execute the use case
    public void publishEdits(int ID, String newTitle, String newDescription, List<List<String>> newCards) {
        editInteractor.publishEdits(new EditInputData(ID, newTitle, newDescription, newCards));
    }

    /**
     * Switches to the home view.
     */
    public void switchToHomeView() {
        editInteractor.switchToHomeView();
    }
}