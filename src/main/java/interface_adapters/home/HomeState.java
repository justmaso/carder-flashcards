package interface_adapters.home;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * The state for the home view model.
 */
public class HomeState {
    private Deque<Integer> IDs = new ArrayDeque<>();
    private Deque<String> titles = new ArrayDeque<>();
    private Deque<String> descriptions = new ArrayDeque<>();
    private Deque<List<List<String>>> cards = new ArrayDeque<>();

    /**
     * Gets all IDs currently in the home state.
     * @return all IDs in the home state.
     */
    public List<Integer> getIDs() {
        // returns a new list to avoid potential modification
        return new ArrayList<>(IDs);
    }

    /**
     * Updates all IDs currently in the home state.
     * @param newIDs the new IDs for the home state.
     */
    public void updateIDs(List<Integer> newIDs) {
        // wraps the list in a deque
        IDs = new ArrayDeque<>(newIDs);
    }

    /**
     * Adds a new ID to the home state.
     * @param ID the ID to add to the home state.
     */
    public void addID(int ID) {
        // efficiently adds an ID to the beginning
        IDs.addFirst(ID);
    }

    /**
     * Gets all titles currently in the home state.
     * @return all titles in the home state.
     */
    public List<String> getTitles() {
        // returns a new list to avoid potential modification
        return new ArrayList<>(titles);
    }

    /**
     * Updates all titles in the home state.
     * @param newTitles the new titles for the home state.
     */
    public void updateTitles(List<String> newTitles) {
        titles = new ArrayDeque<>(newTitles);
    }

    /**
     * Adds a new title to the home state.
     * @param newTitle the title to add.
     */
    public void addTitle(String newTitle) {
        titles.addFirst(newTitle);
    }

    /**
     * Gets all descriptions currently in the home state
     * @return all descriptions in the home state.
     */
    public List<String> getDescriptions() {
        // returns a new list to avoid potential modification
        return new ArrayList<>(descriptions);
    }

    /**
     * Updates all descriptions in the home state.
     * @param newDescriptions the new descriptions for the home state.
     */
    public void updateDescriptions(List<String> newDescriptions) {
        descriptions = new ArrayDeque<>(newDescriptions);
    }

    /**
     * Adds a new description to the home state.
     * @param newDescription the new description to add.
     */
    public void addDescription(String newDescription) {
        descriptions.addFirst(newDescription);
    }

    /**
     * Gets all the current cards in the home state.
     * @return all the current cards.
     */
    public List<List<List<String>>> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Adds a new list of pairs representing front and back texts.
     * @param newCards the list of text pairs to add.
     */
    public void addCards(List<List<String>> newCards) {
        cards.addFirst(newCards);
    }

    /**
     * Updates the home state's cards.
     * @param newCards the new cards to set the home state to.
     */
    public void updateCards(List<List<List<String>>> newCards) {
        cards = new ArrayDeque<>(newCards);
    }
}