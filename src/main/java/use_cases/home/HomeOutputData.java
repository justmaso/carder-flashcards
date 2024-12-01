package use_cases.home;

import java.util.List;

/**
 * Output data for the home use case.
 */
public class HomeOutputData {
    private final List<Integer> IDs;
    private final List<String> titles;
    private final List<String> descriptions;
    private final List<List<List<String>>> cards;

    public HomeOutputData(List<Integer> IDs, List<String> titles, List<String> descriptions,
                          List<List<List<String>>> cards) {
        this.IDs = IDs;
        this.titles = titles;
        this.descriptions = descriptions;
        this.cards = cards;
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
     * Gets all the cards of the outputted card sets.
     * @return all the cards.
     */
    public List<List<List<String>>> getCards() {
        return cards;
    }
}
