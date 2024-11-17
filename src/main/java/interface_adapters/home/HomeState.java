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
    private Deque<List<String>> fronts = new ArrayDeque<>();
    private Deque<List<String>> backs = new ArrayDeque<>();

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
     * Gets all the front texts in the home state.
     * @return a list of all front texts in the home state.
     */
    public List<List<String>> getFronts() {
        // returns a new list to avoid potential modification
        return new ArrayList<>(fronts);
    }

    /**
     * Updates all the front texts in the home state.
     * @param newFronts the new front texts for the home state.
     */
    public void updateFronts(List<List<String>> newFronts) {
        fronts = new ArrayDeque<>(newFronts);
    }

    /**
     * Adds a list of front texts to the home state.
     * @param newFront the new front texts being added.
     */
    public void addFront(List<String> newFront) {
        fronts.addFirst(newFront);
    }

    /**
     * Gets all the back texts in the home state.
     * @return a list of all back texts in the home state.
     */
    public List<List<String>> getBacks() {
        // returns a new list to avoid potential modification
        return new ArrayList<>(backs);
    }

    /**
     * Updates all the back texts in the home state.
     * @param newBacks the new back texts for the home state.
     */
    public void updateBacks(List<List<String>> newBacks) {
        backs = new ArrayDeque<>(newBacks);
    }

    /**
     * Adds a list of back texts to the home state.
     * @param newBack the new back texts being added.
     */
    public void addBack(List<String> newBack) {
        backs.addFirst(newBack);
    }
}

