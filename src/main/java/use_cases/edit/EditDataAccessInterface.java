package use_cases.edit;

import entities.CardSet;

/**
 * The data access interface for the edit use case.
 */
public interface EditDataAccessInterface {
    /**
     * Checks if the given card set title already exists.
     * @return whether the title already exists.
     */
    boolean setExistsByTitle(String cardSetTitle);

    /**
     * Gets the ID of a set by the title.
     * @return the ID of the title (null if the title doesn't exist).
     */
    Integer getIDByTitle(String cardSetTitle);

    /**
     * Updates the currently viewed set.
     * @param newSet the new set.
     */
    void updateSet(CardSet newSet);
}
