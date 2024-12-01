package use_cases.study;

/**
 * The output boundary for the study use case.
 */
public interface StudyOutputBoundary {
    /**
     * Switches to the home view.
     */
    void switchToHomeView();

    /**
     * Moves to the previous card.
     */
    void moveToPrevCard();

    /**
     * Moves to the next card.
     */
    void moveToNextCard();

    /**
     * Flips the currently visible card.
     */
    void flip();

    /**
     * Shuffles the current card set.
     */
    void toggleShuffling();

    /**
     * Toggles the sorting of the current card set.
     */
    void toggleSorting();

    /**
     * Adds the current card to the unknown set.
     * @param front the unknown front text.
     * @param back the unknown back text.
     */
    void addToUnknown(String front, String back);

    /**
     * Restarts the current card set.
     */
    void restart();

    /**
     * Allows the user to study their unknown cards.
     */
    void studyUnknown();

    void fullyRestart();

    void undo();

    void addToKnown();
}
