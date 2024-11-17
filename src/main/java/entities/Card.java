package entities;

/**
 * A class to represent individual cards.
 */
public class Card {
    private String front;
    private String back;

    /**
     * Creates a new Card object.
     * @param front the front text.
     * @param back the back text.
     */
    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Returns the front of this card.
     * @return the front text.
     */
    public String getFront() {
        return this.front;
    }

    /**
     * Sets the front of this card.
     * @param newFront the new front text of this card.
     */
    public void setFront(String newFront) {
        this.front = newFront;
    }

    /**
     * Returns the back of this card.
     * @return the back text.
     */
    public String getBack() {
        return this.back;
    }

    /**
     * Sets the back of this card.
     * @param newBack the new back text of this card.
     */
    public void setBack(String newBack) {
        this.back = newBack;
    }
}
