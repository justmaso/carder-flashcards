package use_cases.create;

import java.util.List;

/**
 * Output data for the creation use case.
 */
public class CreateOutputData {
    private final int ID;
    private final String title;
    private final String description;
    private final List<List<String>> cards;

    public CreateOutputData(int ID, String title, String description,
                            List<List<String>> cards) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.cards = cards;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<List<String>> getCards() { return cards; }
}
