package interface_adapters.study;

import use_cases.study.StudyInputBoundary;

public class StudyController {
    private final StudyInputBoundary studyInteractor;

    public StudyController(StudyInputBoundary studyInteractor) {
        this.studyInteractor = studyInteractor;
    }

    /**
     * Switches to the home view.
     */
    public void switchToHomeView() {
        studyInteractor.switchToHomeView();
    }

    /**
     * Moves to the previous card.
     */
    public void moveToPrevCard() {
        studyInteractor.moveToPrevCard();
    }

    /**
     * Moves to the next card.
     */
    public void moveToNextCard() {
        studyInteractor.moveToNextCard();
    }

    /**
     * Flips the currently visible card.
     */
    public void flip() {
        studyInteractor.flip();
    }

    /**
     * Toggles the shuffling of the current card set.
     */
    public void toggleShuffling() {
        studyInteractor.toggleShuffling();
    }

    public void toggleSorting() {
        studyInteractor.toggleSorting();
    }

    public void addToUnknown(String front, String back) {
        studyInteractor.addToUnknown(front, back);
    }

    public void restart() {
        studyInteractor.restart();
    }

    public void studyUnknown() {
        studyInteractor.studyUnknown();
    }

    public void fullyRestart() {
        studyInteractor.fullyRestart();
    }

    public void undo() {
        studyInteractor.undo();
    }

    public void addToKnown() {
        studyInteractor.addToKnown();
    }
}
