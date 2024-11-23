package use_cases.study;

import entities.CardSet;

/**
 * The data access interface for the study case.
 */
public interface StudyDataAccessInterface {

    /**
     * Gets the card set from the title.
     * @param title of the card set.
     * @return card set.
     */
    CardSet getCardSet(String title);
}
