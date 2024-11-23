package use_cases.study;

import java.util.ArrayList;
import java.util.List;

/**
 * Output data for the creation use case.
 */
public class StudyOutputData {
    private final int id;
    private final String title;
    private final String description;
    private final List<String> fronts;
    private final List<String> backs;
    private final List<String> frontsShuffled;
    private final List<String> backsShuffled;

    public StudyOutputData(int id, String title, String description,
                           List<String> fronts, List<String> backs, List<String> frontsShuffled,
                           List<String> backsShuffled) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fronts = fronts;
        this.backs = backs;
        this.frontsShuffled = frontsShuffled;
        this.backsShuffled = backsShuffled;
    }

    public int getID() {
        return id;
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

    public List<String> getFrontsShuffled() {
        return frontsShuffled;
    }

    public List<String> getBacksShuffled() {
        return backsShuffled;
    }

}