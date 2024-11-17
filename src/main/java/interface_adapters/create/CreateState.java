package interface_adapters.create;

/**
 * The state for the create view model.
 */
public class CreateState {
    private String createTitle;
    private String createDescription;
    private String createError;

    /**
     * Gets the error received when trying to create a set.
     * @return the error message.
     */
    public String getCreateError() {
        return createError;
    }

    /**
     * Updates the error associated with creating a set.
     * @param createError the error message.
     */
    public void setCreateError(String createError) {
        this.createError = createError;
    }

    /**
     * Gets the title of the set being created.
     * @return the new set's title.
     */
    public String getCreateTitle() {
        return createTitle;
    }

    /**
     * Updates the title of the set being created.
     * @param createTitle the set's title
     */
    public void setCreateTitle(String createTitle) {
        this.createTitle = createTitle;
    }

    /**
     * Gets the description of the set being created.
     * @return the set description.
     */
    public String getCreateDescription() {
        return createDescription;
    }

    /**
     * Updates the description of the set being created.
     * @param createDescription the set's description.
     */
    public void setCreateDescription(String createDescription) {
        this.createDescription = createDescription;
    }

    /**
     * Clears the all 'create' state data.
     */
    public void clear() {
        this.createTitle = null;
        this.createDescription = null;
        this.createError = null;
    }
}
