package use_cases.edit;

public interface EditOutputBoundary {
    void editSuccessful();

    void editFailed(String error);

    void switchToHomeView();
}
