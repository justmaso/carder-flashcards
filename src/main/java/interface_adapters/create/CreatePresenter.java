package interface_adapters.create;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeState;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyViewModel;
import use_cases.create.CreateOutputBoundary;
import use_cases.create.CreateOutputData;

/**
 * The presenter for the creation use case.
 */
public class CreatePresenter implements CreateOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CreateViewModel createViewModel;
    private final HomeViewModel homeViewModel;
    private final StudyViewModel studyViewModel;

    public CreatePresenter(ViewManagerModel vmm, CreateViewModel cvm,
                           HomeViewModel hvm, StudyViewModel svm) {
        viewManagerModel = vmm;
        createViewModel = cvm;
        homeViewModel = hvm;
        studyViewModel = svm;
    }

    @Override
    public void prepareSuccessView(CreateOutputData createOutputData) {
        final HomeState homeState = homeViewModel.getState();
        final CreateState createState = createViewModel.getState();

        homeState.addID(createOutputData.getID());
        homeState.addTitle(createOutputData.getTitle());
        homeState.addDescription(createOutputData.getDescription());
        homeState.addFront(createOutputData.getFronts());
        homeState.addBack(createOutputData.getBacks());

        createState.setCreateTitle(createOutputData.getTitle());
        createState.setCreateDescription(createOutputData.getDescription());

        createViewModel.setState(createState);
        homeViewModel.setState(homeState);

        // clears the create view
        createViewModel.firePropertyChanged();
        switchToHomeView();
    }

    @Override
    public void prepareFailView(String error) {
        CreateState createState = createViewModel.getState();
        createState.setCreateError(error);
        createViewModel.setState(createState);
        createViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView() {
        // all inputs in the creation view are unchanged (drafting feature)
        viewManagerModel.setState(homeViewModel.getViewName());
        homeViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToStudyView(CreateOutputData outputData) {
        // TODO
    }
}
