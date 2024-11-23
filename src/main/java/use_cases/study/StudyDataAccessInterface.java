package use_cases.study;
import entities.CardSet;

import java.util.List;

/**
 * The data access interface for the study case.
 */
public interface StudyDataAccessInterface {

    /**
     * Gets the card sets currently stored.
     * @return card set stored.
     */
    CardSet getCardSet(String title);
    }


