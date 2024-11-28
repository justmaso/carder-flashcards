package interface_adapters.study;

import java.util.List;

/**
 * The state for the study view model.
 */
public class StudyState {
    private String title;
    private String description;
    private List<String> fronts;
    private List<String> backs;

    /**
     * Gets the title of the set currently in the study state.
     * @return study set's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the current set in the study state.
     * @param title title of the set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the set currently in the study state.
     * @return study set's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the current set in the study state.
     * @param description description of the set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets all the card front sides of the set currently in the study state.
     * @return study set's front text.
     */
    public List<String> getFronts() {
        return fronts;
    }

    /**
     * Set the front sides of the current set in the study state.
     * @param fronts front sides of the set.
     */
    public void setFronts(List<String> fronts) {
        this.fronts = fronts;
    }

    /**
     * Gets the back sides of the set currently in the study state.
     * @return study set's back text.
     */
    public List<String> getBacks() {
        return backs;
    }

    /**
     * Set the back sides of the current set in the study state.
     * @param backs back sides of the set.
     */
    public void setBacks(List<String> backs) {
        this.backs = backs;
    }
}
