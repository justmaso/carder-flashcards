package use_cases.home;

import interface_adapters.study.StudyState;

/**
 * The home input boundary for home actions.
 */
public interface HomeInputBoundary {
    /**
     * Refreshes the home view and updates the sets visible.
     */
    void execute();

    /**
     * Switches to the create view.
     */
    void switchToCreateView();

    /**
     * Switches to the edit view.
     * @param cardSetTitle the title of the set to edit
     */
    void switchToEditView(String cardSetTitle);

    /**
     * Deletes a card set.
     * @param cardSetTitle the title of the set to delete.
     */
    void deleteCardSet(String cardSetTitle);
}
