package use_cases.study;

import java.util.List;

/**
 * Output data for the study use case.
 */
public class StudyOutputData {
    private final String title;
    private final String description;
    private final List<String> fronts;
    private final List<String> backs;

    public StudyOutputData(String title, String description,
                           List<String> fronts, List<String> backs) {
        this.title = title;
        this.description = description;
        this.fronts = fronts;
        this.backs = backs;
    }

    /**
     * Gets the title of the card set to be studied (output).
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the card set to be studied (output).
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the fronts of the card set to be studied (output).
     * @return the fronts.
     */
    public List<String> getFronts() {
        return fronts;
    }

    /**
     * Gets the backs of the card set to be studied (output).
     * @return the backs.
     */
    public List<String> getBacks() {
        return backs;
    }

}
