package use_cases.home;

import entities.CardSet;

import java.util.List;

/**
 * The data access interface for our home use case.
 */
public interface HomeDataAccessInterface {
    /**
     * Gets all card sets currently stored.
     * @return all card sets stored.
     */
    List<CardSet> getCardSets();

    /**
     * Deletes a card set currently stored.
     * @param cardSetTitle the set to delete
     */
    void deleteCardSet(String cardSetTitle);
}
