package interface_adapters.home;

import interface_adapters.ViewManagerModel;
import interface_adapters.create.CreateViewModel;
import interface_adapters.edit.EditState;
import interface_adapters.edit.EditViewModel;
import interface_adapters.study.StudyState;
import interface_adapters.study.StudyViewModel;
import use_cases.edit.EditInputData;
import use_cases.home.HomeOutputBoundary;
import use_cases.home.HomeOutputData;
import use_cases.study.StudyInputData;

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
    public void refresh(HomeOutputData homeOutputData) {
        final HomeState homeState = homeViewModel.getState();

        homeState.updateIDs(homeOutputData.getIDs());
        homeState.updateTitles(homeOutputData.getTitles());
        homeState.updateDescriptions(homeOutputData.getDescriptions());
        homeState.updateCards(homeOutputData.getCards());

        homeViewModel.setState(homeState);
        homeViewModel.firePropertyChanged();
    }

    @Override
    public void switchToCreateView() {
        viewManagerModel.setState(createViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToEditView(EditInputData editInputData) {
        final EditState editState = editViewModel.getState();

        editState.setID(editInputData.getID());
        editState.setTitle(editInputData.getTitle());
        editState.setDescription(editInputData.getDescription());
        editState.setCards(editInputData.getCards());

        editViewModel.setState(editState);
        editViewModel.firePropertyChanged("init");
        viewManagerModel.setState(editViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void switchToStudyView(StudyInputData cardSetData) {
        final StudyState studyState = studyViewModel.getState();

        studyState.setTitle(cardSetData.getTitle());
        studyState.setDescription(cardSetData.getDescription());
        studyState.setOriginalCards(cardSetData.getCards());
        studyState.setCurrentIndex(0);

        studyViewModel.setState(studyState);
        studyViewModel.firePropertyChanged("init");

        viewManagerModel.setState(studyViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
