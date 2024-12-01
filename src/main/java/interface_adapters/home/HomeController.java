package interface_adapters.home;

import use_cases.edit.EditInputData;
import use_cases.home.HomeInputBoundary;
import use_cases.study.StudyInputData;

import java.util.List;

/**
 * The controller for our home.
 */
public class HomeController {
    private final HomeInputBoundary homeInteractor;

    public HomeController(HomeInputBoundary homeInteractor) {
        this.homeInteractor = homeInteractor;
    }

    /**
     * Executes the refresh use case (of the home).
     */
    public void refresh() {
        // no input data since the home gets no input from the user
        homeInteractor.refresh();
    }

    /**
     * Executes the use case of switching to our create view.
     */
    public void switchToCreateView() {
        homeInteractor.switchToCreateView();
    }

    /**
     * Switches to the edit view.
     * @param ID the ID of the set to edit.
     * @param title the title of the set to edit.
     * @param description the description of the set to edit.
     * @param cards the cards. of the set to edit.
     */
    public void switchToEditView(int ID, String title, String description, List<List<String>> cards) {
        homeInteractor.switchToEditView(new EditInputData(ID, title, description, cards));
    }

    /**
     * Switches to the study view.
     * @param title the title of the set to study.
     * @param description the description of the set to study.
     * @param cards the cards of the set to study.
     */
    public void switchToStudyView(String title, String description, List<List<String>> cards) {
        homeInteractor.switchToStudyView(new StudyInputData(title, description, cards));
    }

    /**
     * Executes the card set deletion use case.
     * @param cardSetTitle the set to delete.
     */
    public void removeCardSet(String cardSetTitle) {
        homeInteractor.deleteCardSet(cardSetTitle);
    }
}