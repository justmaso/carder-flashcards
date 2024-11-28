package interface_adapters.study;

import use_cases.study.StudyInputBoundary;
import use_cases.study.StudyInputData;

/**
 * The controller for study.
 */
public class StudyController {

    private final StudyInputBoundary studyInteractor;

    public StudyController(StudyInputBoundary studyInteractor) {
        this.studyInteractor = studyInteractor;
    }

    /**
     * Executes the study use case for a card set.
     * @param title the set's title.
     */
    public void execute(String title) {
        studyInteractor.execute(new StudyInputData(title));
    }

    /**
     * Executes the use case of switching to the home view.
     */
    public void switchToHomeView() {
        studyInteractor.switchToHomeView();
    }
}
