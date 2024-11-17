package use_cases.create;

/**
 * The output boundary for the creation use case.
 */
public interface CreateOutputBoundary {
    void prepareSuccessView(CreateOutputData createOutputData);

    void prepareFailView(String error);

    void switchToHomeView();

    void switchToStudyView(CreateOutputData outputData);
}