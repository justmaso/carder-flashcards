package use_cases.study;

/**
 * Input data for the study use case.
 */
public class StudyInputData {

    private final String title;

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
