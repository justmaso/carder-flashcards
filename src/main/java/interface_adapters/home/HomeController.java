package interface_adapters.home;

import interface_adapters.study.StudyController;
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
     * @param title title of the set to study.
     * @param studyController to execute study use case.
     */
    public void switchToStudyView(String title, StudyController studyController) {
        studyController.execute(title);
    }

    /**
     * Executes the card set deletion use case.
     * @param cardSetTitle the set to delete.
     */
    public void removeCardSet(String cardSetTitle) {
        homeInteractor.deleteCardSet(cardSetTitle);
    }
}