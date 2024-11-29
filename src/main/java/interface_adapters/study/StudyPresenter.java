package interface_adapters.study;

import interface_adapters.ViewManagerModel;
import interface_adapters.home.HomeViewModel;
import use_cases.study.StudyOutputBoundary;

import java.util.List;

public class StudyPresenter implements StudyOutputBoundary {
    private final HomeViewModel homeViewModel;
    private final StudyViewModel studyViewModel;
    private final ViewManagerModel viewManagerModel;

    public StudyPresenter(ViewManagerModel vmm, StudyViewModel svm, HomeViewModel hvm) {
        viewManagerModel = vmm;
        studyViewModel = svm;
        homeViewModel = hvm;
    }

    @Override
    public void switchToHomeView() {
        final StudyState studyState = studyViewModel.getState();
        studyState.clear();
        studyViewModel.setState(studyState);
        viewManagerModel.setState(homeViewModel.getViewName());
        homeViewModel.firePropertyChanged(); // refreshes the home view
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void moveToPrevCard() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setCurrentIndex(studyState.getCurrentIndex() - 1);
        studyState.setFrontVisible(true);
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void moveToNextCard() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setCurrentIndex(studyState.getCurrentIndex() + 1);
        studyState.setFrontVisible(true);
        studyViewModel.firePropertyChanged();

    }

    @Override
    public void flip() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setFrontVisible(!studyState.getFrontVisible());
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void toggleShuffling() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setCurrentIndex(0);
        studyState.setFrontVisible(true);
        studyState.setShufflingOn(!studyState.getShufflingOn());
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void toggleSorting() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setSortingOn(!studyState.getSortingOn());
        studyState.setFrontVisible(true);
        studyState.setCurrentIndex(0);
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void addToUnknown(String front, String back) {
        final StudyState studyState = studyViewModel.getState();
        studyState.addToUnknownCards(List.of(front, back));
        studyState.pushKnownUnknown(false);
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void addToKnown() {
        final StudyState studyState = studyViewModel.getState();
        studyState.pushKnownUnknown(true);
        studyViewModel.firePropertyChanged();
    }


    @Override
    public void studyUnknown() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setCurrentIndex(0);
        studyState.setFrontVisible(true);
        studyState.setStudyingSorted(true);
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void restart() {
        final StudyState studyState = studyViewModel.getState();
        studyState.setCurrentIndex(0);
        studyState.setFrontVisible(true);
        studyState.setStudyingSorted(false);
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void fullyRestart() {
        studyViewModel.getState().fullyRestart();
        studyViewModel.firePropertyChanged();
    }

    @Override
    public void undo() {
        final StudyState studyState = studyViewModel.getState();
        studyState.popKnownUnknown();
        studyViewModel.firePropertyChanged();
    }
}
