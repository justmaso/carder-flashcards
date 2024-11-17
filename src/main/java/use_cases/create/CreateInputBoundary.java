package use_cases.create;

/**
 * The creation input boundary for 'create' actions.
 */
public interface CreateInputBoundary {

    /**
     * executes our 'create' use case.
     * @param createInputData the input data.
     */
    void execute(CreateInputData createInputData);

    /**
     * switches to the home view.
     * keeps the current state of the create view.
     */
    void switchToHomeView();
}
