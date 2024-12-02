package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A factory for creating CardSet objects.
 */
public class CardSetFactory {
    /**
     * Creates a new CardSet.
     * @param title the title of the set.
     * @param description the description of the set.
     * @param cards the cards of this set.
     * @return the new card set.
     */
    public CardSet create(String title, String description,
                          List<List<String>> cards) {
        return new CardSet(title, description, getTranslatedCards(cards));
    }

    /**
     * Creates a new CardSet.
     * @param ID the ID of the set.
     * @param title the title of the set.
     * @param description the description of the set.
     * @param cards the cards of this set.
     * @return the new card set.
     */
    public CardSet create(int ID, String title, String description,
                          List<List<String>> cards) {
        return new CardSet(ID, title, description, getTranslatedCards(cards));
    }

    private List<Card> getTranslatedCards(List<List<String>> cards) {
        final List<Card> translatedCards = new ArrayList<>();
        for (List<String> pair : cards) {
            translatedCards.add(new Card(pair.get(0), pair.get(1)));
        }
        return translatedCards;
    }
}
