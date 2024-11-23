package use_cases.study;

/**
 * The study input boundary for 'Study' actions.
 */
public interface StudyInputBoundary {

    /**
     * Executes our 'study' use case.
     * @param studyInputData the input data.
     */
    void execute(StudyInputData studyInputData);

    /**
     * Switches to the home view.
     */
    void switchToHomeView();
}
