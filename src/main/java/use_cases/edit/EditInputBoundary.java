package use_cases.edit;


public interface EditInputBoundary {
    /**
     * Attempts the edit use case.
     * @param editInputData the edit input data.
     */
    void publishEdits(EditInputData editInputData);

    /**
     * Switches to the home view.
     */
    void switchToHomeView();
}