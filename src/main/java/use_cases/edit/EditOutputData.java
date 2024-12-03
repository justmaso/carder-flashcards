package use_cases.edit;

import java.util.List;

/**
 * Output data for the edit use case.
 */
public class EditOutputData {
    private final int ID;
    private final String oldTitle;
    private final String newTitle;
    private final String newDescription;
    private final List<String> newFronts;
    private final List<String> newBacks;

    // returns new edits made with the edit use case execution
    public EditOutputData(int ID, String oldTitle, String newTitle, String description,
                          List<String> newFronts, List<String> newBacks) {
        this.ID = ID;
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
        this.newDescription = description;
        this.newFronts = newFronts;
        this.newBacks = newBacks;
    }

    public int getID() {
        return ID;
    }

    public String getOldTitle() {
        return oldTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public List<String> getNewFronts() {
        return newFronts;
    }

    public List<String> getNewBacks() {
        return newBacks;
    }

}