package use_cases.study;

/**
 * The output boundary for our study use case.
 */
public interface StudyOutputBoundary {

    /**
     * Prepares the success view.
     * @param studyOutputData the output data
     */
    void prepareSuccessView(StudyOutputData studyOutputData);

    /**
     * Switches to the home view.
     */
    void switchToHomeView();
}
