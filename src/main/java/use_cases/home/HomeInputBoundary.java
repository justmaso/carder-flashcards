package use_cases.home;

import use_cases.edit.EditInputData;
import use_cases.study.StudyInputData;

/**
 * The home input boundary for home actions.
 */
public interface HomeInputBoundary {
    /**
     * Refreshes the home view and updates the sets visible.
     */
    void refresh();

    /**
     * Switches to the create view.
     */
    void switchToCreateView();

    /**
     * Switches to the edit view.
     * @param editInputData the card set data to edit.
     */
    void switchToEditView(EditInputData editInputData);

    /**
     * Switches to the study view.
     * @param cardSetData the data of the set to study.
     */
    void switchToStudyView(StudyInputData cardSetData);

    /**
     * Deletes a card set.
     * @param cardSetTitle the title of the set to delete.
     */
    void deleteCardSet(String cardSetTitle);
}
