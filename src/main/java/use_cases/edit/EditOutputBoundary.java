package use_cases.edit;

public interface EditOutputBoundary {
    // abstract implementation
    void editSuccessful();

    void editFailed(String error);

    void switchToHomeView();
}