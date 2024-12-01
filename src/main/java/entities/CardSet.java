package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a card set (a collection of cards).
 */
public class CardSet {
    private final int ID;
    private String title;
    private String description;
    private final List<Card> defaultCards;
    private List<Card> shuffledCards;
    private int currIndex;
    private boolean shuffled;

    /**
     * Creates an empty CardSet.
     */
    public CardSet() {
        ID = getUniqueID();
        defaultCards = new ArrayList<>();
        shuffledCards = null;
    }

    /**
     * Creates a (potentially) non-empty CardSet.
     * @param title the title of the set.
     * @param description the description of the set.
     * @param cards the cards of this set.
     */
    public CardSet(String title, String description, List<Card> cards) {
        ID = getUniqueID();
        this.title = title;
        this.description = description;
        defaultCards = new ArrayList<>(cards);
        shuffledCards = null;
    }

    public CardSet(int ID, String title, String description, List<Card> cards) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        defaultCards = new ArrayList<>(cards);
        shuffledCards = null;
    }

    /**
     * Returns the current card of this card set.
     * @return the current card.
     */
    public Card getCurrentCard() {
        // no card exists
        if (currIndex > defaultCards.size() - 1) {
            return null;
        }

        // get the card from the appropriate set
        if (shuffled) {
            return shuffledCards.get(currIndex);
        } else {
            return defaultCards.get(currIndex);
        }
    }

    /**
     * Adds a card to this card set.
     * @param card the card to add.
     */
    public void addCard(Card card) {
        defaultCards.add(card);
    }

    /**
     * Adds multiple cards to this card set.
     * @param cards the cards to add.
     */
    public void addCards(List<Card> cards) {
        defaultCards.addAll(cards);
    }

    /**
     * Returns the cards of this card set.
     * @return the cards of this card set.
     */
    public List<Card> getDefaultCards() {
        return defaultCards;
    }

    /**
     * Returns the unique ID of this card set.
     * @return the ID of this flashcard set.
     */
    public int getID() {
        return ID;
    }

    /**
     * Toggles shuffling on/off for this set.
     */
    public void toggleShuffle() {
        shuffled = !shuffled;
        if (shuffled) {
            shuffleCards();
        } else {
            // clears the previously shuffled cards
            clearShuffledCards();
            // allows users to get new shuffles
        }
    }

    /**
     * Restarts the cards of this card set.
     */
    public void restartCards() {
        currIndex = 0;
    }

    /**
     * Shuffles this card set.
     */
    public void shuffleCards() {
        shuffledCards = new ArrayList<>(defaultCards);
        Collections.shuffle(shuffledCards);
        currIndex = 0;
    }

    public void clearShuffledCards() {
        shuffledCards = null;
    }

    /**
     * Returns the title of this card set.
     * @return the title of this card set.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of this card set.
     * @return the description of this card set.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the front of every card in this card set.
     * @return the fronts of this card set.
     */
    public List<String> getFronts() {
        List<String> fronts = new ArrayList<>();
        for (Card card : defaultCards) {
            fronts.add(card.getFront());
        }
        return fronts;
    }

    /**
     * Returns the back of every card in this card set.
     * @return the backs of this card set.
     */
    public List<String> getBacks() {
        List<String> backs = new ArrayList<>();
        for (Card card : defaultCards) {
            backs.add(card.getBack());
        }
        return backs;
    }

    /**
     * Generates a unique ID for a card set.
     * @return a unique integer ID.
     */
    private int getUniqueID() {
        return (int) (System.currentTimeMillis() & 0xfffffff);
    }

    public List<List<String>> getCards() {
        List<List<String>> cards = new ArrayList<>();
        for (Card card : defaultCards) {
            cards.add(List.of(
                    card.getFront(),
                    card.getBack()
            ));
        }
        return cards;
    }
}
