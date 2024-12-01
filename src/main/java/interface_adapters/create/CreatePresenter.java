package interface_adapters.create;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeState;
import interface_adapters.home.HomeViewModel;
import interface_adapters.study.StudyState;
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
    public void createSuccessful(CreateOutputData createOutputData) {
        final HomeState homeState = homeViewModel.getState();
        final CreateState createState = createViewModel.getState();

        homeState.addID(createOutputData.getID());
        homeState.addTitle(createOutputData.getTitle());
        homeState.addDescription(createOutputData.getDescription());
        homeState.addCards(createOutputData.getCards());

        createState.setCreateTitle(createOutputData.getTitle());
        createState.setCreateDescription(createOutputData.getDescription());
        createState.setCreateError(null);
        createViewModel.firePropertyChanged();
        homeViewModel.setState(homeState);
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
    public void switchToStudyView(CreateOutputData createAndStudyOutputData) {
        final HomeState homeState = homeViewModel.getState();
        final StudyState studyState = studyViewModel.getState();
        final CreateState createState = createViewModel.getState();

        homeState.addID(createAndStudyOutputData.getID());
        homeState.addTitle(createAndStudyOutputData.getTitle());
        homeState.addDescription(createAndStudyOutputData.getDescription());
        homeState.addCards(createAndStudyOutputData.getCards());

        studyState.clear();
        studyState.setTitle(createAndStudyOutputData.getTitle());
        studyState.setDescription(createAndStudyOutputData.getDescription());
        studyState.setOriginalCards(createAndStudyOutputData.getCards());

        createState.setCreateTitle(createAndStudyOutputData.getTitle());
        createState.setCreateDescription(createAndStudyOutputData.getDescription());
        createState.setCreateError(null);
        createViewModel.firePropertyChanged();

        studyViewModel.setState(studyState);
        studyViewModel.firePropertyChanged("init");

        homeViewModel.setState(homeState);
        homeViewModel.firePropertyChanged();

        viewManagerModel.setState(studyViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
