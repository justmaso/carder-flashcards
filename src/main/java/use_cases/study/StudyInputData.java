package use_cases.study;

import java.util.List;

/**
 * Input data for the study use case.
 */
public class StudyInputData {
    private final String title;
    //private final String description;
    //private final List<String> fronts;
    //private final List<String> backs;

    public StudyInputData(String title) {
        this.title = title;
    }

    /**
     * Gets the study set's title.
     * @return set's title.
     */
    public String getTitle() {
        return title;
    }

}
