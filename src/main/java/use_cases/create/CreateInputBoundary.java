package use_cases.create;

/**
 * The creation input boundary for 'create' actions.
 */
public interface CreateInputBoundary {

    /**
     * executes the 'create' use case.
     * @param createInputData the input data.
     */
    void create(CreateInputData createInputData);

    /**
     * Executes the 'create and study' use case.
     * @param createInputData the input data.
     */
    void createAndStudy(CreateInputData createInputData);

    /**
     * Switches to the home view.
     * keeps the current state of the create view.
     */
    void switchToHomeView();
}
