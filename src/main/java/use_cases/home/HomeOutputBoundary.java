package use_cases.home;

import use_cases.edit.EditInputData;
import use_cases.study.StudyInputData;

/**
 * The output boundary for our home use case.
 */
public interface HomeOutputBoundary {
    /**
     * Prepares the success view.
     * @param homeOutputData the output data
     */
    void refresh(HomeOutputData homeOutputData);

    /**
     * Switches to the create view.
     */
    void switchToCreateView();

    /**
     * Switches to the edit view.
     * @param editInputData the title of the set that will be edited.
     */
    void switchToEditView(EditInputData editInputData);

    /**
     * Switches to the study view.
     * @param cardSetData the data of the set to study.
     */
    void switchToStudyView(StudyInputData cardSetData);
}
