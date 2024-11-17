package use_cases.create;

import entities.CardSet;

/**
 * The data access interface for our creation use case.
 */
public interface CreateDataAccessInterface {
    /**
     * Checks if any set already has a certain title.
     * @return whether the title already exists.
     */
    boolean setExistsByTitle(String cardSetTitle);

    /**
     * Saves the card set.
     * @param cardSet the card set.
     */
    void saveSet(CardSet cardSet);
}
