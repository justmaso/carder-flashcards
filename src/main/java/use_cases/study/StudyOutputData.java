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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getFronts() {
        return fronts;
    }

    public List<String> getBacks() {
        return backs;
    }

}
