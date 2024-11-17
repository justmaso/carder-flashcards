package use_cases.home;

import java.util.List;

/**
 * Output data for the home use case.
 */
public class HomeOutputData {
    private final List<Integer> IDs;
    private final List<String> titles;
    private final List<String> descriptions;
    private final List<List<String>> fronts;
    private final List<List<String>> backs;

    public HomeOutputData(List<Integer> IDs, List<String> titles, List<String> descriptions,
                          List<List<String>> fronts, List<List<String>> backs) {
        this.IDs = IDs;
        this.titles = titles;
        this.descriptions = descriptions;
        this.fronts = fronts;
        this.backs = backs;
    }

    /**
     * Gets all the IDs of the outputted card sets.
     * @return all the IDs.
     */
    public List<Integer> getIDs() {
        return IDs;
    }

    /**
     * Gets all the titles of the outputted card sets.
     * @return all the titles.
     */
    public List<String> getTitles() {
        return titles;
    }

    /**
     * Gets all the descriptions of the outputted card sets.
     * @return all the descriptions.
     */
    public List<String> getDescriptions() {
        return descriptions;
    }

    /**
     * Gets all the front text from each outputted card set.
     * @return all the front text.
     */
    public List<List<String>> getFronts() {
        return fronts;
    }

    /**
     * Gets all the back text from each outputted card set.
     * @return all the back text.
     */
    public List<List<String>> getBacks() {
        return backs;
    }
}
