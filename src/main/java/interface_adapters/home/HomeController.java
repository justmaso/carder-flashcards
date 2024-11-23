package interface_adapters.home;

import interface_adapters.study.StudyState;
import use_cases.home.HomeInputBoundary;

/**
 * The controller for our home.
 */
public class HomeController {
    private final HomeInputBoundary homeInteractor;

    public HomeController(HomeInputBoundary homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    /**
     * Executes the 'home' use case (refreshing the home view).
     */
    public void execute() {
        // no input data since the home gets no input from the user
        homeInteractor.execute();
    }

    /**
     * Executes the use case of switching to our create view.
     */
    public void switchToCreateView() {
        homeInteractor.switchToCreateView();
    }

    /**
     * Executes the use case of switching to our study view.
     * @param studyState current study state.
     */
    public void switchToStudyView(StudyState studyState) {
        homeInteractor.switchToStudyView(studyState);
    }

    /**
     * Executes the card set deletion use case.
     * @param cardSetTitle the set to delete.
     */
    public void removeCardSet(String cardSetTitle) {
        homeInteractor.deleteCardSet(cardSetTitle);
    }
}