package use_cases.create;

import java.util.List;

/**
 * Output data for the creation use case.
 */
public class CreateOutputData {
    private final int ID;
    private final String title;
    private final String description;
    private final List<String> fronts;
    private final List<String> backs;

    public CreateOutputData(int ID, String title, String description,
                            List<String> fronts, List<String> backs) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.fronts = fronts;
        this.backs = backs;
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

    public List<String> getFronts() {
        return fronts;
    }

    public List<String> getBacks() {
        return backs;
    }
}
