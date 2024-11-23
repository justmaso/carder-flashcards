package use_cases.home;

import interface_adapters.study.StudyState;

/**
 * The output boundary for our home use case.
 */
public interface HomeOutputBoundary {
    /**
     * Prepares the success view.
     * @param homeOutputData the output data
     */
    void prepareSuccessView(HomeOutputData homeOutputData);

    /**
     * Switches to the create view.
     */
    void switchToCreateView();

    /**
     * Switches to the edit view.
     * @param cardSetTitle the title of the set that will be edited.
     */
    void switchToEditView(String cardSetTitle);

    /**
     * Switches to the study view.
     * @param studyState the set to study.
     */
    void switchToStudyView(StudyState studyState);
}
