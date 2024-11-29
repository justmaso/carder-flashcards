package interface_adapters.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * The studying state.
 */
public class StudyState {
    private String title;
    private String description;

    private List<List<String>> originalCards;
    private List<List<String>> activeCards;
    private List<List<String>> previousActiveCards;
    private final List<List<String>> unknownCards = new ArrayList<>();
    private final Stack<Boolean> knownUnknownStack = new Stack<>();

    private int currentIndex = 0;
    private boolean frontVisible = true;
    private boolean sortingOn;
    private boolean shufflingOn;
    private boolean studyingSorted;


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOriginalCards(List<List<String>> originalCards) {
        this.originalCards = originalCards;
        this.activeCards = new ArrayList<>(originalCards);
        this.previousActiveCards = new ArrayList<>(activeCards);
    }

    public List<List<String>> getOriginalCards() {
        return originalCards;
    }

    public void setActiveCards(List<List<String>> activeCards) {
        this.activeCards = activeCards;
    }

    public List<List<String>> getActiveCards() {
        return activeCards;
    }

    public void setPreviousActiveCards(List<List<String>> previousActiveCards) {
        this.previousActiveCards = previousActiveCards;
    }

    public List<List<String>> getPreviousActiveCards() {
        return previousActiveCards;
    }

    public List<List<String>> getUnknownCards() {
        return unknownCards;
    }

    public void addToUnknownCards(List<String> unknownCard) {
        unknownCards.add(unknownCard);
    }

    public void pushKnownUnknown(boolean knownUnknown) {
        knownUnknownStack.push(knownUnknown);
    }

    public void popKnownUnknown() {
        if (!knownUnknownStack.empty() && !knownUnknownStack.pop()) unknownCards.removeLast();
    }


    public int getCurrentSize() {
        return activeCards.size();
    }

    public String getActiveFront() {
        return activeCards.get(currentIndex).getFirst();
    }

    public String getActiveBack() {
        return activeCards.get(currentIndex).getLast();
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setFrontVisible(boolean frontVisible) {
        this.frontVisible = frontVisible;
    }

    public boolean getFrontVisible() {
        return frontVisible;
    }

    public void setSortingOn(boolean sortingOn) {
        this.sortingOn = sortingOn;
        unknownCards.clear();

        if (sortingOn) {

        } else {

        }
    }
    public boolean getSortingOn() {
        return sortingOn;
    }

    public void setShufflingOn(boolean shufflingOn) {
        this.shufflingOn = shufflingOn;
        unknownCards.clear();

        // shuffling enabled
        if (shufflingOn) {
            // store a copy of the active cards
            previousActiveCards = new ArrayList<>(activeCards);
            // shuffle the active cards
            Collections.shuffle(activeCards);
        } else {
            activeCards = new ArrayList<>(previousActiveCards);
        }
    }

    public boolean getShufflingOn() {
        return shufflingOn;
    }

    public void setStudyingSorted(boolean studyingSorted) {
        this.studyingSorted = studyingSorted;
        knownUnknownStack.clear();

        // start studying the unknown cards
        if (studyingSorted) {
            previousActiveCards = new ArrayList<>(unknownCards);
            activeCards = new ArrayList<>(unknownCards);

        }
        unknownCards.clear();
    }

    public boolean getStudyingSorted() {
        return studyingSorted;
    }

    public void clear() {
        title = null;
        description = null;
        originalCards = null;
        activeCards = null;
        previousActiveCards = null;
        unknownCards.clear();
        knownUnknownStack.clear();
        currentIndex = 0;
        frontVisible = true;
        sortingOn = false;
        shufflingOn = false;
        studyingSorted = false;
    }

    public String toString() {
        return String.format(
                """
                activeCards=%s
                previousActiveCards=%s
                unknownCards=%s
                sortingOn=%s
                shufflingOn=%s
                studyingSorted=%s
                """,
                activeCards,
                previousActiveCards,
                unknownCards,
                sortingOn,
                shufflingOn,
                studyingSorted
        );
    }

    public void fullyRestart() {
        currentIndex = 0;
        frontVisible = true;
        studyingSorted = false;
        unknownCards.clear();
        knownUnknownStack.clear();
        shufflingOn = false;
        activeCards = new ArrayList<>(originalCards);
    }
}
