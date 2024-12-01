package use_cases.create;

import java.util.List;

/**
 * Input data for the creation use case.
 */
public class CreateInputData {
    private final String title;
    private final String description;
    private final List<List<String>> cards;

    public CreateInputData(String title, String description,
                           List<List<String>> cards) {
        this.title = title;
        this.description = description;
        this.cards = cards;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<List<String>> getCards() {
        return cards;
    }
}
