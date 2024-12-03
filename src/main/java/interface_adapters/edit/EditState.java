
package interface_adapters.edit;

import java.util.List;

/**
 * The state for the edit view model.
 */
// stores information that the application holds
    // responsible for returning values related to flashcard info since edit is
    // responsible for managing some data
public class EditState {
    private int ID;
    private String title;
    private String description;
    private List<List<String>> cards;
    private String editError;
    // set ID of flashcard set
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCards(List<List<String>> cards) {
        this.cards = cards;
    }

    public List<List<String>> getCards() {
        return cards;
    }

    public void setEditError(String editError) {
        this.editError = editError;
    }

    public String getEditError() {
        return editError;
    }

    public void clear() {
        ID = 0;
        title = null;
        description = null;
        cards = null;
        editError = null;
    }
}
