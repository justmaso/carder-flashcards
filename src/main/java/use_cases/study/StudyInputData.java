package use_cases.study;

import java.util.List;

public class StudyInputData {
    private final String title;
    private final String description;
    private final List<List<String>> cards;

    public StudyInputData(String title, String description,
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

    public String toString() {
        return String.format("title=%s\ndesc=%s\ncards=%s",
                title,
                description,
                cards);
    }
}
