package interface_adapters.study;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeViewModel;
import use_cases.study.StudyOutputBoundary;
import use_cases.study.StudyOutputData;

/**
 * The presenter for our study use case.
 */
public class StudyPresenter implements StudyOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final HomeViewModel homeViewModel;
    private final StudyViewModel studyViewModel;

    public StudyPresenter(ViewManagerModel vmm,
                           HomeViewModel hvm, StudyViewModel svm) {
        viewManagerModel = vmm;
        homeViewModel = hvm;
        studyViewModel = svm;
    }

    @Override
    public void switchToHomeView() {
        viewManagerModel.setState(homeViewModel.getViewName());
        homeViewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(StudyOutputData studyOutputData) {
        final StudyState studyState = studyViewModel.getState();
        studyState.setTitle(studyOutputData.getTitle());
        studyViewModel.setState(studyState);
        studyViewModel.firePropertyChanged();
    }
}
