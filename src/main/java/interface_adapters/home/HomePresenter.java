package interface_adapters.home;

import interface_adapters.ViewManagerModel;
import interface_adapters.create.CreateViewModel;
import interface_adapters.edit.EditViewModel;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;
import use_cases.home.HomeOutputBoundary;
import use_cases.home.HomeOutputData;

/**
 * The presenter for our home use case.
 */
public class HomePresenter implements HomeOutputBoundary {
    private final HomeViewModel homeViewModel;
    private final CreateViewModel createViewModel;
    private final EditViewModel editViewModel;
    private final StudyViewModel studyViewModel;
    private final ViewManagerModel viewManagerModel;

    public HomePresenter(ViewManagerModel vmm, HomeViewModel hvm,
                         CreateViewModel cvm, EditViewModel evm, StudyViewModel svm) {
        viewManagerModel = vmm;
        homeViewModel = hvm;
        createViewModel = cvm;
        editViewModel = evm;
        studyViewModel = svm;
    }

    @Override
    public void prepareSuccessView(HomeOutputData homeOutputData) {
        final HomeState homeState = homeViewModel.getState();

        homeState.updateIDs(homeOutputData.getIDs());
        homeState.updateTitles(homeOutputData.getTitles());
        homeState.updateDescriptions(homeOutputData.getDescriptions());
        homeState.updateFronts(homeOutputData.getFronts());
        homeState.updateBacks(homeOutputData.getBacks());

        homeViewModel.setState(homeState);
        homeViewModel.firePropertyChanged();
    }

    @Override
    public void switchToCreateView() {
        viewManagerModel.setState(createViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToEditView(String cardSetTitle) {
        // TODO
    }
}
