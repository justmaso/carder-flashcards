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
     * @param fronts the front text for this set.
     * @param backs the back text for this set.
     * @return the new card set.
     */
    public CardSet create(String title, String description,
                          List<String> fronts, List<String> backs) {
        final List<Card> cards = new ArrayList<>();
        for (int k = 0; k < fronts.size(); k++) {
            cards.add(new Card(fronts.get(k), backs.get(k)));
        }

        return new CardSet(title, description, cards);
    }
}
